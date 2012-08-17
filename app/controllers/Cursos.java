package controllers;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;

import views.html.*;
import models.ChatCurso;
import models.Curso;
import models.Mensaje;
import models.MensajeHasReceptor;
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

  /*
   *  Alumno envía nueva pregunta : "Asesoría" -> Mensaje tipo 2
   */

  @BodyParser.Of(play.mvc.BodyParser.Json.class)
  public static Result nuevaAsesoria(String id){

    System.out.println("EN CURSOS : NUEVA ASESORÍA");
    
    RequestBody body = request().body();
    
    System.out.println(body.toString());
    
    JsonNode json = body.asJson();
    
    System.out.println(json.toString());
    
    String mensaje_titulo = json.get("mensaje").get("titulo").toString();
    String mensaje_contenido = json.get("mensaje").get("contenido").toString();
    String mensaje_emisor = json.get("mensaje").get("emisor").toString();
    
    Mensaje mensaje = new Mensaje();
    mensaje.setTitulo(mensaje_titulo);
    mensaje.setContenido(mensaje_contenido);
    mensaje.setEmisor(Usuario.find.ref(Long.parseLong(mensaje_emisor)));
    mensaje.save();
    
    for(int i = 0;i < json.get("mensaje").get("receptores").size();i++){
    	Long receptor_id = Long.parseLong(json.get("mensaje").get("receptores").get(i).getTextValue());
    	MensajeHasReceptor mensajeReceptor = new MensajeHasReceptor();
    	mensajeReceptor.setMensaje(mensaje);
    	mensajeReceptor.setReceptor(Usuario.find.ref(receptor_id));
    	mensajeReceptor.save();
    }
    
    mensaje.save();
    
    return ok("");
  }

  public static Result getProfesorDelCurso(String id){
    System.out.println("QUIERE OBTENER LOS PROFESORES DEL CURSO");
       
    Curso curso = Curso.getCurso(Long.parseLong(id));
    
    JSONArray profesores = new JSONArray();
    
    for(Usuario profesor : curso.getProfesores()){
    	JSONObject json = new JSONObject();
        try {
			json.put("nombres", profesor.getNombres());
			json.put("apellidos", profesor.getApellidos());
			json.put("id",profesor.getId());
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        profesores.put(json);
    }

	  return ok(profesores.toString());
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