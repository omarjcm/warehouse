/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import java.util.ArrayList;

/**
 *
 * @author omarjcm
 */
public class RegisterDistance {
    
    public double sshapeDistance;
    public double sshapeTime;
    public double largestGapDistance;
    public double largestGapTime;
    
    public RegisterDistance() {
        this.sshapeDistance = 0.0;
        this.sshapeTime = 0.0;
        this.largestGapDistance = 0.0;
        this.largestGapTime = 0.0;        
    }
    
    public RegisterDistance(RegisterDistance data) {
        this.sshapeDistance = data.sshapeDistance;
        this.sshapeTime = data.sshapeTime;
        this.largestGapDistance = data.largestGapDistance;
        this.largestGapTime = data.largestGapTime;
    }
    
    public static ArrayList<RegisterDistance> copyOfRegisterDistance(ArrayList<RegisterDistance> distances) {
        ArrayList<RegisterDistance> distancesCopy = new ArrayList<RegisterDistance>();
        
        for (int i=0; i<distances.size(); i++) {
            RegisterDistance object = new RegisterDistance( distances.get( i ) );
            distancesCopy.add( object );
        }
        return distancesCopy;
    }
}