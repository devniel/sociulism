package controllers;

import java.util.List;

import org.h2.expression.ExpressionList;

import play.*;
import play.data.*;
import play.mvc.*;
import play.mvc.Http.Session;
import views.html.index;
import models.User;

public class Users extends Controller {

	static Form<User> userForm = form(User.class);
  
  /*public static Result index() {
    return ok(index.render("Your new application is ready."));
  }*/

  public static Result all(){
  	return ok(views.html.index.render(User.all(), userForm));
  }

  public static Result login(){
    Form<User> filledForm = userForm.bindFromRequest();
    User user = User.getUserByCodigo(filledForm.get().codigo);
    
    // Comprobar contraseña
    if(filledForm.get().password.equals(user.getPassword())){
    	System.out.println("Válido, entrar");

    	// Iniciar sesión
	    String uuid = session("uuid");

	    if(uuid==null){

	      uuid = java.util.UUID.randomUUID().toString();
	      session("uuid",uuid);
	      session("codigo",user.getCodigo());
	      session("password",user.getPassword());

	    }

    }else{
    	System.out.println("Contraseña inválida");
    }
    
    return TODO;

  }


  public static Result create(){

  	Form<User> filledForm = userForm.bindFromRequest();

    // Determinar si los datos del formulario tienen errores
  	if(filledForm.hasErrors()){
  		return badRequest(
  			views.html.index.render(User.all(), filledForm)
  		);
  	}else{

      // Determinar si es usuario o no

      // Es usuario --> Loguear
      if(isUser(filledForm.get().codigo)){
        return login();
      // No es usuario --> Registrar
      }else{
        User.create(filledForm.get());
        return redirect(routes.Users.all()); 
      }
  	}
  }

  public static boolean isUser(String codigo){
    User user = User.getUserByCodigo(codigo);
    
    if(user == null){
      return false;
    }else{
      return true;
    }
  }
  
  public static Result find(Long id){
	  return TODO;
  }

  public static Result delete(Long id){
    return TODO;
  }
  
  /*
   * Comprobar si usuario está logueado
   */
  
  public static void isLogged(){
    if((session.contains("usuario") && 
      session.contains("password") && 
      session.contains("uuid")) && validUserInf() ){
      return true;
    }else{
      return false;
    }
  }

  /*
   * Validar usuario y password, si existen y son válidos
   */

  public static boolean validUserInfo(){
    String codigo = session("codigo");
    String password = session("password");
    User user = User.getUserByCodigo(codigo);
    if(user == null){
      return false;
    }else{
      if(user.getPassword().equals(password)){
        return true;
      }else{
        return false;
      }
    }
  }
  
}