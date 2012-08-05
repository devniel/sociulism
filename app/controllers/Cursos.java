package controllers;

import java.rmi.server.SocketSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.*;
import play.mvc.*;
import play.mvc.BodyParser.Json;
import play.mvc.Http.RequestBody;
import play.libs.Comet;

import views.html.*;
import models.ChatCurso;
import models.Curso;
import models.Usuario;

public class Cursos extends Controller {
	
 public static List<Comet> sockets = new ArrayList<Comet>();
  
  public static Result index() {
    return ok(views.html.curso.render(null,null));
  }

  /**
   *  Show the content of the Course
   */

  public static Result show(Long id){

  	Usuario usuario = Users.getUserSession();

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



  public static Result comet(String id) {	  
  	Comet comet = new Comet("parent.cometMessage"){
  		public void onConnected() {}
  	};
  	sockets.add(comet);
    
    return ok(comet);
  };
  
  @BodyParser.Of(play.mvc.BodyParser.Json.class)
  public static Result cometMessage(String id) {

    // MESSAGE
    //String message = request().body().asFormUrlEncoded().get("message")[0];
    // USER UID
    //String uid = request().body().asFormUrlEncoded().get("uid")[0];
    // COURSE CID
   // String cid = request().body().asFormUrlEncoded().get("cid")[0];

    RequestBody body = request().body();
    
    JsonNode json = body.asJson();

    System.out.println(json.toString());

   // System.out.println("MENSAJE --> " + message);
    System.out.println("HOLA");
    System.out.println("TAMAnho : " + sockets.size());
    for (Comet socket : sockets) {
      socket.sendMessage(json.toString());
    }
	  return ok();

     
     /*Usuario usuario = Usuario.getUserByCodigo(uid);
     Curso curso = Curso.getCursoByCodigo(cid);
     
     // Send a Talk message to the room.
     Mensaje mensaje = new Mensaje(message, usuario, curso);
    
     if(malasPalabras.esMalaPalabra(message)){
       mensaje.setContenido("******");
     }else{
       System.out.println("NO ES MALA PALABRA");
       System.out.println(mensaje.getContenido());
       // GET URLS
       String regex = "(http://|https://|ftp://|file://|www)[-a-z-A-Z0-9+&@#/%?=~_|!:,.;]*[-a-z-A-Z0-9+&@#/%=~_|]";

       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(mensaje.getContenido());
        
       if(matcher.find()){
        String url = matcher.group();
        
        if(url.indexOf("www") == 0){
          url = "http://" + url;
        }
        
        Enlace enlace;
        
        try {
          enlace = Enlace.createFromURL(url);
          enlace.setEmisor(usuario);
          enlace.setCurso(cursoChat);
          enlace.save();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
       }
       mensaje.save();
      }
           
      defaultRoom.tell(mensaje);*/
  };
}