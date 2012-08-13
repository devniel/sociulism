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

	/*Usuario usuario = new Usuario();
	usuario.setPassword("DFYAPL");
	usuario.setCodigo("20082219");


	Carrera carrera = new Carrera();
	carrera.setNombre("INGENIERÍA DE SISTEMAS");
	carrera.save();

	// Creando facultad
	Facultad facultad = new Facultad();
	facultad.setNombre("FACULTAD DE INGENIERÍA DE SISTEMAS");
	facultad.save();

	// Creando universidad

	Universidad universidad = new Universidad();
	universidad.setNombre("UNIVERSIDAD DE LIMA");
	List<Facultad> facultades = new ArrayList<Facultad>();
	facultades.add(facultad);
	universidad.setFacultades(facultades);*/



	

    return Usuarios.index();
  }
  
}