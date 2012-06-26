package models;

import play.mvc.*;
import play.mvc.Http.Session;
import play.mvc.Controller;
import play.libs.*;
import play.libs.F.*;

import akka.util.*;
import akka.actor.*;
import akka.dispatch.*;
import static akka.pattern.Patterns.ask;

import org.codehaus.jackson.*;
import org.codehaus.jackson.node.*;


import java.util.*;

import static java.util.concurrent.TimeUnit.*;

/**
 * A chat room is an Actor.
 */
public class ChatCurso extends UntypedActor {
    
    // Default room.
    static ActorRef defaultRoom = Akka.system().actorOf(new Props(ChatCurso.class));
    
    // Create a Robot, just for fun.
    static {
        new Robot(defaultRoom);
    }
   
    /**
     * Join the default room.
     */
    public static void join(final String username, final String curso, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) throws Exception{
        
        // Send the Join message to the room
        String result = (String)Await.result(ask(defaultRoom,new Join(username,curso,out), 1000),Duration.create(1, SECONDS));
        
        if("OK".equals(result)) {
            
            // For each event received on the socket,
            in.onMessage(new Callback<JsonNode>() {
               public void invoke(JsonNode event) {
            	   
            	   // This method receive the messages from users
            	   
                   String message = event.get("text").asText();
                   String code = username;
                   // Send a Talk message to the room.
            	   // event.get("text").asText()
                   
                   Mensaje mensaje = new Mensaje(message, Usuario.getUserByCodigo(code), Curso.getCursoByCodigo(curso));
                   defaultRoom.tell(mensaje);
                   
                   
               } 
            });
            
            // When the socket is closed.
            in.onClose(new Callback0() {
               public void invoke() {
                   
                   // Send a Quit message to the room.
                   defaultRoom.tell(new Quit(username));
                   
               }
            });
            
        } else {
            
            // Cannot connect, create a Json error.
            ObjectNode error = Json.newObject();
            error.put("error", result);
            
            // Send the error to the socket.
            out.write(error);
            
        }
        
    }
    
    // Members of this room.
    Map<String, WebSocket.Out<JsonNode>> members = new HashMap<String, WebSocket.Out<JsonNode>>();
    Map<WebSocket.Out<JsonNode>, String> usuariosPorCurso = new HashMap<WebSocket.Out<JsonNode>, String>();
    
    // onReceive Implementation

    public void onReceive(Object message) throws Exception {
        
        // Alguien quiere loguearse
        if(message instanceof Join) {
            
            // Received a Join message
            Join join = (Join)message;
            
            // Check if this username is free.
            if(members.containsKey(join.username)) {
                getSender().tell("This username is already used");
            } else {
                members.put(join.username, join.channel);
                usuariosPorCurso.put(join.channel,join.curso);
                notifyAll("join", join.username, "has entered the room");
                getSender().tell("OK");
            }
            
        } else if(message instanceof Mensaje)  {
            
            // Received a Talk message
            Mensaje msg = (Mensaje)message;
            
            notifyAll("talk",msg.getEmisor().getCodigo() , msg.getContenido());
            
        } else if(message instanceof Quit)  {
            
            // Received a Quit message
            Quit quit = (Quit)message;
            
            members.remove(quit.username);
            
            notifyAll("quit", quit.username, "has leaved the room");
        
        } else {
            unhandled(message);
        }
        
    }
    
    // Send a Json event to all members
    public void notifyAll(String kind,String user, String text) {
    	
    	WebSocket.Out<JsonNode> chan = members.get(user);
    	String curso = usuariosPorCurso.get(chan);
    	
        for(WebSocket.Out<JsonNode> channel: members.values()) {
            
            ObjectNode event = Json.newObject();
            event.put("kind", kind);
            event.put("user", user);
            event.put("message", text);
            
            
            ArrayNode m = event.putArray("members");
            for(String u: members.keySet()) {
            	
            	if(usuariosPorCurso.get(members.get(u)).equals(curso)) m.add(u);
            }
            
            String cid = usuariosPorCurso.get(channel);
            
            System.out.println("CID --> " + cid);
            
            System.out.println(cid + " == " + curso + " --> " + (cid.equals(curso)));
            
            if(cid.equals(curso))channel.write(event);
        }
    }
    
    // -- Messages
    
    public static class Join {
        
        final String username;
        final String curso;
        final WebSocket.Out<JsonNode> channel;
        
        public Join(String username, String curso, WebSocket.Out<JsonNode> channel) {
            this.username = username;
            this.curso = curso;
            this.channel = channel;
        }
        
    }
    
    public static class Talk {
        
        final String username;
        final String text;
        
        public Talk(String username, String text) {
            this.username = username;
            this.text = text;
        }
        
    }
    
    public static class Quit {
        
        final String username;
        
        public Quit(String username) {
            this.username = username;
        }
        
    }
    
}