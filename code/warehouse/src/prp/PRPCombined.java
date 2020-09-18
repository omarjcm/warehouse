/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

import warehouse.Configuration;

/**
 *
 * @author omarjcm
 */
public class PRPCombined extends PRPAlgorithm {

    public PRPCombined(Configuration config, int[][] graph) {
        super(config, graph);
    }
    
    @Override
    public void procedure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void calculateDistanceBlockLeftToRight(int initIndexAisle, int endIndexAisle, double pickAislesLength, int current) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void calculateDistanceBlockRightToLeft(int initIndexAisle, int lastIndexAisle, double pickAislesLength, int current) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void countSubaislesByBlock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}