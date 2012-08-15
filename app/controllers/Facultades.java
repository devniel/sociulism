package controllers;

import java.util.List;
import java.util.Map;

import models.Facultad;
import models.Mensaje;
import models.Usuario;

import org.codehaus.jackson.JsonNode;
import org.h2.expression.ExpressionList;

import play.*;
import play.data.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;

import models.*;

public class Facultades extends Controller {

	static Form<Facultad> facultadForm = form(Facultad.class);

	/*
	 * Elegir acción
	 */

	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result nuevoMensaje(String id)
	{
		System.out.println("EN FACULTADES : NUEVO MENSAJE");
		
		RequestBody body = request().body();
		
		System.out.println(body.toString());
		
		JsonNode json = body.asJson();
		
		System.out.println(json.toString());
		
		String uid = json.get("id").toString();
		String contenido = json.get("mensaje").toString();
		
		/*
		 * Obtener facultad
		 */
		
		Facultad facultad = Facultad.find.byId(Long.parseLong(id));
		
		/*
		 * Crear mensaje
		 */
		
		Mensaje mensaje = new Mensaje();
		mensaje.setEmisor(Usuario.getUser(Long.parseLong(id)));
		mensaje.setContenido(contenido);
		mensaje.setFacultad(facultad);
		mensaje.save();
		
		return ok(json.toString());		
	}

	/*
	 * WITH JSON
	 */

	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result createWithJson(){

		System.out.println("EN FACULTADES : NUEVO MENSAJE");
		
		RequestBody body = request().body();
		
		System.out.println(body.toString());
		
		JsonNode json = body.asJson();
		
		System.out.println(json.toString());
		
		String nombre = json.get("facultad.nombre").toString();

		String universidad_id = json.get("facultad.universidad").toString();
		
		/*
		 * Obtener facultad
		 */
		
		Universidad universidad = Universidad.find.byId(Long.parseLong(universidad_id));

		Facultad facultad = new Facultad();
		facultad.setNombre(nombre);
		facultad.setUniversidad(universidad);
		facultad.save();

		return ok("");
	}

	/*
	 * WITHOUT JSON
	 */

	public static Result create(){
		Map<String, String[]> formData = request().body().asFormUrlEncoded();
	    String nombre = formData.get("nombre")[0];
	    String universidad_id = formData.get("universidad_id")[0];
	    
	    Universidad universidad = Universidad.find.byId(Long.parseLong(universidad_id));

		Facultad facultad = new Facultad();
		facultad.setNombre(nombre);
		facultad.setUniversidad(universidad);
		facultad.save();
	    
	    
	    // Redireccionar a página inicial (con o sin carga de sesión)
	    return redirect(routes.Admin.facultades());
	}

}