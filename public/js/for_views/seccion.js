  // SECCION 

  $('#navbar').affix();

  /*
   *  Al cliquear sobre cualquier área fuera de las sugerencias, estas se eliminan.
   */ 

  $("body").click(function(e) {
   if(e.target.className !== 'suggestions' || e.target.className !== 'suggestion'){
      $(".suggestions").remove();
    }
  });

  /*
   *  Al seleccionar una sugerencia
   */

  $("form").on("click",".input-prepend .suggestions .suggestion",function(e){

    var nombre = $(e.target).text();

    console.log("LEN: " + $(e.target).find(".alumno").length );

    if($(e.target).find(".alumno").length != 0){
      // SUGERENCIAS DE PROFESORES
      var id = $($(e.target).parent()).find(".profesor_id").text();
      // Asignar nombre a input
      $("input[name='seccion.profesor']").val(nombre);
      $("input[name='seccion.profesor_id']").val(id);
    }else{
      // SUGERENCIAS DE ESTUDIANTES
      var id = $($(e.target).parent()).find(".alumno_id").text();
      // Asignar nombre a input
      $("input[name='seccion.alumno']").val(nombre);
      $("input[name='seccion.alumno_id']").val(id);
    }
  });

  /*
   * Sugerencias de profesores del curso a ser receptores
   */

  $("#seccion-profesor").on("keyup", "input[name='seccion.profesor']" , function(e){

    $(".suggestions").remove();

    $.ajax({
        type: "GET",
        url: "/usuarios/profesores",
        contentType : "application/json;charset=utf-8",
        dataType : "json",
      }).done(function( msg ) {

        console.log("PROFESORES : " ,msg);

        var lista = msg;

        var value = $(e.target).val();
        var m = new RegExp(value,"g");
        var sugerencias = "";

        for(var i in lista){
          if(lista[i].nombres.match(m) != null){

            var exists= false;

            for(var j=0;j<$(".receptores ul li").length;j++){
              if(lista[i].id == $($(".receptores ul li")[j]).find(".receptor_id").text()){
                exists = true;
                break;
              }
            }

            if(!exists)
              sugerencias += '<li><span class="profesor suggestion_metadata profesor_id">' + lista[i].id + '</span><a class="suggestion">' + lista[i].nombres + ' ' + lista[i].apellidos + '</a></li>';
          }      
        }

        if(sugerencias != ""){
          var lista_sugerencias = $("<ul class='nav nav-list span6 suggestions'>" + sugerencias + "</ul>");
          lista_sugerencias.insertAfter($(e.target));
        }
    });

  });



  /*
   * Sugerencias de alumnos del curso a ser agregados
   */

  $("input[name='seccion.alumno']").keyup(function(e){

    $(".suggestions").remove();

    $.ajax({
        type: "GET",
        url: "/usuarios/alumnos",
        contentType : "application/json;charset=utf-8",
        dataType : "json",
      }).done(function( msg ) {

        console.log("ESTUDIANTES : " ,msg);

        var lista = msg;

        var value = $(e.target).val();
        var m = new RegExp(value,"g");
        var sugerencias = "";

        for(var i in lista){
          if(lista[i].apellidos.match(m) != null){

            var exists= false;

            for(var j=0;j<$(".receptores ul li").length;j++){
              if(lista[i].id == $($(".receptores ul li")[j]).find(".receptor_id").text()){
                exists = true;
                break;
              }
            }

            if(!exists)
              sugerencias += '<li><span class="alumno suggestion_metadata alumno_id">' + lista[i].id + '</span><a class="suggestion">' + lista[i].nombres + ' ' + lista[i].apellidos + '</a></li>';
          }      
        }

        if(sugerencias != ""){
          var lista_sugerencias = $("<ul class='nav nav-list span6 suggestions'>" + sugerencias + "</ul>");
          lista_sugerencias.insertAfter($(e.target));
        }
    });

  });


  /*
   * Al hacer clic en modificar, se muestra el formulario del Profesor
   */


  $("#showFormProfesor").click(function(){
    $("#seccion-profesor").html('<form class="form-horizontal" method="POST" action="/admin/cursos/@curso.getId()/seccion/@seccion.getId()/update/profesor/"><fieldset><div class="control-group"><label class="control-label" for="input01">Profesor :</label><div class="controls"><div class="input-prepend"><span class="add-on"><i class="icon-user"></i></span><input name="seccion.profesor" class="span6 with-icon" id="inputIcon" type="text" placeholder="Asignar profesor" autocomplete="off"></div></div></div><input type="hidden" name="seccion.profesor_id"/><div class="control-group"><div class="controls"><button class="btn  btn-primary" type="submit">Asignar</button</div></div></fieldset></form>');
  });


