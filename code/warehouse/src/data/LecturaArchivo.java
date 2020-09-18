/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omarjcm
 */
public class LecturaArchivo {
    
    public String nombre_archivo;
    
    public LecturaArchivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }
    
    public Properties cargar() {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream( this.nombre_archivo );
        Properties prop = new Properties();
        
        try {
            prop.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(LecturaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;        
    }
}