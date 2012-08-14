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

public class Facultades extends Controller {

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

}