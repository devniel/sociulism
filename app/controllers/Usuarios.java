package controllers;

import java.util.List;
import java.util.Map;

import org.h2.expression.ExpressionList;
import org.json.JSONArray;
import org.json.JSONObject;

import play.*;
import play.data.*;
import play.mvc.*;
import play.mvc.Http.Session;
import models.Carrera;
import models.Curso;
import models.CursoHasUsuario;
import models.Facultad;
import models.Ulima;
import models.Universidad;
import models.Usuario;

import views.html.*;

public class Usuarios extends Controller {

	static Form<Usuario> userForm = form(Usuario.class);

	/*
	 * Elegir acción
	 */

	public static Result index()
	{
		// Comprobar si está logueado
		if (!isLogged()) 
		{
			// Mostrar la vista pública
			return ok(views.html.index.render(userForm));
		}
		else
		{
			// Mostrar la vista de usuario logueado
			return show();
		}
	}

	/*
	 * Mostrar la lista de usuarios y formulario para registro/logueo.
	 */

	public static Result all() 
	{
		if(isAdmin()){ // Si es admin
			Usuario usuario = getUserSession();
			return ok(views.html.admin.index.render(usuario));
		}else{ // Si es públic
			return ok(views.html.index.render(userForm));
		}		
	}

	/*
	 *	Determinar si la actual sesión es de un administrador o no
	 */

	public static Boolean isAdmin(){
		Boolean admin = false;

		if(validSessionInfo()){
			if(session("username") == "admin"){
				admin = true;
			}
		}

		return admin;
	}

	/*
	 * Cargar sesión a partir de objeto usuario.
	 */
	
	public static void loadSession(Usuario user)
	{
		String uuid = java.util.UUID.randomUUID().toString();
		session("uuid", uuid);
		session("cuuid", uuid);
		session("username", user.getUsername());
		session("password", user.getPassword());
	}

	/*
	 * Logueo de usuario, se ejecuta cuando se loguean en el portal :
	 * index.scala.html
	 */

	public static Result login()
	{
		Map<String, String[]> formData = request().body().asFormUrlEncoded();
		String username = formData.get("username")[0];
		String password = formData.get("password")[0];
		
		Usuario user = null;

		System.out.println("Usuarion logueando con :" + username + "/" + password);
		
		try
		{
			user = Usuario.getUserByUsername(username);
			
			// Comprobar password y cargar sesión.
			if (password.equals(user.getPassword()))
			{
				switch(user.getRol()){
					// ESTUDIANTE
					case 0:
						// Si el usuario no tiene cursos cargados se lanzará una excepción
						Integer cursos = CursoHasUsuario.find.where().ilike("usuario_id", user.getId().toString()).findList().size();
						user = cargarCursos(user);
						if(cursos == 0)
							user = cargarCursos(user);
						break;
					// PROFESOR
					case 1:
						System.out.println("ES UN PROFESOR");
						break;
					// ADMINISTRADOR
					case 2:
						System.out.println("ES UN ADMINISTRADOR !!!");
				}

				// Cargar sesión
				loadSession(user);
			}
			
		}
		catch (NullPointerException ex) 
		{
			ex.printStackTrace();
			
			// Si captura este error, no encontró al usuario en la base de datos, intentaremos
			// crearlo, si no se puede generar el usuario ( por una contraseña o usuario incorrecto que no logre loguearse
			// en Usuario.create ) se lanza una excepción y se ejecuta el finally.

			// Crear desde mi Ulima
			Usuario newUser = crearDesdeUlima(username,password);
			loadSession(newUser);
		}
		finally
		{
			// Redireccionar a página inicial (con o sin carga de sesión)
			return redirect(routes.Application.index());
		}
	}

	/*
	 * Buscar un usuario por su codigo
	 */

	public static Result show(String codigo)
	{
		return TODO;
	}

	/*
	 * Mostrar usuario de la sesión
	 */

	public static Result show()
	{


		Usuario user = Usuario.getUserByUsername(session("username"));

		// Mostrar Intranet de acuerdo a privilegios

		Result view = null;

		if(user.getPrivilegio() == 2)
		{
			view = ok(views.html.admin.index.render(user));
		}
		else
		{
			view = ok(views.html.usuarios.index.render(user));
		}

		return view;
	}

	/*
	 * Comprobar formulario y crear nuevo usuario
	 * Desde un formulario CONVENCIONAL y no usando los templates de Play Framework
	 */

