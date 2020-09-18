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
abstract class JOBPRPLSAlgorithm {
    
    public int numOrders;
    public int numCapacity;
    public ArrayList<Order> orders;
    public Configuration warehouse;
    public int typeOfOBAlgorithm;
    public int typeOfPRAlgorithm;
    
    public OrderBatching object;
    public PickerRouting route;
    
    public ArrayList<Batch> initialSolutions;
    public ArrayList<Batch> solutions;
    
    public JOBPRPLSAlgorithm(int numOrders, int numCapacity, ArrayList<Order> orders, 
            Configuration warehouse, int typeOfOBAlgorithm, int typeOfPRAlgorithm) {
        this.numOrders = numOrders;
        this.numCapacity = numCapacity;
        this.orders = orders;
        this.warehouse = warehouse;
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
        
        this.initialSolutions = new ArrayList<Batch>();
        this.solutions = new ArrayList<Batch>();
    }
    
    protected ArrayList<Batch> initialSolutions( double[] paramaters ) {
        this.object = new OrderBatching(this.numOrders, this.numCapacity, this.warehouse.numLocations);
        this.object.procedure(this.warehouse.productLocation, this.orders, this.typeOfOBAlgorithm, paramaters);
        this.route = new PickerRouting( this.warehouse, this.typeOfPRAlgorithm );
        
        return ( this.route.procedure( this.object ) );
    }
    
    protected ArrayList<Batch> initialSolutions( ) {
        this.object = new OrderBatching(this.numOrders, this.numCapacity, this.warehouse.numLocations);
        this.object.procedure(this.warehouse.productLocation, this.orders, this.typeOfOBAlgorithm);
        this.route = new PickerRouting( this.warehouse, this.typeOfPRAlgorithm );
        
        return ( this.route.procedure( this.object ) );
    }
    
    protected Batch updatedSolution(ArrayList<Batch> batches, int indexBatch) {
        this.object.procedure(this.warehouse.productLocation, indexBatch, batches);
        return this.route.procedure(this.object, indexBatch);
    }
    
    protected int totalOrders(ArrayList<Batch> batches) {
        int countOrders = 0;
        for (int i=0; i<batches.size(); i++) {
            countOrders += batches.get(i).orders.size();
        }
        return countOrders;
    }
    
    protected double getTotalDistance(ArrayList<Batch> batches) {
        if (this.typeOfPRAlgorithm == Constant.S_SHAPE) {
            return Batch.totalDistanceBatchesSShape( batches );
        } else if (this.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
            return Batch.totalDistanceBatchesLargestGap( batches );
        }
        return 0;
    }
    
    protected double getDistance(Batch batch) {
        if (this.typeOfPRAlgorithm == Constant.S_SHAPE) {
            return batch.register.sshapeDistance;
        } else if (this.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
            return batch.register.largestGapDistance;
        }
        return 0;
    }
}