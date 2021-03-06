/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import instances.OrderSimilarity;
import instances.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author omarjcm
 */
public class OBPGrasp_03 extends OBPGraspAlgorithm {
    
    public OBPGrasp_03(ArrayList<Order> orders, int capacityByBatch) {
        super(orders);
        this.capacityByBatch = capacityByBatch;
        this.alpha = Math.random();
    }
    
    public OBPGrasp_03(ArrayList<Order> orders, int capacityByBatch, double alpha) {
        super(orders);
        this.capacityByBatch = capacityByBatch;
        this.alpha = alpha;
    }
    
    @Override
    public void procedure() {
        Collections.sort(this.orders, Order.ORDER_DESC_COMPARATOR);
        
        ArrayList<OrderSimilarity> candidateList = getSimilarityList( );
        
        while (candidateList.size() > 0) {
            ArrayList<OrderSimilarity> restrictedCandidateList = createRestrictedCandidateList( candidateList );
            int indexRandom = Random.getRandomIndex( restrictedCandidateList.size() );
            OrderSimilarity os = new OrderSimilarity( restrictedCandidateList.get( indexRandom ) );
            
            Order order_01 = new Order( this.orders.get( os.order_01.index ), os.order_01.index );
            if (!order_01.isAssigned) {
                this.assignedOrderToBatch( order_01 );
            }
            
            Order order_02 = new Order(this.orders.get( os.order_02.index ), os.order_02.index);
            if (!order_02.isAssigned) {
                this.assignedOrderToBatch( order_02 );
            }
            candidateList.remove( indexRandom );
        }
    }
    
    private void setAssignedOrder(Order order) {
        order.isAssigned = Boolean.TRUE;
        this.orders.set(order.index, order);
    }

    private void assignedOrderToBatch( Order order ) {
        if (this.batches.size() > 0) {
            int sizeBatches = this.batches.size();
            
            for (int i=0; i<sizeBatches; i++) {
                Batch selectedBatch = new Batch( this.batches.get( i ) );

                if (this.capacityByBatch >= (order.totalItems + selectedBatch.totalItemsBatch)) {
                    this.setAssignedOrder( order );
                    selectedBatch.orders.add( order );
                    selectedBatch.totalItemsBatch += order.totalItems;
                    this.batches.set(i, selectedBatch);
                    break;
                } else if (i == (sizeBatches-1)) {
                    this.setAssignedOrder( order );
                    this.addOrderToBatch(order);
                }
            }
        } else {
            this.setAssignedOrder( order );
            this.addOrderToBatch( order );
        }
    }
    
    private ArrayList<OrderSimilarity> getSimilarityList( ) {
        ArrayList<OrderSimilarity> similarityList = new ArrayList<OrderSimilarity>();
        
        for (int i=0; i<this.orders.size(); i++) {
            Order order_01 = new Order( this.orders.get( i ), i );
            for (int j=i+1; j<this.orders.size(); j++) {
                Order order_02 = new Order( this.orders.get( j ), j );
                if (this.capacityByBatch >= (order_01.totalItems + order_02.totalItems)) {
                    similarityList.add( new OrderSimilarity( order_01, order_02 ) );
                }
            }
        }
        Collections.sort( similarityList, OrderSimilarity.SIMILARITY_DESC_COMPARATOR );
        
        return similarityList;
    }
}