/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import experiments.Register;
import instances.Instance;
import instances.LoadInstancesSShape;
import instances.Order;
import java.util.ArrayList;
import java.util.List;
import jobprp.JOBPRPGrasp;

/**
 *
 * @author omarjcm
 */
public class WarehouseGrasp_01_IUSShape {
    
    public static void main(String[] args) {
                Configuration warehouse = new Configuration( 
                Configuration.NUM_EXTRA_CROSS_AISLES[1], 
                Configuration.NUM_AISLES[0], 
                Configuration.NUM_STORAGE_LOCATIONS[0] );
        warehouse.loadConfiguration();
        
        LoadInstancesSShape masterSS = new LoadInstancesSShape();
        List<Instance> instances = masterSS.readInstances( );
        ArrayList<Register> registers = new ArrayList<Register>();
        
        int numIterations = 10000;
        
        System.out.println("GRASP 01 - LS - S-SHAPE");
        for (Instance instance : instances) {
            if (instance.idInstance.indexOf("-10.txt") != -1) {
                System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
                /**
                 * GRASP 01 - LS 1x0 - SSHape
                 */
                double[] alpha = new double[1];
                alpha[0] = Math.random();

                //System.out.println("GRASP 01 - LS 1x0 - SSHape");
                long startTime = 0;
                long estimatedTime = 0;
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.S_SHAPE, 
                            Constant.LS_1X0, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.S_SHAPE, Constant.LS_1X0, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                //System.out.println("GRASP 01 - LS 1x1 - SSHape");
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.S_SHAPE, 
                            Constant.LS_1X1, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.S_SHAPE, Constant.LS_1X1, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                //System.out.println("GRASP 01 - LS 1x2 - SSHape");
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.S_SHAPE, 
                            Constant.LS_1X2, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.S_SHAPE, Constant.LS_1X2, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                //System.out.println("GRASP 01 - LS 2x2 - SSHape");
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.S_SHAPE, 
                            Constant.LS_2X2, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.S_SHAPE, Constant.LS_2X2, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                Register.procedureGRASP( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.GRASP_01, "SS_" + instance.idInstance + "_" , registers );
                Register.summaryProcedureGRASP( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.GRASP_01, "SS_" + instance.idInstance + "_", registers );
            }
        }
        registers = new ArrayList<Register>();
        
        System.out.println("GRASP 01 - LS - LARGEST GAP");
        for (Instance instance : instances) {
            if (instance.idInstance.indexOf("-10.txt") != -1) {
                System.out.println("\nInstance: " + instance.idInstance + " - Num. Orders: " + instance.numOrders + " - Capacity of Picking Device: " + instance.numCapacity);
                /**
                 * GRASP 01 - LS 1x0 - SSHape
                 */
                double[] alpha = new double[1];
                alpha[0] = Math.random();

                //System.out.println("GRASP 01 - LS 1x0 - SSHape");
                long startTime = 0;
                long estimatedTime = 0;
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.LARGEST_GAP, 
                            Constant.LS_1X0, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.LARGEST_GAP, Constant.LS_1X0, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                //System.out.println("GRASP 01 - LS 1x1 - SSHape");
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.LARGEST_GAP, 
                            Constant.LS_1X1, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.LARGEST_GAP, Constant.LS_1X1, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                //System.out.println("GRASP 01 - LS 1x2 - SSHape");
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.LARGEST_GAP, 
                            Constant.LS_1X2, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.LARGEST_GAP, Constant.LS_1X2, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }

                //System.out.println("GRASP 01 - LS 2x2 - SSHape");
                for (int iteration=1; iteration<=numIterations; iteration=iteration*10) {
                    startTime = System.currentTimeMillis();
                    JOBPRPGrasp object = new JOBPRPGrasp(instance.numOrders, instance.numCapacity, 
                            Order.copyOfOrders( instance.orders ), warehouse, Constant.GRASP_01, Constant.LARGEST_GAP, 
                            Constant.LS_2X2, iteration, alpha[0]);
                    object.procedure();
                    estimatedTime = System.currentTimeMillis() - startTime;

                    Register register = new Register(estimatedTime, instance, object.bestSolution, warehouse, 
                            Constant.GRASP_01, Constant.LARGEST_GAP, Constant.LS_2X2, iteration, alpha, Constant.INSTANCES_UNIFORM);
                    registers.add( register);
                }
                
                Register.procedureGRASP( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.GRASP_01, "LG_" + instance.idInstance + "_" , registers );
                Register.summaryProcedureGRASP( Constant.INSTANCES_UNIFORM, Constant.S_SHAPE, Constant.GRASP_01, "LG_" + instance.idInstance + "_", registers );
            }
        }
        
        System.gc();
    }
}