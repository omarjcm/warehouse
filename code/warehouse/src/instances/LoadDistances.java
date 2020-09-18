/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class LoadDistances extends Load {
    
    private String nameFile;
    private int groupAlgorithm;

    public LoadDistances(String nameFile, int groupAlgorithm) {
        super( "distances" );
        this.nameFile = nameFile;
        this.groupAlgorithm = groupAlgorithm;
    }
    
    @Override
    public List<Results_OBC> readInstances() {
//        try {
//            if (this.groupAlgorithm == Constant.OBC) {
//                List<Results_OBC> results = new ArrayList<Results_OBC>();
//
//                CsvReader file = new CsvReader( this.path + this.nameFile );
//                file.readHeaders();
//                while( file.readRecord() ) {
//                    Results_OBC object = new Results_OBC();
//                    object.setIdInstance( file.get("idInstance") );
//                    object.setNumOrders( file.get("numOrders") );
//                    object.setNumCapacity( file.get("numCapacity") );
//                    object.setRouting( file.get("routing") );
//                    object.setFCFS_Time_ns( file.get("FCFS_Time_ns") );
//                    object.setRANDOM_Time_ns( file.get("RANDOM_Time_ns") );
//                    object.setSOP_Time_ns( file.get("SOP_Time_ns") );
//                    object.setGREEDY_01_Time_ns( file.get("GREEDY_01_Time_ns") );
//                    object.setGREEDY_02_Time_ns( file.get("GREEDY_02_Time_ns") );
//                    object.setGREEDY_03_Time_ns( file.get("GREEDY_03_Time_ns") );
//
//                    results.add(object);
//                }
//                file.close();
//
//                return results;
//            } else if (this.groupAlgorithm == Constant.OB_GRASP) {
//                List<Results_GRASP> results = new ArrayList<Results_GRASP>();
//                
//                CsvReader file = new CsvReader( this.path + this.nameFile );
//                file.readHeaders();
//                while( file.readRecord() ) {
//                    Results_GRASP object = new Results_GRASP();
//                    object.setIdInstance( file.get("idInstance") );
//                    object.setNumOrders( file.get("numOrders") );
//                    object.setNumCapacity( file.get("numCapacity") );
//                    object.setRouting( file.get("routing") );
//                    object.setFCFS_Time_ns( file.get("FCFS_Time_ns") );
//                    object.setRANDOM_Time_ns( file.get("RANDOM_Time_ns") );
//                    object.setSOP_Time_ns( file.get("SOP_Time_ns") );
//                    object.setGREEDY_01_Time_ns( file.get("GREEDY_01_Time_ns") );
//                    object.setGREEDY_02_Time_ns( file.get("GREEDY_02_Time_ns") );
//                    object.setGREEDY_03_Time_ns( file.get("GREEDY_03_Time_ns") );
//                    
//                    results.add(object);
//                }
//                file.close();
//                
//                return results;
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(LoadDistances.class.getName()).log(Level.SEVERE, null, ex);
//        }            
        return null;
    }
    
    public static void main(String[] args) {
        LoadDistances object = new LoadDistances("MBLOCK-LargestGap-OBC.csv", Constant.OBC);
        List<Results_OBC> resultsLG = object.readInstances();
        object = new LoadDistances("MBLOCK-SShape-OBC.csv", Constant.OBC);
        List<Results_OBC> resultsSS = object.readInstances();
        
        List<Results_OBC> results = resultsLG;
        results.addAll( resultsSS );
        
        for (Results_OBC result: results) {
            System.out.println( result.getIdInstance() + "Routing: " + result.getRouting() );
        }
    }
}