	public static Result create()
	{

		Map<String, String[]> formData = request().body().asFormUrlEncoded();

	    String nombres = formData.get("usuario.nombres")[0];
	    String apellidos = formData.get("usuario.apellidos")[0];
	    String username = formData.get("usuario.username")[0];
	    String password = formData.get("usuario.password")[0];
	    String email = formData.get("usuario.email")[0];
	    String rol = formData.get("usuario.rol")[0];
	    String privilegio = formData.get("usuario.privilegio")[0];

	    String universidad_id = (formData.get("usuario.universidad") != null)? formData.get("usuario.universidad")[0] : null;
	    String facultad_id = (formData.get("usuario.facultad")!= null)? formData.get("usuario.facultad")[0] : null;
	    String carrera_id = (formData.get("usuario.carrera") != null) ? formData.get("usuario.carrera")[0] : null;

		Usuario usuario = new Usuario();

		// Asignar cuenta
		usuario.setUsername(username);
		usuario.setPassword(password);

		// Asignar datos personales
		usuario.setNombres(nombres);
		usuario.setApellidos(apellidos);
		usuario.setEmail(email);

		// Asignar permisos
		usuario.setRol(Integer.parseInt(rol));
		usuario.setPrivilegio(Integer.parseInt(privilegio));

		// Asignar dependencias
		
		// Asignar universidad
		if(universidad_id != null)
		{
	    	Universidad universidad = Universidad.find.byId(Long.parseLong(universidad_id));
	    	usuario.setUniversidad(universidad);
		}
		
		// Asignar facultad
	    if(facultad_id != null)
	    {
	    	Facultad facultad = Facultad.find.byId(Long.parseLong(facultad_id));
	    	usuario.setFacultad(facultad);
	    }
	    
	    // Asignar carrera
	    if(carrera_id != null){
	    	Carrera carrera = Carrera.find.byId(Long.parseLong(carrera_id));
	    	usuario.setCarrera(carrera);
	    }

	    // Crear usuario
		usuario.save();
	    
	    
	    // Redireccionar a página inicial (con o sin carga de sesión)
	    return redirect(routes.Admin.usuarios());

	}
	
	/*
	 * Cargar cursos de usuario a objeto USUARIO desde MiUlima
	 * siempre y cuando el usuario tenga el rol de Estudiante
	 */
	
	public static Usuario cargarCursos(Usuario _usuario) throws Exception {
		
		if(_usuario.getRol() == 0)
		{
			System.out.println("Entró en cargarCursos ...");

			String userHTML = "";
			
			// Determinar si el usuario existe o no, se lanza una Exception, si pasa la Exception entonces se guarda
			// el contenido html del logueo resultante en userPage
			
			System.out.println("Loguear usuario en MiUlima ...");

			userHTML = Ulima.login(_usuario.getUsername(), _usuario.getPassword());
								
			System.out.println("Obtener cursos de usuario ...");
			// Obtener cursos de usuario
			JSONArray cursos = Ulima.getCourses(userHTML);
			
			System.out.println("Por cada curso, crearlo y agregar profesor en caso no exista ...");

			// Por cada curso, crearlo y agregar profesor en caso no exista
			for(int i=0;i<cursos.length();i++)
			{
				JSONObject node = new JSONObject();
				node = (JSONObject)cursos.get(i);
				
				String codigo = node.getString("codigo");
				String nombre = node.getString("nombre");
				String seccion = node.getString("seccion");
				String profesor_nombre = node.getString("profesor");
				
				Curso curso = Curso.getCursoByCodigo(codigo);
				
				if(curso == null)
				{
					System.out.println("ESTA ENTRANDO ACA ");
					// Crear curso
					curso = new Curso();
					curso.setCodigo(codigo);
					curso.setNombre(nombre);
					curso = Curso.create(curso);
					
					// Se supone que los profesores han sido registrados en el sistema.
					// No tienen cursos asignados
					
					// Buscar profesor
					String[] tokensNombre = profesor_nombre.split(" ");
					String profesor_aprox_apellidos = tokensNombre[0] + " " + tokensNombre[1];
					String profesor_aprox_nombre = tokensNombre[tokensNombre.length - 1];
					
					System.out.println("PRIMERO : --> " + tokensNombre[0] + " " + tokensNombre[1]);
					System.out.println("ULTIMO : --> " + tokensNombre[tokensNombre.length - 1]);
					
					Usuario profesor = Usuario.find.where().ilike("apellidos", "%" + profesor_aprox_apellidos + "%").ilike("nombres", "%" + profesor_aprox_nombre + "%").findUnique();
					
					System.out.println("ENCONTRADO : " + profesor.getNombres() + " , " + profesor.getApellidos());
					
					/*Usuario profesor = new Usuario();
					profesor.setNombres(profesor_nombre);
					profesor.setRol(1);
					profesor.setPrivilegio(1);
					profesor.save();*/
					
					// Crear asociación entre profesor --> curso
					
					// Determinar primero si la asociación ya existe
					CursoHasUsuario profesorCurso = CursoHasUsuario.find.where()
					.ilike("usuario_id",profesor.getId().toString())
					.ilike("curso_id", curso.getId().toString())
					.ilike("seccion", seccion).findUnique();
					
					if(profesorCurso == null){
						profesorCurso = new CursoHasUsuario();
						profesorCurso.setUsuario(profesor);
						profesorCurso.setCurso(curso);
						profesorCurso.setSeccion(Integer.parseInt(seccion));
						profesorCurso.save();
						profesor.getCursos().add(profesorCurso); // Actualizar profesor
					}

					
					// Crear asociación entre estudiante --> curso
					// Determinar primero si la asociación ya existe					
					CursoHasUsuario estudianteCurso = CursoHasUsuario.find.where()
					.ilike("usuario_id",_usuario.getId().toString())
					.ilike("curso_id", curso.getId().toString())
					.ilike("seccion", seccion).findUnique();

					if(estudianteCurso == null){
						estudianteCurso = new CursoHasUsuario();
						estudianteCurso.setUsuario(_usuario);
						estudianteCurso.setCurso(curso);
						estudianteCurso.setSeccion(Integer.parseInt(seccion));
						estudianteCurso.save();	
						_usuario.getCursos().add(estudianteCurso); // Actualizar usuario
					}
				}
			}
	
			_usuario.save();
			return _usuario;
		}
		else
		{
			return _usuario;
		}
	}

