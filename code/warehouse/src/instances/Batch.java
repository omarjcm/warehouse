/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import experiments.RegisterDistance;
import java.util.ArrayList;

/**
 *
 * @author omarjcm
 */
public class Batch {
    
    public ArrayList<Order> orders;
    public int totalItemsBatch;
    public boolean isClosed;
    public boolean isVisited;
    
    public RegisterDistance register;
    
    public Batch( Batch batch ) {
        this.totalItemsBatch = batch.totalItemsBatch;
        this.isClosed = batch.isClosed;
        this.isVisited = batch.isVisited;
        
        this.register = new RegisterDistance( batch.register );
        this.orders = Order.copyOfOrders( batch.orders );
    }
    
    public Batch( ) {
        this.totalItemsBatch = 0;
        this.isClosed = Boolean.FALSE;
        this.isVisited = Boolean.FALSE;
        
        this.register = new RegisterDistance();
        this.orders = new ArrayList<Order>();
    }

    public static double totalDistanceBatchesSShape( ArrayList<Batch> batches ) {
        double distances = 0;
        
        for (int i=0; i<batches.size(); i++) {
            distances += batches.get(i).register.sshapeDistance;
        }
        return distances;
    }
    
    public static double totalDistanceBatchesLargestGap( ArrayList<Batch> batches ) {
        double distances = 0;
        
        for (int i=0; i<batches.size(); i++) {
            distances += batches.get(i).register.largestGapDistance;
        }
        return distances;
    }
    
    public static double avgDistanceBatchesSShape( ArrayList<Batch> batches ) {
        double distances = 0;
        
        for (int i=0; i<batches.size(); i++) {
            distances += batches.get(i).register.sshapeDistance;
        }
        return (distances / batches.size());
    }
    
    public static double avgDistanceBatchesLargestGap( ArrayList<Batch> batches ) {
        double distances = 0;
        
        for (int i=0; i<batches.size(); i++) {
            distances += batches.get(i).register.largestGapDistance;
        }
        return (distances / batches.size());
    }
    
    public static void printBatches( ArrayList<Batch> batches ) {
        int count = 1;
        for (int i=0; i<batches.size(); i++) {
            Batch batch = new Batch( batches.get( i ) );
            System.out.println("Batch: " + (i+1) + " | "+batch.totalItemsBatch);
            for (int j=0; j<batch.orders.size(); j++) {
                Order order = new Order( batch.orders.get( j ) );
                System.out.print( (count) + " Index: " + order.index + " Size: " + order.totalItems + "\t|" );
                count++;
                for (int k=0; k<order.items.size(); k++) {
                    Item item = new Item( order.items.get(k) );
                    System.out.print("("+item.id_product+")("+item.quantity+")|");
                }
                System.out.println("");
            }
        }
    }
    
    public static void printBatches(ArrayList<Batch> batches, int index) {
        String batchesStr = "";
        System.out.println("Batch No. " + (index + 1) + "\tSize: " + batches.get(index).orders.size());
        for (Order order : batches.get(index).orders) {
            System.out.print("Size: " + order.items.size() + " -> Orders: ");
            for (Item item : order.items) {
                batchesStr += item.id_product + "|";
            }
            System.out.println( batchesStr );
            batchesStr = "";
        }
    }    
    
    public static ArrayList<Batch> copyOfBatches(ArrayList<Batch> batches) {
        ArrayList<Batch> batchesCopy = new ArrayList<Batch>();
        
        for (int i=0; i<batches.size(); i++) {
            batchesCopy.add( new Batch( batches.get(i) ) );
        }
        return batchesCopy;
    }
}