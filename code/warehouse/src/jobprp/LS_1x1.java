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
public class LS_1x1 extends JOBPRPLSAlgorithm {
    
    public LS_1x1(int numOrders, int numCapacity, ArrayList<Order> orders, 
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
            
            int indexSelectedBatch = Random.getRandomIndex( batchesSize );
            Batch selectedBatch = batches.get( indexSelectedBatch );
            int indexSelectedOrder = Random.getRandomIndex( selectedBatch.orders.size() );
            Order selectedOrder = selectedBatch.orders.get( indexSelectedOrder );
            
            if ((!selectedOrder.isVerified) && countOrders > 0) {
                selectedBatch.orders.get( indexSelectedOrder ).isVerified = Boolean.TRUE;
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
                        
                        int indexOrder = Random.getRandomIndex( batch.orders.size() );
                        Order order = new Order( batch.orders.get( indexOrder ) );
                        
                        if (!order.isVerified && countOrders > 0) {
                            order.isVerified = Boolean.TRUE;
                            batches.get( indexBatch ).orders.set( indexOrder, order );
                            countOrders--;
                            
                            int capacitySelectedBatch = selectedBatch.totalItemsBatch - selectedOrder.totalItems;
                            int capacityBatch = batch.totalItemsBatch - order.totalItems;

                            if ((this.numCapacity >= (capacitySelectedBatch + order.totalItems)) && 
                                    (this.numCapacity >= (capacityBatch + selectedOrder.totalItems))) {
                                // Hago una copia de los batches antes de modificarlos
                                ArrayList<Batch> beforeBatches = Batch.copyOfBatches( batches );

                                /**
                                 * Intercambio de pedidos en lotes seleccionados
                                 */
                                batch.orders.remove( indexOrder );
                                batch.totalItemsBatch -= order.totalItems;
                                batch.orders.add( selectedOrder );
                                batch.totalItemsBatch += selectedOrder.totalItems;
                                batches.set(indexBatch, batch);

                                selectedBatchCopy.orders.remove( indexSelectedOrder );
                                selectedBatchCopy.totalItemsBatch -= selectedOrder.totalItems;
                                selectedBatchCopy.orders.add( order );
                                selectedBatchCopy.totalItemsBatch += order.totalItems;
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
            }
        }
        return batches;
    }
}
