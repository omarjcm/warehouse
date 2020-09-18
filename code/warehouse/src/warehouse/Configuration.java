    package warehouse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author omarjcm
 */
public class Configuration {
    
    /**
     * Scholz A, Wäscher G (2017) Order Batching and Picker Routing in manual order picking systems:
     * the benefits of integrated routing. Central European Journal of Operations Research 25(2):491–
     * 520, DOI 10.1007/s10100-017-0467-x
     * 
     * Basado en:
     * Henn S, Koch S, Doerner KF, Strauss C, Wäscher G (2010) Metaheuristics for the Or-
     * der Batching Problem in Manual Order Picking Systems. Business Research 3(1):82–105, DOI
     * 10.1007/BF03342717
     * Henn S, Wäscher G (2012) Tabu Search Heuristics for the Order Batching Problem in Man-
     * ual Order Picking Systems. European Journal of Operational Research 222(3):484–494, DOI
     * 10.1016/j.ejor.2012.05.049
     */
    
    public static int[] CAPACITY_PICK_DEVICE = { 30, 45, 60, 75 };
    public static int[] NUM_EXTRA_CROSS_AISLES = { 0, 1, 2, 3 };
    public static int[] NUM_AISLES = { 10, 20, 30 };
    public static int[] NUM_STORAGE_LOCATIONS = { 900, 2000, 3000 };
    public static int[] ORDERS_LENGTH = { 20, 30, 40, 50, 60, 70, 80, 90, 100 };
    
    // Datos para la generación aleatoria de pedidos
    public static int MIN_ITEMS = 1;
    public static int MAX_ITEMS = 25;
    public static int NUM_ITEMS = 1;
    
    /**
     * almacen rectangular.
     */
    public int[][] warehouseLayout;
    /**
     * Distancias entre los vertices que se encuentran en el almacen.
     */
    public double distances[][];
    /**
     * Numero de filas.
     */
    public int numRows;
    /**
     * Numero de columnas.
     */
    public int numColumns;
    /**
     * Numero de ubicaciones por cada lado del pasillo.
     */
    public int numLocationsPerAisleSide;
    /**
     * Ubicaciones de los pasillos transversales.
     */
    public int crossAislesLocation[];
    /**
     * Numero total de ubicaciones en el almacen.
     */
    public int numTotalLocations;
    /**
     * Numero de vertices de los productos.
     */
    public int numProductVertices;
    /**
     * Numero de vertices articiales de los pasillos transversales.
     */
    public int numArtificialVertices;
    /**
     * Numero de vertices totales.
     */
    public int numVertices;
    /**
     * Vertice inicial de los vertices artificiales.
     */
    public int firstArtificialVertex;
    
    public int[][] aisleBlock;
    public int numCrossAisles;
    
    public int[] productLocation;
    public int sizeLocationsProducts;
    
    public int numAisles;
    public int numExtraCrossAisles;
    public int numLocations;
    
    public Warehouse warehouse;
    
    public Configuration(int numExtraCrossAisles, int numAisles, int numLocations) {
        this.numExtraCrossAisles = numExtraCrossAisles;
        this.numAisles = numAisles;
        this.numLocations = numLocations;
        
        this.warehouse = new Warehouse();
        this.warehouse.loadWarehouse( );
    }
    
    /**
     * Calcula el numero de localidades por cada lado del pasillo dependiendo del numero de productos
     */
    public void calculationNumLocationsPerAisleSide( ) {
        this.numTotalLocations = this.numAisles * 2 * this.warehouse.num_shelves;
        this.numLocationsPerAisleSide = this.numLocations / this.numTotalLocations;
        if ((this.numLocationsPerAisleSide * this.numTotalLocations) < numLocations) {
            this.numLocationsPerAisleSide++;
        }
        //int numLocations = numLocationsPerAisleSide * this.numAisles * 2 * this.numShelves;        
    }
    
    /**
     * Calcula los indices de los pasillos transversales
     */
    public void calculationCrossAislesLocation( ) {
        this.numCrossAisles = this.numExtraCrossAisles + this.warehouse.num_cross_aisles;
        this.crossAislesLocation = new int[ this.numCrossAisles ];
        this.crossAislesLocation[0] = 0;
        
        if (this.numExtraCrossAisles > 0) {
            int baseLoc = this.numLocationsPerAisleSide / (this.numExtraCrossAisles + 1);
            int oneExtra = this.numLocationsPerAisleSide % (this.numExtraCrossAisles + 1);
            int pos = 0;
            for (int i = 0; i < this.numExtraCrossAisles; i++) {
                pos = pos + baseLoc;
                if (oneExtra > 0) {
                    pos++;
                    oneExtra--;
                }
                this.crossAislesLocation[i+1] = pos+(i+1);
            }
        }
        this.crossAislesLocation[this.crossAislesLocation.length-1] = this.numLocationsPerAisleSide + ( this.numExtraCrossAisles ) + 1;
    }
    
