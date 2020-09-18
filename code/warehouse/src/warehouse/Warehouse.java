/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import data.LecturaArchivo;
import java.util.Properties;

/**
 *
 * @author omarjcm
 */
public class Warehouse {
    
    public int num_cross_aisles;
    public int num_shelves;
    public double aisle_width;
    public int rack_depth;
    public double location_width;
    public double location_length;
    public double cross_aisle_width;
    public double cross_aisle_length;
    public double source_to_first_cross_aisle;
    
    public Warehouse() {
        
    }
    
    public void loadWarehouse( ) {
        LecturaArchivo archivo = new LecturaArchivo("data.properties");
        Properties objeto = archivo.cargar();
        
        this.num_cross_aisles = Integer.parseInt( objeto.getProperty("warehouse.num_cross_aisles") );
        this.num_shelves = Integer.parseInt( objeto.getProperty("warehouse.num_shelves") );
        this.aisle_width = Double.parseDouble( objeto.getProperty("warehouse.aisle_width") );
        this.rack_depth = Integer.parseInt( objeto.getProperty("warehouse.rack_depth") );
        this.location_width = Double.parseDouble( objeto.getProperty("warehouse.location_width") );
        this.location_length = Double.parseDouble( objeto.getProperty("warehouse.location_length") );
        this.cross_aisle_width = Double.parseDouble( objeto.getProperty("warehouse.cross_aisles_width") );
        this.cross_aisle_length = Double.parseDouble( objeto.getProperty("warehouse.cross_aisles_length") );
        this.source_to_first_cross_aisle = Double.parseDouble( objeto.getProperty("warehouse.source_to_first_cross_aisle") );
    }
}