	/*
	 * Método para crear un usuario ESTUDIANTE a partir de su usuario y
	 * contraseña de la Universidad de Lima.
	 * 
	 * Parámetros : [Usuario]
	 * Retorna : [Usuario]
	 */

	public static Usuario crearDesdeUlima(String _username, String _password) throws Exception
	{
		
		String userHTML = "";
				
		// Determinar si el usuario existe o no, se lanza una Exception, si pasa la Exception entonces se guarda
		// el contenido html del logueo resultante en userPage
		
		Usuario usuario = new Usuario();
		
		userHTML = Ulima.login(_username, _password);

		System.out.println("GUARDANDO USUARIO ... ");

		// Asignando roles y privilegios por defecto
		usuario.setPrivilegio(0);
		usuario.setRol(0);

		// Asignando universidad
		Universidad universidad = Universidad.find.where().eq("nombre","UNIVERSIDAD DE LIMA").findUnique();
		Facultad facultad = Facultad.find.where().eq("nombre","FACULTAD DE INGENIERÍA DE SISTEMAS").findUnique();

		if(universidad != null && facultad != null){
			usuario.setUniversidad(universidad);
			usuario.setFacultad(facultad);		
		}
		

		// Crear usuario
		usuario.save();

		System.out.println("Obteniendo cursos de usuario ...");
					
		// Obtener cursos de usuario
		JSONArray cursos = Ulima.getCourses(userHTML);
		
		// Por cada curso, crearlo y agregar profesor en caso no exista
		for(int i=0;i<cursos.length();i++)
		{
			JSONObject node = new JSONObject();
			node = (JSONObject)cursos.get(i);
			
			String codigo = node.getString("codigo");
			String nombre = node.getString("nombre");
			String seccion = node.getString("seccion");
			String profesor_nombre = node.getString("profesor");
			
			Curso curso = Curso.getCursoByCodigo(codigo);
			
			if(curso == null)
			{
				// Crear curso
				curso = new Curso();
				curso.setCodigo(codigo);
				curso.setNombre(nombre);
				curso = Curso.create(curso);
			
				// Crear asociación entre estudiante --> curso
				CursoHasUsuario estudianteCurso = new CursoHasUsuario();
				estudianteCurso.setUsuario(usuario);
				estudianteCurso.setCurso(curso);
				estudianteCurso.setSeccion(Integer.parseInt(seccion));
				estudianteCurso.save();
				
				// Actualizar profesor
				usuario.getCursos().add(estudianteCurso);
				
			}
		}

		usuario.save();	
		
		// Crear usuario
		return usuario;
	}

	/*
	 * Determinar si el usuario existe
	 */

	public static boolean isUser(String username) 
	{
		Usuario user = Usuario.getUserByUsername(username);
		if (user == null)
			return false;
		else
			return true;
	}

	/*
	 * Buscar el usuario por id
	 */

	public static Result find(Long id) 
	{
		return TODO;
	}

	/*
	 * Borrar usuario
	 */

	public static Result delete(Long id) 
	{
		return TODO;
	}

	/*
	 * Comprobar si usuario está logueado
	 */

	public static boolean isLogged() 
	{
		if (validSessionInfo()) {
			return true;
		} else {
			session().clear();
			return false;
		}
	}

	/*
	 * Validar usuario y password, si existen y son válidos
	 */

	public static boolean validSessionInfo() 
	{
		String username = session("username");

		String password = session("password");

		System.out.println(username + " " + password + " " + session("uuid") + " " + session("cuuid"));

		if(username == "" || password == "") return false;

		Usuario user = Usuario.getUserByUsername(username);

		if (user == null)
		{
			return false;
		} 
		else if (!session("uuid").toString().equals(session("cuuid").toString())) 
		{
			return false;
		}
		else 
		{
			if (user.getPassword().equals(password))
			{
				return true;
			} 
			else
			{
				return false;
			}
		}

	}

	public static Usuario getUserSession() 
	{
		String username = session("username");
		String password = session("password");
		Usuario user = Usuario.getUserByUsername(username);
		return user;
	}

	public static Result logout() 
	{
		session().clear();
		return redirect(routes.Application.index());
	}

	/*
	 *	From https://github.com/playframework/Play20/blob/master/samples/java/forms/app/controllers/Wizard.java
	 */

}