package models;

import play.*;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;

import akka.util.*;
import akka.actor.*;
import akka.dispatch.*;

import org.codehaus.jackson.*;
import org.codehaus.jackson.node.*;

import static java.util.concurrent.TimeUnit.*;

public class Robot {
    
    public Robot(ActorRef chatRoom) {
        
        // Create a Fake socket out for the robot that log events to the console.
        WebSocket.Out<JsonNode> robotChannel = new WebSocket.Out<JsonNode>() {
            
            public void write(JsonNode frame) {
                Logger.of("robot").info(Json.stringify(frame));
            }
            
            public void close() {}
            
        };
        
        // Join the room
        chatRoom.tell(new ChatCurso.Join("Robot", "1", robotChannel));
        
        // Make the robot talk every 30 seconds
        Akka.system().scheduler().schedule(
            Duration.create(30, SECONDS),
            Duration.create(30, SECONDS),
            chatRoom,
            new ChatCurso.Talk("Robot", "I'm still alive")
        );
        
    }
    
}