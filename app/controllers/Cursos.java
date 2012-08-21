package controllers;

import java.util.ArrayList;
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
import models.CursoHasUsuario;
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


    Result view = null;

    switch(usuario.getRol()){
    	// Usuario común
      case 0:
        // Select * from mensaje where emisor_id = 2 and curso_id = 1;
        List<Mensaje> preguntas_enviadas = Mensaje.find.where().eq("emisor_id",usuario.getId().toString()).eq("curso_id", curso.getId().toString()).findList();
        view = ok(views.html.curso.asesoria.render(usuario,curso,preguntas_enviadas));
        break;
        // Profesor
      case 1:
        List<MensajeHasReceptor> preguntas_recibidas_relacion = MensajeHasReceptor.find.where().eq("receptor_id",usuario.getId().toString()).findList();
        
        List<Mensaje> preguntas_recibidas = new ArrayList<Mensaje>();
        
        for (MensajeHasReceptor pregunta_recibida : preguntas_recibidas_relacion) {
			   preguntas_recibidas.add(pregunta_recibida.getMensaje());
		    }
        
        view = ok(views.html.curso.profesor.asesoria.render(usuario,curso,preguntas_recibidas));
        break;
        // Administrador
      /*case 2:
        view  = ok(views.html.curso.profesor.asesoria.render(usuario,curso,null));*/
    }

    return view;

  }

  /*
   *  Alumno envía nueva pregunta : "Asesoría" -> Mensaje tipo 2
   *  Para un mensaje en asesoría se debe guardar las siguientes propiedades
   *  - emisor_id
   *  - curso_id
   *  - universidad_id
   *  - facultad_id
   *  - carrera_id
   *  - receptor_id [ asociación ]
   */

  @BodyParser.Of(play.mvc.BodyParser.Json.class)
  public static Result nuevaAsesoria(String id){
    
    RequestBody body = request().body();
    
    JsonNode json = body.asJson();
    
    String mensaje_titulo = json.get("mensaje").get("titulo").toString();
    String mensaje_contenido = json.get("mensaje").get("contenido").toString();
    String mensaje_emisor = json.get("mensaje").get("emisor").toString();

    String curso_id = json.get("mensaje").get("curso").toString();
    String curso_seccion = json.get("mensaje").get("seccion").toString();

    Mensaje mensaje = new Mensaje();
    mensaje.setTipo(2); // Tipo de pregunta.
    mensaje.setTitulo(mensaje_titulo);
    System.out.println("CONTENIDO ----> " + mensaje_contenido.length());
    mensaje.setContenido(mensaje_contenido);
    mensaje.setEmisor(Usuario.find.ref(Long.parseLong(mensaje_emisor)));
    mensaje.setCurso(Curso.find.ref(Long.parseLong(curso_id)));
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

  /*
   *  Obtiene todos los profesores del curso determinado por Id
   *
   */

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

  /*
   *  Obtener la pregunta determinada y cargar en la interfaz de asesorías del profesor
   */

  public static Result getPregunta(String cid, String pid){

    Curso curso = Curso.getCurso(Long.parseLong(cid));
    Mensaje pregunta = Mensaje.find.ref(Long.parseLong(pid));
    Usuario usuario = Usuarios.getUserSession();

    Result view = ok(views.html.curso.profesor.pregunta.render(usuario,curso,pregunta));
    return view;
  }

  /*
   *
   */

  @BodyParser.Of(play.mvc.BodyParser.Json.class)
  public static Result postRespuesta(String cid, String pid){

    Curso curso = Curso.find.ref(Long.parseLong(cid));
    Mensaje pregunta = Mensaje.find.ref(Long.parseLong(pid));
    String seccion = json.get("respuesta").get("seccion").toString();


    RequestBody body = request().body();
    
    JsonNode json = body.asJson();
    
    String respuesta_contenido = json.get("respuesta").get("contenido").toString();
    String respuesta_emisor = json.get("respuesta").get("emisor").toString();

    Mensaje respuesta = new Mensaje();
    respuesta.setTipo(0); // Tipo de respuesta o comentario.
    System.out.println("CONTENIDO ----> " + respuesta_contenido.length());
    respuesta.setContenido(respuesta_contenido);
    respuesta.setEmisor(Usuario.find.ref(Long.parseLong(respuesta_emisor)));
    respuesta.setMensaje(Mensaje.find.ref(Long.parseLong(pid)));
    respuesta.setCurso(Curso.find.ref(Long.parseLong(cid)));
    respuesta.save();
    
    /*for(int i = 0;i < json.get("mensaje").get("receptores").size();i++){
      Long receptor_id = Long.parseLong(json.get("mensaje").get("receptores").get(i).getTextValue());
      MensajeHasReceptor mensajeReceptor = new MensajeHasReceptor();
      mensajeReceptor.setMensaje(mensaje);
      mensajeReceptor.setReceptor(Usuario.find.ref(receptor_id));
      mensajeReceptor.save();
    }
    
    mensaje.save();*/
    
    return ok("");

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