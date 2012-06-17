package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
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

  public static Result create(){
  	Form<User> filledForm = userForm.bindFromRequest();
  	if(filledForm.hasErrors()){
  		return badRequest(
  			views.html.index.render(User.all(), filledForm)
  		);
  	}else{
  		User.create(filledForm.get());
  		return redirect(routes.Users.all());
  	}
  }

  public static Result find(Long id){
  	//User user = find.ref(id);
    return TODO;
  }

  public static Result delete(Long id){
    return TODO;
  }
  
}