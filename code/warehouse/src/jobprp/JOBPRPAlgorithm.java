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
abstract class JOBPRPAlgorithm {
    
    protected int numOrders;
    protected int numCapacity;
    protected ArrayList<Order> orders;
    protected Configuration warehouse;
    protected int typeOfOBAlgorithm;
    protected int typeOfPRAlgorithm;
    
    protected OrderBatching object;
    protected PickerRouting route;

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    public int getNumCapacity() {
        return numCapacity;
    }

    public void setNumCapacity(int numCapacity) {
        this.numCapacity = numCapacity;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Configuration getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Configuration warehouse) {
        this.warehouse = warehouse;
    }

    public int getTypeOfOBAlgorithm() {
        return typeOfOBAlgorithm;
    }

    public void setTypeOfOBAlgorithm(int typeOfOBAlgorithm) {
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
    }

    public int getTypeOfPRAlgorithm() {
        return typeOfPRAlgorithm;
    }

    public void setTypeOfPRAlgorithm(int typeOfPRAlgorithm) {
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
    }

    public OrderBatching getObject() {
        return object;
    }

    public void setObject(OrderBatching object) {
        this.object = object;
    }

    public PickerRouting getRoute() {
        return route;
    }

    public void setRoute(PickerRouting route) {
        this.route = route;
    }
    
    
    
    public JOBPRPAlgorithm(int numOrders, int numCapacity, ArrayList<Order> orders, 
            Configuration warehouse, int typeOfOBAlgorithm, int typeOfPRAlgorithm) {
        this.numOrders = numOrders;
        this.numCapacity = numCapacity;
        this.orders = orders;
        this.warehouse = warehouse;
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
    }
    
    public abstract void procedure();

    protected double getTotalDistance(ArrayList<Batch> batches) {
        if (this.typeOfPRAlgorithm == Constant.S_SHAPE) {
            return Batch.totalDistanceBatchesSShape( batches );
        } else if (this.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
            return Batch.totalDistanceBatchesLargestGap( batches );
        }
        return 0;
    }

}