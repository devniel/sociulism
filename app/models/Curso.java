package models;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

//Task.java
@Entity
@Table(name="Curso")
public class Curso extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long Id;
	
	@Column(unique=true) 
	public String codigo;
	
	@OneToMany
	private List<CursoHasUsuario> usuarios;
	
	@OneToMany
	private List<Mensaje> mensajes;
	
	@OneToMany
	private List<Enlace> enlaces;
	
	public String nombre;

	public static Finder<Long,Curso> find = new Finder(Long.class, Curso.class);

	public static List<Curso> all(){
		//return TODO;
		return find.all();
	}
	
	/*
	 * Agrega un nuevo curso a la base de datos
	 * determinando si este existe o no.
	 */

	public static Curso create(Curso curso)
	{
		
		Curso cursoAux = getCursoByCodigo(curso.getCodigo());
		
		if(cursoAux == null)
		{
			curso.save();
		}
		else
		{
			curso = cursoAux;
		}
		
		return curso;
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}

	public static Curso getCurso(Long id){
		Curso curso = find.ref(id);
		return curso;
	}

	public static Curso getCursoByCodigo(String codigo){
		Curso curso = find.where().eq("codigo",codigo).findUnique();
		return curso;
	}

	/** GETTERS AND SETTERS **/
	
	
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<CursoHasUsuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<CursoHasUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Enlace> getEnlaces() {
		return enlaces;
	}

	public void setEnlaces(List<Enlace> enlaces) {
		this.enlaces = enlaces;
	}
	
	

	

}