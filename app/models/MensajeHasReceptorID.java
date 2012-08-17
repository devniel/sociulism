package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MensajeHasReceptorID {
		private long mensaje_id;
		private long receptor_id;
}
