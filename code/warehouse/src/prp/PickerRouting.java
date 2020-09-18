/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

import instances.Batch;
import java.util.ArrayList;
import java.util.List;
import obp.OrderBatching;
import warehouse.Configuration;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class PickerRouting {
    
    private int[][] graph;
    private int[][] warehouseLayout;
    private int[][] aisleBlock;
    private Configuration configuration;
    
    private int numRows;
    private int numColumns;
    private int firstArtificialVertex;
    
    private int typeOfPRPAlgorithm;
    
    public PickerRouting(Configuration config, int typeOfPRPAlgorithm) {
        this.configuration = config;
        
        this.warehouseLayout = config.warehouseLayout;
        this.aisleBlock = config.aisleBlock;
        this.numRows = config.numRows;
        this.numColumns = config.numColumns;
        this.firstArtificialVertex = config.firstArtificialVertex;
        
        this.typeOfPRPAlgorithm = typeOfPRPAlgorithm;
    }
    
    /**
     * 
     * @param object
     * @return 
     */
    public ArrayList<Batch> procedure(OrderBatching object) {
        for (int i=0; i<object.vertixLocationBatch.size(); i++) {
            this.graphLayout( object.vertixLocationBatch.get(i) );
            
            //this.printGraph();
            if (this.typeOfPRPAlgorithm == Constant.S_SHAPE) {
                PRPSShape sshape = new PRPSShape(this.configuration, this.graph);
                sshape.procedure( );
                sshape.calculateTime( object.orders.size() );
                //object.printBatches( i );
                
                object.batches.get(i).register.sshapeDistance = sshape.distance;
                object.batches.get(i).register.sshapeTime = sshape.time;
            } else if (this.typeOfPRPAlgorithm == Constant.LARGEST_GAP) {
                PRPLargestGap largestGap = new PRPLargestGap(this.configuration, this.graph);
                largestGap.procedure( );
                largestGap.calculateTime( object.orders.size() );
                //System.out.println("Distance: " + largestGap.distance);
                
                object.batches.get(i).register.largestGapDistance = largestGap.distance;
                object.batches.get(i).register.largestGapTime = largestGap.time;                
            }
        }
        return object.batches;
    }
    
    /**
     * 
     * @param object
     * @param index
     * @return 
     */
    public Batch procedure(OrderBatching object, int index) {
        this.graphLayout( object.vertixLocationBatch.get( index ) );
        
        if (this.typeOfPRPAlgorithm == Constant.S_SHAPE) {
            PRPSShape sshape = new PRPSShape(this.configuration, this.graph);
            sshape.procedure( );
            sshape.calculateTime( object.orders.size() );
            //object.printBatches( index );
            object.batches.get(index).register.sshapeDistance = sshape.distance;
            object.batches.get(index).register.sshapeTime = sshape.time;
        } else if (this.typeOfPRPAlgorithm == Constant.LARGEST_GAP) {
            PRPLargestGap largestGap = new PRPLargestGap(this.configuration, this.graph);
            largestGap.procedure( );
            largestGap.calculateTime( object.orders.size() );
            
            object.batches.get( index ).register.largestGapDistance = largestGap.distance;
            object.batches.get( index ).register.largestGapTime = largestGap.time;            
        }
        //this.printGraph();
        return object.batches.get(index);
    }
    
    private void printGraph() {
        for (int i=0; i<this.graph.length; i++) {
            System.out.print( "i[" + i + "]:\t" );
            for (int j=0; j<this.graph[i].length; j++) {
                System.out.print( "j[" + j + "] - " + this.graph[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private void graphLayout( ArrayList<Integer> vertices ) {
        this.graph = Constant.initializeInt(this.numRows, this.numColumns);
        
        // Copia de los datos de un almacen a un grafo con los vertices seleccionado.
        for (int i=0; i < this.warehouseLayout.length; i++)
            System.arraycopy(this.warehouseLayout[i], 0, this.graph[i], 0, this.warehouseLayout[i].length);
        
        for (int j=0; j<this.numColumns; j++) {
            for (int i=0; i<this.numRows; i++) {
                if (this.graph[i][j] < this.firstArtificialVertex && !isFound( vertices, this.graph[i][j] )) {
                    this.graph[i][j] = 0;
                }
            }
        }
    }
    
    private boolean isFound(List<Integer> array, int element) {
        for (int i=0; i<array.size(); i++) {
            if (array.get(i) == element)
                return true;
        }
        return false;
    }
}