/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobprp;

import instances.Batch;
import instances.Order;
import java.util.ArrayList;
import obp.OrderBatching;
import prp.PickerRouting;
import warehouse.Configuration;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class JOBPRPLocalSearchTest {
    
    private final int numOrders;
    private final int numCapacity;
    private final int typeOfOBAlgorithm;
    private final int typeOfLSAlgorithm;
    private final int typeOfPRAlgorithm;
    private ArrayList<Order> orders;
    private Configuration warehouse;
    
    public JOBPRPLocalSearchTest(int numOrders, int numCapacity, ArrayList<Order> orders, 
            Configuration warehouse, int typeOfOBAlgorithm, int typeOfLSAlgorithm, int typeOfPRAlgorithm) {
        this.numOrders = numOrders;
        this.numCapacity = numCapacity;
        this.orders = Order.copyOfOrders( orders );
        this.warehouse = warehouse;
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfLSAlgorithm = typeOfLSAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
    }
    
    private int[] getIndexBatches(int numOrdersExchange1, int numOrdersExchange2) {
        int[] indexBatches = new int[ numOrdersExchange1 + numOrdersExchange2 ];
        return indexBatches;
    }
    
    public void procedure() {
        ArrayList<Batch> batches = new ArrayList<Batch>();
        
        /**
         * Construcción de la solución inicial.
         */
        OrderBatching object = new OrderBatching(this.numOrders, this.numCapacity, warehouse.numLocations);
        object.procedure(warehouse.productLocation, this.orders, this.typeOfOBAlgorithm);
        batches = Batch.copyOfBatches( object.batches );
        
        PickerRouting route = new PickerRouting( this.warehouse, this.typeOfPRAlgorithm );
        batches = route.procedure( object );
        
        for (int i=0; i<this.orders.size() * 10; i++) {
            /*
             * PASO 1: Obtengo dos indices aleatorios de lotes diferentes.
             */
            int batchesSize = batches.size();
            int[] LSBatches = LSBatch.getRandomDifferents(2, batchesSize);
            
            /**
             * PASO 2: Dependiendo del tipo de algoritmo LS, se obtienen los indices aleatorios
             * de los pedidos
             */
            LSBatch[] indexBatchList = LSBatch.initializeLSBatch(this.typeOfLSAlgorithm, LSBatches, batchesSize, batches);
            
            int capacity_01 = 0, capacity_01_batch = 0, capacity_01_orders = 0;
            int capacity_02 = 0, capacity_02_batch = 0, capacity_02_orders = 0;
            boolean guard = Boolean.FALSE;
            
            int index = 0;
            if (indexBatchList[ index ].getIndexOrderList() != null && 
                    indexBatchList[ index+1 ].getIndexOrderList() != null) {
                // Batch 1
                capacity_01_batch = indexBatchList[ index ].getTotalItemsBatch();
                Batch batch = new Batch( batches.get( LSBatches[ index ] ) );
                for (int j=0; j<indexBatchList[ index ].getIndexOrderList().length; j++) {
                    Order order = new Order( batch.orders.get( indexBatchList[ index ].getIndexOrderList()[j] ) );
                    capacity_01_orders += order.totalItems;
                }
                // Batch 2
                capacity_02_batch = indexBatchList[ index+1 ].getTotalItemsBatch();
                batch = new Batch( batches.get( LSBatches[ index+1 ] ) );
                for (int j=0; j<indexBatchList[ index+1 ].getIndexOrderList().length; j++) {
                    Order order = new Order( batch.orders.get( indexBatchList[ index+1 ].getIndexOrderList()[j] ) );
                    capacity_02_orders += order.totalItems;
                }
                capacity_01 = capacity_01_batch - capacity_01_orders;
                capacity_02 = capacity_02_batch - capacity_02_orders;
                guard = Boolean.TRUE;
            }
            if (guard && (this.numCapacity >= (capacity_01 + capacity_02_orders)) &&
                    (this.numCapacity >= (capacity_02 + capacity_01_orders))) {
                index = 0;
                
                Batch batch_01 = batches.get( indexBatchList[ index ].getIndexBatch() );
                Batch batch_02 = batches.get( indexBatchList[ index+1 ].getIndexBatch() );
                
                if (this.typeOfLSAlgorithm == Constant.LS_1X1) {
                    batches = swap(batches, batch_01, batch_02, 
                            indexBatchList[ index ].getIndexBatch(), indexBatchList[ index+1 ].getIndexBatch(), 
                            indexBatchList[ index ].getIndexOrderList()[0], indexBatchList[ index+1 ].getIndexOrderList()[0]);
                }
                
                double sshape_01_distance_before = batch_01.register.sshapeDistance;
                double sshape_01_time_before = batch_01.register.sshapeDistance;
                object.procedure(warehouse.productLocation, indexBatchList[ index ].getIndexBatch(), batches);
                batch_01 = route.procedure(object, indexBatchList[ index ].getIndexBatch() );
                double sshape_01_distance_after = batch_01.register.sshapeDistance;
                double sshape_01_time_after = batch_01.register.sshapeDistance;
                
                double sshape_02_distance_before = batch_02.register.sshapeDistance;
                double sshape_02_time_before = batch_02.register.sshapeDistance;
                object.procedure(warehouse.productLocation, indexBatchList[ index+1 ].getIndexBatch(), batches);
                batch_02 = route.procedure(object, indexBatchList[ index+1 ].getIndexBatch() );
                double sshape_02_distance_after = batch_02.register.sshapeDistance;
                double sshape_02_time_after = batch_02.register.sshapeDistance;
                
                if ((sshape_01_distance_after > sshape_01_distance_before) || (sshape_01_time_after > sshape_01_time_before) || 
                        (sshape_02_distance_after > sshape_02_distance_before) || (sshape_02_time_after > sshape_02_time_before)) {
                    batches = swap(batches, batch_01, batch_02, 
                            indexBatchList[ index ].getIndexBatch(), indexBatchList[ index+1 ].getIndexBatch(), 
                            indexBatchList[ index ].getIndexOrderList()[0], indexBatchList[ index+1 ].getIndexOrderList()[0]);                    
                } else {
                    
                    System.out.println("======================");
                    System.out.print("D(a): " + sshape_01_distance_before + "\tT(a): " + sshape_01_time_before + "\t==> ");
                    System.out.println("D(d): " + sshape_01_distance_after + "\tT(d): " + sshape_01_time_after);
                    System.out.print("D(a): " + sshape_02_distance_before + "\tT(a): " + sshape_02_time_before + "\t==> ");
                    System.out.println("D(d): " + sshape_02_distance_after + "\tT(d): " + sshape_02_time_after);
                    System.out.println("======================");
                }
            }
        }
    }
    
    private ArrayList<Batch> swap(ArrayList<Batch> batches, Batch batch_01, Batch batch_02, 
            int index_batch_01, int index_batch_02, int index_order_01, int index_order_02) {
        Order order_01 = batch_01.orders.get( index_order_01 );
        Order order_02 = batch_02.orders.get( index_order_02 );

        Order order_temp = new Order(order_01);
        order_01 = new Order(order_02);
        order_02 = new Order(order_temp);

        batch_01.orders.set( index_order_01, order_01 );
        batch_01.totalItemsBatch -= order_02.totalItems;
        batch_01.totalItemsBatch += order_01.totalItems;
        
        batch_02.orders.set( index_order_02, order_02 );
        batch_02.totalItemsBatch -= order_01.totalItems;
        batch_02.totalItemsBatch += order_02.totalItems;

        batches.set(index_batch_01, batch_01);
        batches.set(index_batch_02, batch_02);
        
        return batches;
    }
}