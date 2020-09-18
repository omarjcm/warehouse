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
public class ScannerCSV {
    
    public static String getString(String line, int position, String separator) {
        int frecuency = 1;
        int init = 0;
        int indexSeparator = 0;
        String subLineBefore = "";
        String subLineAfter = line;
        
        while (frecuency < position) {
            indexSeparator = line.indexOf(separator);
            subLineBefore = subLineAfter.substring( init, line.indexOf(separator) );
            init = indexSeparator + 1;
            subLineAfter = subLineAfter.substring( init, line.length() );
            
            System.out.println(subLineBefore);
            System.out.println(subLineAfter);
            init = 0;
            frecuency++;
        }
        return line;
    }
}
