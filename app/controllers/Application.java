package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import play.*;
import play.mvc.*;

import utils.Scraper;
import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
	  
	 /* Scraper scrappy = new Scraper();
	  System.out.println(".......");
		try {
			scrappy.getInfo("http://elcomercio.pe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = "www.elcomercio.pe";
		
		try {
			URL url2 = new java.net.URL(url);
			System.out.println(url2.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/





		/* DETECTAR URL */
                   
       // separete input by spaces ( URLs don't have spaces )
       /*String [] parts = "Visita el www.elcomercio.pe ".split("\\s");
       
       String _url = "";*/

       // Attempt to convert each item into an URL.   
       /*for( String item : parts ) try {
           URL url = new URL(item);
           // If possible then replace with anchor...
           _url = item;
           System.out.print("<a href=\"" + _url + "\">"+ _url + "</a> " ); 
           break;
       } catch (MalformedURLException e) {
           // If there was an URL that was not it!...
       	System.out.println("No hay URL");
           System.out.print( item + " " );
       }
       
       
       if(_url != ""){
    	   System.out.println("URL DETECTADO --> " + _url);
       }*/
		
    return Users.index();
  }
  
}