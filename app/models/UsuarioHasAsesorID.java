package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioHasAsesorID {
	private long usuario_id;
	private long asesor_id;
}

