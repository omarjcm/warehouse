/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

/**
 *
 * @author omarjcm
 */
public class Random {
    
    private final int[] instances;
    
    public Random() {
        this.instances = new int[100];
    }
    
    /**
     * Método que retorna un valor entre [1 y un valor maximo].
     * @param max valor maximo
     * @return se obtiene un valor entre [1, max].
     */
    public static int getRandom(int max) {
        return (int) ((Math.random() * max) + 1);
    }
    
    /**
     * Método que retorna un valor entre [0 y un valor (maximo-1)].
     * @param max valor maximo
     * @return se obtiene un valor entre [1, (max-1)].
     */
    public static int getRandomIndex(int max) {
        return (int) (Math.random() * max);
    }
    
    public void procedure() {
        for (int i=0; i<instances.length; i++) {
            this.instances[i] = this.getRandom( 10 );
        }
    }
}