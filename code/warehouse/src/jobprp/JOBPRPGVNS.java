/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobprp;

import instances.Order;
import java.util.ArrayList;
import warehouse.Configuration;

/**
 *
 * @author omarjcm
 */
public class JOBPRPGVNS extends JOBPRPAlgorithm {

    public JOBPRPGVNS(int numOrders, int numCapacity, ArrayList<Order> orders, 
            Configuration warehouse, int typeOfOBAlgorithm, int typeOfPRAlgorithm) {
        super(numOrders, numCapacity, orders, warehouse, typeOfOBAlgorithm, typeOfPRAlgorithm);
    }

    @Override
    public void procedure( ) {
        
    }
    
}
