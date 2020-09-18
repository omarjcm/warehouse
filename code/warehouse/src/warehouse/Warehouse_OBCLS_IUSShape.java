/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import experiments.Register;
import instances.Batch;
import instances.Instance;
import instances.LoadInstancesSShape;
import instances.Order;
import java.util.ArrayList;
import java.util.List;
import jobprp.LS_1x0;
import jobprp.LS_1x1;
import jobprp.LS_1x2;
import jobprp.LS_2x2;

/**
 *
 * @author omarjcm
 */
public class Warehouse_OBCLS_IUSShape {
    
    public static void main(String[] args) {
                Configuration warehouse = new Configuration( 
                Configuration.NUM_EXTRA_CROSS_AISLES[0], 
                Configuration.NUM_AISLES[0], 
                Configuration.NUM_STORAGE_LOCATIONS[0] );
        warehouse.loadConfiguration();
        
        LoadInstancesSShape masterSS = new LoadInstancesSShape();
        List<Instance> instances = masterSS.readInstances( );
        ArrayList<Register> registers = new ArrayList<Register>();
        
        System.out.println("S-SHAPE");
        
        for (Instance instance : instances) {
            System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
            /**
             * GREEDY 01 - LOCAL SEARCH - S-SHAPE
             */
            System.out.println("GREEDY 01 - LS 1x0 - SSHAPE");
            long startTime = System.nanoTime();
            LS_1x0 obpr1x0 = new LS_1x0(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.S_SHAPE);
            obpr1x0.procedure();
            ArrayList<Batch> batches = Batch.copyOfBatches( obpr1x0.solutions );
            ArrayList<Batch> initialBatches = Batch.copyOfBatches( obpr1x0.initialSolutions );
            long estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.S_SHAPE, Constant.LS_1X0, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 01 - LS 1x1 - SSHAPE");
            startTime = System.nanoTime();
            LS_1x1 obpr1x1 = new LS_1x1(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.S_SHAPE);
            obpr1x1.procedure();
            batches = Batch.copyOfBatches( obpr1x1.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x1.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.S_SHAPE, Constant.LS_1X1, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 01 - LS 1x2 - SSHAPE");
            startTime = System.nanoTime();
            LS_1x2 obpr1x2 = new LS_1x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.S_SHAPE);
            obpr1x2.procedure();
            batches = Batch.copyOfBatches( obpr1x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.S_SHAPE, Constant.LS_1X2, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 01 - LS 2x2 - SSHAPE");
            startTime = System.nanoTime();
            LS_2x2 obpr2x2 = new LS_2x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.S_SHAPE);
            obpr2x2.procedure();
            batches = Batch.copyOfBatches( obpr2x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr2x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.S_SHAPE, Constant.LS_2X2, Constant.INSTANCES_UNIFORM) );
            /**
             * GREEDY 02 - LOCAL SEARCH - S-SHAPE
             */
            System.out.println("GREEDY 02 - LS 1x0 - SSHAPE");
            startTime = System.nanoTime();
            obpr1x0 = new LS_1x0(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.S_SHAPE);
            obpr1x0.procedure();
            batches = Batch.copyOfBatches( obpr1x0.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x0.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.S_SHAPE, Constant.LS_1X0, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 02 - LS 1x1 - SSHAPE");
            startTime = System.nanoTime();
            obpr1x1 = new LS_1x1(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.S_SHAPE);
            obpr1x1.procedure();
            batches = Batch.copyOfBatches( obpr1x1.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x1.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.S_SHAPE, Constant.LS_1X1, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 02 - LS 1x2 - SSHAPE");
            startTime = System.nanoTime();
            obpr1x2 = new LS_1x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.S_SHAPE);
            obpr1x2.procedure();
            batches = Batch.copyOfBatches( obpr1x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.S_SHAPE, Constant.LS_1X2, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 02 - LS 2x2 - SSHAPE");
            startTime = System.nanoTime();
            obpr2x2 = new LS_2x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.S_SHAPE);
            obpr2x2.procedure();
            batches = Batch.copyOfBatches( obpr2x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr2x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.S_SHAPE, Constant.LS_2X2, Constant.INSTANCES_UNIFORM) );
            
            /**
             * GREEDY 03 - LOCAL SEARCH - S-SHAPE
             */
            System.out.println("GREEDY 03 - LS 1x0 - SSHAPE");
            startTime = System.nanoTime();
            obpr1x0 = new LS_1x0(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.S_SHAPE);
            obpr1x0.procedure();
            batches = Batch.copyOfBatches( obpr1x0.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x0.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.S_SHAPE, Constant.LS_1X0, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 03 - LS 1x1 - SSHAPE");
            startTime = System.nanoTime();
            obpr1x1 = new LS_1x1(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.S_SHAPE);
            obpr1x1.procedure();
            batches = Batch.copyOfBatches( obpr1x1.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x1.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.S_SHAPE, Constant.LS_1X1, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 03 - LS 1x2 - SSHAPE");
            startTime = System.nanoTime();
            obpr1x2 = new LS_1x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.S_SHAPE);
            obpr1x2.procedure();
            batches = Batch.copyOfBatches( obpr1x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.S_SHAPE, Constant.LS_1X2, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 03 - LS 2x2 - SSHAPE");
            startTime = System.nanoTime();
            obpr2x2 = new LS_2x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.S_SHAPE);
            obpr2x2.procedure();
            batches = Batch.copyOfBatches( obpr2x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr2x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.S_SHAPE, Constant.LS_2X2, Constant.INSTANCES_UNIFORM) );
        }
        
