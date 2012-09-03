package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.context.annotation.Primary;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="Seccion")

public class Seccion extends Model {
	
	@Id
	public Long Id;
	
	/*
	 * Número de sección
	 */
	
	@PrimaryKeyJoinColumn
	@Column(name = "seccion")
	public Integer seccion;
	
	/*
	 * Profesor , relación 1-1
	 */
	
	@OneToOne
	public Usuario profesor;
	
	/*
	 * Curso de la sección 
	 */
	
	@ManyToOne
	public Curso curso;
	
	/*
	 * Estudiantes y/o auxiliares de la sección
	 */
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<SeccionHasUsuario> usuarios;
	
	/*
	 * Mensajes de la sección
	 */
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Mensaje> mensajes;
	
	
	public static Finder<Long,Seccion> find = new Finder(Long.class, Seccion.class);

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public Usuario getProfesor() {
		return profesor;
	}

	public void setProfesor(Usuario profesor) {
		this.profesor = profesor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<SeccionHasUsuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<SeccionHasUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	/*
	 *	Obtener preguntas realizadas en sección
	 */

	public List<Mensaje> getPreguntas(){
		return Mensaje.find.where().eq("seccion_id",this.getId().toString()).eq("tipo","2").findList();
	}
	
}