package controllers;

import org.codehaus.jackson.JsonNode;

import play.*;
import play.mvc.*;

import views.html.*;
import models.ChatCurso;
import models.Curso;
import models.Usuario;

public class Cursos extends Controller {
  
  public static Result index() {
    return ok(views.html.curso.render(null,null));
  }

  public static Result show(Long id){

  	Usuario usuario = Usuarios.getUserSession();

  	Curso curso = Curso.getCurso(id);

    System.out.println("# MENSAJES --> " + curso.getMensajes().size());

  	return ok(views.html.curso.render(usuario,curso));
  }
  
  
  /**
   * Handle the chat websocket.
   */
  public static WebSocket<JsonNode> chat(final String id,final String codigo) {
      return new WebSocket<JsonNode>() {
          
          // Called when the Websocket Handshake is done.
          public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
              
              // Join the chat room.
              try { 
                  ChatCurso.join(codigo, id, in, out);
              } catch (Exception ex) {
                  ex.printStackTrace();
              }
          }
      };
  }
  
}