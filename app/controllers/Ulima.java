package controllers;

/*
 * Author : Daniel Flores
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Ulima {
	
	public static void login(String codigo, String password){
		try {
			
			/*
			 * This cookie manager is important for save cookies through the request
			 */
			
			CookieManager manager = new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(manager);
			
			System.setProperty("http.keepAlive", "false");
			String url = "https://webaloe.ulima.edu.pe/portalUL/j_security_check";
			String charset = "UTF-8";
			String param1 = codigo;
			String param2 = password;
			String query = String.format("j_username=%s&j_password=%s",
					URLEncoder.encode(param1,charset),
					URLEncoder.encode(param2,charset));
			
			HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Accept-Charset",charset);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=" + charset);
			OutputStream output = null;
			
			try{
				output = connection.getOutputStream();
				output.write(query.getBytes(charset));
			}finally{
				if(output != null) try { output.close(); } catch(IOException logOrIgnore){
					System.out.println("FAIL");
				}
			}
			
			// Make request
			connection.connect();
			
			List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
			
			System.out.println("COOKIES --> " + cookies);
			
			connection.disconnect();
			
			// The cookies for session has been saved by the cookie manager.
			URLConnection connection2 = new URL("http://webaloe.ulima.edu.pe/portalUL/layout.jsp").openConnection();
			connection2.setRequestProperty("Accept-Charset", charset);
			connection2.setDoInput(true);
			connection2.setDoOutput(true);
			
			// Read and show response
			
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new InputStreamReader(connection2.getInputStream(), charset));
		        for (String line; (line = reader.readLine()) != null;) {
		            System.out.println(line);
		        }
		    } finally {
		        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
		    }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		System.out.println("Empezar request ...");
		login("20082219","XXXXX");
	}

}
