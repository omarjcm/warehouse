/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobprp;

import instances.Batch;
import instances.Order;
import java.util.ArrayList;
import warehouse.Configuration;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class JOBPRPGrasp extends JOBPRPAlgorithm {

    public int iterations;
    public int typeOfILSAlgorithm;
    public double alpha;
    
    public ArrayList<Batch> bestSolution;
    
    public JOBPRPGrasp(int numOrders, int numCapacity, ArrayList<Order> orders, Configuration warehouse, 
            int typeOfOBAlgorithm, int typeOfPRAlgorithm, int typeOfILSAlgorithm, int iterations, double alpha) {
        super(numOrders, numCapacity, orders, warehouse, typeOfOBAlgorithm, typeOfPRAlgorithm);
        
        this.iterations = iterations;
        this.typeOfILSAlgorithm = typeOfILSAlgorithm;
        this.alpha = alpha;
        this.bestSolution = new ArrayList<Batch>();
    }
    
    @Override
    public void procedure() {
        double f_ = 100000000;
        for (int i=0; i<this.iterations; i++) {
            // En el localSearch se ejecuta el algoritmo OBPGrasp (tipo = 1, 2, 3)
            ArrayList<Batch> batches = localSearch( );
            
            double f = this.getTotalDistance( batches );
            
            if (f < f_) {
                f_ = f;
                this.bestSolution = Batch.copyOfBatches( batches );
            }
        }
    }
    
    private ArrayList<Batch> localSearch( ) {
        double[] parameters = new double[1];
        parameters[0] = this.alpha;
        
        ArrayList<Batch> batches = new ArrayList<Batch>();
        
        switch (this.typeOfILSAlgorithm) {
            case Constant.LS_1X0:
                {
                    LS_1x0 object = new LS_1x0(this.numOrders, this.numCapacity, this.orders, this.warehouse,
                            this.typeOfOBAlgorithm, this.typeOfPRAlgorithm);
                    object.procedure( parameters );
                    batches = object.solutions;
                    break;
                }
            case Constant.LS_1X1:
                {
                    LS_1x1 object = new LS_1x1(this.numOrders, this.numCapacity, this.orders, this.warehouse,
                            this.typeOfOBAlgorithm, this.typeOfPRAlgorithm);
                    object.procedure( parameters );
                    batches = object.solutions;
                    break;
                }
            case Constant.LS_1X2:
                {
                    LS_1x2 object = new LS_1x2(this.numOrders, this.numCapacity, this.orders, this.warehouse,
                            this.typeOfOBAlgorithm, this.typeOfPRAlgorithm);
                    object.procedure( parameters );
                    batches = object.solutions;
                    break;
                }
            case Constant.LS_2X2:
                {
                    LS_2x2 object = new LS_2x2(this.numOrders, this.numCapacity, this.orders, this.warehouse,
                            this.typeOfOBAlgorithm, this.typeOfPRAlgorithm);
                    object.procedure( parameters );
                    batches = object.solutions;
                    break;
                }
            default:
                break;
        }
        return batches;
    }
}