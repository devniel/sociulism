package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Ulima {
	

	public static void main(String[] args){
		// System.out.println(":o");

		HttpURLConnection con;
		
		System.out.println("Empezar request ...");
		
		try {
			//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			
			// Establecer conexión
			con = (HttpURLConnection) new URL("https://webaloe.ulima.edu.pe/portalUL/j_security_check").openConnection();
			// Habilitar redirección
			//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setInstanceFollowRedirects(false);
			// Método de solicitud, POST
			con.setRequestMethod("POST");
			// Obtener el stream de la conexión
			con.setAllowUserInteraction(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			// Usuario :
			String codigo = "20082219";
			// Contraseña : 
			String password = "DFYAPL";
			String query = "j_username=" + codigo +"&j_password=" + password + "&ac=";			
		    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		    wr.write("j_username=20082219&j_password=DFYAPL&ac=");
		    wr.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			System.out.println(con.getHeaderField("Cookie"));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				System.out.println(decodedString);
			}
			
			// LEER COOKIES
			System.out.println("LEER COOKIES");
			
			/*
			 * Redireccionar
			 */
			
			// MOSTRAR COOKIES
			
			List<String> cookies = con.getHeaderFields().get("Set-Cookie");
			for (String cookie : cookies) {
				
				int s = cookie.indexOf("; Path=/");
				cookie = cookie.substring(0,s);
				System.out.println("Cookie --> " + cookie);
			}
				
			/*
			// Establecer conexión
			HttpURLConnection con2 = (HttpURLConnection) new URL("https://webaloe.ulima.edu.pe/portalUL/layout.jsp?tab=1").openConnection();
			con2.setRequestMethod("GET");
			con2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.1");
			
			for (String cookie : cookies) {
				
				int s = cookie.indexOf("; Path=/");
				cookie = cookie.substring(0,s);
				System.out.println("Cookie --> " + cookie);
				con2.setRequestProperty("Cookie", cookie);
			}
			
			System.out.println(con2.getHeaderField("User-Agent"));*/
			
			/*System.out.println(con2.getHeaderFields().toString());
			BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				System.out.println(decodedString);
			}
			
			con2.disconnect();
			in.close();	*/
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
