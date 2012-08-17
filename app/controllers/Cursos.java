package controllers;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.*;
import play.mvc.*;

import views.html.*;
import models.ChatCurso;
import models.Curso;
import models.Usuario;

public class Cursos extends Controller {
  
  public static Result index() {
    return ok(views.html.curso.index.render(null,null));
  }

  public static Result show(String id){

  	Usuario usuario = Usuarios.getUserSession();

  	Curso curso = Curso.getCurso(Long.parseLong(id));
  	
  	List<Usuario> profesores = curso.getProfesores();
  	
  	System.out.println("PROFESORES DEL CURSO: ");
  	
  	for (Usuario profesor : profesores) {
  		System.out.println(profesor.getNombres() + " --- " + profesor.getApellidos());
	   }

  	return ok(views.html.curso.index.render(usuario,curso));
  }

  /*
  * CREAR CURSO
  */
  public static Result create(){
      Map<String, String[]> formData = request().body().asFormUrlEncoded();
      String codigo = formData.get("codigo")[0];
      String nombre = formData.get("nombre")[0];
      
      Curso curso = new Curso();
      curso.setNombre(nombre);
      curso.setCodigo(codigo);
      curso.save();
      
      // Redireccionar a página inicial (con o sin carga de sesión)
      return redirect(routes.Admin.cursos());
  }

  /*
   * Asesoría del cursos
   */

  public static Result asesoria(String id){

    Usuario usuario = Usuarios.getUserSession();

    Curso curso = Curso.getCurso(Long.parseLong(id));

    return ok(views.html.curso.asesoria.render(usuario,curso));

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