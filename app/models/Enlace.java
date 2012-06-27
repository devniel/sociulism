package models;

import java.io.IOException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import play.db.ebean.Model;

import play.db.ebean.*;

@Entity
@Table(name="Enlace")
public class Enlace extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long eid;
	
	public String url;
	public String descripcion;
	public String imagen;
	public String titulo;
	

	@OneToOne(cascade = {CascadeType.ALL})
	public Usuario emisor;
	
	@OneToOne
	public Curso curso;
	
	public static Finder<Long,Enlace> find = new Finder(Long.class, Enlace.class);
	
	
	public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}
	
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
		link.setUrl(url);
		
		//System.out.println(doc.toString());
		List<Element> metas = doc.getElementsByTag("meta");

		String desc = doc.select("meta[property=og:description").first().attr("content");
		if(desc == "") desc = doc.select("meta[name=description]").first().attr("content");
		
		if(desc.length() > 255){
			desc = desc.substring(0,250);
		}
		
		link.setDescripcion(desc);
		
		String src_image = "";
		src_image = doc.select("meta[property=og:image").first().attr("content");

		if(src_image == ""){
			List<Element> images = doc.body().getElementsByTag("img");
			
			
			for(Element image: images){
				
				Integer width = (image.attr("width").equals(""))? 0:Integer.parseInt(image.attr("width"));
				Integer height = (image.attr("height").equals(""))? 0 : Integer.parseInt(image.attr("height"));
				//System.out.println(width.toString() + " " + height.toString());
				
				if(width < 30 || height < 30){
					continue;
				}else{
					src_image = image.attr("src");
					break;
				}
			}
		}
		
		link.setImagen(src_image);
		
		String title = doc.select("meta[property=og:title").first().attr("content");
		if(title == "")doc.select("title").first().text();
		
		if(title.length() > 255){
			title = title.substring(0,250);
		}
		
		link.setTitulo(title);
		
		System.out.println("LLEGO HASTA AQUÍ");
		System.out.println(link.getDescripcion());
		System.out.println(link.getImagen());
		System.out.println(link.getTitulo());
		
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
