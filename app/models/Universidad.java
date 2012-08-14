package models;

import java.util.Date;
import java.util.List;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
@Table(name="Universidad")

public class Universidad extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long Id;
	
	public String nombre;
	
	public Date fecha_registro;
	
	public static Finder<Long,Universidad> find = new Finder(Long.class, Universidad.class);
	
	@OneToMany
	private List<Facultad> facultades;

	@OneToMany
	public List<Carrera> carreras;

	@OneToMany
	public List<Usuario> usuarios;
	
	/*
	 * GETTERS AND SETTERS
	 */

	public Long getId() {
		return Id;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Facultad> getFacultades() {
		return facultades;
	}

	public void setFacultades(List<Facultad> facultades) {
		this.facultades = facultades;
	}

	public void setId(Long Id) {
		this.Id = Id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
