package models;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="Mensaje")
public class Mensaje extends Model{
	
	/*
	 * Usando Single Table Inheritance, una misma tabla para varios modelos.
	 * Mensaje puede ser de diferentes modelos. En este caso se especifica en el Tipo.
	 */

	/*
	Mensaje 
	-----------------------		
		+id			
		+Usuario_id		
		~Usuario_Destino	
		~Curso_Destino		
		~Facultad_Destino	
		~Universidad_Destino	
		-contenido				
		-fecha				
	------------------------
	*/
	
	private static final long serialVersionUID = 1L;

	@Id
	public Long Id;
	
	/*
	 * Título del mensaje
	 * Si es :
	 * Pregunta -> OBLIGATORIO
	 * Otros -> NO NECESARIO
	 */
	
	public String titulo;
	
	/*
	 * Contenido del mensaje
	 * OBLIGATORIO
	 */
	
	@Column(length=4000, nullable=false)
	public String contenido;
	
	/*
	 * Tipo de mensaje:
	 * 0 -> Comentario o Respuesta
	 * 1 -> Notificación
	 * 2 -> Pregunta
	 */
	public Integer tipo;

	/*
	 * Fecha de envío del mensaje
	 */

	public Date fecha;
	
	/*
	 * Comentarios o Respuestas - Recursivo
	 */
	
	@OneToMany
	public List<Mensaje> mensajes;
	
	@ManyToOne
	public Mensaje mensaje;
	

	/*
	 * USUARIO emisor, quien envía el mensaje
	 */

	@OneToOne(cascade = {CascadeType.ALL})
	private Usuario emisor;

	/*
	 * USUARIO receptor, quien recibe el mensaje - NULL
	 */

	@OneToMany(cascade = {CascadeType.ALL})
	private List<MensajeHasReceptor> receptores;
	
	/*
	 * SECCIÓN (CURSO) a donde se envía el mensaje - chat - NULL
	 */

	@OneToOne
	private Seccion seccion;

	/*
	 * FACULTAD a donde se envía el mensaje - intranet - NULL
	 */

	@OneToOne
	private Facultad facultad;

	/*
	 * UNIVERSIDAD a donde se envía el mensaje - portal - NULL
	 */

	@OneToOne
	private Universidad universidad;
	
	/*
	 * CARRERA a donde se envía el mensaje - intranet también
	 */

	@OneToOne
	private Carrera carrera;
	
	/*
	 * FINDER
	 */
	
	public static Finder<Long,Mensaje> find = new Finder(Long.class, Mensaje.class);

	
	/*
	 * CURSO
	 */
	
	public Mensaje() {
		super();
		this.fecha = new Date();
		// TODO Auto-generated constructor stub
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	/* 
		GETTERS AND SETTERS
	*/
		
	
	
	public Usuario getEmisor() {
		return emisor;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public List<MensajeHasReceptor> getReceptores() {
		return receptores;
	}

	/*
	 * Obtener el id de receptores en formato de texto : "1,2,3,4"
	 */

	public String getStringReceptores(){
		String receptores = "";

		List<MensajeHasReceptor> mensaje_receptores = this.getReceptores();
		int i = 1;
		for(MensajeHasReceptor mensaje_receptor : mensaje_receptores){
			if(i % 2 == 0) receptores += ",";
			receptores += mensaje_receptor.getReceptor().getId().toString();
			i++;
		}

		return receptores;
	}

	public void setReceptores(List<MensajeHasReceptor> receptores) {
		this.receptores = receptores;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public Universidad getUniversidad() {
		return universidad;
	}

	public void setUniversidad(Universidad universidad) {
		this.universidad = universidad;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getFechaFormateada(){
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEEEEE dd 'de' MMMM 'del' yyyy, 'a las' HH:mm");
		String _fecha = "";
		if(fecha != null) _fecha = sdf.format(fecha);
		return _fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
