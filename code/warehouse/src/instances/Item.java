/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author omarjcm
 */
public class Item {
    
    public int id_product;
    public int quantity;
    
    public Item() {
    }
    
    public Item(Item item) {
        this.id_product = item.id_product;
        this.quantity = item.quantity;
    }
    
    public static ArrayList<Item> copyOfOrders( ArrayList<Item> items ) {
        ArrayList<Item> itemsCopy = new ArrayList<Item>();
        
        for (int i=0; i<items.size( ); i++) {
            Item item =  new Item( items.get( i ) );
            itemsCopy.add( item );
        }
        return itemsCopy;
    }
    
    public static Comparator<Item> ITEM_DESC_COMPARATOR = new Comparator<Item>() {
        @Override
        public int compare(Item i1, Item i2) {
            return i2.id_product - i1.id_product;
        }
    };    
}