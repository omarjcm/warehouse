/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author omarjcm
 */
public class OrderSimilarity {
    
    public Order order_01;
    public Order order_02;
    public int similarity;
    
    public static Comparator<OrderSimilarity> SIMILARITY_DESC_COMPARATOR = new Comparator<OrderSimilarity>() {
        @Override
        public int compare(OrderSimilarity o1, OrderSimilarity o2) {
            return o2.similarity - o1.similarity;
        }
    };
    
    public OrderSimilarity(OrderSimilarity os) {
        this.order_01 = new Order( os.order_01 );
        this.order_02 = new Order( os.order_02 );
        this.similarity = os.similarity;
    }
    
    public OrderSimilarity(Order order_01, Order order_02) {
        this.order_01 = new Order( order_01 );
        this.order_02 = new Order( order_02 );
        this.similarity = this.getSimilarity( this.order_01, this.order_02 );
    }
    
    private int getSimilarity( Order order_01, Order order_02 ) {
        int metric = 0;
        
        Collections.sort( order_01.items, Item.ITEM_DESC_COMPARATOR );
        Collections.sort( order_02.items, Item.ITEM_DESC_COMPARATOR );
        
        for (int i=0; i<order_01.items.size(); i++) {
            Item item_01 = new Item( order_01.items.get( i ) );
            
            for (int j=0; j<order_02.items.size(); j++) {
                Item item_02 = new Item( order_02.items.get( j ) );
                
                if (item_01.id_product == item_02.id_product) {
                    metric++;
                }
            }
        }
        return metric;
    }
}