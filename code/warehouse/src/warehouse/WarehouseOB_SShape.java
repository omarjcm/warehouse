/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import obp.OrderBatching;
import prp.PickerRouting;

/**
 *
 * @author omarjcm
 */
public class WarehouseOB_SShape {
    public static void main(String[] args) {
        int _NUM_EXTRA_CROSS_AISLES_ = 3;
        int _NUM_AISLES_ = 5;
        int _NUM_STORAGE_LOCATIONS_ = 200;
        
        Configuration warehouse = new Configuration( _NUM_EXTRA_CROSS_AISLES_, _NUM_AISLES_, _NUM_STORAGE_LOCATIONS_ );
        warehouse.loadConfiguration();
        
        int _ORDERS_LENGTH_ = 20;
        int _MIN_ITEMS_ = 1;
        int _MAX_ITEMS_ = 25;
        int _NUM_ITEMS_ = 1;
        
        int _CAPACITY_PICK_DEVICE_ = 25;
        
        OrderBatching object = new OrderBatching(_ORDERS_LENGTH_, _MIN_ITEMS_, _MAX_ITEMS_, _NUM_ITEMS_, 
                _CAPACITY_PICK_DEVICE_, _NUM_STORAGE_LOCATIONS_);
        object.procedure(warehouse.productLocation, null, Constant.GREEDY_03);
        PickerRouting route = new PickerRouting(warehouse, Constant.LARGEST_GAP);
        route.procedure( object );
        System.out.println("");
    }
}