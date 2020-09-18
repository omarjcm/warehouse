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
public class OBPFCFS extends OBPAlgorithm {

    public OBPFCFS(ArrayList<Order> orders, int capacityByBatch) {
        super(orders);
        this.capacityByBatch = capacityByBatch;
    }
    
    @Override
    public void procedure() {
        for (int i = 0; i < this.orders.size(); i++) {
            if (this.batches.size() > 0) {
                Order order = this.orders.get(i);
                boolean guard = Boolean.TRUE;
                int index = 0;
                while (guard) {
                    if (!this.batches.get(index).isClosed && 
                            (order.totalItems + this.batches.get( index ).totalItemsBatch) <= this.capacityByBatch) {
                        this.batches.get(index).orders.add(order);
                        this.batches.get(index).totalItemsBatch += order.totalItems;
                        guard = Boolean.FALSE;
                    } else if (!this.batches.get(index).isClosed && 
                            (order.totalItems + this.batches.get(index).totalItemsBatch) > this.capacityByBatch) {
                        this.batches.get(index).isClosed = Boolean.TRUE;
                        addOrderToBatch( i );
                        guard = Boolean.FALSE;
                    }
                    index++;
                }
            } else {
                addOrderToBatch( i );
            }
        }
    }
    
    private void addOrderToBatch(int i) {
        Batch batch = new Batch();
        batch.orders.add(this.orders.get( i ));
        batch.totalItemsBatch += this.orders.get( i ).totalItems;
        this.batches.add(batch);
    }
}