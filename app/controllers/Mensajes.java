package controllers;

import java.util.List;

import models.Mensaje;
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

}
