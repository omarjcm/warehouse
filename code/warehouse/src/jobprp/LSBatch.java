/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobprp;

import instances.Batch;
import instances.Random;
import java.util.ArrayList;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class LSBatch {
    
    private int indexBatch;
    private int totalItemsBatch;
    private int[] indexOrderList;
    
    public LSBatch() {
    }
    
    public LSBatch(int size) {
        this.indexOrderList = Constant.initializeWithMinusOne( size );
    }

    public int getIndexBatch() {
        return indexBatch;
    }

    public void setIndexBatch(int indexBatch) {
        this.indexBatch = indexBatch;
    }

    public int getTotalItemsBatch() {
        return totalItemsBatch;
    }

    public void setTotalItemsBatch(int totalItemsBatch) {
        this.totalItemsBatch = totalItemsBatch;
    }

    public int[] getIndexOrderList() {
        return indexOrderList;
    }

    public void setIndexOrderList(int[] indexOrderList) {
        this.indexOrderList = indexOrderList;
    }
    
    public static LSBatch[] initializeLSBatch(int typeOfLSAlgorithm, int[] LSBatches, int batchesSize, ArrayList<Batch> batches) {
        LSBatch[] indexBatchList = new LSBatch[2];
        int count = 0;
        boolean guard = Boolean.TRUE;
        
        for (int i=0; i<LSBatches.length; i++) {
            indexBatchList[i] = new LSBatch();
            indexBatchList[i].indexBatch = LSBatches[i];
            indexBatchList[i].totalItemsBatch = batches.get( LSBatches[i] ).totalItemsBatch;
            
            int orderSize = batches.get( LSBatches[i] ).orders.size();
            
            if (typeOfLSAlgorithm == Constant.LS_1X1) {
                indexBatchList[i].indexOrderList = LSBatch.getRandomDifferents(1, orderSize);
            } else if (typeOfLSAlgorithm == Constant.LS_1X2) {
                if (count == 0) {
                    indexBatchList[i].indexOrderList = LSBatch.getRandomDifferents(1, orderSize);
                    count++;
                } else {
                    if (indexBatchList[i-1].indexOrderList.length >= 2) {
                        indexBatchList[i].indexOrderList = LSBatch.getRandomDifferents(2, orderSize);                        
                    } else {
                        indexBatchList[i-1].indexOrderList = null;
                        indexBatchList[i].indexOrderList = null;
                    }
                }
            } else if (typeOfLSAlgorithm == Constant.LS_1X2) {
                if (count == 0) {
                    indexBatchList[i].indexOrderList = LSBatch.getRandomDifferents(2, orderSize);
                    count++;
                } else {
                    if (indexBatchList[i].indexOrderList.length >= 2) {
                        indexBatchList[i].indexOrderList = LSBatch.getRandomDifferents(1, orderSize);                        
                    } else {
                        indexBatchList[i-1].indexOrderList = null;
                        indexBatchList[i].indexOrderList = null;
                    }
                }
            } else if (typeOfLSAlgorithm == Constant.LS_2X2) {
                indexBatchList[i].indexOrderList = LSBatch.getRandomDifferents(2, orderSize);
            }
        }
        return indexBatchList;
    }
    
    public static int[] getRandomDifferents(int indexListSize, int size) {
        int[] indexList = new int[ indexListSize ];
        indexList = Constant.initializeWithMinusOne( indexListSize );
        
        if (indexListSize == 1) {
            indexList[0] = 0;
        } else {
            int indexFound = 0;
            for (int i=0; i<indexListSize; i++) {
                while (true) {
                    indexFound = Random.getRandomIndex( size );
                    if ( !isEqual(indexList, indexFound) )
                        break;
                }
                indexList[i] = indexFound;
            }
        }
        return indexList;
    }
    
    private static boolean isEqual(int[] indexList, int index) {
        
        for (int i=0; i<indexList.length; i++) {
            if (indexList[i] == index)
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }    
}