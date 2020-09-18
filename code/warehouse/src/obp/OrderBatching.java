/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obp;

import instances.Batch;
import instances.Order;
import instances.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import warehouse.Constant;

/**
 *
 * @author omarjcm
 */
public class OrderBatching {

    public List<ArrayList<Integer>> productLocationBatch;
    public List<ArrayList<Integer>> vertixLocationBatch;

    public ArrayList<Batch> batches;

    public int sizeSetOrders;
    public int minSizeSetItems;
    public int sizeSetItems;
    public int sizeQuantity;
    public int capacityPickDevice;
    public int minProductsRequired;
    
    public boolean instancesRandom = Boolean.FALSE;
    
    public ArrayList<Order> orders;
    
    /**
     *
     * @param sizeSetOrders Tamanio de los pedidos
     * @param capacityPickDevice capacidad del carrito
     * @param minProductsRequired productos minimos requeridos
     */
    public OrderBatching(int sizeSetOrders, int capacityPickDevice, int minProductsRequired) {
        this.sizeSetOrders = sizeSetOrders;
        this.capacityPickDevice = capacityPickDevice;
        this.minProductsRequired = minProductsRequired;
        this.orders = null;
    }

    /**
     *
     * @param sizeSetOrders Tamanio de los pedidos
     * @param minSizeSetItems Tamanio minimo de items por pedidos
     * @param sizeSetItems Tamanio maximo de items por pedidos
     * @param sizeQuantity Tamanio del numero de items por cada detalle del
     * pedido
     * @param capacityPickDevice capacidad del carrito
     * @param minProductsRequired productos minimos requeridos
     */
    public OrderBatching(int sizeSetOrders, int minSizeSetItems, int sizeSetItems,
            int sizeQuantity, int capacityPickDevice, int minProductsRequired) {
        this.sizeSetOrders = sizeSetOrders;
        this.minSizeSetItems = minSizeSetItems;
        this.sizeSetItems = sizeSetItems;
        this.sizeQuantity = sizeQuantity;
        this.capacityPickDevice = capacityPickDevice;
        this.minProductsRequired = minProductsRequired;
        this.orders = null;
    }

