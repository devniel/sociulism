function autoTextArea(element){

    element.style.overflow = "hidden";

    function borrar() {
      $(inv_box).html("");
    }

    var sw = 0;
    var inc = 20;
    //alert($(element).css("line-height"));
    // Crear inv_box
    inv_box = $('<div class="invisible_box">').css({
      height : 'auto',
      overflow : 'auto',
      position : 'absolute',
      'line-height' : '4px',
      top : '0px',
      left : '0px',
      background : '#CCC',
      visibility : 'visible',
      width : $(element).width() + "px",
      'font-size' : $(element).css("font-size"),
      'font-family' : $(element).css("font-family"),
      'padding-top' : $(element).css("padding-top"),
      'padding-left' : $(element).css("padding-left"),
      'padding-bottom' : '0px'
    }).appendTo($('body'));



    function text() {
      if(sw == 1){
        borrar();
      }

      lines = element.value.split("\n");

      //line = lines[0].replace(/ /g,"[[space]]");

      //console.log(line);

      for(i in lines){
        p = $("<p/>").css({
          'display':'block'
          });
        //var contenido = document.createTextNode(lines[i] + "_");
        line = lines[i].replace(/\t/g,"[[tab]]");
        //alert(line);          
        p.html(line + "\u00a0");
        //p.html(line + "\n");
        inv_box.append(p);
      }

      $(element).css({
        'height' : $(inv_box).height() + "px",
      }); // Before,this was by rows.
      inc = inc + 20;
      sw = 1;     
    }
    element.onfocus = text;
    element.onkeydown = text;
    element.onkeyup = text;
  }

