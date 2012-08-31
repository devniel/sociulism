package models;

import java.util.Date;
import java.util.List;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
@Table(name="Facultad")

public class Facultad extends Model{
	
	private static final long serialVersionUID = 1L;

	@Id
	public Long Id;
	
	public String nombre;
	
	public Date fecha_registro;
	
	public static Finder<Long,Facultad> find = new Finder(Long.class, Facultad.class);
	
	@OneToMany
	@OrderBy("fecha desc")
	private List<Mensaje> mensajes;
	
	@OneToMany
	private List<Carrera> carreras;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Universidad universidad;

	public static Facultad create(Facultad facultad){
		facultad.save();
		return facultad;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
		
	public Long getId() {
		return Id;
	}

	public Universidad getUniversidad() {
		return universidad;
	}

	public void setUniversidad(Universidad universidad) {
		this.universidad = universidad;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
	
	