package models;

import play.mvc.*;
import play.mvc.Http.Session;
import play.libs.*;
import play.libs.F.*;
import utils.Diccionario;

import akka.util.*;
import akka.actor.*;
import akka.dispatch.*;
import static akka.pattern.Patterns.ask;

import org.codehaus.jackson.*;
import org.codehaus.jackson.node.*;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;

import static java.util.concurrent.TimeUnit.*;

/**
 * A chat room is an Actor.
 */
public class ChatCurso extends UntypedActor {
    
    // Default room.
    static ActorRef defaultRoom = Akka.system().actorOf(new Props(ChatCurso.class));
    
    // Cargar diccionarios
    static Diccionario malasPalabras;
    
    public ChatCurso() {
		super();
		malasPalabras = new Diccionario(new File(".").getAbsolutePath() + "/app/resources/lisuras.txt");
	}

	// Create a Robot, just for fun.
    static {
        new Robot(defaultRoom);
    }
   
    /**
     * Join the default room.
     * @throws Exception 
     */
    public static void join(final String username, final String curso, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
        
        // Send the Join message to the room
        String result = null;
        
		try {
			result = (String)Await.result(ask(defaultRoom,new Join(username,curso,out), 5000),Duration.create(200, SECONDS));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if("OK".equals(result)) {
            
            // For each event received on the socket,
            in.onMessage(new Callback<JsonNode>() {
               public void invoke(JsonNode event) {
            	   
            	   // This method receive the messages from users
            	   
                   String message = event.get("text").asText();
                   String code = username;
                   
                   Usuario usuario = Usuario.getUserByCodigo(code);
                   Curso cursoChat = Curso.getCursoByCodigo(curso.toString());
                   
                   // Send a Talk message to the room.
                   Mensaje mensaje = new Mensaje(message, usuario, cursoChat);
                  
                   if(malasPalabras.esMalaPalabra(message)){
                	   mensaje.setContenido("******");
                   }else{
                	   System.out.println("NO ES MALA PALABRA");
                	   System.out.println(mensaje.getContenido());
                	   // GET URLS
	                	String regex = "(http://|https://|ftp://|file://|www)[-a-z-A-Z0-9+&@#/%?=~_|!:,.;]*[-a-z-A-Z0-9+&@#/%=~_|]";
	
		               	Pattern pattern = Pattern.compile(regex);
		               	Matcher matcher = pattern.matcher(mensaje.getContenido());
		               	Enlace enlace = null;
		               	
	               		if(matcher.find()){
	               			String url = matcher.group();
	               			
	               			if(url.indexOf("www") == 0){
	               				url = "http://" + url;
	               			}
	               			
	               			
	               			
							try {
								enlace = Enlace.createFromURL(url);
								enlace.setEmisor(usuario);
								enlace.setCurso(cursoChat);
								enlace.save();
                                mensaje.setEnlace(enlace); 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	               		}

	                	mensaje.save();
	                	
	                	if(enlace != null) enlace.setMensaje(mensaje);
                   }
                   
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
            if(members.containsKey(join.usuario.getCodigo())) {

                getSender().tell("Este nombre de usuario ya está siendo usado en una sala de chat");

            } else {
                
                members.put(join.usuario.getCodigo(), join.channel);
                usuariosPorCurso.put(join.channel,join.curso.getCodigo());

                notifyJoin(join.usuario, join.curso);

                getSender().tell("OK");

            }
            
        } else if(message instanceof Mensaje)  {
            
            // Received a Talk message
            Mensaje msg = (Mensaje)message;
            
            //notifyAll("talk",msg.getEmisor().getCodigo() , msg.getContenido());

            notifyMessage(msg);
            
        } else if(message instanceof Quit)  {
            
            // Received a Quit message
            Quit quit = (Quit)message;
            
            members.remove(quit.username);
            
            notifyAll("quit", quit.username, "ha dejado el cubículo.");
        
        } else {
            unhandled(message);
        }
        
    }
    
    // Send a Json event to all members WHEN a new user enters to the room

    public void notifyJoin(Usuario user,Curso curso){

        WebSocket.Out<JsonNode> wsUser = members.get(user.getCodigo());
        String cid = curso.getCodigo();
        Gson gson = new Gson();

         for(WebSocket.Out<JsonNode> channel: members.values()) {
            
            ObjectNode event = Json.newObject();
            event.put("kind", "join"); // Tipo de Evento
            event.put("user.code",user.getCodigo());
            event.put("user.name",user.getNombres());
            event.put("message",user.getNombres() + " ha ingresado al cubículo");

            ArrayNode m = event.putArray("members");

            for(String usercode: members.keySet()) {
                if(usuariosPorCurso.get(members.get(usercode)).equals(cid)){
                    // TODO
                    Usuario anotherUser = Usuario.getUserByCodigo(usercode);
                    m.add("{\"nombres\":\"" + anotherUser.getNombres() + 
                        "\",\"apellidos\":\"" + anotherUser.getApellidos() + 
                        "\",\"codigo\":\"" + anotherUser.getCodigo() + "\"}");
                }
            }
            
            if(usuariosPorCurso.get(channel).equals(cid))
                channel.write(event);
        }
    }

    public void notifyMessage(Mensaje message){

         WebSocket.Out<JsonNode> wsUser = members.get(message.getEmisor().getCodigo());
         String cid = message.getCurso().getCodigo();
         Gson gson = new Gson();

         for(WebSocket.Out<JsonNode> channel: members.values()) {
            
            ObjectNode event = Json.newObject();
            event.put("kind", "talk"); // Tipo de Evento
            event.put("user","{\"nombres\":\"" + message.getEmisor().getNombres() + 
                        "\",\"apellidos\":\"" + message.getEmisor().getApellidos() + 
                        "\",\"codigo\":\"" + message.getEmisor().getCodigo() + "\"}");
            event.put("message",message.getContenido());

            try{
                System.out.println("ENLACE : " + message.getEnlace().getUrl()); 
            }catch(Exception e){

            }
            

            if(message.getEnlace() != null){
            
                event.put("enlace","{\"url\":\"" + message.getEnlace().getUrl() + 
                            "\",\"titulo\":\"" + message.getEnlace().getTitulo() + 
                            "\",\"imagen\":\"" + message.getEnlace().getImagen() + 
                            "\",\"descripcion\":\"" + message.getEnlace().getDescripcion() + "\"}");
            }

            ArrayNode m = event.putArray("members");

            for(String usercode: members.keySet()) {
                if(usuariosPorCurso.get(members.get(usercode)).equals(cid)){
                    // TODO
                    Usuario anotherUser = Usuario.getUserByCodigo(usercode);
                    m.add("{\"nombres\":\"" + anotherUser.getNombres() + 
                        "\",\"apellidos\":\"" + anotherUser.getApellidos() + 
                        "\",\"codigo\":\"" + anotherUser.getCodigo() + "\"}");
                }
            }
            
            if(usuariosPorCurso.get(channel).equals(cid))
                channel.write(event);
        }


    }

    public void notifyAll(String kind,String user, String text) {
    	
    	WebSocket.Out<JsonNode> chan = members.get(user);
    	String curso = usuariosPorCurso.get(chan);
    	
        for(WebSocket.Out<JsonNode> channel: members.values()) {
            
            ObjectNode event = Json.newObject();
            event.put("kind", kind); // Tipo de Evento
            event.put("user", user);

            Usuario _user = Usuario.getUserByCodigo(user);
            event.put("nombres",_user.getNombres());
            event.put("message", text);

            
            
            ArrayNode m = event.putArray("members");

            for(String u: members.keySet()) {
            	
            	if(usuariosPorCurso.get(members.get(u)).equals(curso)) m.add(u);
            }
            
            String cid = usuariosPorCurso.get(channel);
            
            System.out.println("CID --> " + cid);
            
            System.out.println(cid + " == " + curso + " --> " + (cid.equals(curso)));
            
            if(cid.equals(curso))
                channel.write(event);
        }
    }
    
    // -- Messages
    
    public static class Join {
        
        Usuario usuario;
        Curso curso;
        final WebSocket.Out<JsonNode> channel;
        
        public Join(String username, String curso, WebSocket.Out<JsonNode> channel) {
            this.usuario = Usuario.getUserByCodigo(username);
            this.curso = Curso.getCursoByCodigo(curso);
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