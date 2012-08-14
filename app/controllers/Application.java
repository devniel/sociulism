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

	/*Universidad universidad = new Universidad();
	universidad.setNombre("UNIVERSIDAD DE LIMA");
	universidad.save();

	// Creando facultad
	Facultad facultad = new Facultad();
	facultad.setNombre("FACULTAD DE INGENIERÍA DE SISTEMAS");
	facultad.setUniversidad(universidad);
	facultad.save();

	Carrera carrera = new Carrera();
	carrera.setNombre("INGENIERÍA DE SISTEMAS");
	carrera.setFacultad(facultad);
	carrera.setUniversidad(universidad);
	carrera.save();

	Usuario usuario = new Usuario();
	usuario.setPassword("DFYAPL");
	usuario.setCodigo("20082219");
	usuario.setNombres("Daniel Mauricio");
	usuario.setFacultad(facultad);
	usuario.setUniversidad(universidad);
	usuario.setCarrera(carrera);
	
	try
	{
		Usuario.create(usuario);

	}catch (Exception ex){
		// TODO Auto-generated catch block
		ex.printStackTrace();
	}

	usuario.save();*/

    return Usuarios.index();
    
  }
  
}