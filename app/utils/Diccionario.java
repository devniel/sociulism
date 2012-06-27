package utils;
import java.io.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * FUENTE : http://www.lab.dit.upm.es/~lprg/material/apuntes/log/Diccionario.java
 */

public class Diccionario {
    private final int MAX = 100;
    private String[] palabras = new String[MAX];
    private final int primera = 0;
    private int ultima = -1;

    public Diccionario(String fichero) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));

            for (String linea = br.readLine(); linea != null; linea = br.readLine()) {
                if (this.ultima >= MAX - 1) {
                    continue;
                }
                System.out.println(linea);
                palabras[++this.ultima] = linea;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        }
    }

    public boolean esMalaPalabra(String mensaje) {
    	
    	System.out.println("MALAS PALABRAS");
    	System.out.println(String.valueOf(this.ultima));
    	boolean exists = false;
    	    	
    	for(int i=0;i<=this.ultima;i++){
    		System.out.println(palabras[i] +  " : " + mensaje + " <----> " + Pattern.matches(mensaje, palabras[i]));
    		
    		//boolean malaPalabra = Pattern.matches(palabras[i], mensaje );
    		boolean malaPalabra = (mensaje.toLowerCase().indexOf(palabras[i]) > -1)?true:false;
    		if(malaPalabra){
    			exists = true;
    			break;
    		}
    	}
       
        return exists;
    }

    /*public static void main(String[] args) {
    	Diccionario diccionario = new Diccionario(new File(".").getAbsolutePath() + "/resources/lisuras.txt");
        String palabra = "maricon";
        boolean exists = diccionario.esMalaPalabra(palabra);
        System.out.println(palabra + " : " + exists);
    }*/
}