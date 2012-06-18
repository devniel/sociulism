package models;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

// Task.java
@Entity
public class User extends Model{
	
	@Id
	public Long uid;
	
	@Column(unique=true) 
	@Required
	public String codigo;

	@Required
	public String password;
	
	public static Finder<Long,User> find = new Finder(Long.class, User.class);

	public static List<User> all(){
		//return TODO;
		return find.all();
	}

	public static User create(User user){
		user.save();
		return user;
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}

	public static User getUser(Long id){
		User user = find.ref(id);
		return user;
	}

	public static User getUserByCodigo(String codigo){
		User user = find.where().eq("codigo",codigo).findUnique();
		return user;
	}

	/** GETTERS AND SETTERS **/
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}