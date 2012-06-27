package utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.print.DocFlavor.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Scraper {
	
	public void getInfo(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println(doc.toString());
	}
	
	public static void main(String[] args){
		Scraper scrappy = new Scraper();
		java.net.URL url;
		try {
			url = new java.net.URL("www.elcomercio.pe");
			System.out.println(url.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//scrappy.getInfo("www.elcomercio.pe");
	}

}
