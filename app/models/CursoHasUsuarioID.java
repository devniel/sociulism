package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CursoHasUsuarioID {
	private long cid;
	private long uid;
}

