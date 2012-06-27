package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.*;
import play.mvc.*;

import utils.Scraper;
import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
	  
	  Scraper scrappy = new Scraper();
	  System.out.println(".......");
		try {
			scrappy.getInfo("http://elcomercio.pe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    return Users.index();
  }
  
}