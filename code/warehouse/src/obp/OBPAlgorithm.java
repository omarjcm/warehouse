/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import java.util.ArrayList;

/**
 *
 * @author omarjcm
 */
abstract class OBPAlgorithm {
    
    public ArrayList<Batch> batches;
    public ArrayList<Order> orders;
    public int capacityByBatch;
    
    public OBPAlgorithm(ArrayList<Order> orders) {
        this.orders = Order.copyOfOrders( orders );
        this.batches = new ArrayList<Batch>();
    }
    
    public abstract void procedure();
    
    protected void addOrderToBatch(Order order) {
        Batch batch = new Batch( );
        batch.orders.add( order );
        batch.totalItemsBatch += order.totalItems;
        this.batches.add(batch);
    }
}