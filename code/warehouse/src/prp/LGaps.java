/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

import java.util.ArrayList;

/**
 *
 * @author omarjcm
 */
public class LGaps {
    
    public ArrayList<Gap> gaps;
    
    public LGaps() {
        this.gaps = new ArrayList<Gap>();
    }
    
    public LGaps(ArrayList<Gap> gaps) {
        this.gaps = gaps;
    }
    
    public static LGaps copyofLGaps(LGaps objects) {
        LGaps gaps = new LGaps();
        for (int i=0; i<objects.gaps.size(); i++) {
            gaps.gaps.add( new Gap( objects.gaps.get(i) ) );
        }
        return gaps;
    }
}