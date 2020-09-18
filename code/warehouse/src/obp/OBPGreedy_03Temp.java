/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import instances.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author omarjcm
 */
public class OBPGreedy_03Temp extends OBPAlgorithm {
    
    private HashMap<Integer, Integer> frecuency;
    
    public OBPGreedy_03Temp(ArrayList<Order> orders, int capacityByBatch) {
        super( orders );
        this.frecuency = new HashMap<Integer, Integer>();
        this.capacityByBatch = capacityByBatch;
    }
    
    @Override
    public void procedure() {
        // Obtengo las frecuencias de cada item.
        this.getFrecuency();
        
        // Se ordena de mayor a menor las frecuencias de los items.
        Object[] productsFrecuency = this.frecuency.entrySet().toArray();
        Arrays.sort(productsFrecuency, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<Integer, Integer>) o2).getValue()
                           .compareTo(((Map.Entry<Integer, Integer>) o1).getValue());
            }
        });
        
        // Se recorre cada item ordenado 
        for (Object product : productsFrecuency) {
            ArrayList<Order> objects = this.searchOrder( ((Map.Entry<Integer, Integer>) product).getKey() );
            
            for (int k=0; k<objects.size(); k++) {
                Order order = objects.get( k );
                
                if (this.batches.size() > 0) {
                    int batchSize = this.batches.size();

                    for (int i=0; i<batchSize; i++) {
                        Batch batch = this.batches.get( i );

                        if (this.capacityByBatch >= (order.totalItems + batch.totalItemsBatch)) {
                            batch.orders.add( order );
                            batch.totalItemsBatch += order.totalItems;

                            this.setAssignedOrder( order );
                            break;
                        } else if (i == (batchSize-1)) {
                            this.addOrderToBatch( order );
                            this.setAssignedOrder( order );
                        }
                    }
                } else {
                    this.addOrderToBatch( order );
                    this.setAssignedOrder( order );
                }
            }
        }
    }
    
    private void setAssignedOrder(Order order) {
        order.isAssigned = Boolean.TRUE;
        this.orders.set(order.index, order);        
    }
    
    private ArrayList<Order> searchOrder(int id_product) {
        ArrayList<Order> objects = new ArrayList<Order>();
        
        for (int i=0; i<this.orders.size(); i++) {
            Order selectedOrder = new Order( this.orders.get(i) );
            
            if (!selectedOrder.isAssigned) {
                for (int j=0; j<selectedOrder.items.size( ); j++) {
                    if (selectedOrder.items.get( j ).id_product == id_product) {
                        selectedOrder.index = i;
                        objects.add( selectedOrder );
                        break;
                    }
                }
            }
        }
        return objects;
    }
    
    private void getFrecuency() {
        for (int i=0; i<this.orders.size(); i++) {
            Order order = this.orders.get( i );
            for (int j=0; j<order.items.size(); j++) {
                Item item = order.items.get( j );
                
                if ( this.frecuency.containsKey( item.id_product ) ) {
                    this.frecuency.put( item.id_product, this.frecuency.get( item.id_product ) + item.quantity );
                } else {
                    this.frecuency.put( item.id_product, item.quantity );
                }
            }
        }
    }
}