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
	
	private static final long serialVersionUID = 1L;

	@Id
	public Long mid;
	
	public String contenido;

	public Date fecha;

	@OneToOne(cascade = {CascadeType.ALL})
	private Usuario emisor;
	
	@OneToOne
	private Curso curso;
	
	public static Finder<Long,Mensaje> find = new Finder(Long.class, Mensaje.class);
	
	public Mensaje(String contenido, Usuario emisor, Curso curso) {
		super();
		this.contenido = contenido;
		this.emisor = emisor;
		this.curso = curso;
	}
	
	/* CRUD */
	
	public static List<Mensaje> all(){
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
	
	/* */

	public Usuario getEmisor() {
		return emisor;
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
