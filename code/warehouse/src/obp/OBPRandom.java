/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import instances.Random;
import java.util.ArrayList;

/**
 *
 * @author omarjcm
 */
public final class OBPRandom extends OBPAlgorithm {
    
    /**
     * 
     * @param orders los pedidos a recoger
     * @param capacityByBatch la capacidad por lote que sería la del carrito
     */
    public OBPRandom( ArrayList<Order> orders, int capacityByBatch ) {
        super( orders );
        this.capacityByBatch = capacityByBatch;
    }
    
    private int getIndexOrderNotAssigned( ) {
        boolean centinela = Boolean.TRUE;
        int index = -1;
        while (centinela) {
            index = Random.getRandom( this.orders.size() ) - 1;
            if (!this.orders.get(index).isAssigned) 
                centinela = Boolean.FALSE;
        }
        return index;
    }
    
    @Override
    public void procedure() {
        int frecuency = 0;
        while (frecuency < this.orders.size()) {
            int index = getIndexOrderNotAssigned( );
            boolean guard = Boolean.TRUE;
            for (int i=0; i<this.batches.size(); i++) {
                if (guard && (this.batches.size() > 0) && 
                        ((this.orders.get(index).totalItems + this.batches.get(i).totalItemsBatch) <= this.capacityByBatch)) {
                    this.orders.get(index).isAssigned = Boolean.TRUE;
                    this.batches.get(i).totalItemsBatch += this.orders.get(index).totalItems;
                    this.batches.get(i).orders.add( this.orders.get(index) );
                    guard = Boolean.FALSE;
                    frecuency++;
                }
            }
            if (guard) {
                this.orders.get(index).isAssigned = Boolean.TRUE;
                Batch batch = new Batch();
                batch.orders.add( this.orders.get( index ) );
                batch.totalItemsBatch += this.orders.get(index).totalItems;
                this.batches.add( batch );
                frecuency++;
            }
        }
    }
}