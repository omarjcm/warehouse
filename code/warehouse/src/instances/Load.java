/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import data.LecturaArchivo;
import java.util.Properties;

/**
 *
 * @author omarjcm
 */
public abstract class Load {
    
    protected String path;

    public Load(String parameter) {
        LecturaArchivo archivo = new LecturaArchivo("data.properties");
        Properties objeto = archivo.cargar();
        
        this.path = objeto.getProperty( parameter );
    }
    
    public abstract Object readInstances();
}