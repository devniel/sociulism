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
  

  
}