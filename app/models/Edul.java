package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.context.annotation.Primary;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import anorm.SqlRow;

import com.avaje.ebean.*;

public class Edul {

	public List<Usuario> getProfesores(){
		return Usuario.find.where().eq("rol","1").findList();
	}

	public List<Usuario> getAlumnos(){
		return Usuario.find.where().eq("rol","0").findList();
	}

	public List<Mensaje> getMensajes(){
		return Mensaje.find.all();
	}

	/*
	 * Obtener preguntas
	 */

	public List<Mensaje> getPreguntas(){
		return Mensaje.find.where().eq("tipo","2").findList();
	}

	public List<Mensaje> getRespuestas(){
		return Mensaje.find.where().eq("tipo","0").findList();
	}

	public List<Seccion> getSecciones(){
		return Seccion.find.all();
	}
	
	public List<com.avaje.ebean.SqlRow> getPreguntasPorDia(){
			
		List<com.avaje.ebean.SqlRow> preguntasPorDia = Ebean.createSqlQuery("select DAY(fecha) as día,MONTH(fecha) as mes,YEAR(fecha) as año,tipo,count(*) from mensaje where tipo = 2 group by tipo, DAY(fecha) desc").findList();
		
		return preguntasPorDia;
	}
	
	
}