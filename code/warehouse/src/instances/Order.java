/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instances;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author omarjcm
 */
public class Order {
    
    public ArrayList<Item> items;
    public int totalItems;
    public boolean isAssigned;
    public boolean isVerified;
    
    public int index;
    
    public Order(Order order) {
        this.totalItems = order.totalItems;
        this.isAssigned = order.isAssigned;
        this.isVerified = order.isVerified;
        this.index = order.index;
        
        this.items = Item.copyOfOrders( order.items );
    }
    
    public Order(Order order, int index) {
        this.totalItems = order.totalItems;
        this.isAssigned = order.isAssigned;
        this.isVerified = order.isVerified;
        this.index = index;
        
        this.items = Item.copyOfOrders( order.items );
    }
    
    public Order( ) {
        this.items = new ArrayList<Item>();
        // Este atributo me sirve en el algoritmo OBPRandom & Greedy_03
        this.isAssigned = Boolean.FALSE;
        // Este atributo me sirve para el algoritmo LS
        this.isVerified = Boolean.FALSE;
    }
    
    public Order(int minSize, int size) {
        int n;
        if (size > minSize) {
            n = size;
        } else {
            n = minSize;
        }
        this.items = new ArrayList<Item>(n);
        for (int i=0; i<n; i++) {
            this.items.add( new Item( ) );
        }
        // Este atributo me sirve en el algoritmo OBPRandom & Greedy_03
        this.isAssigned = Boolean.FALSE;
    }
       
    /**
     * 
     * @param sizeSetOrders tamanio del conjunto de pedidos
     * @param minSizeSetItems tamanio minimo del conjunto de elementos por pedido
     * @param sizeSetItems tamanio del conjunto de elementos por pedido
     * @param sizeQuantity cantidad de un elemento dentro de un pedido
     * @param minProductsRequired minimo de productos requeridos
     * @return lista de pedidos aleatorios
     */
    public static ArrayList<Order> createSetRandomOrders(int sizeSetOrders, int minSizeSetItems, 
            int sizeSetItems, int sizeQuantity, int minProductsRequired) {
        
        ArrayList<Order> orders = new ArrayList<Order>( sizeSetOrders );
        for (int i=0; i<sizeSetOrders; i++) {
            Order order = new Order( minSizeSetItems, Random.getRandom( sizeSetItems ) );
            //order.createOrderByItems( Random.getRandom( sizeQuantity ), minProductsRequired );
            order.createOrderByItems( sizeQuantity, minProductsRequired );
            orders.add( order );
        }
        return (ArrayList<Order>) orders;
    }
    
    /**
     * Metodo que asigna los items a un pedido de manera aleatoria.
     * @param quantity la cantidad de un item.
     * @param minProductsRequired minimo de productos requeridos.
     */
    public void createOrderByItems( int quantity, int minProductsRequired ) {
        this.totalItems = 0;
        for (int i=0; i<this.items.size(); i++) {
            Item item = new Item();
            item.id_product = Random.getRandom( minProductsRequired )-1;
            item.quantity = quantity;
            // Se almacena y se mantiene actualizada la cantidad de elementos en el pedido.
            this.totalItems += item.quantity;
            this.items.set(i, item);
        }
    }
    
    public static ArrayList<Order> copyOfOrders( ArrayList<Order> orders ) {
        ArrayList<Order> ordersCopy = new ArrayList<Order>();
        
        for (int i=0; i<orders.size(); i++) {
            Order order = new Order( orders.get(i) );
            ordersCopy.add( order );
        }
        return ordersCopy;
    }

    public static void print(List<Order> objects) {
        for (Order object: objects) {
            System.out.println( object.totalItems );
        }
    }
    
    public static Comparator<Order> ORDER_DESC_COMPARATOR = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o2.totalItems - o1.totalItems;
        }
    };
    
    public static Comparator<Order> ORDER_ASC_COMPARATOR = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.totalItems - o2.totalItems;
        }
    };
}