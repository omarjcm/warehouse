/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import experiments.Register;
import instances.Batch;
import instances.Instance;
import instances.LoadInstancesLG;
import instances.Order;
import java.util.ArrayList;
import java.util.List;
import obp.OrderBatching;
import prp.PickerRouting;

/**
 *
 * @author omarjcm
 */
public class WarehouseOB_LG {
    
    public static void main(String[] args) {
        Configuration warehouse = new Configuration( 
                Configuration.NUM_EXTRA_CROSS_AISLES[1], 
                Configuration.NUM_AISLES[0], 
                Configuration.NUM_STORAGE_LOCATIONS[0] );
        warehouse.loadConfiguration();
        
        LoadInstancesLG masterLG = new LoadInstancesLG();
        List<Instance> instances = masterLG.readInstances( );
        
        ArrayList<Register> registers = new ArrayList<Register>();
        
        for (Instance instance : instances) {
            System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
            
            OrderBatching ob = new OrderBatching(instance.numOrders, instance.numCapacity, warehouse.numLocations);
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.FIRST_COME_FIRST_SERVE);
            ArrayList<Batch> batches = Batch.copyOfBatches( ob.batches );
            PickerRouting pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
        }
    }
}