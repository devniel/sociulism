package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.*;


import play.*;
import play.mvc.*;

import utils.Scraper;
import views.html.*;

public class Application extends Controller {
  
  public static Result index() {

    return Usuarios.index();
    
  }
  
}