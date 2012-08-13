package models;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

// Task.java
@Entity
@Table(name="Usuario")
public class Usuario extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long uid;
	
	@Column(unique=true) 
	@Required
	public String codigo;

	@Required
	public String password;

	public String nombres;
	
	public String apellidos;
	
	public static Finder<Long,Usuario> find = new Finder(Long.class, Usuario.class);

	@OneToMany
    private List<CursoHasUsuario> cursos;
	
	public static List<Usuario> all(){
		//return TODO;
		return find.all();
	}

	public String validate() {
        if(codigo == "" || password == "") {
            return "Invalid email or password";
        }
        return null;
    }
	
	/*
	 * Método para crear un usuario a partir de un objeto
	 * Usuario que debe contener el usuario y la contraseña
	 * del mismo - desde un formulario, por ejemplo.
	 * 
	 * Parámetros : [Usuario]
	 * Retorna : [Usuario]
	 */

	public static Usuario create(Usuario user) throws Exception
	{
		
		String userPage = "";
				
		// Determinar si el usuario existe o no, se lanza una Exception, si pasa la Exception entonces se guarda
		userPage = Ulima.login(user.getCodigo(),user.getPassword());
		
		// Crear usuario
		user.save();
					
		List<CursoInfo> cursos = Ulima.getCourses(userPage);
		
		for (CursoInfo cursoInfo : cursos)
		{
			
			Curso curso = new Curso();
			curso.setCodigo(cursoInfo.getCodigo());
			curso.setNombre(cursoInfo.getNombre());
			
			// Se crea curso o se obtiene el ya existente
			
			curso= Curso.create(curso);
			
			// Agregar a tabla asociativa
			
			CursoHasUsuario asoc = new CursoHasUsuario();
			asoc.setUsuario(user);
			asoc.setCurso(curso);
			asoc.setSeccion(cursoInfo.getSeccion());
			asoc.save();
			
			// Actualizar usuario
			user.getCursos().add(asoc);
		}
		
		user.save();	
		
		// Crear usuario
		return user;
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}

	public static Usuario getUser(Long id){
		Usuario user = find.ref(id);
		return user;
	}

	public static Usuario getUserByCodigo(String codigo){
		Usuario user = find.where().eq("codigo",codigo).findUnique();
		return user;
	}

	/** GETTERS AND SETTERS **/
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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