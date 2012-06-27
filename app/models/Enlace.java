package models;

import java.io.IOException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="Enlace")
public class Enlace extends Model{
	
	private String url;
	private String descripcion;
	private String imagen;
	private String titulo;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Usuario emisor;
	
	@OneToOne
	private Curso curso;
	
	public static Finder<Long,Enlace> find = new Finder(Long.class, Enlace.class);
	
	public static List<Enlace> all(){
		//return TODO;
		return find.all();
	}

	public static Enlace create(Enlace enlace){
		enlace.save();
		return enlace;
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}

	public static Enlace getEnlace(Long id){
		Enlace link = find.ref(id);
		return link;
	}
	
	public static Enlace createFromURL(String url) throws IOException{
		Enlace link = new Enlace();
		Document doc = Jsoup.connect(url).get();
		List<Element> metas = doc.getElementsByTag("meta");
		String desc = doc.select("meta[name=description]").first().attr("content");
		
		link.setDescripcion(desc);
		
		List<Element> images = doc.body().getElementsByTag("img");
		String src_image = "";
		for(Element image: images){
			
			Integer width = (image.attr("width").equals(""))? 0:Integer.parseInt(image.attr("width"));
			Integer height = (image.attr("height").equals(""))? 0 : Integer.parseInt(image.attr("height"));
			System.out.println(width.toString() + " " + height.toString());
			
			if(width < 30 || height < 30){
				continue;
			}else{
				src_image = image.attr("src");
				break;
			}
		}
		
		link.setImagen(src_image);
		
		String title = doc.select("title").first().text();

		link.setTitulo(title);
		return link;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	
}
