/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

/**
 *
 * @author omarjcm
 */
public class TimeExecution {
    
    public static long getMilliseconds(long lStartTime, long lEndTime) {
        return ((lEndTime - lStartTime) / 1000000);
    }
}