        Register.procedureLS( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OB_LOCAL_SEARCH, "SS_", registers );
        Register.summaryProcedureLS( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OB_LOCAL_SEARCH, "SS_", registers );
        
        registers = new ArrayList<Register>();
        
        System.out.println("LARGEST GAP");
        for (Instance instance : instances) {
            System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
            
            /**
             * GREEDY 01 - LOCAL SEARCH - LARGEST GAP
             */
            System.out.println("GREEDY 01 - LS 1x0 - LGAP");
            long startTime = System.nanoTime();
            LS_1x0 obpr1x0 = new LS_1x0(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.LARGEST_GAP);
            obpr1x0.procedure();
            ArrayList<Batch> batches = Batch.copyOfBatches( obpr1x0.solutions );
            ArrayList<Batch> initialBatches = Batch.copyOfBatches( obpr1x0.initialSolutions );
            long estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.LARGEST_GAP, Constant.LS_1X0, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 01 - LS 1x1 - LGAP");
            startTime = System.nanoTime();
            LS_1x1 obpr1x1 = new LS_1x1(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.LARGEST_GAP);
            obpr1x1.procedure();
            batches = Batch.copyOfBatches( obpr1x1.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x1.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.LARGEST_GAP, Constant.LS_1X1, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 01 - LS 1x2 - LGAP");
            startTime = System.nanoTime();
            LS_1x2 obpr1x2 = new LS_1x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.LARGEST_GAP);
            obpr1x2.procedure();
            batches = Batch.copyOfBatches( obpr1x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.LARGEST_GAP, Constant.LS_1X2, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 01 - LS 2x2 - LGAP");
            startTime = System.nanoTime();
            LS_2x2 obpr2x2 = new LS_2x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_01, Constant.LARGEST_GAP);
            obpr2x2.procedure();
            batches = Batch.copyOfBatches( obpr2x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr2x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_01, Constant.LARGEST_GAP, Constant.LS_2X2, Constant.INSTANCES_UNIFORM) );
            
            /**
             * GREEDY 02 - LOCAL SEARCH - LARGEST GAP
             */
            System.out.println("GREEDY 02 - LS 1x0 - LGAP");
            startTime = System.nanoTime();
            obpr1x0 = new LS_1x0(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.LARGEST_GAP);
            obpr1x0.procedure();
            batches = Batch.copyOfBatches( obpr1x0.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x0.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.LARGEST_GAP, Constant.LS_1X0, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 02 - LS 1x1 - LGAP");
            startTime = System.nanoTime();
            obpr1x1 = new LS_1x1(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.LARGEST_GAP);
            obpr1x1.procedure();
            batches = Batch.copyOfBatches( obpr1x1.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x1.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.LARGEST_GAP, Constant.LS_1X1, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 02 - LS 1x2 - LGAP");
            startTime = System.nanoTime();
            obpr1x2 = new LS_1x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.LARGEST_GAP);
            obpr1x2.procedure();
            batches = Batch.copyOfBatches( obpr1x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.LARGEST_GAP, Constant.LS_1X2, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 02 - LS 2x2 - LGAP");
            startTime = System.nanoTime();
            obpr2x2 = new LS_2x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_02, Constant.LARGEST_GAP);
            obpr2x2.procedure();
            batches = Batch.copyOfBatches( obpr2x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr2x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_02, Constant.LARGEST_GAP, Constant.LS_2X2, Constant.INSTANCES_UNIFORM) );
            
            /**
             * GREEDY 03 - LOCAL SEARCH - LARGEST GAP
             */
            System.out.println("GREEDY 03 - LS 1x0 - LGAP");
            startTime = System.nanoTime();
            obpr1x0 = new LS_1x0(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.LARGEST_GAP);
            obpr1x0.procedure();
            batches = Batch.copyOfBatches( obpr1x0.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x0.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.LARGEST_GAP, Constant.LS_1X0, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 03 - LS 1x1 - LGAP");
            startTime = System.nanoTime();
            obpr1x1 = new LS_1x1(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.LARGEST_GAP);
            obpr1x1.procedure();
            batches = Batch.copyOfBatches( obpr1x1.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x1.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.LARGEST_GAP, Constant.LS_1X1, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 03 - LS 1x2 - LGAP");
            startTime = System.nanoTime();
            obpr1x2 = new LS_1x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.LARGEST_GAP);
            obpr1x2.procedure();
            batches = Batch.copyOfBatches( obpr1x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr1x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.LARGEST_GAP, Constant.LS_1X2, Constant.INSTANCES_UNIFORM) );
            
            System.out.println("GREEDY 03 - LS 2x2 - LGAP");
            startTime = System.nanoTime();
            obpr2x2 = new LS_2x2(instance.numOrders, instance.numCapacity, 
                    Order.copyOfOrders( instance.orders ), warehouse, Constant.GREEDY_03, Constant.LARGEST_GAP);
            obpr2x2.procedure();
            batches = Batch.copyOfBatches( obpr2x2.solutions );
            initialBatches = Batch.copyOfBatches( obpr2x2.initialSolutions );
            estimatedTime = System.nanoTime() - startTime;
            
            registers.add( new Register(estimatedTime, instance, initialBatches, batches, warehouse, 
                    Constant.GREEDY_03, Constant.LARGEST_GAP, Constant.LS_2X2, Constant.INSTANCES_UNIFORM) );
        }
        
        Register.procedureLS( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OB_LOCAL_SEARCH, "LG_", registers );
        Register.summaryProcedureLS( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.OB_LOCAL_SEARCH, "LG_", registers );
        
        System.gc();
    }
}