package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="Carrera")

public class Carrera extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String nombre;
	
	public Date fecha_registro;
	
	public static Finder<Long,Carrera> find = new Finder(Long.class, Carrera.class);
	
	@OneToMany
	private List<Mensaje> mensajes;
		
	@OneToOne(cascade = {CascadeType.ALL})
	private Facultad facultad;
	
	@OneToOne(cascade = {CascadeType.ALL})
	public Universidad universidad;

	/**
	 * GETTERS AND SETTERS
	 */
	
	
	public Long getId() {
		return id;
	}

	public Universidad getUniversidad() {
		return universidad;
	}

	public void setUniversidad(Universidad universidad) {
		this.universidad = universidad;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
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
