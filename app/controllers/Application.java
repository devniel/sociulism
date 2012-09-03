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

	Usuario usuario = Usuario.find.ref(Long.parseLong("3"));
	System.out.println("NOMBRES -->" + usuario.getNombres());
	/*
	Usuario asesor = Usuario.find.ref(Long.parseLong("4"));

	UsuarioHasAsesor usuarioHasAsesor = new UsuarioHasAsesor();
	usuarioHasAsesor.setUsuario(usuario);
	usuarioHasAsesor.setAsesor(asesor);
	usuarioHasAsesor.save();

	usuario.getAsesores().add(usuarioHasAsesor);
	usuario.save();*/

	System.out.println("ASESORES : " + usuario.getAsesores().size());

    return Usuarios.index();
    
  }
  
}