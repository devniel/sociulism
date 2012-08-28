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

	/*
	 *	CURSOS
	 */


	/*
	 *	FACULTADES
	 */


	/*
	 *	USUARIOS
	 */



	public static Result facultades()
	{
		// Una facultad requiere de una universidad, en el formulario aparecerá
		// la opción de escoger universidad de una lista, por eso se le pasa los objetos Universidad.
		return ok("");
	}

	public static Result cursos()
	{

		return ok("");
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
		return ok("");
	}

	/*
	 *	FACULTADES
	 */


}
