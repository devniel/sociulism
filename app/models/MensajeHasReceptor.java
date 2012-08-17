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
@Table(name="Mensaje_Receptor")
@IdClass(MensajeHasReceptorID.class)

public class MensajeHasReceptor extends Model {

	@ManyToOne
	@PrimaryKeyJoinColumn(name="MENSAJE_ID", referencedColumnName="ID")
	private Mensaje mensaje;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="RECEPTOR_ID", referencedColumnName="ID")
	private Usuario receptor;

	public static Finder<Long,MensajeHasReceptor> find = new Finder(Long.class, MensajeHasReceptor.class);
		
	public MensajeHasReceptor create(){
		this.save();
		return this;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
	
	

}
