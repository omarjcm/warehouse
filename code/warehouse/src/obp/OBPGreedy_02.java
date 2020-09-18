/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author omarjcm
 */
public class OBPGreedy_02 extends OBPAlgorithm {
    
    public OBPGreedy_02(ArrayList<Order> orders, int capacityByBatch) {
        super( orders );
        this.capacityByBatch = capacityByBatch;
    }
    
    @Override
    public void procedure() {
        Collections.sort(this.orders, Order.ORDER_ASC_COMPARATOR);
        
        for (int i = 0; i < this.orders.size(); i++) {
            if (this.batches.size() > 0) {
                Order order = this.orders.get(i);
                
                int sizeBatches = this.batches.size();
                for (int j=0; j<sizeBatches; j++) {
                    Batch selectedBatch = new Batch( this.batches.get( j ) );
                    
                    if (this.capacityByBatch >= (order.totalItems + selectedBatch.totalItemsBatch)) {
                        selectedBatch.orders.add(order);
                        selectedBatch.totalItemsBatch += order.totalItems;
                        this.batches.set(j, selectedBatch);
                        break;
                    } else if (j == (sizeBatches-1)) {
                        this.addOrderToBatch(order);
                    }
                }
            } else {
                this.addOrderToBatch( this.orders.get( i ) );
            }
        }
    }
}