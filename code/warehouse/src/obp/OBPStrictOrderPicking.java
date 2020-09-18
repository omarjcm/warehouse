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
public class OBPStrictOrderPicking extends OBPAlgorithm {

    public OBPStrictOrderPicking(ArrayList<Order> orders, int capacityByBatch ) {
        super(orders);
        this.capacityByBatch = capacityByBatch;
    }

    @Override
    public void procedure() {
        for (Order order : this.orders) {
            Batch object = new Batch();
            object.orders.add( order );
            this.batches.add( object );
        }
    }
}
