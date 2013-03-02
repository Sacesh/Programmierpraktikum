package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

/**
 * 
 * @author spoeri
 */
public class Data {
    
    
    public static int trainingWindowSize = 17;
    
    /**
     * prevInWindow + middle(1) + rest = trainingWindowSize
     */
    public static int prevInWindow = 8;
    
    /**
     * one letter code of all AA
     */
    final public static char[] aaTable = {
        'A', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'K', 'L',
        'M', 'N', 'P', 'Q', 'R',
        'S', 'T', 'V', 'W', 'Y',
    };
    
    public static char[] secStruct = {
        'C', 'E', 'H'
    };
    
    public static final String[] colors = {
        "#CFF", "#CCF", "#CCC", "#9CC",
        "#9C9", "#FCC", "#C9C", "#09C",
        "#99C", "#669", "#636", "#603",
        "#069", "#CF3", "#6F0", "#FC0",
        "#F90", "#F60", "#C30", "#C39",
        "#06F", "#93C", "#33F", "#F06",
        "#C0F", "#909", "#390", "#060",
        "#060", "#006", "#000", "#F00",
        "#0F0", "#00F", "#333", "#666"
    };
}
