/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import instances.OrderSimilarity;
import java.util.ArrayList;
import java.util.Comparator;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
abstract class OBPGraspAlgorithm {
    
    protected static Comparator<Order> ORDER_DESC_COMPARATOR = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o2.totalItems - o1.totalItems;
        }
    }; 
    
    protected static Comparator<Order> ORDER_ASC_COMPARATOR = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.totalItems - o2.totalItems;
        }
    }; 
    
    protected double alpha;
    protected ArrayList<Order> orders;
    protected int capacityByBatch;
    
    public ArrayList<Batch> batches;
    
    public OBPGraspAlgorithm(ArrayList<Order> orders) {
        this.orders = Order.copyOfOrders( orders );
        this.batches = new ArrayList<Batch>();
    }
    
    public abstract void procedure();
    
    protected ArrayList<Order> createRestrictedCandidateList(ArrayList<Order> candidateList, int typeOfComparison) {
        ArrayList<Order> restrictedCandidateList = new ArrayList<Order>( );
        
        int max = candidateList.get(0).totalItems;
        int min = candidateList.get( candidateList.size()-1 ).totalItems;
        double threshold = max - this.alpha * ( max - min );
        
        for (int i=0; i<candidateList.size(); i++) {
            Order order = candidateList.get(i);
            if (typeOfComparison == Constant.DESC && threshold <= order.totalItems) {
                restrictedCandidateList.add( new Order( order ) );
            } else if (typeOfComparison == Constant.ASC && threshold >= order.totalItems) {
                restrictedCandidateList.add( new Order( order ) );
            } else {
                break;
            }
        }
        return restrictedCandidateList;
    }
    
    protected ArrayList<OrderSimilarity> createRestrictedCandidateList(ArrayList<OrderSimilarity> candidateList) {
        ArrayList<OrderSimilarity> restrictedCandidateList = new ArrayList<OrderSimilarity>( );
        
        int max = candidateList.get(0).similarity;
        int min = candidateList.get( candidateList.size()-1 ).similarity;
        double threshold = max - this.alpha * ( max - min );
        
        for (int i=0; i<candidateList.size(); i++) {
            OrderSimilarity os = candidateList.get(i);
            if (threshold <= os.similarity) {
                restrictedCandidateList.add( new OrderSimilarity( os ) );
            } else {
                break;
            }
        }
        return restrictedCandidateList;
    }
    
    protected void addOrderToBatch(Order order) {
        Batch batch = new Batch( );
        batch.orders.add( order );
        batch.totalItemsBatch += order.totalItems;
        this.batches.add(batch);
    }    
}