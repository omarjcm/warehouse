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
public class LS_1x0 extends JOBPRPLSAlgorithm {
    
    public LS_1x0(int numOrders, int numCapacity, ArrayList<Order> orders, 
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
    
    public void procedure(ArrayList<Batch> batches) {
        this.solutions = local_search( Batch.copyOfBatches( batches ) );        
    }
    
    private ArrayList<Batch> local_search(ArrayList<Batch> batches) {
        // conteo de los pedidos en todos los lotes
        int countOrders = totalOrders( batches );
        
        while (countOrders > 0) {
            int batchesSize = batches.size();
            /**
             * Se selecciona un lote y un pedido del lote, de manera aleatoria
             */
            int indexSelectedBatch = Random.getRandomIndex( batchesSize );
            Batch selectedBatch = batches.get( indexSelectedBatch );
            int indexSelectedOrder = Random.getRandomIndex( selectedBatch.orders.size() );
            Order selectedOrder = selectedBatch.orders.get( indexSelectedOrder );
            
            // Se verifica que el pedido seleccionado no se haya seleccionado antes
            if (!selectedOrder.isVerified && countOrders > 0) {
                selectedBatch.orders.get(indexSelectedOrder).isVerified = Boolean.TRUE;
                batches.set(indexSelectedBatch, selectedBatch);
                countOrders--;
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
                        
                        if (this.numCapacity >= (batch.totalItemsBatch + selectedOrder.totalItems)) {
                            
                            // Hago una copia de los batches antes de modificarlos
                            ArrayList<Batch> beforeBatches = Batch.copyOfBatches( batches );
                            
                            selectedBatchCopy.totalItemsBatch -= selectedOrder.totalItems;
                            selectedBatchCopy.orders.remove( indexSelectedOrder );
                            batches.set(indexSelectedBatch, selectedBatchCopy);
                            
                            batch.orders.add( selectedOrder );
                            batch.totalItemsBatch += selectedOrder.totalItems;
                            batches.set(indexBatch, batch);
                            
                            double distance_before_01 = this.getDistance( selectedBatchCopy );
                            Batch newBatch_01 = this.updatedSolution(batches, indexSelectedBatch);
                            double distance_after_01 = this.getDistance( newBatch_01 );
                            
                            double distance_before_02 = this.getDistance( batch );
                            Batch newBatch_02 = this.updatedSolution(batches, indexBatch);
                            double distance_after_02 = this.getDistance( newBatch_02 );
                            
                            if (selectedBatchCopy.totalItemsBatch == 0) {
                                batches.remove( indexSelectedBatch );
                                batchesSize = batches.size();
                            }
                            // Existen casos donde el intercambio 1x0 deja al lote inicial sin nada.
                            // Minimizacion de la funcion objetivo
                            if (distance_after_01 >= distance_before_01 || distance_after_02 >= distance_before_02) {
                                batches = Batch.copyOfBatches( beforeBatches );
                            } else
                                break;
                        }
                    }
                }
            }
        }
        return batches;
    }
}