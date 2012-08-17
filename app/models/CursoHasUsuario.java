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
@Table(name="Curso_Usuario")
@IdClass(CursoHasUsuarioID.class)

public class CursoHasUsuario extends Model {
	
	@Column(name = "seccion")
	private Integer seccion;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="USUARIOID", referencedColumnName="ID")
	private Usuario usuario;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="CURSOID", referencedColumnName="ID")
	private Curso curso;

	public static Finder<Long,CursoHasUsuario> find = new Finder(Long.class, CursoHasUsuario.class);
	
	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public CursoHasUsuario create(){
		this.save();
		return this;
	}

	
}