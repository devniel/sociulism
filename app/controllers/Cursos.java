package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;

import views.html.*;
import models.*;

public class Cursos extends Controller {
  
  public static Result index() {
    return ok(views.html.curso.index.render(null,null));
  }

  public static Result show(String id){

  	Usuario usuario = Usuarios.getUserSession();

  	Curso curso = Curso.getCurso(Long.parseLong(id));
  	
  	List<Usuario> profesores = curso.getProfesores();
  	
  	System.out.println("PROFESORES DEL CURSO: ");
  	
  	for (Usuario profesor : profesores) {
  		System.out.println(profesor.getNombres() + " --- " + profesor.getApellidos());
	   }

  	return ok(views.html.curso.index.render(usuario,curso));
  }

  /*
  * CREAR CURSO
  */
  public static Result create(){
      Map<String, String[]> formData = request().body().asFormUrlEncoded();
      String codigo = formData.get("curso.codigo")[0];
      String nombre = formData.get("curso.nombre")[0];
      
      Curso curso = new Curso();
      curso.setNombre(nombre);
      curso.setCodigo(codigo);
      curso.save();
      
      // Redireccionar a página inicial (con o sin carga de sesión)
      return redirect(routes.Cursos.all());
  }

  /*
   * Asesoría del cursos
   */

  public static Result asesoria(String id){

    Usuario usuario = Usuarios.getUserSession();

    Curso curso = Curso.getCurso(Long.parseLong(id));


    Result view = null;

    switch(usuario.getRol()){
    	// Usuario común
      case 0:
        // Select * from mensaje where emisor_id = 2 and curso_id = 1;
        List<Mensaje> preguntas_enviadas = Mensaje.find.where().eq("emisor_id",usuario.getId().toString()).eq("curso_id", curso.getId().toString()).eq("tipo","2").findList();
        view = ok(views.html.curso.asesoria.render(usuario,curso,preguntas_enviadas));
        break;
        // Profesor
      case 1:
        List<MensajeHasReceptor> preguntas_recibidas_relacion = MensajeHasReceptor.find.where().eq("receptor_id",usuario.getId().toString()).findList();
        
        List<Mensaje> preguntas_recibidas = new ArrayList<Mensaje>();
        
        for (MensajeHasReceptor pregunta_recibida : preguntas_recibidas_relacion) {
			   preguntas_recibidas.add(pregunta_recibida.getMensaje());
		    }
        
        view = ok(views.html.curso.asesoria.render(usuario,curso,preguntas_recibidas));
        break;
        // Administrador
      /*case 2:
        view  = ok(views.html.curso.profesor.asesoria.render(usuario,curso,null));*/
    }

    return view;

  }

  /*
   *  Alumno envía nueva pregunta : "Asesoría" -> Mensaje tipo 2
   *  Para un mensaje en asesoría se debe guardar las siguientes propiedades
   *  - emisor_id
   *  - curso_id
   *  - universidad_id
   *  - facultad_id
   *  - carrera_id
   *  - receptor_id [ asociación ]
   */

  @BodyParser.Of(play.mvc.BodyParser.Json.class)
  public static Result nuevaAsesoria(String id){
    
    RequestBody body = request().body();
    
    JsonNode json = body.asJson();
    
    String mensaje_titulo = json.get("mensaje").get("titulo").toString();
    String mensaje_contenido = json.get("mensaje").get("contenido").toString();
    String mensaje_emisor = json.get("mensaje").get("emisor").toString();

    String curso_seccion = json.get("mensaje").get("curso_seccion").toString();

    Mensaje mensaje = new Mensaje();
    mensaje.setTipo(2); // Tipo de pregunta.
    mensaje.setTitulo(mensaje_titulo);
    System.out.println("CONTENIDO ----> " + mensaje_contenido.length());
    mensaje.setContenido(mensaje_contenido);
    mensaje.setEmisor(Usuario.find.ref(Long.parseLong(mensaje_emisor)));
    
    
    mensaje.setSeccion(Seccion.find.ref(Long.parseLong(curso_seccion)));
    mensaje.save();
    
    for(int i = 0;i < json.get("mensaje").get("receptores").size();i++){
    	Long receptor_id = Long.parseLong(json.get("mensaje").get("receptores").get(i).getTextValue());
    	MensajeHasReceptor mensajeReceptor = new MensajeHasReceptor();
    	mensajeReceptor.setMensaje(mensaje);
    	mensajeReceptor.setReceptor(Usuario.find.ref(receptor_id));
    	mensajeReceptor.save();
    }
    
    mensaje.save();
    
    return ok("");
  }

