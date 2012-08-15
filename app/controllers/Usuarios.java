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
import views.html.index;
import models.Curso;
import models.CursoHasUsuario;
import models.Ulima;
import models.Usuario;

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

			// Mostrar la vista principal
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
		return ok(views.html.index.render(userForm));
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

	@SuppressWarnings("finally")
	public static Result login()
	{
		Map<String, String[]> formData = request().body().asFormUrlEncoded();
		String username = formData.get("username")[0];
		String password = formData.get("password")[0];
		
		Usuario user = null;
		
		try
		{
			user = Usuario.getUserByUsername(username);
			// Comprobar password y cargar sesión.
			if (password.equals(user.getPassword()))
			{
				System.out.println("Válido, entrar");
				loadSession(user);
			}
		}
		catch (NullPointerException ex) 
		{
			// Si captura este error, no encontró al usuario en la base de datos, intentaremos
			// crearlo, si no se puede generar el usuario ( por una contraseña o usuario incorrecto que no logre loguearse
			// en Usuario.create ) se lanza una excepción y se ejecuta el finally.

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

		if(user.getPrivilegio() == 3)
		{
			view = ok(views.html.admin.render(user));
		}
		else
		{
			view = ok(views.html.intranet.render(user));
		}

		return view;
	}

	/*
	 * Comprobar formulario y crear nuevo usuario
	 */

	public static Result create()
	{
		Form<Usuario> filledForm = userForm.bindFromRequest();
		// Determinar si los datos del formulario tienen errores
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(filledForm));
		} else {
			// Es usuario --> Loguear
			if (isUser(filledForm.get().username)) 
			{
				return login();
				// No es usuario --> Registrar
			}
			else 
			{
				Usuario user;
				try {
					user = crearDesdeUlima(filledForm.get().getUsername(),filledForm.get().getPassword());
				} catch (Exception e) {
					flash("Error", "Existen problemas con el usuario");
					return redirect(routes.Application.index());
				}
				loadSession(user);
				return redirect(routes.Application.index());
			}
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
		
		// Crear usuario
		usuario.save();
					
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
				
				// Registrando a profesor
				
				/*
				
				Usuario profesor = new Usuario();
				profesor.setNombres(profesor_nombre);
				profesor.setRol(1);
				profesor.setPrivilegio(1);
				profesor.save();
				
				// Crear asociación entre profesor --> curso
				CursoHasUsuario profesorCurso = new CursoHasUsuario();
				profesorCurso.setUsuario(profesor);
				profesorCurso.setCurso(curso);
				profesorCurso.setSeccion(Integer.parseInt(seccion));
				profesorCurso.save();
				
				// Actualizar profesor
				profesor.getCursos().add(profesorCurso);
				
				*/
				
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

}