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
public class Results_OBC {
    
    private String idInstance;
    private int numOrders;
    private int numCapacity;
    private String routing;
    private long FCFS_Time_ns;
    private long RANDOM_Time_ns;
    private long SOP_Time_ns;
    private long GREEDY_01_Time_ns;
    private long GREEDY_02_Time_ns;
    private long GREEDY_03_Time_ns;
    
    public Results_OBC() {
    }
    
    public Results_OBC(Results_OBC object) {
        this.idInstance = object.idInstance;
        this.numOrders = object.numOrders;
        this.numCapacity = object.numCapacity;
        this.routing = object.routing;
        this.FCFS_Time_ns = object.FCFS_Time_ns;
        this.RANDOM_Time_ns = object.RANDOM_Time_ns;
        this.SOP_Time_ns = object.SOP_Time_ns;
        this.GREEDY_01_Time_ns = object.GREEDY_01_Time_ns;
        this.GREEDY_02_Time_ns = object.GREEDY_02_Time_ns;
        this.GREEDY_03_Time_ns = object.GREEDY_03_Time_ns;
    }
    
    public Results_OBC(String idInstance, int numOrders, int numCapacity, String routing,
            long FCFS_Time_ns, long RANDOM_Time_ns, long SOP_Time_ns, 
            long GREEDY_01_Time_ns, long GREEDY_02_Time_ns, long GREEDY_03_Time_ns) {
        this.idInstance = idInstance;
        this.numOrders = numOrders;
        this.numCapacity = numCapacity;
        this.routing = routing;
        this.FCFS_Time_ns = FCFS_Time_ns;
        this.RANDOM_Time_ns = RANDOM_Time_ns;
        this.SOP_Time_ns = SOP_Time_ns;
        this.GREEDY_01_Time_ns = GREEDY_01_Time_ns;
        this.GREEDY_02_Time_ns = GREEDY_02_Time_ns;
        this.GREEDY_03_Time_ns = GREEDY_03_Time_ns;
    }

    public String getIdInstance() {
        return idInstance;
    }

    public void setIdInstance(String idInstance) {
        this.idInstance = idInstance;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(String numOrders) {
        this.numOrders = Integer.parseInt( numOrders );
    }

    public int getNumCapacity() {
        return numCapacity;
    }

    public void setNumCapacity(String numCapacity) {
        this.numCapacity = Integer.parseInt( numCapacity );
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public long getFCFS_Time_ns() {
        return FCFS_Time_ns;
    }

    public void setFCFS_Time_ns(String FCFS_Time_ns) {
        this.FCFS_Time_ns = Long.parseLong( FCFS_Time_ns );
    }

    public long getRANDOM_Time_ns() {
        return RANDOM_Time_ns;
    }

    public void setRANDOM_Time_ns(String RANDOM_Time_ns) {
        this.RANDOM_Time_ns = Long.parseLong( RANDOM_Time_ns );
    }

    public long getSOP_Time_ns() {
        return SOP_Time_ns;
    }

    public void setSOP_Time_ns(String SOP_Time_ns) {
        this.SOP_Time_ns = Long.parseLong( SOP_Time_ns );
    }

    public long getGREEDY_01_Time_ns() {
        return GREEDY_01_Time_ns;
    }

    public void setGREEDY_01_Time_ns(String GREEDY_01_Time_ns) {
        this.GREEDY_01_Time_ns = Long.parseLong( GREEDY_01_Time_ns );
    }

    public long getGREEDY_02_Time_ns() {
        return GREEDY_02_Time_ns;
    }

    public void setGREEDY_02_Time_ns(String GREEDY_02_Time_ns) {
        this.GREEDY_02_Time_ns = Long.parseLong( GREEDY_02_Time_ns );
    }

    public long getGREEDY_03_Time_ns() {
        return GREEDY_03_Time_ns;
    }

    public void setGREEDY_03_Time_ns(String GREEDY_03_Time_ns) {
        this.GREEDY_03_Time_ns = Long.parseLong( GREEDY_03_Time_ns );
    }
    
    
}