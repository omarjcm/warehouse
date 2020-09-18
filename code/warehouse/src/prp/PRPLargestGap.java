/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

import java.util.ArrayList;
import java.util.Collections;
import warehouse.Configuration;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class PRPLargestGap extends PRPAlgorithm {
    
    private Gap[][] largestGaps;
    
    public PRPLargestGap(Configuration config, int[][] graph) {
        super(config, graph);
    }
    
    @Override
    public void procedure() {
        // Obtengo una lista de todos los espacios en cada pasillo (existan o no productos)
        LGaps[][] gaps = this.getGaps( );
        // Obtengo el espacio más grande en cada pasillo (solo uno)
        this.largestGaps = this.getLargestGap( gaps );
        
        gaps = null;
        
        int distance_temp = 0;
        // Contabilizan los pasillos de recogida o subpasillos (que contengan items) por bloques en un almacén
        this.countSubaislesByBlock();
        /** 
         * encuentra el indice de la primera columna (pasillo de recogida) del almacen (de izquierda a 
         * derecha) que contenga items a recoger
         */
        int firstIndexAisle = this.searchFirstAisleWithItems();
        // si firstIndexAisle == -1, esto ocurre cuando no hay items en el almacén.
        if ( firstIndexAisle != -1 ) {
            if (firstIndexAisle > 0)
                this.countCorners++;
            /**
             * Se obtiene la distancia desde el depot hasta la parte frontal del bloque el primer pasillo 
             * donde se encuentren items
             */
            this.distance = this.crossAislesWidth * firstIndexAisle;
            int firstIndexBlock = this.searchFirstBlockWithItems();
            
            if (firstIndexBlock != -1) {
                this.getBlocksLength();
                
                /**
                 * Se obtiene la distancia temporal desde pasillo transveral inicial (donde se encuentra 
                 * el depot) hasta el pasillo frontal del bloque donde se encuentra algún item
                 */
                distance_temp += getDistanceFromDepotToLastBlock( firstIndexBlock + 1 );
                
                int step = 0;
                for (int current = firstIndexBlock; current < this.crossAislesLocation.length-1; current++) {
                    double pickAislesLength = this.blocksLength[current];
                    
                    if (step == 0) {
                        this.decrementSubaisleItems(firstIndexAisle, current+1);
                        this.distance += distance_temp;
                        this.calculateDistanceToLastBlock(firstIndexAisle, this.numAisles, pickAislesLength, current);
                        step++;
                    } else if (step > 0) {
                        if (this.subaislesByBlock[current] == 0) {
                            this.distance += pickAislesLength;
                        } else if ( isThereOnlyOneSubAisle(current) ) {
                            int indexAisles = this.searchIndexAisleWithItemsFromLeftToRigth(firstIndexAisle+1, this.numAisles, current);
                            int distanceToAisles = Math.abs( indexAisles - this.actualColumn );
                            if (distanceToAisles > 0) {
                                this.countCorners += 2;
                            }
                            this.actualColumn = indexAisles;
                            this.distance += pickAislesLength + (distanceToAisles * this.crossAislesWidth);
                        } else {
                            int indexLeftAisle = this.searchIndexLeftOrRightMostSubaisle(firstIndexAisle, current, PRPSShape._LEFT_MOST_SUBAISLE_);
                            int indexRightAisle = this.searchIndexLeftOrRightMostSubaisle(firstIndexAisle, current, PRPSShape._RIGHT_MOST_SUBAISLE_);
                            
                            int diffLeftAisle = Math.abs( indexLeftAisle - this.actualColumn );
                            int diffRigthAisle = Math.abs( indexRightAisle - this.actualColumn );
                            
                            if (diffRigthAisle < diffLeftAisle) {
                                // si la distancia es mayor a cero, eso significa que tendrá que virar dos veces.
                                if (diffRigthAisle > 0) {
                                    this.countCorners += 2;
                                }
                                this.calculateDistanceBlockRightToLeft(indexLeftAisle, indexRightAisle, pickAislesLength, current);                                
                            } else if (diffLeftAisle <= diffRigthAisle) {
                                if (diffLeftAisle > 0) {
                                    this.countCorners += 2;
                                }
                                this.calculateDistanceBlockLeftToRight(indexLeftAisle, indexRightAisle, pickAislesLength, current);
                            }
                        }
                    }
                }
                this.distance += this.actualColumn * this.crossAislesWidth;
            }
        }
    }
    
    public void calculateDistanceToLastBlock(int initIndexAisle, int endIndexAisle, double pickAislesLength, int current) {
        boolean guard = Boolean.TRUE, countCornersOn = Boolean.TRUE;
        
        double distance_temp = 0;
        int crossAisle_temp = 0, initialColumn = 0, position = 0;
        
        int initIndex = this.crossAislesLocation[current];
        int finalIndex = this.crossAislesLocation[current+1];
        
        Gap gap = null;
        
        for (int i=initIndexAisle; i<endIndexAisle; i++) {
            if (isThereProductsIntoSpecificAisle(current, i)) {
                gap = this.largestGaps[current][i];
                
                if (guard) {
                    if ( isThereOnlyOneSubAisle(current) ) {
                        this.distance += distance_temp;
                        position = Constant.FRONT;
                    } else {
                        this.distance += distance_temp + pickAislesLength;
                        position = Constant.BACK;
                    }
                    this.decrementSubaisleItems(gap, current);
                    this.countCorners += crossAisle_temp;
                    initialColumn = i;
                    guard = Boolean.FALSE;
                } else {
                    this.distance += distance_temp + ((gap.indexInitial - initIndex) * 2);
                    this.decrementSubaisleItems(gap, Constant.BACK, current);
                }
                distance_temp = 0;
                crossAisle_temp = 0;
                countCornersOn = Boolean.TRUE;
                this.actualColumn = i;
            }
            if (i != (endIndexAisle-1)) {
                distance_temp += this.crossAislesWidth;
                
                if (countCornersOn) {
                    crossAisle_temp = 2;
                    countCornersOn = Boolean.FALSE;
                }
            }
        }
        
        gap = this.largestGaps[current][this.actualColumn];
        this.decrementSubaisleItems(gap, Constant.FRONT, current);
        
        if (position == Constant.BACK) {
            this.distance += pickAislesLength - ((gap.indexInitial - initIndex) * 2);
        } else {
            if (gap.indexInitial != initIndex)
                this.distance += (finalIndex - gap.indexInitial) * 2;
            else 
                this.distance += (finalIndex - gap.indexFinal) * 2;
        }
        
        if (this.subaislesByBlock[current] > 0) {
            distance_temp = this.crossAislesWidth;
            
            for (int i=actualColumn-1; i>initialColumn; i--) { 
                gap = this.largestGaps[current][i];

                if (isThereProductsIntoSpecificAisle(current, i)) {
                    this.distance += distance_temp + ((finalIndex - gap.indexFinal) * 2);
                    this.countCorners += crossAisle_temp;
                    
                    distance_temp = 0.0;
                    crossAisle_temp = 0;
                    this.actualColumn = i;
                    
                    this.decrementSubaisleItems(gap, Constant.FRONT, current);
                    
                    if (this.subaislesByBlock[current] == 0) {
                        break;
                    }
                }
                if (i != (initialColumn+1)) {
                    distance_temp += this.crossAislesWidth;

                    if (countCornersOn) {
                        crossAisle_temp = 2;
                        countCornersOn = Boolean.FALSE;
                    }
                }
            }
        }
    }

    public void calculateDistanceBlockLeftToRight(int initIndexAisle, int endIndexAisle, double pickAislesLength, int current) {
        boolean guard = Boolean.TRUE, countCornersOn = Boolean.TRUE;
        
        double distance_temp = 0;
        int crossAisle_temp = 0, initialColumn = 0, count = 0;
        
        int initIndex = this.crossAislesLocation[current];
        int finalIndex = this.crossAislesLocation[current+1];
        int actualColumnCopy = this.actualColumn;
        
        Gap gap = null;
        
        for (int i=initIndexAisle; i<=endIndexAisle; i++) {
            if (isThereProductsIntoSpecificAisle(current, i)) {
                gap = this.largestGaps[current][i];
                
                int difference = gap.indexInitial - initIndex;
                // Se almacena el índice del primer pasillo donde se encuentro
                if (guard) {
                    initialColumn = i;
                    guard = Boolean.FALSE;
                }
                if (!guard && (difference > 0) && count == 0) {
                    this.distance += difference * 2 - Math.abs( actualColumnCopy - i ) * this.crossAislesWidth;
                    count++;
                } else {
                    this.distance += distance_temp + (difference * 2);
                }
                
                distance_temp = 0;
                crossAisle_temp = 0;
                countCornersOn = Boolean.TRUE;
                this.actualColumn = i;
                
                this.decrementSubaisleItems(gap, Constant.BACK, current);
            }
            if (i != (endIndexAisle)) {
                distance_temp += this.crossAislesWidth;
                
                if (countCornersOn) {
                    crossAisle_temp = 2;
                    countCornersOn = Boolean.FALSE;
                }
            }
        }
        
        gap = this.largestGaps[current][this.actualColumn];
        this.decrementSubaisleItems(gap, Constant.FRONT, current);
        this.distance += pickAislesLength - ((gap.indexInitial - initIndex) * 2);
        
        if (this.subaislesByBlock[current] > 0) {
            distance_temp = this.crossAislesWidth;
            
            for (int i=endIndexAisle-1; i>=initIndexAisle; i--) {
                if (isThereProductsIntoSpecificAisle(current, i)) {
                    gap = this.largestGaps[current][i];
                    this.distance += distance_temp + ((finalIndex - gap.indexFinal) * 2);
                    this.countCorners += crossAisle_temp;

                    distance_temp = 0.0;
                    crossAisle_temp = 0;
                    this.actualColumn = i;
                    
                    this.decrementSubaisleItems(gap, Constant.FRONT, current);
                    
                    if (this.subaislesByBlock[current] == 0) {
                        break;
                    }
                }
                if (i != (initIndexAisle)) {
                    distance_temp += this.crossAislesWidth;

                    if (countCornersOn) {
                        crossAisle_temp = 2;
                        countCornersOn = Boolean.FALSE;
                    }                
                }
            }
        }
    }
    
    public void calculateDistanceBlockRightToLeft(int initIndexAisle, int endIndexAisle, double pickAislesLength, int current) {
        boolean guard = Boolean.TRUE, countCornersOn = Boolean.TRUE;
        
        double distance_temp = 0;
        int crossAisle_temp = 0, initialColumn = 0, count = 0;
        
        int initIndex = this.crossAislesLocation[current];
        int finalIndex = this.crossAislesLocation[current+1];
        int actualColumnCopy = this.actualColumn;
        
        Gap gap = null;
        
        for (int i=endIndexAisle; i>=initIndexAisle; i--) {
            if (isThereProductsIntoSpecificAisle(current, i)) {
                gap = this.largestGaps[current][i];
                
                int difference = gap.indexInitial - initIndex;
                // Se almacena el índice del primer pasillo donde se encuentr
                if (guard) {
                    initialColumn = i;
                    guard = Boolean.FALSE;
                }
                if (!guard && (difference > 0) && count == 0) {
                    this.distance += difference * 2 - Math.abs( actualColumnCopy - i ) * this.crossAislesWidth;
                    count++;
                } else {
                    this.distance += distance_temp + (difference * 2);
                }
                
                distance_temp = 0;
                crossAisle_temp = 0;
                countCornersOn = Boolean.TRUE;
                this.actualColumn = i;
                
                this.decrementSubaisleItems(gap, Constant.BACK, current);
            }
            if (i != (initIndexAisle-1)) {
                distance_temp += this.crossAislesWidth;
                
                if (countCornersOn) {
                    crossAisle_temp = 2;
                    countCornersOn = Boolean.FALSE;
                }
            }
        }
        
        gap = this.largestGaps[current][this.actualColumn];
        this.decrementSubaisleItems(gap, Constant.FRONT, current);
        this.distance += pickAislesLength - ((gap.indexInitial - initIndex) * 2);
        
        if (this.subaislesByBlock[current] > 0) {
            distance_temp = this.crossAislesWidth;
            
            for (int i=initIndexAisle+1; i<=endIndexAisle; i++) {
                if (isThereProductsIntoSpecificAisle(current, i)) {
                    gap = this.largestGaps[current][i];
                    this.distance += distance_temp + ((finalIndex - gap.indexFinal) * 2);
                    this.countCorners += crossAisle_temp;
                    
                    distance_temp = 0.0;
                    crossAisle_temp = 0;
                    this.actualColumn = i;
                    
                    this.decrementSubaisleItems(gap, Constant.FRONT, current);
                    
                    if (this.subaislesByBlock[current] == 0) {
                        break;
                    }
                }
                if (i != (endIndexAisle)) {
                    distance_temp += this.crossAislesWidth;
                    
                    if (countCornersOn) {
                        crossAisle_temp = 2;
                        countCornersOn = Boolean.FALSE;
                    }                
                }
            }
        }
    }
    
    protected Boolean isThereOnlyOneSubAisle(int current) {
        Boolean guard = Boolean.FALSE;
        
        int initialIndex = this.crossAislesLocation[current];
        int finalIndex = this.crossAislesLocation[current+1];
        
        for (int i=0; i<this.largestGaps[current].length; i++) {
            Gap gap = new Gap( this.largestGaps[current][i] );
            if (gap.indexInitial != initialIndex && gap.indexFinal != finalIndex) {
                if (this.subaislesByBlock[current] == 2) {
                    return Boolean.TRUE;
                }
            } else if ((gap.indexInitial == initialIndex && gap.indexFinal != finalIndex) || 
                    (gap.indexInitial != initialIndex && gap.indexFinal == finalIndex)) {
                if (this.subaislesByBlock[current] == 1) {
                    return Boolean.TRUE;
                }
            }
        }
        return guard;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Decremento del numero de pasillos con items">
    protected void decrementSubaisleItems(Gap gap, int current) {
        if (this.subaislesByBlock[current] > 0) {
            int initialIndex = this.crossAislesLocation[current];
            int finalIndex = this.crossAislesLocation[current+1];

            if (gap.indexInitial != initialIndex && gap.indexFinal != finalIndex) {
                this.subaislesByBlock[current] -= 2;
            } else if (gap.indexInitial == initialIndex && gap.indexFinal != finalIndex) {
                this.subaislesByBlock[current]--;
            } else if (gap.indexInitial != initialIndex && gap.indexFinal == finalIndex) {
                this.subaislesByBlock[current]--;
            }
        }
    }
    
    protected void decrementSubaisleItems(Gap gap, int location, int current) {
        if (this.subaislesByBlock[current] > 0) {
            int initialIndex = this.crossAislesLocation[current];
            int finalIndex = this.crossAislesLocation[current+1];

            if (location == Constant.BACK) {
                if (gap.indexInitial != initialIndex) {
                    this.subaislesByBlock[current]--;
                }
            } else if (location == Constant.FRONT) {
                if (gap.indexFinal != finalIndex) {
                    this.subaislesByBlock[current]--;
                }
            }
        }
    }
    
    /**
     * 
     * @param firstAisleSelected
     * @param current 
     */
    protected void decrementSubaisleItems(int firstAisleSelected, int current) {
        if (current < this.subaislesByBlock.length && this.subaislesByBlock[current] > 0) {
            for (int block = current; block < this.largestGaps.length; block++) {
                int initialIndex = this.crossAislesLocation[block];
                int finalIndex = this.crossAislesLocation[block+1];

                Gap gap = new Gap( this.largestGaps[block][firstAisleSelected] );

                if (gap.indexInitial != initialIndex && gap.indexFinal != finalIndex) {
                    this.subaislesByBlock[block] -= 2;
                } else if (gap.indexInitial == initialIndex && gap.indexFinal != finalIndex) {
                    this.subaislesByBlock[block]--;
                } else if (gap.indexInitial != initialIndex && gap.indexFinal == finalIndex) {
                    this.subaislesByBlock[block]--;
                }
            }
        }
    }
    // </editor-fold>
    
    protected void countSubaislesByBlock() {
        this.subaislesByBlock = new int[this.crossAislesLocation.length-1];
        
        for (int block = 0; block < this.largestGaps.length; block++) {
            for (int aisle = 0; aisle < this.largestGaps[block].length; aisle++) {
                int initialIndex = this.crossAislesLocation[block];
                int finalIndex = this.crossAislesLocation[block+1];
                
                Gap gap = new Gap( this.largestGaps[block][aisle] );
                if (gap.indexInitial != initialIndex && gap.indexFinal != finalIndex) {
                    this.subaislesByBlock[block] += 2;
                } else if (gap.indexInitial == initialIndex && gap.indexFinal != finalIndex) {
                    this.subaislesByBlock[block]++;
                } else if (gap.indexInitial != initialIndex && gap.indexFinal == finalIndex) {
                    this.subaislesByBlock[block]++;
                }
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Para la obtención de los Gaps por pasillo y por bloque">
    private LGaps[][] getGaps( ) {
        LGaps[][] gapsByBlockAndAisle = new LGaps[this.crossAislesLocation.length-1][this.numAisles];
        
        int index = 0;
        
        for (int aisle=0; aisle<this.numAisles; aisle++) {
            ArrayList<Gap> gaps = new ArrayList<Gap>();
            Gap gap = null;
            int count = 0;
            Boolean guard = Boolean.TRUE;
            
            for (int current = 0; current < this.crossAislesLocation.length-1; current++) {
                for (int location=this.crossAislesLocation[current]; location <= this.crossAislesLocation[current+1]; location++) {
                    
                    if (guard) {
                        gap = new Gap();
                        gap.indexInitial = location;
                        count++;
                        guard = Boolean.FALSE;
                    } else if (this.graph[location][aisle] == 0) {
                        count++;
                    } else if (gap != null) {
                        gap.indexFinal = location;
                        gap.lengthGap = count;
                        count = 0;
                        gaps.add( gap );
                        
                        if ((location+1) <= this.crossAislesLocation[current+1]) {
                            gap  = new Gap();
                            gap.indexInitial = location;
                            count++;
                        }
                    }
                }
                gapsByBlockAndAisle[index][aisle] = new LGaps( gaps );
                gaps = new ArrayList<Gap>();
                guard = Boolean.TRUE;
                index++;
            }
            index = 0;
        }
        return gapsByBlockAndAisle;
    }
    
    private Gap[][] getLargestGap(LGaps[][] gapsByBlockAndAisle) {
        Gap[][] gaps = new Gap[this.crossAislesLocation.length-1][this.numAisles];
        
        for (int block=0; block<gapsByBlockAndAisle.length; block++) {
            for (int aisle=0; aisle<gapsByBlockAndAisle[block].length; aisle++) {
                LGaps gapsByAisle = gapsByBlockAndAisle[block][aisle];
                
                LGaps gapsByAisleOrder = LGaps.copyofLGaps( gapsByAisle );
                Collections.sort( gapsByAisleOrder.gaps );
                int max = gapsByAisleOrder.gaps.get(0).lengthGap;
                int count = getCountMaxLengthGap(gapsByAisleOrder, max);
                
                if (count == 1) {
                    gaps[block][aisle] = new Gap( gapsByAisleOrder.gaps.get(0) );
                } else {
                    int initIndex = this.crossAislesLocation[block];
                    int finalIndex = this.crossAislesLocation[block+1];
                    int differences = (int) Math.floor( (finalIndex - initIndex)/2 );
                    int reference = initIndex + differences;
                    
                    Gap selectedGap = new Gap();
                    int distance_min = 100;
                    for (int i=0; i<gapsByAisle.gaps.size(); i++) {
                        Gap gap = new Gap( gapsByAisle.gaps.get(i) );
                        if (gap.lengthGap == max) {
                            int distance = Math.abs( reference - gap.indexInitial );
                            if (distance <= distance_min) {
                                selectedGap = gap;
                            }
                        }
                    }
                    gaps[block][aisle] = new Gap( selectedGap );
                }
            }
        }
        return gaps;
    }
    
    private int getCountMaxLengthGap(LGaps gapsByAisleOrder, int max) {
        int count = 0;
        for (int i=0; i<gapsByAisleOrder.gaps.size(); i++) {
            int number = gapsByAisleOrder.gaps.get(i).lengthGap;
            if (number == max) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
    // </editor-fold>
}