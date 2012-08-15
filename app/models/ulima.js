importClass(org.json.JSONArray);

msg = msg.replace(/\n/g, "");
msg = msg.replace(/\/\/(\s|\S)*\/\//gim, "");
eval(msg)

print(cursosMat)

var courses = new JSONArray();

for ( var i in cursosMat) {
	for ( var j = 0; j < cursosMat[i].oSeccion.nHorario.length; j++) {
		if (cursosMat[i].oSeccion.nHorario[j].length > 0) {
			var course = {
				codigo : cursosMat[i].sCoCurs,
				nombre : cursosMat[i].sNoCmpCurs,
				seccion : cursosMat[i].oSeccion.sCoSecc,
				profesor : cursosMat[i].oSeccion.sNoProf
				//shour : cursosMat[i].oSeccion.nHorario[j][0],
				//ehour : cursosMat[i].oSeccion.nHorario[j][cursosMat[i].oSeccion.nHorario[j].length - 1] + 1
			};
			courses.put(course)
		}
	}
}