/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobprp;

import instances.Batch;
import instances.Order;
import instances.Random;
import java.util.ArrayList;
import warehouse.Configuration;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class LS_2x2 extends JOBPRPLSAlgorithm {
    
    public LS_2x2(int numOrders, int numCapacity, ArrayList<Order> orders, 
            Configuration warehouse, int typeOfOBAlgorithm, int typeOfPRAlgorithm) {
        super(numOrders, numCapacity, orders, warehouse, typeOfOBAlgorithm, typeOfPRAlgorithm);
    }
    
    public void procedure(double[] parameters) {
        this.initialSolutions = this.initialSolutions( parameters );
        this.solutions = local_search( Batch.copyOfBatches( this.initialSolutions ) );
    }
    
    public void procedure() {
        this.initialSolutions = this.initialSolutions( );
        this.solutions = local_search( Batch.copyOfBatches( this.initialSolutions ) );
    }
    
    private ArrayList<Batch> local_search(ArrayList<Batch> batches) {
        // conteo de los pedidos en todos los lotes
        int countOrders = totalOrders( batches );
        
        while (countOrders > 0) {
            int batchesSize = batches.size();
            
            int indexSelectedBatch = Random.getRandomIndex( batchesSize );
            Batch selectedBatch = batches.get( indexSelectedBatch );
            /**
             * Para el recorrido de los demas lotes, se obtiene una propuesta de 
             * lista de lotes ordenados aleatoriamente
             */
            int[] indexListBatches = Constant.getRandomDifferents(batchesSize, batchesSize);
            
            for (int i = 0; i < batchesSize; i++) {
                int indexBatch = indexListBatches[i];
                
                if (indexSelectedBatch != indexBatch) {
                    Batch batch = new Batch( batches.get( indexBatch ) );
                    Batch selectedBatchCopy = new Batch( selectedBatch );
                    
                    if (selectedBatchCopy.orders.size() >= 2 && batch.orders.size() >= 2) {
                        int[] indexListSelectedOrder = Constant.getRandomDifferents(2, selectedBatchCopy.orders.size());
                        
                        Order selectedOrder_01 = selectedBatchCopy.orders.get( indexListSelectedOrder[0] );
                        Order selectedOrder_02 = selectedBatchCopy.orders.get( indexListSelectedOrder[1] );
                        
                        int[] indexListOrder = Constant.getRandomDifferents(2, batch.orders.size());

                        Order order_01 = new Order( batch.orders.get( indexListOrder[0] ) );
                        Order order_02 = new Order( batch.orders.get( indexListOrder[1] ) );
                        
                        int capacitySelectedBatch = selectedBatch.totalItemsBatch - (selectedOrder_01.totalItems + selectedOrder_02.totalItems);
                        int capacityBatch = batch.totalItemsBatch - (order_01.totalItems + order_02.totalItems);

                        if ((this.numCapacity >= (capacitySelectedBatch + (order_01.totalItems + order_02.totalItems))) && 
                                (this.numCapacity >= (capacityBatch + selectedOrder_01.totalItems + selectedOrder_02.totalItems))) {
                            // Hago una copia de los batches antes de modificarlos
                            ArrayList<Batch> beforeBatches = Batch.copyOfBatches( batches );
                            /**
                             * Intercambio de pedidos en lotes seleccionados
                             */
                            batch.orders.add( selectedOrder_01 );
                            batch.orders.add( selectedOrder_02 );
                            if (indexListOrder[0] > indexListOrder[1]) {
                                batch.orders.remove( indexListOrder[0] );
                                batch.orders.remove( indexListOrder[1] );
                            } else {
                                batch.orders.remove( indexListOrder[1] );
                                batch.orders.remove( indexListOrder[0] );
                            }
                            batch.totalItemsBatch -= (order_01.totalItems + order_02.totalItems);
                            batch.totalItemsBatch += (selectedOrder_01.totalItems + selectedOrder_02.totalItems);
                            batches.set(indexBatch, batch);

                            selectedBatchCopy.orders.add( order_01 );
                            selectedBatchCopy.orders.add( order_02 );
                            if (indexListSelectedOrder[0] > indexListSelectedOrder[1]) {
                                selectedBatchCopy.orders.remove( indexListSelectedOrder[0] );
                                selectedBatchCopy.orders.remove( indexListSelectedOrder[1] );
                            } else {
                                selectedBatchCopy.orders.remove( indexListSelectedOrder[1] );
                                selectedBatchCopy.orders.remove( indexListSelectedOrder[0] );
                            }
                            selectedBatchCopy.totalItemsBatch -= (selectedOrder_01.totalItems + selectedOrder_02.totalItems);
                            selectedBatchCopy.totalItemsBatch += (order_01.totalItems + order_02.totalItems);
                            batches.set(indexSelectedBatch, selectedBatchCopy);

                            /**
                             * Calculo de las distancias de los lotes seleccionados
                             */
                            double distance_selectedBatch_before = this.getDistance( selectedBatchCopy );
                            Batch newSelectedBatch = this.updatedSolution(batches, indexSelectedBatch);
                            double distance_selectedBatch_after = this.getDistance( newSelectedBatch );

                            double distance_batch_before = this.getDistance( batch );
                            Batch newBatch = this.updatedSolution(batches, indexBatch);
                            double distance_batch_after = this.getDistance( newBatch );

                            // Existen casos donde el intercambio 1x0 deja al lote inicial sin nada.
                            if (selectedBatchCopy.totalItemsBatch == 0) {
                                batches.remove( indexSelectedBatch );
                            }
                            if (batch.totalItemsBatch == 0) {
                                batches.remove( indexBatch );
                            }
                            // Minimizacion de la funcion objetivo
                            if ((distance_selectedBatch_after < distance_selectedBatch_before) && 
                                    (distance_batch_after < distance_batch_before)) {
                                break;
                            } else {
                                // en el caso de no disminuir la distancia la 
                                // lista de batches se mantiene como estaba antes
                                batches = Batch.copyOfBatches( beforeBatches );
                            }                            
                        }
                    }
                }
            }
            countOrders--;
        }
        return batches;
    }
}
