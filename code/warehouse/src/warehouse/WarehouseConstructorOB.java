/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import experiments.Register;
import instances.Batch;
import instances.Instance;
import instances.LoadInstancesLG;
import instances.LoadInstancesSShape;
import instances.Order;
import java.util.ArrayList;
import java.util.List;
import obp.OrderBatching;
import prp.PickerRouting;

/**
 *
 * @author omarjcm
 */
public class WarehouseConstructorOB {
    public static void main(String[] args) {
        Configuration warehouse = new Configuration( 
                Configuration.NUM_EXTRA_CROSS_AISLES[1], 
                Configuration.NUM_AISLES[0], 
                Configuration.NUM_STORAGE_LOCATIONS[0] );
        warehouse.loadConfiguration();

        LoadInstancesSShape masterSS = new LoadInstancesSShape();
        List<Instance> instances = masterSS.readInstances( );
        
        ArrayList<Register> registers = new ArrayList<Register>();

        for (Instance instance : instances) {
            System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
            /**
             * FIRST COME FIRST SERVE
             */
            long startTime = System.nanoTime();
            OrderBatching ob = new OrderBatching(instance.numOrders, instance.numCapacity, warehouse.numLocations);
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.FIRST_COME_FIRST_SERVE);
            ArrayList<Batch> batches = Batch.copyOfBatches( ob.batches );
            PickerRouting pr = new PickerRouting( warehouse, Constant.S_SHAPE );
            batches = pr.procedure( ob );
            long estimatedTime = System.nanoTime() - startTime;

            Register register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.FIRST_COME_FIRST_SERVE, Constant.S_SHAPE, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * RANDOM
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.RANDOM);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.S_SHAPE );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.RANDOM, Constant.S_SHAPE, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * STRICT_ORDER_PICKING
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.STRICT_ORDER_PICKING);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.S_SHAPE );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.STRICT_ORDER_PICKING, Constant.S_SHAPE, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * GREEDY_01
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.GREEDY_01);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.S_SHAPE );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.GREEDY_01, Constant.S_SHAPE, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * GREEDY_02
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.GREEDY_02);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.S_SHAPE );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.GREEDY_02, Constant.S_SHAPE, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * GREEDY_03
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.GREEDY_03);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.S_SHAPE );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.GREEDY_03, Constant.S_SHAPE, Constant.INSTANCES_UNIFORM);
            registers.add( register );
        }
        
        Register.procedureOB( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OBC, "SS_", registers );
        Register.summaryProcedureOB( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OBC, "SS_", registers );
        
        LoadInstancesLG masterLG = new LoadInstancesLG();
        instances = masterLG.readInstances( );
        
        registers = new ArrayList<Register>();
        System.gc();
        
        for (Instance instance : instances) {
            System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
            /**
             * FIRST COME FIRST SERVE
             */
            long startTime = System.nanoTime();
            OrderBatching ob = new OrderBatching(instance.numOrders, instance.numCapacity, warehouse.numLocations);
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.FIRST_COME_FIRST_SERVE);
            ArrayList<Batch> batches = Batch.copyOfBatches( ob.batches );
            PickerRouting pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
            long estimatedTime = System.nanoTime() - startTime;

            Register register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.FIRST_COME_FIRST_SERVE, Constant.LARGEST_GAP, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * RANDOM
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.RANDOM);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.RANDOM, Constant.LARGEST_GAP, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * STRICT_ORDER_PICKING
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.STRICT_ORDER_PICKING);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.STRICT_ORDER_PICKING, Constant.LARGEST_GAP, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * GREEDY_01
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.GREEDY_01);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.GREEDY_01, Constant.LARGEST_GAP, Constant.INSTANCES_UNIFORM);
            registers.add( register );
            /**
             * GREEDY_02
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.GREEDY_02);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.GREEDY_02, Constant.LARGEST_GAP, Constant.INSTANCES_UNIFORM );
            registers.add( register );
            /**
             * GREEDY_03
             */
            startTime = System.nanoTime();
            ob.procedure(warehouse.productLocation, Order.copyOfOrders( instance.orders ), Constant.GREEDY_03);
            batches = Batch.copyOfBatches( ob.batches );
            pr = new PickerRouting( warehouse, Constant.LARGEST_GAP );
            batches = pr.procedure( ob );
            estimatedTime = System.nanoTime() - startTime;

            register = new Register(estimatedTime, instance, batches, warehouse, 
                    Constant.GREEDY_03, Constant.LARGEST_GAP, Constant.INSTANCES_UNIFORM );
            registers.add( register );
        }
        
        Register.procedureOB( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OBC, "LG_", registers );
        Register.summaryProcedureOB( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OBC, "LG_", registers );
    }
}