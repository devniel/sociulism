package models;

/*
 * Author : Daniel Flores
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Ulima {
	
	/*
	 * Método que permite obtener la página html de perfil de usuario Ulima.
	 */
	
	public static String login(String codigo, String password) throws Exception{
		String response = "";
		
		try 
		{
			
			/*
			 * This cookie manager is important for save cookies through the request
			 */
			
			CookieManager manager = new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(manager);
			
			System.setProperty("http.keepAlive", "false");
			
			String url 		= "https://webaloe.ulima.edu.pe/portalUL/j_security_check";
			String charset 	= "ISO-8859-1";
			String param1 	= codigo;
			String param2 	= password;
			String query 	= String.format("j_username=%s&j_password=%s",URLEncoder.encode(param1,charset),URLEncoder.encode(param2,charset));
			
			HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Accept-Charset",charset);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=" + charset);
			OutputStream output = null;
			
			try
			{
				output = connection.getOutputStream();
				output.write(query.getBytes(charset));
			}
			finally
			{
				if(output != null) try { output.close(); } catch(IOException logOrIgnore)
				{
					System.out.println("FAIL");
				}
			}
			
			// Make request
			connection.connect();

			// THIS LINE OF CODE IS SO IMPORTANT FOR SAVE THE COOKIES IN THE COOKIESTORE [*]
			connection.getHeaderFields().get("Set-Cookie");

			// Detect if the user and password are correct
			CookieStore cookieStore = manager.getCookieStore();
			List<HttpCookie> cookies = cookieStore.getCookies();

			connection.disconnect();

			// The cookies for session has been saved by the cookie manager.
			// Now the application make a request to another URL ["Consolidado de Matrícula"]
			String url1 = "http://webaloe.ulima.edu.pe/portalUL/layout.jsp";
			String url2 = "http://webaloe.ulima.edu.pe/portalUL/gama/servlets/ComandoMostrarConsMatr?COCICLO=20122&Fg=1";
			URLConnection connection2 = new URL(url2).openConnection();

			connection2.setRequestProperty("Accept-Charset", charset);
			connection2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
			connection2.setDoInput(true);
			connection2.setDoOutput(true);


			// SET COOKIES IN CASE THAT COOKIEMANAGER DOESN´T WORK

			String cookiess = "";
			String cook = "";

			for(HttpCookie cookie: cookies){
				cook = cookie.getName() + "=" + cookie.getValue() + "; ";
				cookiess += cook;
			}

			connection2.setRequestProperty("Cookie",cookiess);

			connection2.connect();
			
			// Read and show response
			
		    BufferedReader reader = null;
		    
		    try 
		    {
		        reader = new BufferedReader(new InputStreamReader(connection2.getInputStream(), charset));
		        
		        for (String line; (line = reader.readLine()) != null;) 
		        {
		            response+=line;
		        }
		        
		    } 
		    finally 
		    {
		        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
		    }
		    		    
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
				
		if(response.length() <= 460)
		{
			// Not a user
			throw new Exception("No es usuario");
		}
		else
		{
			return response;
		}
	}


	/*
	 * A partir de la página de perfil de usuario se obtiene los scripts contenidos
	 */
	
	public static String getJS(String html){
		Document doc = Jsoup.parse(html);
		Elements scripts = doc.getElementsByTag("script");
		Element script = scripts.get(2);
		String js = script.html();
		return js;
	}
	
	/*
	 * Se ejecuta el script del contenido de la página de perfil de usuario,
	 * mediante el cual se obtiene un objeto JSON con toda la información de los
	 * cursos del usuario.
	 */
	
	public static JSONArray getCourses(String html){
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
		String js = getJS(html);
		JSONArray cursos = null;
		
		try {
			String currentDir = new File("./app/models/ulima.js").getAbsolutePath();
			System.out.println("CURRENT DIRECTORY : " + currentDir);
			File script = new File(currentDir);
            Reader reader = new FileReader(script);
			engine.eval("var msg = '" + js + "';");
			engine.eval(reader);
			cursos = (JSONArray) engine.get("courses");			 
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
		
		return cursos;
	}

	// How to run this with cmd?
	// javac -cp .;C:/jsoup-1.6.3.jar;C:\play-2.0.3\repository\local\org.json\json\20080701\jars\json.jar  models/Ulima.java
	// java -cp .;C:/jsoup-1.6.3.jar;C:\play-2.0.3\repository\local\org.json\json\20080701\jars\json.jar  models/Ulima
}
