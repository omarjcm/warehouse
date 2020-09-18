/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

import java.util.ArrayList;
import warehouse.Configuration;

/**
 *
 * @author omarjcm
 */
public class PRPSShape extends PRPAlgorithm {
    
    public PRPSShape(Configuration config, int[][] graph) {
        super(config, graph);
    }
    
    @Override
    public void procedure( ) {
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
                    /** 
                     * this.warehouse.location_length * (numRowsRange - 2) elimina la distancia de un subpasillo 
                     * a un pasillo transversal (los extremos), ya que puede variar dependiendo de la 
                     * configuración del almacén
                     */
                    double pickAislesLength = this.blocksLength[current];
                    
                    // Este código ejecuta el ruteo S-SHape en el último bloque donde se encuentran elementos
                        if (step == 0) {
                        this.decrementSubaisleItems(firstIndexAisle, this.crossAislesLocation[current + 1], current+1);
                        this.distance += distance_temp;
                        this.calculateDistanceBlockLeftToRight(firstIndexAisle, this.numAisles, pickAislesLength, current);
                        step++;
                    } else if (step > 0) {
                        if (this.subaislesByBlock[current] > 1) {
                            int indexLeftAisle = this.searchIndexLeftOrRightMostSubaisle(firstIndexAisle, current, PRPSShape._LEFT_MOST_SUBAISLE_);
                            int indexRightAisle = this.searchIndexLeftOrRightMostSubaisle(firstIndexAisle, current, PRPSShape._RIGHT_MOST_SUBAISLE_);
                            
                            int diffLeftAisle = Math.abs( indexLeftAisle - this.actualColumn );
                            int diffRigthAisle = Math.abs( indexRightAisle - this.actualColumn );
                            
                            if (diffRigthAisle < diffLeftAisle) {
                                // si la distancia es mayor a cero, eso significa que tendrá que virar dos veces.
                                if (diffRigthAisle > 0) {
                                    this.countCorners += 2;
                                }
                                // con este calculo de la distancia se envía al recolector siempre a la parte frontal de cada bloque
                                this.distance += pickAislesLength + (diffRigthAisle * this.crossAislesWidth);
                                // dado que ya se recorrio el primer pasillo de recogida se disminuye el contador de pasillos con items
                                this.subaislesByBlock[current]--;
                                // se calcula el avance en la distancia, hacia el siguiente pasillo de recogida a través del pasillo transversal
                                this.distance += this.crossAislesWidth;
                                this.calculateDistanceBlockRightToLeft(indexLeftAisle, indexRightAisle, pickAislesLength, current);                                
                            } else if (diffLeftAisle <= diffRigthAisle) {
                                if (diffLeftAisle > 0) {
                                    this.countCorners += 2;
                                }
                                this.distance += pickAislesLength + (diffLeftAisle * this.crossAislesWidth);
                                this.subaislesByBlock[current]--;
                                this.distance += this.crossAislesWidth;
                                this.calculateDistanceBlockLeftToRight(indexLeftAisle+1, indexRightAisle+1, pickAislesLength, current);
                            }
                        } else if (this.subaislesByBlock[current] == 1) {
                            int indexAisles = this.searchIndexAisleWithItemsFromLeftToRigth(firstIndexAisle+1, this.numAisles, current);
                            int distanceToAisles = Math.abs( indexAisles - this.actualColumn );
                            if (distanceToAisles > 0) {
                                this.countCorners += 2;
                            }
                            this.actualColumn = indexAisles;
                            this.distance += pickAislesLength + (distanceToAisles * this.crossAislesWidth);
                        } else if (this.subaislesByBlock[current] == 0) {
                            this.distance += pickAislesLength;
                        }
                    }
                }
                this.distance += this.actualColumn * this.crossAislesWidth;
            }
        }
    }
    
    public void calculateDistanceBlockRightToLeft(int initIndexAisle, int lastIndexAisle, double pickAislesLength, int current) {
        boolean guard = Boolean.TRUE;
        boolean countCornersOn = Boolean.TRUE;
        
        int row = -1;
        int crossAisle_temp = 0;
        int distance_temp = 0;
        
        int _BACK_ = 0;
        int _FRONT_ = 1;
        
        row = _FRONT_;
        
        for (int i=lastIndexAisle-1; i>=initIndexAisle; i--) {
            if (isThereAislesWithItems(current) && isThereProductsIntoSpecificAisle(current, i)) {
                this.distance += (distance_temp + pickAislesLength);
                this.countCorners += crossAisle_temp;
                
                distance_temp = 0;
                crossAisle_temp = 0;
                countCornersOn = Boolean.TRUE;
                
                this.actualColumn = i;
                
                if (guard) {
                    if (this.subaislesByBlock[current] == 1) {
                        this.indexItem = getIndexFromLastItemIntoAisles(i, current);
                    }
                    row = _BACK_;
                    guard = Boolean.FALSE;
                } else {
                    row = _FRONT_;
                    guard = Boolean.TRUE;
                }
                this.subaislesByBlock[current]--;
            }
            if (isThereAislesWithItems(current) && i!=lastIndexAisle) {
                distance_temp += this.crossAislesWidth;
                
                if (countCornersOn) {
                    crossAisle_temp = 2;
                    countCornersOn = Boolean.FALSE;
                }
            }
        }
        if (row == _BACK_) {
            this.calculateDistanceWhenFinishedBackAisle(pickAislesLength, current);
        }
    }
    
    public void calculateDistanceBlockLeftToRight(int initIndexAisle, int endIndexAisle, double pickAislesLength, int current) {
        boolean guard = Boolean.TRUE;
        boolean countCornersOn = Boolean.TRUE;
        
        int row = -1;
        int distance_temp = 0;
        int crossAisle_temp = 0;
        
        int _BACK_ = 0;
        int _FRONT_ = 1;
        
        for (int i=initIndexAisle; i<endIndexAisle; i++) {
            if (isThereAislesWithItems(current) && isThereProductsIntoSpecificAisle(current, i)) {
                this.distance += (distance_temp + pickAislesLength);
                // contabiliza los giros que se realizan en los pasillos transversales.
                this.countCorners += crossAisle_temp;
                
                distance_temp = 0;
                crossAisle_temp = 0;
                countCornersOn = Boolean.TRUE;
                
                this.actualColumn = i;
                
                if (guard) {
                    if (this.subaislesByBlock[current] == 1) {
                        /**
                         * En este paso, se obtiene la ubicación de un item en el 
                         * último pasillo a recorrer, cuando lo cruza desde el frente 
                         * hasta la parte de atrás del bloque
                         */
                        this.indexItem = getIndexFromLastItemIntoAisles(i, current);
                    }
                    row = _BACK_;
                    guard = Boolean.FALSE;
                } else {
                    row = _FRONT_;
                    guard = Boolean.TRUE;
                }
                // Se resta de uno en uno los subpasillos que tienen items a recoger
                this.subaislesByBlock[current]--;
            }
            /**
             * En el caso de existir mas items en un solo bloque, se contabilizan 
             * los trayectos por los pasillos transversales
             */
            if (isThereAislesWithItems(current) && i != (endIndexAisle-1)) {
                distance_temp += this.crossAislesWidth;
                
                if (countCornersOn) {
                    crossAisle_temp = 2;
                    countCornersOn = Boolean.FALSE;
                }
            }
        }
        /**
         * En el caso de terminar recorriendo un pasillo entero (y ya no hay items 
         * por recoger en los demás pasillos o es el último item); por el cual debe regresar
         */
        if (row == _BACK_) {
            this.calculateDistanceWhenFinishedBackAisle(pickAislesLength, current);
        }
    }
    
    /**
     * Contabiliza el número de pasillos de recogida que tienen items por bloques
     */
    protected void countSubaislesByBlock() {
        this.subaislesByBlock = new int[this.crossAislesLocation.length-1];
        
        for (int i=0; i<this.numAisles; i++) {
            boolean guard = Boolean.TRUE;
            int current = 0;
            for (int j=0; j<this.numRows; j++) {
                if (this.crossAislesLocation[current] == j) {
                    current++;
                    guard = Boolean.TRUE;
                } else if (guard && this.graph[j][i] < this.firstArtificialVertex && this.graph[j][i] != 0) {
                    this.subaislesByBlock[current == 0 ? current : current-1]++;
                    guard = Boolean.FALSE;
                }
            }
        }
    }
    
    protected void decrementSubaisleItems(int firstAisleSelected, int initialLocationIndex, int current) {
        boolean guard = Boolean.TRUE;
        
        for (int j=initialLocationIndex; j<this.numRows; j++) {
            if (this.crossAislesLocation[current] == j) {
                current++;
                guard = Boolean.TRUE;
            } else if (guard && this.graph[j][firstAisleSelected] < this.firstArtificialVertex && this.graph[j][firstAisleSelected] != 0) {
                this.subaislesByBlock[current-1]--;
                guard = Boolean.FALSE;
            }
        }
    }    
}