    /**
     * Calcula las distancias por cada nodo
     */
    public void calculateDistances() {
        this.distances = Constant.initializeDouble(this.numVertices+1, this.numVertices+1);
        
        int vertexProduct = 1;
        int vertexProduct2 = this.numLocationsPerAisleSide;
        int increment = 1;
        int increment2 = 2;

        for (int i=0; i<this.aisleBlock.length; i++) {
            for (int j=0; j<this.aisleBlock[i].length; j++) {
                /**
                 * Distancia entre vértices de los pasillos transversales.
                 */
                if (j+1 < this.aisleBlock[i].length) {
                    this.distances[ this.aisleBlock[i][j] ][ this.aisleBlock[i][j+1] ] = 
                            this.warehouse.location_width * 2 + this.warehouse.aisle_width;
                    this.distances[ this.aisleBlock[i][j+1] ][ this.aisleBlock[i][j] ] = 
                            this.warehouse.location_width * 2 + this.warehouse.aisle_width;
                }
                /**
                 * Distancia entre vertices del pasillo transversal al pasillo de recogida (atras).
                 */
                if (this.crossAislesLocation[i] == i) {
                    this.distances[ this.aisleBlock[i][j] ][ vertexProduct ] = 
                            this.warehouse.location_length/2 + this.warehouse.cross_aisle_width/2;
                    this.distances[ vertexProduct ][ this.aisleBlock[i][j] ] = 
                            this.warehouse.location_length/2 + this.warehouse.cross_aisle_width/2;
                    vertexProduct = this.numLocationsPerAisleSide * increment + 1;
                    increment++;
                }
                /**
                 * Distancia entre vertices del pasillo transversal al pasillo de recogida (adelante).
                 */
                if (this.crossAislesLocation[i] == this.numLocationsPerAisleSide+1) {
                    this.distances[ this.aisleBlock[i][j] ][ vertexProduct2 ] = 
                            this.warehouse.location_length/2 + this.warehouse.cross_aisle_width/2;
                    this.distances[ vertexProduct2 ][ this.aisleBlock[i][j] ] = 
                            this.warehouse.location_length/2 + this.warehouse.cross_aisle_width/2;
                    vertexProduct2 = this.numLocationsPerAisleSide * increment2;
                    increment2++;
                }
            }
        }
        /**
         * Distancia entre los vertices de los pasillos de recogida.
         */
        int contador = 0;
        for (int vertex=1; vertex<=this.numProductVertices; vertex++) {
            if (contador < this.numLocationsPerAisleSide-1) {
                this.distances[ vertex ][ vertex + 1 ] = this.warehouse.location_length;
                this.distances[ vertex + 1 ][ vertex ] = this.warehouse.location_length;
                contador++;
            } else {
                contador = 0;
            }
        }
    }
    
    /**
     * Calculo de los valores del tamaño del almacen.
     */
    public void warehouseLayout() {
        this.numRows = (this.numExtraCrossAisles + this.warehouse.num_cross_aisles) + this.numLocationsPerAisleSide;
        this.numColumns = this.numAisles;
        
        this.warehouseLayout = Constant.initializeInt(this.numRows, this.numColumns );
    }
    
    /**
     * Configura la cantidad de vertices que tendra el almacen.
     */
    public void configTotalVertices() {
        this.numProductVertices = this.numLocationsPerAisleSide * this.numAisles;
        this.numArtificialVertices = (this.warehouse.num_cross_aisles + this.numExtraCrossAisles) * this.numAisles;
        this.numVertices = this.numProductVertices + this.numArtificialVertices;
        this.firstArtificialVertex = 1 + this.numProductVertices;
        
        this.sizeLocationsProducts = this.numProductVertices * 2;
    }
    
    /**
     * Configura la cantidad de vertices artificiales (pasillos transversales) que tiene el almacen.
     */
    public void configArtificialVertex() {
        this.aisleBlock = new int[ this.numCrossAisles ][ this.numColumns ];
        int vertex = this.firstArtificialVertex;
        for (int i=0; i<this.numCrossAisles; i++) {
            for (int j=0; j<this.numColumns; j++) {
                this.aisleBlock[i][j] = vertex;
                vertex++;
            }
        }
    }
    
    /**
     * Asignacion de vertices al almacen.
     */
    public void vertexToWarehouse() {
        int vertex = 1;
        int cur = 0;
        for (int j=0; j < this.numColumns; j++) {
            for (int i=0; i < this.numRows; i++) {
                if ((cur < (this.crossAislesLocation.length-1) && cur > 0 && i == this.crossAislesLocation[cur]) ||
                        (cur < this.crossAislesLocation.length && cur == 0 && this.crossAislesLocation[cur] == i)) {
                    this.warehouseLayout[i][j] = this.aisleBlock[cur][j];
                    cur++;
                } else if (cur == (this.crossAislesLocation.length-1) && i == this.crossAislesLocation[cur] ) {
                    this.warehouseLayout[i][j] = this.aisleBlock[cur][j];
                    cur=0;
                } else {
                    this.warehouseLayout[i][j] = vertex;
                    vertex++;
                }
            }
        }
    }
    
    /**
     * Setea las ubicaciones de los productos en el almacen.
     */
    public void setToIndexProducts() {
        this.productLocation = new int[ this.sizeLocationsProducts ];
        
        int index = 0;
        int vertex = 1;
        int increment = 2;
        while (index < this.sizeLocationsProducts) {
            int size = index + this.numLocationsPerAisleSide;
            while (index < size) {
                if ((index + this.numLocationsPerAisleSide) < this.sizeLocationsProducts) {
                    this.productLocation[index] = vertex;                    
                    this.productLocation[index + this.numLocationsPerAisleSide] = vertex;
                }
                index++;
                vertex++;
            }
            index = this.numLocationsPerAisleSide * increment;
            increment = increment + 2;
        }
    }
    
    public void loadConfiguration() {
        calculationNumLocationsPerAisleSide();
        calculationCrossAislesLocation();
        warehouseLayout();
        configTotalVertices();
        configArtificialVertex();
        vertexToWarehouse();
        calculateDistances();
        setToIndexProducts();
    }
}