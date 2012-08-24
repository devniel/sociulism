package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SeccionHasUsuarioID {
	private long seccion_id;
	private long usuario_id;
}

