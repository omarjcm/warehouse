/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import data.LecturaArchivo;
import instances.Batch;
import instances.Instance;
import instances.Item;
import instances.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import warehouse.Configuration;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class Register {
    
    private int numCrossAisles;
    // { 10, 20, 30 }
    private int numAisles;
    // { 1000, 2000, 3000 }
    private int numStorageLocations;
    
    private String codeInstance;
    // { 30, 45, 60, 75 }
    private int numCapacityDevice;
    // { 20, 40, 60, 80 }
    private int numOrders;
    
    private int typeOfOBAlgorithm;
    private int typeOfPRAlgorithm;
    private int typeOfLSAlgorithm;
    private int typeOfDistribution;
    
    private ArrayList<Batch> initialBatches;
    private ArrayList<Batch> batches;
    
    private int iteration;
    private double[] parameters;
    
    private long nanoSeconds;
    
    public Register( String name ) {
        this.initialBatches = new ArrayList<Batch>();
        this.batches = new ArrayList<Batch>();
    }
    
    // <editor-fold defaultstate="collapsed" desc="General Constructor">
    public Register( Register data ) {
        this.nanoSeconds = data.nanoSeconds;
        
        this.numCrossAisles = data.numCrossAisles;
        this.numAisles = data.numAisles;
        this.numStorageLocations = data.numStorageLocations;
        
        this.codeInstance = data.codeInstance;
        this.numCapacityDevice = data.numCapacityDevice;
        this.numOrders = data.numOrders;
        
        this.typeOfOBAlgorithm = data.typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = data.typeOfPRAlgorithm;
        this.typeOfLSAlgorithm = data.typeOfLSAlgorithm;
        this.typeOfDistribution = data.typeOfDistribution;
        
        this.iteration = data.iteration;
        this.parameters = data.parameters;
        
        this.batches = (data.batches != null) ? Batch.copyOfBatches( data.batches ) : null;
        this.initialBatches = (data.initialBatches != null)? Batch.copyOfBatches( data.initialBatches ) : null;
    }
    // </editor-fold>
    
    public Register(long time, Instance instance, ArrayList<Batch> batches, Configuration warehouse, 
            int typeOfOBAlgorithm, int typeOfPRAlgorithm, int typeOfDistribution) {
        this.nanoSeconds = time;
        
        this.numCrossAisles = warehouse.numCrossAisles;
        this.numAisles = warehouse.numAisles;
        this.numStorageLocations = warehouse.numLocations;
        
        this.codeInstance = instance.idInstance;
        this.numCapacityDevice = instance.numCapacity;
        this.numOrders = instance.numOrders;
        
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
        this.typeOfDistribution = typeOfDistribution;
        
        this.batches = Batch.copyOfBatches( batches );
    }
    
    // <editor-fold defaultstate="collapsed" desc="LS Constructor">
    public Register(long time, Instance instance, ArrayList<Batch> initialBatches, ArrayList<Batch> batches, Configuration warehouse, 
            int typeOfOBAlgorithm, int typeOfPRAlgorithm, int typeOfLSAlgorithm, int typeOfDistribution) {
        this.nanoSeconds = time;
        
        this.numCrossAisles = warehouse.numCrossAisles;
        this.numAisles = warehouse.numAisles;
        this.numStorageLocations = warehouse.numLocations;
        
        this.codeInstance = instance.idInstance;
        this.numCapacityDevice = instance.numCapacity;
        this.numOrders = instance.numOrders;
        
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
        this.typeOfLSAlgorithm = typeOfLSAlgorithm;
        this.typeOfDistribution = typeOfDistribution;
        
        this.initialBatches = Batch.copyOfBatches( initialBatches );
        this.batches = Batch.copyOfBatches( batches );
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GRASP Constructor">
    public Register(long time, Instance instance, ArrayList<Batch> batches, Configuration warehouse, 
            int typeOfOBAlgorithm, int typeOfPRAlgorithm, int typeOfLSAlgorithm, int iteration, double[] parameters, int typeOfDistribution) {
        this.nanoSeconds = time;
        
        this.numCrossAisles = warehouse.numCrossAisles;
        this.numAisles = warehouse.numAisles;
        this.numStorageLocations = warehouse.numLocations;
        
        this.codeInstance = instance.idInstance;
        this.numCapacityDevice = instance.numCapacity;
        this.numOrders = instance.numOrders;
        
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
        this.typeOfLSAlgorithm = typeOfLSAlgorithm;
        this.typeOfDistribution = typeOfDistribution;
        
        this.iteration = iteration;
        this.parameters = parameters;
        
        this.batches = Batch.copyOfBatches( batches );
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ILS Constructor">
    public Register(long time, Instance instance, ArrayList<Batch> batches, Configuration warehouse, 
            int typeOfOBAlgorithm, int typeOfPRAlgorithm, int iteration, double[] parameters, int typeOfDistribution) {
        this.nanoSeconds = time;
        
        this.numCrossAisles = warehouse.numCrossAisles;
        this.numAisles = warehouse.numAisles;
        this.numStorageLocations = warehouse.numLocations;
        
        this.codeInstance = instance.idInstance;
        this.numCapacityDevice = instance.numCapacity;
        this.numOrders = instance.numOrders;
        
        this.typeOfOBAlgorithm = typeOfOBAlgorithm;
        this.typeOfPRAlgorithm = typeOfPRAlgorithm;
        this.typeOfDistribution = typeOfDistribution;
        
        this.iteration = iteration;
        this.parameters = parameters;
        
        this.batches = Batch.copyOfBatches( batches );
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Path de acceso a las instancias">
    public static String getPath(int type_of_dist, int type_of_pr, int type_of_ob) {
        String name = "";
        
        LecturaArchivo archivo = new LecturaArchivo("data.properties");
        Properties objeto = archivo.cargar();
        
        if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.OBC && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_obc");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.OBC && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_obc");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.OB_LOCAL_SEARCH && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_obc_ls");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.OB_LOCAL_SEARCH && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_obc_ls");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.RANDOM_LOCAL_SEARCH && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_r_ls");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.RANDOM_LOCAL_SEARCH && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_r_ls");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.GRASP_01 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_grasp_01");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.GRASP_01 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_grasp_01");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.GRASP_02 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_grasp_02");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.GRASP_02 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_grasp_02");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.GRASP_03 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_grasp_03");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.GRASP_03 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_grasp_03");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_FCFS && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_ils_fcfs");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_FCFS && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_ils_fcfs");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_RANDOM && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_ils_random");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_RANDOM && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_ils_random");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_SOP && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_ils_sop");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_SOP && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_ils_sop");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_GREEDY_01 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_ils_greedy_01");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_GREEDY_01 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_ils_greedy_01");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_GREEDY_02 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_ils_greedy_02");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_GREEDY_02 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_ils_greedy_02");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_GREEDY_03 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("iu_sshape_ils_greedy_03");
        } else if (type_of_dist == Constant.INSTANCES_UNIFORM && type_of_ob == Constant.ILS_GREEDY_03 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("iu_lgap_ils_greedy_03");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.OBC && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("icb_sshape_obc");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.OBC && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("icb_lgap_obc");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.OB_LOCAL_SEARCH && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("icb_sshape_obc_ls");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.OB_LOCAL_SEARCH && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("icb_lgap_obc_ls");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.RANDOM_LOCAL_SEARCH && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("icb_sshape_r_ls");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.RANDOM_LOCAL_SEARCH && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("icb_lgap_r_ls");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.GRASP_01 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("icb_sshape_grasp_01");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.GRASP_01 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("icb_lgap_grasp_01");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.GRASP_02 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("icb_sshape_grasp_02");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.GRASP_02 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("icb_lgap_grasp_02");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.GRASP_03 && type_of_pr == Constant.S_SHAPE) {
            name = objeto.getProperty("icb_sshape_grasp_03");
        } else if (type_of_dist == Constant.INSTANCES_CLASSES_BASED && type_of_ob == Constant.GRASP_03 && type_of_pr == Constant.LARGEST_GAP) {
            name = objeto.getProperty("icb_lgap_grasp_03");
        }
        return name;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Order Batching Writters">
    public static void summaryProcedureOB( int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_ob-summary.csv";
            FileWriter writer = new FileWriter( csvFile );
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm", 
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time", "code_instance", "capacity_device", "num_orders", 
                    "num_batches", "distance");
                    
            CSVUtils.writeLine(writer, header);
            
            for (int index=0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );
                
                List<String> list = new ArrayList<String>();
                
                list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                list.add( Integer.toString( register.numAisles ) );
                list.add( Integer.toString( register.numCrossAisles ) );
                list.add( Integer.toString( register.numStorageLocations ) );
                list.add( Long.toString( register.nanoSeconds ) );
                list.add( register.codeInstance );
                list.add( Integer.toString( register.numCapacityDevice ) );
                list.add( Integer.toString( register.numOrders ) );
                
                list.add( Integer.toString( register.batches.size() ) );
                
                if (register.typeOfPRAlgorithm == Constant.S_SHAPE)
                    list.add( Double.toString( Batch.totalDistanceBatchesSShape( register.batches ) ) );
                else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP)
                    list.add( Double.toString( Batch.totalDistanceBatchesLargestGap( register.batches ) ) );
                
                CSVUtils.writeLine(writer, list);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void procedureOB( int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers ) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_ob.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm", 
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time", "code_instance", "capacity_device", "num_orders", 
                    "batch", "num_items", "order_id", "order_total_item", "item_id", "item_quantity", "distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int index=0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );

                for (int i=0; i<register.batches.size(); i++) {
                    Batch batch = new Batch( register.batches.get( i ) );
                    
                    for (int j=0; j<batch.orders.size(); j++) {
                        Order order = new Order( batch.orders.get( j ) );
                        
                        for (int k=0; k<order.items.size(); k++) {
                            Item item = new Item( order.items.get( k ) );
                            
                            List<String> list = new ArrayList<String>();

                            list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                            list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                            list.add( Integer.toString( register.numAisles ) );
                            list.add( Integer.toString( register.numCrossAisles ) );
                            list.add( Integer.toString( register.numStorageLocations ) );
                            list.add( Long.toString( register.nanoSeconds ) );
                            list.add( register.codeInstance );
                            list.add( Integer.toString( register.numCapacityDevice ) );
                            list.add( Integer.toString( register.numOrders ) );
                            list.add( Integer.toString( i ) );
                            list.add( Integer.toString( batch.totalItemsBatch ) );
                            
                            list.add( Integer.toString( order.index ) );
                            list.add( Integer.toString( order.totalItems ) );
                            list.add( Integer.toString( item.id_product ) );
                            list.add( Integer.toString( item.quantity ) );
                            
                            if (register.typeOfPRAlgorithm == Constant.S_SHAPE) {
                                list.add( Double.toString( batch.register.sshapeDistance ) );
                            } else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
                                list.add( Double.toString( batch.register.largestGapDistance ) );
                            }
                            CSVUtils.writeLine(writer, list);
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Local Search Writters">
    public static void procedureLS(int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_ls.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm", "ls_algorithm",
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time", "code_instance", "capacity_device", "num_orders",  
                    "batch", "num_items", "order_id", "order_total_item", "item_id", "item_quantity", "distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int index=0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );
                
                for (int i=0; i<register.batches.size(); i++) {
                    Batch batch = new Batch( register.batches.get(i) );
                    
                    for (int j=0; j<batch.orders.size(); j++) {
                        Order order = new Order( batch.orders.get( j ) );
                        
                        for (int k=0; k<order.items.size(); k++) {
                            Item item = new Item( order.items.get( k ) );
                            
                            List<String> list = new ArrayList<String>();

                            list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                            list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                            list.add( Constant.getLS( register.typeOfLSAlgorithm ) );
                            list.add( Integer.toString( register.numAisles ) );
                            list.add( Integer.toString( register.numCrossAisles ) );
                            list.add( Integer.toString( register.numStorageLocations ) );
                            list.add( Long.toString( register.nanoSeconds ) );
                            list.add( register.codeInstance );
                            list.add( Integer.toString( register.numCapacityDevice ) );
                            list.add( Integer.toString( register.numOrders ) );
                            list.add( Integer.toString( i+1 ) );
                            list.add( Integer.toString( batch.totalItemsBatch ) );

                            list.add( Integer.toString( order.index ) );
                            list.add( Integer.toString( order.totalItems ) );
                            list.add( Integer.toString( item.id_product ) );
                            list.add( Integer.toString( item.quantity ) );

                            if (register.typeOfPRAlgorithm == Constant.S_SHAPE) {
                                list.add( Double.toString( batch.register.sshapeDistance ) );
                            } else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
                                list.add( Double.toString( batch.register.largestGapDistance ) );
                            }

                            CSVUtils.writeLine(writer, list);
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void summaryProcedureLS( int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_ls-summary.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm", "ls_algorithm",
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time", "code_instance", "capacity_device", "num_orders",  
                    "before_num_batches", "before_distance", "after_num_batches", "after_distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int index=0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );
                
                List<String> list = new ArrayList<String>();
                
                list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                list.add( Constant.getLS( register.typeOfLSAlgorithm ) );
                list.add( Integer.toString( register.numAisles ) );
                list.add( Integer.toString( register.numCrossAisles ) );
                list.add( Integer.toString( register.numStorageLocations ) );
                list.add( Long.toString( register.nanoSeconds ) );
                list.add( register.codeInstance );
                list.add( Integer.toString( register.numCapacityDevice ) );
                list.add( Integer.toString( register.numOrders ) );
                
                int initialTotalBatches = register.initialBatches.size();
                double initialTotalDistanceBatches = 0.0;
                if (register.typeOfPRAlgorithm == Constant.S_SHAPE)
                    initialTotalDistanceBatches = Batch.totalDistanceBatchesSShape( register.initialBatches );
                else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP)
                    initialTotalDistanceBatches = Batch.totalDistanceBatchesLargestGap( register.initialBatches );
                
                list.add( Integer.toString( initialTotalBatches ) );
                list.add( Double.toString( initialTotalDistanceBatches ) );
                
                int totalBatches = register.batches.size();
                double totalDistanceBatches = 0.0;
                if (register.typeOfPRAlgorithm == Constant.S_SHAPE) {
                    totalDistanceBatches = Batch.totalDistanceBatchesSShape( register.batches );
                } else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
                    totalDistanceBatches = Batch.totalDistanceBatchesLargestGap( register.batches );
                }
                
                list.add( Integer.toString( totalBatches ) );
                list.add( Double.toString( totalDistanceBatches ) );
                
                CSVUtils.writeLine(writer, list);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Iterated Local Search Writters">
    public static void procedureILS(int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_ils.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm", 
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time_ms", "code_instance", "capacity_device", "num_orders",  
                    "batch", "num_items", "order_id", "order_total_item", "item_id", "item_quantity", "lambda", "miu", "distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int index=0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );
                
                for (int i=0; i<register.batches.size(); i++) {
                    Batch batch = new Batch( register.batches.get(i) );
                    
                    for (int j=0; j<batch.orders.size(); j++) {
                        Order order = new Order( batch.orders.get( j ) );
                        
                        for (int k=0; k<order.items.size(); k++) {
                            Item item = new Item( order.items.get( k ) );
                            
                            List<String> list = new ArrayList<String>();

                            list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                            list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                            list.add( Integer.toString( register.numAisles ) );
                            list.add( Integer.toString( register.numCrossAisles ) );
                            list.add( Integer.toString( register.numStorageLocations ) );
                            list.add( Long.toString( register.nanoSeconds ) );
                            list.add( register.codeInstance );
                            list.add( Integer.toString( register.numCapacityDevice ) );
                            list.add( Integer.toString( register.numOrders ) );
                            list.add( Integer.toString( i+1 ) );
                            list.add( Integer.toString( batch.totalItemsBatch ) );

                            list.add( Integer.toString( order.index ) );
                            list.add( Integer.toString( order.totalItems ) );
                            list.add( Integer.toString( item.id_product ) );
                            list.add( Integer.toString( item.quantity ) );
                            list.add( Double.toString( register.parameters[0] ) );
                            list.add( Double.toString( register.parameters[1] ) );
                            
                            if (register.typeOfPRAlgorithm == Constant.S_SHAPE) {
                                list.add( Double.toString( batch.register.sshapeDistance ) );
                            } else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
                                list.add( Double.toString( batch.register.largestGapDistance ) );
                            }

                            CSVUtils.writeLine(writer, list);
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void summaryProcedureILS( int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_ils-summary.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm",
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time", "code_instance", "capacity_device", "num_orders", "lambda", "miu",
                    "num_batches", "distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int index=0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );
                
                List<String> list = new ArrayList<String>();
                
                list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                list.add( Integer.toString( register.numAisles ) );
                list.add( Integer.toString( register.numCrossAisles ) );
                list.add( Integer.toString( register.numStorageLocations ) );
                list.add( Long.toString( register.nanoSeconds ) );
                list.add( register.codeInstance );
                list.add( Integer.toString( register.numCapacityDevice ) );
                list.add( Integer.toString( register.numOrders ) );
                list.add( Double.toString( register.parameters[0] ) );
                list.add( Double.toString( register.parameters[1] ) );
                
                int totalBatches = register.batches.size();
                double totalDistanceBatches = 0.0;
                if (register.typeOfPRAlgorithm == Constant.S_SHAPE) {
                    totalDistanceBatches = Batch.totalDistanceBatchesSShape( register.batches );
                } else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
                    totalDistanceBatches = Batch.totalDistanceBatchesLargestGap( register.batches );
                }
                
                list.add( Integer.toString( totalBatches ) );
                list.add( Double.toString( totalDistanceBatches ) );
                
                CSVUtils.writeLine(writer, list);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GRASP Writters">
    public static void procedureGRASP( int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_grasp.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("grasp_algorithm", "prp_algorithm", "ls_algorithm",
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time_ms", "code_instance", "capacity_device", "num_orders",  
                    "iteration", "alpha", "batch", "num_items", "order_id", "order_total_item", "item_id", "item_quantity", "distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int index = 0; index<registers.size(); index++) {
                Register register = new Register( registers.get( index ) );
                
                for (int i=0; i<register.batches.size(); i++) {
                    Batch batch = new Batch( register.batches.get(i) );
                    
                    for (int j=0; j<batch.orders.size(); j++) {
                        Order order = new Order( batch.orders.get( j ) );
                        
                        for (int k=0; k<order.items.size(); k++) {
                            Item item = new Item( order.items.get( k ) );
                            
                            List<String> list = new ArrayList<String>();

                            list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                            list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                            list.add( Constant.getLS( register.typeOfLSAlgorithm ) );
                            list.add( Integer.toString( register.numAisles ) );
                            list.add( Integer.toString( register.numCrossAisles ) );
                            list.add( Integer.toString( register.numStorageLocations ) );
                            list.add( Long.toString( register.nanoSeconds ) );
                            list.add( register.codeInstance );
                            list.add( Integer.toString( register.numCapacityDevice ) );
                            list.add( Integer.toString( register.numOrders ) );
                            list.add( Integer.toString( register.iteration ) );
                            list.add( Double.toString( register.parameters[0] ) );
                            list.add( Integer.toString( i+1 ) );
                            list.add( Integer.toString( batch.totalItemsBatch ) );
                            
                            list.add( Integer.toString( order.index ) );
                            list.add( Integer.toString( order.totalItems ) );
                            list.add( Integer.toString( item.id_product ) );
                            list.add( Integer.toString( item.quantity ) );
                            
                            if (register.typeOfPRAlgorithm == Constant.S_SHAPE) {
                                list.add( Double.toString( batch.register.sshapeDistance ) );
                            } else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP) {
                                list.add( Double.toString( batch.register.largestGapDistance ) );
                            }

                            CSVUtils.writeLine(writer, list);
                        }
                    }
                }
            }
            
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void summaryProcedureGRASP( int type_dist, int type_pr, int type_ob, String routing, ArrayList<Register> registers) {
        try {
            String csvFile = Register.getPath(type_dist, type_pr, type_ob) + routing + "_grasp-summary.csv";
            FileWriter writer = new FileWriter(csvFile);
            
            List<String> header = Arrays.asList("obp_algorithm", "prp_algorithm", "ls_algorithm",
                    "num_aisles", "num_cross_aisles", "num_storage_locations", 
                    "time_ms", "code_instance", "capacity_device", "num_orders", 
                    "iteration", "alpha", "num_batches", "distance");
            
            CSVUtils.writeLine(writer, header);
            
            for (int i=0; i<registers.size(); i++) {
                Register register = new Register( registers.get(i) );
                
                List<String> list = new ArrayList<String>();

                list.add( Constant.getOrderBatching( register.typeOfOBAlgorithm ) );
                list.add( Constant.getRoute( register.typeOfPRAlgorithm ) );
                list.add( Constant.getLS( register.typeOfLSAlgorithm ) );
                list.add( Integer.toString( register.numAisles ) );
                list.add( Integer.toString( register.numCrossAisles ) );
                list.add( Integer.toString( register.numStorageLocations ) );
                list.add( Long.toString( register.nanoSeconds ) );
                list.add( register.codeInstance );
                list.add( Integer.toString( register.numCapacityDevice ) );
                list.add( Integer.toString( register.numOrders ) );
                list.add( Integer.toString( register.iteration ) );
                list.add( Double.toString( register.parameters[0] ) );

                int totalBatches = register.batches.size();
                double totalDistanceBatches = 0.0;
                if (register.typeOfPRAlgorithm == Constant.S_SHAPE)
                    totalDistanceBatches = Batch.totalDistanceBatchesSShape( register.batches );
                else if (register.typeOfPRAlgorithm == Constant.LARGEST_GAP)
                    totalDistanceBatches = Batch.totalDistanceBatchesLargestGap( register.batches );
                
                list.add( Integer.toString( totalBatches ) );
                list.add( Double.toString( totalDistanceBatches ) );
                
                CSVUtils.writeLine(writer, list);
            }
            
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
}