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

public class Edul {

	public static List<Usuario> getProfesores(){
		return Usuario.find.where().eq("rol","1").findList();
	}

	public static List<Mensaje> getMensajes(){
		return Mensaje.find.all();
	}

	public static List<Seccion> getSecciones(){
		return Seccion.find.all();
	}
}