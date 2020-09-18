/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import data.LecturaArchivo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import warehouse.Configuration;

/**
 *
 * @author omarjcm
 */
public class LoadInstancesLG extends Load {
    
    public LoadInstancesLG() {
        super("instances_iu_lgap");
    }
    
    @Override
    public List<Instance> readInstances() {
        List<Instance> instances = new ArrayList<Instance>();
        try {
            int count = 1;
            int initial = 1;
            for (int i=0; i<Configuration.ORDERS_LENGTH.length; i++) {
                for (int j=0; j<Configuration.CAPACITY_PICK_DEVICE.length; j++) {
                    for (int k=0; k<40; k++) {
                        String directory = "LG O"+Configuration.ORDERS_LENGTH[i]+" C"+Configuration.CAPACITY_PICK_DEVICE[j]+"/";
                        String nameFile = initial + "l-"+Configuration.ORDERS_LENGTH[i]+"-"+Configuration.CAPACITY_PICK_DEVICE[j]+"-"+k+".txt";
                        count++;
                        
                        File file = new File( this.path + directory + nameFile );
                        Scanner input = new Scanner(file);
                        
                        Instance instance = new Instance();
                        instance.idInstance = nameFile;
                        instance.numOrders = Configuration.ORDERS_LENGTH[i];
                        instance.numCapacity = Configuration.CAPACITY_PICK_DEVICE[j];
                        
                        Order order = new Order();
                        boolean guard = Boolean.FALSE;
                        while (input.hasNextLine()) {
                            String line = input.nextLine();
                            if (line.contains("Order")) {
                                if (guard) {
                                    instance.orders.add(order);
                                    order = new Order();
                                } else {
                                    order = new Order();
                                    guard = Boolean.TRUE;
                                }
                            } else {
                                Item item = new Item();
                                item.quantity = 1;
                                
                                int aisle = Integer.parseInt( line.substring(line.indexOf("Aisle") + "Aisle ".length(), line.indexOf("Location")-1 ) );
                                int location = Integer.parseInt( line.substring( line.indexOf("Location") + "Location ".length() ) );
                                item.id_product = ( aisle + 1 ) * ( location + 1 ) - 1;
                                order.totalItems++;
                                order.items.add(item);
                            }
                        }
                        if (guard) {
                            instance.orders.add(order);                            
                        }
                        instances.add( instance );
                    }
                    if (Configuration.ORDERS_LENGTH[i] == 60 && Configuration.CAPACITY_PICK_DEVICE[j] == 75)
                        initial = 41;
                    else
                        initial++;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadInstancesLG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instances;
    }
    
    public void main(String[] args) {
        List<Instance> instances = readInstances();
        
        for (Instance instance : instances) {
            System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
            for (Order order : instance.orders) {
                System.out.print( order.totalItems + ":" );
                for (Item item : order.items) {
                    if (item.id_product >= 100)
                        System.out.print( item.id_product + "-" );
                }
                System.out.println("");
            }
        }
    }
}