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
  
  /*
   * Elegir acción
   */

  public static Result index() {
    System.out.println("INDEX---_");
    // Comprobar si está logueado
    if(!isLogged()){
      // Mostrar la vista principal
      return all();
    }else{
      // Mostrar la vista de usuario logueado
      return show();
    }
  }

  /*
   * Mostrar la lista de usuarios y formulario para registro/logueo.
   */

  public static Result all(){
  	return ok(views.html.index.render(User.all(), userForm));
  }


  /*
   * Cargar sesión a partir de objeto usuario.
   */
  public static void loadSession(User user){
    String uuid = java.util.UUID.randomUUID().toString();
    session("uuid",uuid);
    session("codigo",user.getCodigo());
    session("password",user.getPassword());
  }
  /*
   * Logueo de usuario
   */

  public static Result login(){
    Form<User> filledForm = userForm.bindFromRequest();
    User user = User.getUserByCodigo(filledForm.get().codigo);
    
    // Comprobar contraseña
    if(filledForm.get().password.equals(user.getPassword())){
    	System.out.println("Válido, entrar");
      // Cargar sesión
      loadSession(user);
      // Mostar usuario
      return show();
    }else{
    	System.out.println("Contraseña inválida");
      return all();
    }
  }

  /*
   * Buscar un usuario por su codigo
   */ 

  public static Result show(String codigo){
    return TODO;
  }

  /*
   * Mostrar usuario de la sesión
   */

  public static Result show(){
    User user = User.getUserByCodigo(session("codigo"));
    return ok(views.html.user.render(user));
  }

  /*
   * Comprobar formulario y crear nuevo usuario
   */

  public static Result create(){
  	Form<User> filledForm = userForm.bindFromRequest();
    // Determinar si los datos del formulario tienen errores
  	if(filledForm.hasErrors()){
  		return badRequest(views.html.index.render(User.all(), filledForm));
  	}else{
      // Es usuario --> Loguear
      if(isUser(filledForm.get().codigo)){
        return login();
      // No es usuario --> Registrar
      }else{
        User user = User.create(filledForm.get());
        loadSession(user);
        return show(); 
      }
  	}
  }

  /*
   * Determinar si el usuario existe
   */

  public static boolean isUser(String codigo){
    User user = User.getUserByCodigo(codigo);
    if(user == null)
      return false;
    else
      return true;
  }

  /*
   * Buscar el usuario por id
   */
  
  public static Result find(Long id){
	  return TODO;
  }

  /*
   * Borrar usuario
   */

  public static Result delete(Long id){
    return TODO;
  }
  
  /*
   * Comprobar si usuario está logueado
   */
  
  public static boolean isLogged(){
    System.out.println("SESIÓN : " + session("usuario"));
    if((session("usuario") == null && session("password")== null && session("uuid")==null) && validSessionInfo()){
      return true;
    }else{
      return false;
    }
  }

  /*
   * Validar usuario y password, si existen y son válidos
   */

  public static boolean validSessionInfo(){
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