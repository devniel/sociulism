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
@Table(name="Usuario_Asesor")
@IdClass(UsuarioHasAsesorID.class)

public class UsuarioHasAsesor extends Model {
	
	@ManyToOne(targetEntity=Usuario.class)
	@PrimaryKeyJoinColumn(name="USUARIOID", referencedColumnName="ID")
	private Usuario usuario;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="ASESORID", referencedColumnName="ID")
	private Usuario asesor;
	
	public static Finder<Long,UsuarioHasAsesor> find = new Finder(Long.class, UsuarioHasAsesor.class);
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getAsesor() {
		return asesor;
	}

	public void setAsesor(Usuario asesor) {
		this.asesor = asesor;
	}

	public UsuarioHasAsesor create(){
		this.save();
		return this;
	}

	
}