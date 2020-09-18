/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

import warehouse.Configuration;
import warehouse.Constant;
import warehouse.Warehouse;

/**
 *
 * @author omarjcm
 */
abstract class PRPAlgorithm {
    
    protected static int _RIGHT_MOST_SUBAISLE_ = 0;
    protected static int _LEFT_MOST_SUBAISLE_ = 1;
    
    protected Warehouse warehouse;
    protected int[][] aisleBlock;
    protected int[][] graph;
    protected int[] crossAislesLocation;
    
    protected int numRows;
    protected int numColumns;
    protected int numAisles;
    protected int firstArtificialVertex;
    
    protected int[] productsByBlock;
    protected int[] subaislesByBlock;
    protected double[] blocksLength;
    
    protected int actualColumn;
    protected int indexItem;
    protected double crossAislesWidth;
    
    protected double distance;
    protected double time;
    protected int countCorners;

    public PRPAlgorithm(Configuration config, int[][] graph) {
        this.warehouse = config.warehouse;
        this.aisleBlock = config.aisleBlock;
        this.numColumns = config.numColumns;
        this.numRows = config.numRows;
        this.numAisles = config.numAisles;
        this.crossAislesLocation = config.crossAislesLocation;
        this.firstArtificialVertex = config.firstArtificialVertex;
        this.graph = graph;
        
        this.distance = 0;
        this.time = 0;
        this.countCorners = 0;
        /**
         * Variables específicas para el algoritmo de enrutamiento
         */
        this.actualColumn = -1;
        this.indexItem = -1;
        this.crossAislesWidth = ( this.warehouse.location_width * 2 ) + this.warehouse.aisle_width;        
    }
    
    public abstract void procedure();
    
    public void calculateTime(int ordersSize) {
        this.time = (int) ((this.distance / Constant.SPEED_M_S) + 
                this.countCorners * Constant.COURNER_TIME_S + 
                ordersSize * Constant.PICKING_TIME_S );
    }
    
    /**
     * Contabiliza los items por bloques
     */
    protected void countProductsByBlock() {
        int current = 0;
        int sizeCrossAislesLocation = this.crossAislesLocation.length;
        
        this.productsByBlock = new int[this.crossAislesLocation.length-1];
        
        for (int i=0; i<this.graph.length; i++) {
            for (int j=0; j<this.graph[i].length; j++) {
                if (current >= sizeCrossAislesLocation) {
                    current = 0;
                } else if (this.crossAislesLocation[current] == i) {
                    current++;
                } else {
                    if (this.graph[i][j] < this.firstArtificialVertex && this.graph[i][j] != 0) {
                        this.productsByBlock[current == 0 ? current : current-1]++;
                    }
                }
            }
        }
    }

    protected void getBlocksLength() {
        this.blocksLength = new double[this.crossAislesLocation.length-1];
        for (int current = 0; current < this.crossAislesLocation.length-1; current++) {
            double numRowsRange = this.crossAislesLocation[current + 1] - this.crossAislesLocation[current];
            this.blocksLength[current] = ( this.warehouse.location_length * (numRowsRange - 2) ) + this.warehouse.cross_aisle_length + this.warehouse.location_length;
        }
    }
    
    protected double getDistanceFromDepotToLastBlock(int initialBlock) {
        double sum = 0;
        for (int current = initialBlock; current<this.blocksLength.length; current++) {
            sum += this.blocksLength[current];
        }
        return sum;
    }
    
    /**
     * Verifica si existen items por pasillos
     * @param index_i
     * @param index_n
     * @param aisle
     * @return 
     */
    protected boolean verifyProductsExistence(int index_i, int index_n, int aisle) {
        for (int i=index_i; i<index_n; i++) {
            if ((this.graph[i][aisle] < this.firstArtificialVertex) && this.graph[i][aisle] != 0)
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    protected int getIndexCrossAisleLocation(int value) {
        for (int i=0; i<this.crossAislesLocation.length; i++) {
            if (this.crossAislesLocation[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Busca el indice del primer bloque que tenga items a recoger.
     * @return 
     */
    protected int searchFirstBlockWithItems() {
        for (int firstIndexBlock=0; firstIndexBlock<this.subaislesByBlock.length; firstIndexBlock++) {
            // Busco el primer bloque de arriba a abajo que tenga items a recoger
            if (this.subaislesByBlock[firstIndexBlock] > 0) {
                // Retorno el índice de ese bloque.
                return firstIndexBlock;
            }
        }
        return -1;
    }
    
    /**
     * Busca el índice del primer pasillo que tenga items a recoger.
     * @return indice
     */
    protected int searchFirstAisleWithItems() {
        for (int index=0; index<this.numAisles; index++) {
            for (int j=0; j<this.numRows; j++) {
                if (this.graph[j][index] < this.firstArtificialVertex && this.graph[j][index] != 0) {
                    return index;
                }
            }
        }
        return -1;
    }
    
    protected Boolean isThereAislesWithItems(int current) {
        return (this.subaislesByBlock[current] > 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    protected Boolean isThereProductsIntoSpecificAisle(int current, int aisle) {
        return verifyProductsExistence(this.crossAislesLocation[current], this.crossAislesLocation[current+1],  aisle) ? Boolean.TRUE : Boolean.FALSE;        
    }
        
    protected int searchIndexAisleWithItemsFromLeftToRigth(int initIndexAisle, int lastIndexAisle, int cur) {
        int index = 0;
        for (int i = initIndexAisle; i<lastIndexAisle; i++) {
            if ( verifyProductsExistence(this.crossAislesLocation[cur], this.crossAislesLocation[cur+1],  i) ) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    protected int searchIndexAisleWithItemsFromRigthToLeft(int initIndexAisle, int lastIndexAisle, int cur) {
        int index = 0;
        for (int i = initIndexAisle; i>lastIndexAisle; i--) {
            if ( verifyProductsExistence(this.crossAislesLocation[cur], this.crossAislesLocation[cur+1],  i) ) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    protected int searchIndexLeftOrRightMostSubaisle(int firstIndexAisle, int cur, int type_search) {
        int index = -1;
        if (type_search == PRPSShape._LEFT_MOST_SUBAISLE_) {
            index = searchIndexAisleWithItemsFromLeftToRigth(firstIndexAisle+1, this.numAisles, cur);
        } else if (type_search == PRPSShape._RIGHT_MOST_SUBAISLE_) {
            index = searchIndexAisleWithItemsFromRigthToLeft(this.numAisles-1, firstIndexAisle, cur);
        }
        return index;
    }
    
    protected void calculateDistanceWhenFinishedBackAisle(double pickAislesLength, int current) {
        this.distance -= pickAislesLength;
        this.distance += (this.warehouse.cross_aisle_length/2 + this.warehouse.location_length/2) * 2 + 
                (this.crossAislesLocation[current+1] - this.indexItem - 1) * (this.warehouse.location_length) * 2;
    }
    
    protected int getIndexFromLastItemIntoAisles(int i, int current) {
        int index = -1;
        for (int j=this.crossAislesLocation[current]; j<this.crossAislesLocation[current+1]-1; j++) {
            if (this.graph[j][i] < this.firstArtificialVertex && this.graph[j][i] != 0) {
                index = j;
                break;
            }
        }
        return index;
    }    
}