/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import java.util.ArrayList;

/**
 *
 * @author omarjcm
 */
public class Instance {
    
    public String idInstance;
    public int numOrders;
    public int numCapacity;
    public ArrayList<Order> orders;
    
    public Instance() {
        this.orders = new ArrayList<Order>();
    }
}