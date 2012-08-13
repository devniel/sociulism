package models;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	public Long mid;
	
	public String contenido;

	/*
	 * Fecha de envío del mensaje
	 */

	public Date fecha;

	/*
	 * USUARIO emisor, quien envía el mensaje
	 */

	@OneToOne(cascade = {CascadeType.ALL})
	private Usuario emisor;

	/*
	 * USUARIO receptor, quien recibe el mensaje - NULL
	 */

	@OneToOne
	private Usuario receptor;
	
	/*
	 * CURSO a donde se envía el mensaje - chat - NULL
	 */

	@OneToOne
	private Curso curso;

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
	 * CONSTRUCTOR
	 */
	
	public Mensaje(String contenido, Usuario emisor, Curso curso)
	{
		super();
		this.contenido = contenido;
		this.emisor = emisor;
		this.curso = curso;
	}
	
	/*
	 * CURSO
	 */
	
	public static List<Mensaje> all()
	{
		//return TODO;
		return find.all();
	}

	public static Mensaje create(Mensaje mensaje){
		mensaje.save();
		return mensaje;
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}

	public static Mensaje getMensaje(Long id){
		Mensaje msg = find.ref(id);
		return msg;
	}
	
	/* 
		GETTERS AND SETTERS
	*/
	
	

	public Usuario getEmisor() {
		return emisor;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
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

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
