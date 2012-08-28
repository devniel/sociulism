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
	public List<Seccion> secciones;
	
	public String nombre;

	public static Finder<Long,Curso> find = new Finder(Long.class, Curso.class);
	
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
	
	

	public List<Seccion> getSecciones() {
		return secciones;
	}


	public void setSecciones(List<Seccion> secciones) {
		this.secciones = secciones;
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

	public List<Usuario> getProfesores(){
		// 1 --> Rol Profesor

		List<Usuario> profesores = Usuario.find.join("cursos")
        .where()
        .eq("curso_id",this.Id)
        .eq("rol", "1")
        .findList();
		
		return profesores;
	}

	public Long getId() {
		return Id;
	}



	public void setId(Long id) {
		Id = id;
	}



	public String getCodigo() {
		return codigo;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/** GETTERS AND SETTERS **/

	
	
	

}