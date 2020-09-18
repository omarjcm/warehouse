/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import instances.Random;

/**
 *
 * @author omarjcm
 */
public class Constant {
    
    public final static int RANDOM = 0;
    public final static int STRICT_ORDER_PICKING = 1;
    public final static int FIRST_COME_FIRST_SERVE = 2;
    public final static int GREEDY_01 = 3;
    public final static int GREEDY_02 = 4;
    public final static int GREEDY_03 = 5;
    public final static int GRASP_01 = 6;
    public final static int GRASP_02 = 7;
    public final static int GRASP_03 = 8;
    
    public final static int ILS_FCFS = 0; 
    public final static int ILS_RANDOM = 1;
    public final static int ILS_SOP = 2;
    public final static int ILS_GREEDY_01 = 3;
    public final static int ILS_GREEDY_02 = 4;
    public final static int ILS_GREEDY_03 = 5;
    
    public final static int LS_1X0 = 0;
    public final static int LS_1X1 = 1;
    public final static int LS_1X2 = 2;
    public final static int LS_2X2 = 3;
        
    public final static int SPEED_M_S = 1;
    public final static int PICKING_TIME_S = 10;
    public final static int COURNER_TIME_S = 2;
    
    public final static int DESC = 0;
    public final static int ASC = 1;
    
    public final static int FRONT = 0;
    public final static int BACK = 1;
    
    public final static int S_SHAPE = 0;
    public final static int LARGEST_GAP = 1;
    
    public final static int OBC = 0;
    public final static int RANDOM_LOCAL_SEARCH = 1;
    public final static int OB_LOCAL_SEARCH = 2;
    public final static int OB_GRASP = 3;
    
    public final static int INSTANCES_UNIFORM = 0;
    public final static int INSTANCES_CLASSES_BASED = 1;
    
    public static String getOrderBatching(int option) {
        String orderBatching = "";
        switch (option) {
            case Constant.RANDOM:
                orderBatching = "RANDOM";
                break;
            case Constant.STRICT_ORDER_PICKING:
                orderBatching = "SOP";
                break;
            case Constant.FIRST_COME_FIRST_SERVE:
                orderBatching = "FCFS";
                break;
            case Constant.GREEDY_01:
                orderBatching = "G01";
                break;
            case Constant.GREEDY_02:
                orderBatching = "G02";
                break;
            case Constant.GREEDY_03:
                orderBatching = "G03";
                break;
            case Constant.GRASP_01:
                orderBatching = "GRASP01";
                break;
            case Constant.GRASP_02:
                orderBatching = "GRASP02";
                break;
            case Constant.GRASP_03:
                orderBatching = "GRASP03";
                break;
            default:
                break;
        }
        return orderBatching;
    }
    
    public static String getRoute(int option) {
        String route = "";
        if (option == Constant.S_SHAPE) {
            route = "SSHAPE";
        } else if (option == Constant.LARGEST_GAP) {
            route = "LGAP";
        }
        return route;
    }
    
    public static String getLS(int option) {
        String route = "";
        switch (option) {
            case Constant.LS_1X0:
                route = "LS_1x0";
                break;
            case Constant.LS_1X1:
                route = "LS_1x1";
                break;
            case Constant.LS_1X2:
                route = "LS_1x2";
                break;
            case Constant.LS_2X2:
                route = "LS_2x2";
                break;
            default:
                break;
        }
        return route;
    }
    
    /**
     * Impresion de los valores en las matrices
     * @param array arreglo donde se almacenan los valores
     * @param n numero de filas
     * @param m numero de columnas
     */
    public static void imprimir(int[][] array, int n, int m) {
        System.out.println();
        for (int i=0; i < n; i++) {
            System.out.print( i + " - \t");
            for (int j=0; j< m; j++) {
                System.out.print( array[i][j] + "\t" );
            }
            System.out.println();
        }
    }
    
    public static void imprimir(double[][] array, int n, int m) {
        System.out.println();
        for (int i=0; i < n; i++) {
            System.out.print( i + " - \t");
            for (int j=0; j< m; j++) {
                System.out.print( array[i][j] + "\t" );
            }
            System.out.println();
        }
    }
    
    /**
     * Inicializa las matrices en valores con cero.
     * @return 
     */
    public static int[][] initializeInt( int numRows, int numColumns ) {
        int[][] array = new int[ numRows ][ numColumns ];
        for (int i=0; i < numRows; i++) {
            for (int j=0; j< numColumns; j++) {
                array[i][j] = 0;
            }
        }
        return array;
    }
    
    public static int[] initializeWithMinusOne(int size) {
        int[] array = new int[ size ];
        for (int i=0; i<size; i++) {
            array[i] = -1;
        }
        return array;
    }
    
    public static double[][] initializeDouble( int numRows, int numColumns ) {
        double[][] array = new double[ numRows ][ numColumns ];
        for (int i=0; i < numRows; i++) {
            for (int j=0; j< numColumns; j++) {
                array[i][j] = 0.0;
            }
        }
        return array;
    }
    
    public static int[] getRandomDifferents(int indexListSize, int size) {
        int[] indexList = new int[ indexListSize ];
        indexList = Constant.initializeWithMinusOne( indexListSize );
        
        if (indexListSize == 1) {
            indexList[0] = 0;
        } else {
            int indexFound = 0;
            for (int i=0; i<indexListSize; i++) {
                while (true) {
                    indexFound = Random.getRandomIndex( size );
                    if ( !isFound(indexList, indexFound) )
                        break;
                }
                indexList[i] = indexFound;
            }
        }
        return indexList;
    }
    
    private static boolean isFound(int[] indexList, int index) {
        
        for (int i=0; i<indexList.length; i++) {
            if (indexList[i] == index)
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }    
}
