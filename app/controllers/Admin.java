package controllers;

import java.util.List;
import java.util.Map;

import org.h2.expression.ExpressionList;

import play.*;
import play.data.*;
import play.mvc.*;
import play.mvc.Http.Session;
import views.html.index;

import models.*;
import views.html.admin.*;

public class Admin extends Controller {


	static Form<Usuario> usuarioForm = form(Usuario.class);
	static Form<Facultad> facultadForm = form(Facultad.class);
	static Form<Carrera> carreraForm = form(Carrera.class);
	static Form<Universidad> universidadForm = form(Universidad.class);


	public static Result facultades()
	{
		System.out.println("Usuario quiere crear facultad");

		// Una facultad requiere de una universidad, en el formulario aparecerá
		// la opción de escoger universidad de una lista, por eso se le pasa los objetos Universidad.
		return ok(facultades.render(Universidad.find.all()));
	}

	public static Result cursos()
	{

		System.out.println("Alguien quiere crear cursos");

		return ok(cursos.render());
	}

	/*public static Result carreras()
	{	
		System.out.println("Usuario quiere crear carreras");
		return ok(views.html.carreras.render(carreraForm));
	}

	public static Result universidades()
	{
		System.out.println("Usuario quiere crear universidades");
		return ok(views.html.universidades.render(universidadForm));
	}*/

	public static Result usuarios()
	{
		System.out.println("Usuario quiere crear usuarios");
		return ok(usuarios.render(Universidad.find.all(),Facultad.find.all(),Carrera.find.all(),Usuario.find.all()));
	}

	/*
	 *	FACULTADES
	 */


}
