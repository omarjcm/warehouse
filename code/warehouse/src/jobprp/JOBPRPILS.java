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
public class JOBPRPILS extends JOBPRPLSAlgorithm {

    public double lambda;
    public double miu;
    public int iterations;
    
    public ArrayList<Batch> bestSolution;
    
    public JOBPRPILS(int numOrders, int numCapacity, ArrayList<Order> orders, 
            Configuration warehouse, int typeOfOBAlgorithm, int typeOfPRAlgorithm, int iterations, double lambda, double miu) {
        super(numOrders, numCapacity, Order.copyOfOrders(orders), warehouse, typeOfOBAlgorithm, typeOfPRAlgorithm);
        this.iterations = iterations;
        this.lambda = lambda;
        this.miu = miu;
        this.iterations = iterations; 
        this.initialSolutions = new ArrayList<Batch>();
        this.bestSolution = new ArrayList<Batch>();
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getMiu() {
        return miu;
    }

    public void setMiu(double miu) {
        this.miu = miu;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public ArrayList<Batch> getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(ArrayList<Batch> bestSolution) {
        this.bestSolution = bestSolution;
    }
    
    
    
    public void procedure() {
        int count = 1;
        int step = 0;
        
        this.initialSolutions = this.initialSolutions();
        
        this.bestSolution =  this.local_search( this.initialSolutions );
        ArrayList<Batch> x_inc = Batch.copyOfBatches( this.bestSolution );
        
        do {
            double gamma = x_inc.size() * this.lambda + 1;
            
            ArrayList<Batch> x = perturbations( x_inc, (int) Math.floor( gamma ) );
            x = local_search( x );
            
            double fr = this.distance( x );
            double fr_ = this.distance( this.bestSolution );
            
            if ( fr < fr_ ) {
                this.bestSolution = Batch.copyOfBatches( x );
                x_inc = Batch.copyOfBatches( x );
            }
            
            if (Boolean.TRUE && ((fr - fr_) < (this.miu * fr_))) {
                x_inc = Batch.copyOfBatches( x );
                count = 0;
            }
            step++;
        } while (step < this.iterations);
        
        if (this.distance( this.initialSolutions ) < this.distance( this.bestSolution )) {
            this.bestSolution = Batch.copyOfBatches( this.initialSolutions );
        }
    }
    
    private double distance( ArrayList<Batch> batches ) {
        double distances = 0;
        
        for (int i=0; i<batches.size(); i++) {
            if (this.typeOfPRAlgorithm == Constant.S_SHAPE)
                distances += batches.get(i).register.sshapeDistance;
            else if (this.typeOfPRAlgorithm == Constant.LARGEST_GAP)
                distances += batches.get(i).register.largestGapDistance;
        }
        return distances;
    }
    
    private ArrayList<Batch> local_search(ArrayList<Batch> batches) {
        ArrayList<Batch> updatedBatches = this.swap( Batch.copyOfBatches( batches ) );
        updatedBatches = this.shift( Batch.copyOfBatches( updatedBatches ) );
        
        return updatedBatches;
    }
    
    private ArrayList<Batch> swap(ArrayList<Batch> batches) {
        LS_1x1 object = new LS_1x1(this.numOrders, this.numCapacity, this.orders, 
                this.warehouse, this.typeOfOBAlgorithm, this.typeOfPRAlgorithm);
        object.procedure( batches );
        return object.solutions;
    }
    
    private ArrayList<Batch> shift(ArrayList<Batch> batches) {
        LS_1x0 object = new LS_1x0(this.numOrders, this.numCapacity, this.orders, 
                this.warehouse, this.typeOfOBAlgorithm, this.typeOfPRAlgorithm);
        object.procedure( batches );
        return object.solutions;
    }
    
    private ArrayList<Order> getListOrders(Batch batch, int q) {
        ArrayList<Order> orders = new ArrayList<Order>();
        
        for (int index=q-1; index>=0; index--) {
            Order order = batch.orders.remove( index );
            batch.totalItemsBatch -= order.totalItems;
            orders.add( order );
        }
        return orders;
    }
    
    private void addOrdersToBatch(Batch batch, ArrayList<Order> orders) {
        for (int index=0; index<orders.size(); index++) {
            Order order = new Order( orders.get(index) );
            batch.totalItemsBatch += order.totalItems;
            batch.orders.add( order );
        }
    }
    
    private ArrayList<Batch> perturbations(ArrayList<Batch> batchesList, int gamma) {
        ArrayList<Batch> batches = Batch.copyOfBatches( batchesList );
                
        for (int i=1; i<=gamma; i++) {
            /**
             * Seleccion de los lotes aleatorios
             */
            int[] indexSelectedBatches = Constant.getRandomDifferents(2, batches.size());
            
            Batch batch_01 = new Batch( batches.get( indexSelectedBatches[0] ) );
            Batch batch_02 = new Batch( batches.get( indexSelectedBatches[1] ) );
            
            Batch batch_01_temp = new Batch( batch_01 );
            Batch batch_02_temp = new Batch( batch_02 );
                        
            int q1 = Random.getRandom( batch_01.orders.size() / 2 );
            ArrayList<Order> orders_01 = this.getListOrders(batch_01, q1);
            
            int q2 = Random.getRandom( batch_02.orders.size() / 2 );
            ArrayList<Order> orders_02 = this.getListOrders(batch_02, q2);
            
            this.addOrdersToBatch(batch_01, orders_02);
            this.addOrdersToBatch(batch_02, orders_01);
            
            if ((this.numCapacity >= batch_01.totalItemsBatch) && (this.numCapacity >= batch_02.totalItemsBatch)) {
                /**
                 * Actualizacion de distancias.
                 */
                batch_01 = this.updatedSolution(batches, indexSelectedBatches[0]);
                batches.set(indexSelectedBatches[0], batch_01);
                
                batch_02 = this.updatedSolution(batches, indexSelectedBatches[1]);
                batches.set(indexSelectedBatches[1], batch_02);
            } else {
                /**
                 * Dejar los batches como estaban al inicio.
                 */
                batch_01 = new Batch( batch_01_temp );
                batch_02 = new Batch( batch_02_temp );
            }
        }
        return batches;                                                                                                                                                                                                                                                                                                                                                                                                                                                
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 