  /*
   *  Obtiene todos los profesores del curso determinado por Id
   *
   */

  public static Result getProfesorDelCurso(String id){
    System.out.println("QUIERE OBTENER LOS PROFESORES DEL CURSO");
       
    Curso curso = Curso.getCurso(Long.parseLong(id));
    
    JSONArray profesores = new JSONArray();
    
    for(Usuario profesor : curso.getProfesores()){
    	JSONObject json = new JSONObject();
        try {
			json.put("nombres", profesor.getNombres());
			json.put("apellidos", profesor.getApellidos());
			json.put("id",profesor.getId());
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        profesores.put(json);
    }

	  return ok(profesores.toString());
  }

  /*
   *  Obtener la pregunta determinada y cargar en la interfaz de asesorías del profesor
   */

  public static Result getPregunta(String cid, String pid){

    Curso curso = Curso.getCurso(Long.parseLong(cid));
    Mensaje pregunta = Mensaje.find.ref(Long.parseLong(pid));
    Usuario usuario = Usuarios.getUserSession();

    Result view = ok(views.html.curso.pregunta.render(usuario,curso,pregunta));
    return view;
  }

  /*
   *
   */

  @BodyParser.Of(play.mvc.BodyParser.Json.class)
  public static Result postRespuesta(String cid, String pid){
	  
	RequestBody body = request().body();
	JsonNode json = body.asJson();

    Seccion curso_seccion = Seccion.find.ref(Long.parseLong(cid));
    
    Mensaje pregunta = Mensaje.find.ref(Long.parseLong(pid));
    String seccion = json.get("respuesta").get("seccion").toString();


    String respuesta_contenido = json.get("respuesta").get("contenido").toString();
    String respuesta_emisor = json.get("respuesta").get("emisor").toString();

    Mensaje respuesta = new Mensaje();
    respuesta.setTipo(0); // Tipo de respuesta o comentario.
    System.out.println("CONTENIDO ----> " + respuesta_contenido.length());
    respuesta.setContenido(respuesta_contenido);
    respuesta.setEmisor(Usuario.find.ref(Long.parseLong(respuesta_emisor)));
    respuesta.setMensaje(Mensaje.find.ref(Long.parseLong(pid)));
    
    respuesta.setSeccion(curso_seccion);
    
    respuesta.save();
    
    /*for(int i = 0;i < json.get("mensaje").get("receptores").size();i++){
      Long receptor_id = Long.parseLong(json.get("mensaje").get("receptores").get(i).getTextValue());
      MensajeHasReceptor mensajeReceptor = new MensajeHasReceptor();
      mensajeReceptor.setMensaje(mensaje);
      mensajeReceptor.setReceptor(Usuario.find.ref(receptor_id));
      mensajeReceptor.save();
    }
    
    mensaje.save();*/
    
    return ok("");

  }
  
  /*
   *  Mostrar todas los cursos
   */

  public static Result all(){
    return ok(views.html.cursos.all.render(Curso.find.all()));
  }

  /*
   *  Mostrar formulario de creación de nuevo curso
   */

  public static Result showCreate(){

    return ok(views.html.cursos.create.render(Universidad.find.all(), Facultad.find.all()));
  }


  /*
   *  Actualizar curso
   */

  public static Result update(Long id){
    Map<String, String[]> formData = request().body().asFormUrlEncoded();
    String codigo = formData.get("curso.codigo")[0];
    String nombre = formData.get("curso.nombre")[0];
    
    Curso curso = Curso.find.ref(id);
    curso.setNombre(nombre);
    curso.setCodigo(codigo);
    curso.save();
    
    // Redireccionar a página inicial (con o sin carga de sesión)
    return redirect(routes.Cursos.all());
  }

  /*
   *  Editar a curso
   */

  public static Result edit(Long id){
    return ok(views.html.cursos.edit.render(Curso.find.ref(id)));
  }

  public static Result delete(Long id){
    Curso.find.ref(id).delete();
    return redirect(routes.Cursos.all());
  }

  /*
   *  Agregar sección al curso
   */
  
  public static Result addSeccion(Long id){

    System.out.println("CREANDO SECCÍÓN ...");

    Map<String, String[]> formData = request().body().asFormUrlEncoded();
    String seccion_codigo = formData.get("seccion.codigo")[0];
    
    Seccion seccion = new Seccion();
    seccion.setSeccion(Integer.parseInt(seccion_codigo));
    seccion.setCurso(Curso.find.ref(id));
    seccion.save();
    
    // Redireccionar a página inicial (con o sin carga de sesión)
    return redirect(routes.Cursos.edit(id));
  }

  /*
   *  Mostrar sección
   */

  public static Result showSeccion(Long id, Long sid){

    return ok(views.html.cursos.seccion.render(Curso.find.ref(id),Seccion.find.ref(sid)));
  }

  /*
   *  Mostrar lista de secciones del curso
   */

  public static Result showSecciones(Long id){
    Curso curso = Curso.find.ref(id);
    return ok(views.html.cursos.secciones.render(curso));
  }

  /*
   *  Asignar profesor a la seccion
   */

  public static Result asignarProfesor(Long id, Long sid){
    
    System.out.println("ASIGNANDO PROFESOR A SECCÍÓN ...");

    Map<String, String[]> formData = request().body().asFormUrlEncoded();
    String seccion_profesor = formData.get("seccion.profesor_id")[0];
    
    Usuario profesor = Usuario.find.ref(Long.parseLong(seccion_profesor));
    
    // Actualizar seccion
    Seccion seccion = Seccion.find.ref(sid);
    seccion.setProfesor(profesor);
    seccion.save();
    
    // Generar relación
    SeccionHasUsuario seccionHasUsuario = new SeccionHasUsuario();
    seccionHasUsuario.setSeccion(seccion);
    seccionHasUsuario.setUsuario(profesor);
    
    // Actualizar profesor
    profesor.getCursos().add(seccionHasUsuario);
    profesor.save();
    

    seccion.save();
    
    // Redireccionar a página inicial (con o sin carga de sesión)
    return redirect(routes.Cursos.showSeccion(seccion.getCurso().getId(), seccion.getId()));
  }

  /*
   *  Actualizar profesor de la seccion
   */

  public static Result updateProfesor(Long id, Long sid){
    
    System.out.println("ASIGNANDO PROFESOR A SECCÍÓN ...");

    Map<String, String[]> formData = request().body().asFormUrlEncoded();
    String seccion_profesor = formData.get("seccion.profesor_id")[0];
    Usuario profesor = Usuario.find.ref(Long.parseLong(seccion_profesor));

     // Actualizar seccion
    Seccion seccion = Seccion.find.ref(sid);
    seccion.setProfesor(profesor);
    seccion.save();
    
    SeccionHasUsuario seccionHasUsuario = SeccionHasUsuario.find.where().eq("usuario_id", "sd").eq("seccion_id","sds").findUnique();
   
    if(seccionHasUsuario == null){
    	seccionHasUsuario = new SeccionHasUsuario();
    }
    
    seccionHasUsuario.setSeccion(seccion);
    seccionHasUsuario.setUsuario(profesor);
    seccionHasUsuario.save();
    
    // Actualizar profesor
    profesor.getCursos().add(seccionHasUsuario);
    profesor.save();
    
    // Redireccionar a página inicial (con o sin carga de sesión)
    return redirect(routes.Cursos.showSeccion(seccion.getCurso().getId(), seccion.getId()));
  }

  /*
   *  Agregar alumno a la sección
   */

  public static Result agregarAlumno(Long id, Long sid){

    System.out.println("ASIGNANDO ALUMNO A SECCÍÓN ...");

    Map<String, String[]> formData = request().body().asFormUrlEncoded();
    String seccion_alumno = formData.get("seccion.alumno_id")[0];

    Seccion seccion = Seccion.find.ref(sid);

    SeccionHasUsuario seccionHasUsuario = new SeccionHasUsuario();
    seccionHasUsuario.setUsuario(Usuario.find.ref(Long.parseLong(seccion_alumno)));
    seccionHasUsuario.setSeccion(Seccion.find.ref(sid));
    seccionHasUsuario.save();

    seccion.getUsuarios().add(seccionHasUsuario);
    seccion.save();
    
    // Redireccionar a página inicial (con o sin carga de sesión)
    return redirect(routes.Cursos.showSeccion(seccion.getCurso().getId(), seccion.getId()));

  }

}