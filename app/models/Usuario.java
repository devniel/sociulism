package models;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

import org.json.*;

import com.avaje.ebean.*;
import com.avaje.ebean.validation.NotNull;


@Entity
@Table(name="Usuario")
public class Usuario extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long Id;
	
	@Column(unique=true) 
	@Required
	@NotNull
	public String username;

	@Required
	@NotNull
	public String password;
	
	public String nombres;
	
	public String apellidos;
	
	/*
	 * Privilegios :
	 * 0 -> Estudiante
	 * 1 -> Profesor
	 * 2 -> Secretaria
	 */
	@NotNull
	@Column(columnDefinition="Integer default '0'")
	public Integer privilegio;
	
	/*
	 * Rol : 
	 * 0 -> Estudiante
	 * 1 -> Profesor
	 * 2 -> Secretaria
	 */
	
	@NotNull
	@Column(columnDefinition="Integer default '0'")
	public Integer rol;
	
	public static Finder<Long,Usuario> find = new Finder(Long.class, Usuario.class);

	@OneToMany
	public List<CursoHasUsuario> cursos;
	
	@ManyToOne
	public Universidad universidad;
	
	@ManyToOne
	public Carrera carrera;
	
	@ManyToOne
	public Facultad facultad;
	
	/*
	 * Obtener todos los usuarios
	 *  de la aplicación
	 */
	
	public static List<Usuario> all(){
		//return TODO;
		return find.all();
	}
	
	/*
	 * Eliminar usuario en base
	 * a su Id
	 */

	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	/*
	 * Obtener usuario en base
	 * a su Id
	 */

	public static Usuario getUser(Long id){
		Usuario user = find.ref(id);
		return user;
	}

	/*
	 *	Obtener usuario por código 
	 *	de universidad 
	 */
	
	public static Usuario getUserByUsername(String username){
		Usuario user = find.where().eq("username",username).findUnique();
		return user;
	}
	

	/*
	 * GETTERS AND SETTERS 
	 */
	
	public Universidad getUniversidad() {
		return universidad;
	}

	public Integer getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(Integer privilegio) {
		this.privilegio = privilegio;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CursoHasUsuario> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoHasUsuario> cursos) {
		this.cursos = cursos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	
	
	
}