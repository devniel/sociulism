package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import play.libs.F.*;

import org.codehaus.jackson.*;

import views.html.*;

import models.*;


public class Application extends Controller {
  
  public static Result index() {
    return redirect(routes.Users.index());
  }
  
  /**
   * Display the chat room.
   */
  public static Result chatRoom(String id) {
      if(id == null || id.trim().equals("")) {
          flash("error", "Please choose a valid username.");
          return redirect(routes.Application.index());
      }
      //falta el archivo en classes_managed/veiws.html/catRoom.class
      return ok(chatRoom.render(id));
  }
  /**
   * Handle the chat websocket.
   */
  public static WebSocket<JsonNode> chat(final String id) {
      return new WebSocket<JsonNode>() {
          
          // Called when the Websocket Handshake is done.
          public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
              
              // Join the chat room.
              try { 
                  ChatRoomSociul.join(id, in, out);
              } catch (Exception ex) {
                  ex.printStackTrace();
              }
          }
      };
  }
  
}