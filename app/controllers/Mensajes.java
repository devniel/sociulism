package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Mensaje;
import models.MensajeHasReceptor;
import play.mvc.Result;

import play.*;
import play.data.*;
import play.mvc.*;
import play.mvc.Http.Session;

import views.html.*;

public class Mensajes extends Controller{
	
	 public static Result getPregunta(String id) {
		 
		 Mensaje pregunta = Mensaje.find.ref(Long.parseLong(id));
		 
		 List<Mensaje> respuestas = Mensaje.find.where().eq("mensaje_id", id).findList();

		 return ok("");	 
	 }

	 /*
	  * Que tal nombre ...
	  */

	 public static List<Mensaje> getPreguntasDeUsuarioEnSeccion(Long uid,Long sid){
	 	List<Mensaje> preguntas = 
	 			Mensaje.find.where()
	 			.eq("emisor_id",uid.toString())
	 			.eq("seccion_id", sid.toString())
	 			.eq("tipo","2").findList();
	 	return preguntas;
	 }
	 
	 /*
	  * Obtiene las preguntas que se realizan en toda una sección
	  * Parámetros :
	  * - sid, id de la sección
	  */

	 public static List<Mensaje> getPreguntasDeSeccion(Long sid){
	 	List<Mensaje> preguntas = 
	 			Mensaje.find.where()
	 			.eq("seccion_id", sid.toString())
	 			.eq("tipo","2").findList();  
	 	return preguntas;
	 }

	 /*
	  * Obtiene todas las preguntas enviadas a un determinado usuario
	  * Parámetros
	  * - uid , id del usuario a quién se le envían las preguntas
	  */

	 public static List<Mensaje> getPreguntasRecibidas(Long uid){
	 	List<MensajeHasReceptor> preguntas_recibidas_relacion = 
	 			MensajeHasReceptor.find.where()
	 			.eq("receptor_id",uid.toString())
	 			.findList();
	 	
        List<Mensaje> preguntas_recibidas = new ArrayList<Mensaje>();
        
        for (MensajeHasReceptor pregunta_recibida : preguntas_recibidas_relacion){
         preguntas_recibidas.add(pregunta_recibida.getMensaje());
        }
        
        return preguntas_recibidas;
	 }

}
