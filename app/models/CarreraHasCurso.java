package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="Carrera_Curso")
@IdClass(CarreraHasCurso.class)

public class CarreraHasCurso extends Model {

	@ManyToOne
	@PrimaryKeyJoinColumn(name="CARRERA_ID", referencedColumnName="ID")
	private Carrera carrera;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="CURSO_ID", referencedColumnName="ID")
	private Curso curso;

	/*
	 * GETTERS AND SETTERS
	 */
	
	public Curso getCurso() {
		return curso;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public CarreraHasCurso create(){
		this.save();
		return this;
	}
	
}
