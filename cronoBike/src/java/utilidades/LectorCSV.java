/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jabih
 */
public class LectorCSV {
    
    public static String procesarFichero(File fichero){
        BufferedReader br = null;
        String msg="";
		
		try {
			String leido;
			br = new BufferedReader(new FileReader(fichero));
			// How to read file in java line by line?
			while ((leido = br.readLine()) != null) {
				System.out.println("Raw CSV data: " + leido);
                                
                                msg += leido + ";";
                                
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
		}
                
                return msg;
    }
    
    
    
}
