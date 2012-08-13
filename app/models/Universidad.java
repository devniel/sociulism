package models;

import java.util.Date;
import java.util.List;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
@Table(name="Universidad")

public class Universidad {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String nombre;
	
	public Date fecha_registro;
	
	public static Finder<Long,Universidad> find = new Finder(Long.class, Universidad.class);
	
	@OneToMany
	private List<Facultad> facultades;
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public Long getId() {
		return id;
	}

	public List<Facultad> getFacultades() {
		return facultades;
	}

	public void setFacultades(List<Facultad> facultades) {
		this.facultades = facultades;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static Finder<Long, Universidad> getFind() {
		return find;
	}

	public static void setFind(Finder<Long, Universidad> find) {
		Universidad.find = find;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
