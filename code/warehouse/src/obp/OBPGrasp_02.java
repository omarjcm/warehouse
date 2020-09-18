/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import instances.Random;
import java.util.ArrayList;
import java.util.Collections;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class OBPGrasp_02 extends OBPGraspAlgorithm {
    
    public OBPGrasp_02(ArrayList<Order> orders, int capacityByBatch) {
        super(orders);
        this.capacityByBatch = capacityByBatch;
        this.alpha = Math.random();
    }
    
    public OBPGrasp_02(ArrayList<Order> orders, int capacityByBatch, double alpha) {
        super(orders);
        this.capacityByBatch = capacityByBatch;
        this.alpha = alpha;
    }
    
    @Override
    public void procedure() {
        ArrayList<Order> candidateList = Order.copyOfOrders( this.orders );
        Collections.sort(candidateList, ORDER_ASC_COMPARATOR);
        
        while (candidateList.size() > 0) {
            ArrayList<Order> restrictedCandidateList = createRestrictedCandidateList( candidateList, Constant.ASC );
            int indexRandom = Random.getRandomIndex( restrictedCandidateList.size() );
            Order order = new Order( restrictedCandidateList.get( indexRandom ) );
            
            if (this.batches.size() > 0) {
                int sizeBatches = this.batches.size();
                for (int i=0; i<sizeBatches; i++) {
                    Batch selectedBatch =  this.batches.get( i );
                    
                    if (this.capacityByBatch >= (order.totalItems + selectedBatch.totalItemsBatch)) {
                        selectedBatch.orders.add( order );
                        selectedBatch.totalItemsBatch += order.totalItems;
                        
                        this.batches.set(i, selectedBatch);
                        break;
                    } else if (i == sizeBatches-1) {
                        this.addOrderToBatch( order );
                    }
                }
            } else {
                this.addOrderToBatch( order );
            }
            candidateList.remove( indexRandom );
        }
    }
}