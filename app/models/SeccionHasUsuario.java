package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="Seccion_Usuario")
@IdClass(SeccionHasUsuarioID.class)

public class SeccionHasUsuario extends Model {
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="USUARIOID", referencedColumnName="ID")
	private Usuario usuario;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="SECCIONID", referencedColumnName="ID")
	private Seccion seccion;
	
	public static Finder<Long,SeccionHasUsuario> find = new Finder(Long.class, SeccionHasUsuario.class);
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public SeccionHasUsuario create(){
		this.save();
		return this;
	}

	
}