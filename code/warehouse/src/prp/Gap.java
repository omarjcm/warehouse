/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prp;

/**
 *
 * @author omarjcm
 */
public class Gap implements Comparable {

    public int indexInitial;
    public int indexFinal;
    public int lengthGap;
    
    public Gap(Gap gap) {
        this.indexInitial = gap.indexInitial;
        this.indexFinal = gap.indexFinal;
        this.lengthGap = gap.lengthGap;
    }
    
    public Gap() {
    }
    
    @Override
    public int compareTo(Object t) {
        Gap o = ( (Gap) t );
        return (o.lengthGap - this.lengthGap);
    }
}