    /**
     * Elaboracion de lotes de pedidos aleatorios.
     *
     * @param typeOfAlgorithm tipo de algoritmo por lotes
     */
    private void setBatches(ArrayList<Order> objects, int typeOfAlgorithm, double[] parameters) {
        this.orders = objects;
        // Construccion de los lotes
        switch (typeOfAlgorithm) {
            case Constant.GRASP_01: {
                OBPGrasp_01 object = new OBPGrasp_01(this.orders, this.capacityPickDevice, parameters[0]);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.GRASP_02: {
                OBPGrasp_02 object = new OBPGrasp_02(this.orders, this.capacityPickDevice, parameters[0]);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.GRASP_03: {
                OBPGrasp_03 object = new OBPGrasp_03(this.orders, this.capacityPickDevice, parameters[0]);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            default:
                break;
        }
    }
    
    private void setBatches(ArrayList<Order> objects, int typeOfAlgorithm) {
        if (objects == null) {
            // Generación de pedidos aleatorios.
            this.orders = Order.createSetRandomOrders(this.sizeSetOrders, this.minSizeSetItems, this.sizeSetItems, this.sizeQuantity, this.minProductsRequired);
            this.instancesRandom = Boolean.TRUE;
        } else
            this.orders = objects;

        // Construccion de los lotes
        switch (typeOfAlgorithm) {
            case Constant.RANDOM: {
                OBPRandom object = new OBPRandom(this.orders, this.capacityPickDevice);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.STRICT_ORDER_PICKING: {
                OBPStrictOrderPicking object = new OBPStrictOrderPicking(this.orders, this.capacityPickDevice);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.FIRST_COME_FIRST_SERVE: {
                OBPFCFS object = new OBPFCFS(this.orders, this.capacityPickDevice);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.GREEDY_01: {
                OBPGreedy_01 object = new OBPGreedy_01(this.orders, this.capacityPickDevice);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.GREEDY_02: {
                OBPGreedy_02 object = new OBPGreedy_02(this.orders, this.capacityPickDevice);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            case Constant.GREEDY_03: {
                OBPGreedy_03 object = new OBPGreedy_03(this.orders, this.capacityPickDevice);
                object.procedure();
                this.batches = object.batches;
                break;
            }
            default:
                break;
        }
    }

    /**
     * Elaboración de la lista de pedidos no repetidos antes del ruteo.
     */
    private void setBatchesToLocations() {
        this.productLocationBatch = new ArrayList<ArrayList<Integer>>();

        // Recorro cada lote
        for (int i = 0; i < this.batches.size(); i++) {
            ArrayList<Integer> productsBatch = new ArrayList<Integer>();
            // Recorro cada conjunto de pedido por lote
            for (int j = 0; j < this.batches.get(i).orders.size(); j++) {
                // localizo objetos unicos sin repeticiones
                insertProductsLocations(productsBatch, this.batches.get(i).orders.get(j).items);
            }
            Collections.sort(productsBatch);
            this.productLocationBatch.add(productsBatch);
        }
    }
    
    /**
     * Elaboración de la lista de pedidos no repetidos antes del ruteo de un lote.
     */
    private void setBatchesToLocations(int index) {
        // Recorro cada lote
        ArrayList<Integer> productsBatch = new ArrayList<Integer>();
        // Recorro cada conjunto de pedido por lote
        for (int j = 0; j < this.batches.get(index).orders.size(); j++) {
            // localizo objetos unicos sin repeticiones
            insertProductsLocations(productsBatch, this.batches.get(index).orders.get(j).items);
        }
        Collections.sort(productsBatch);
        this.productLocationBatch.set(index, productsBatch);
    }

    private void insertProductsLocations(ArrayList<Integer> productionBatch, List<Item> items) {
        for (Item item : items) {
            if (!isRepeted(productionBatch, item.id_product)) {
                productionBatch.add(item.id_product);
            }
        }
    }

    /**
     * Busqueda de items si se repiten a traves del id del producto
     *
     * @param id_product
     * @return returna verdadero si se repite; caso contrario, falso.
     */
    private boolean isRepeted(ArrayList<Integer> list, int element) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elaboracion de la lista de los vertices con los indices de los productos
     * no repetidos previo al ruteo.
     *
     * @param productLocation
     */
    private void setProductsVertices(int[] productLocation) {
        this.vertixLocationBatch = new ArrayList<ArrayList<Integer>>();
        
        // Recorro por cada lote
        for (int i = 0; i < this.productLocationBatch.size(); i++) {
            ArrayList<Integer> verticesBatch = new ArrayList<Integer>();
            // Recorro por cada pedido en un lote
            for (int j = 0; j < this.productLocationBatch.get(i).size(); j++) {
                // Recorro por cada producto de cada pedido.
                if (!isRepeted(verticesBatch, productLocation[ this.productLocationBatch.get(i).get(j) ])) {
                    verticesBatch.add( productLocation[ this.productLocationBatch.get(i).get(j) ] );
                }
            }
            Collections.sort( verticesBatch );
            this.vertixLocationBatch.add( verticesBatch );
        }
    }
    
    /**
     * Elaboracion de la lista de los vertices con los indices de los productos
     * no repetidos previo al ruteo de un lote.
     *
     * @param productLocation
     */
    private void setProductsVertices(int[] productLocation, int index) {
        // Recorro por cada lote
        ArrayList<Integer> verticesBatch = new ArrayList<Integer>();
        // Recorro por cada pedido en un lote
        for (int j = 0; j < this.productLocationBatch.get(index).size(); j++) {
            // Recorro por cada producto de cada pedido.
            if (!isRepeted(verticesBatch, productLocation[ this.productLocationBatch.get(index).get(j) ])) {
                verticesBatch.add(productLocation[this.productLocationBatch.get(index).get(j)]);
            }
        }
        Collections.sort(verticesBatch);
        this.vertixLocationBatch.set(index, verticesBatch);
    }
    
    /**
     * Procedimiento que funciona para utilizar los algoritmos OB Constructive
     * @param productLocation
     * @param orders
     * @param typeOfAlgorithm 
     */
    public void procedure(int[] productLocation, ArrayList<Order> orders, int typeOfAlgorithm) {
        this.setBatches(orders, typeOfAlgorithm);
        this.setBatchesToLocations();
        this.setProductsVertices(productLocation);
    }
    
    /**
     * Procedimiento que funciona sobre los algoritmos GRASP
     * @param productLocation
     * @param orders
     * @param typeOfAlgorithm
     * @param parameters 
     */
    public void procedure(int[] productLocation, ArrayList<Order> orders, int typeOfAlgorithm, double[] parameters) {
        this.setBatches(orders, typeOfAlgorithm, parameters);
        this.setBatchesToLocations();
        this.setProductsVertices(productLocation);
    }
    
    /**
     * Procedimiento que funciona cuando ya se tiene un batche definido
     * @param productLocation
     * @param indexBatch
     * @param batches 
     */
    public void procedure(int[] productLocation, int indexBatch, ArrayList<Batch> batches) {
        this.batches = batches;
        this.setBatchesToLocations( indexBatch );
        this.setProductsVertices(productLocation, indexBatch);
    }
}