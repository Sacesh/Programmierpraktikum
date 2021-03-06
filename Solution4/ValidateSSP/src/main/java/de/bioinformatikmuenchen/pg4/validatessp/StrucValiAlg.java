/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validatessp;

import java.util.ArrayList;

/**
 *
 * @author schoeffel
 */
public class StrucValiAlg {

    //predicted secondary structure
    private String pred;
    //reference secondary structure
    private String observ;
    //safing intermediate results for Qs
    private double AH;
    private double BH;
    private double AE;
    private double BE;
    private double AC;
    private double BC;
    //safing intermediate results for SOVs
    private double sumSH;
    private double sumSE;
    private double sumSC;
    private double numH;
    private double numE;
    private double numC;
    //lists for overlapping segments
    private ArrayList<VTupel> OvH = new ArrayList();
    private ArrayList<VTupel> OvE = new ArrayList();
    private ArrayList<VTupel> OvC = new ArrayList();
    //lists for not overlapping segments
    private ArrayList<ZTupel> NvH = new ArrayList();
    private ArrayList<ZTupel> NvE = new ArrayList();
    private ArrayList<ZTupel> NvC = new ArrayList();

    public StrucValiAlg(String pre, String sec) {
        assert (sec.length() == pre.length());
        pred = pre;
        observ = sec;
        //cutting useless ends generated by GOR
        this.cutends();
        assert sec.length() > 0;
        //counting secondary structure elements
        this.detStruc('H');
        this.detStruc('E');
        this.detStruc('C');
        //determine overlapping segments
        this.detOver('H');
        this.detOver('E');
        this.detOver('C');
        //determine SovNs
        this.detSovNumber('H');
        this.detSovNumber('E');
        this.detSovNumber('C');
        //detminte SovSums
        this.detSovSum('H');
        this.detSovSum('E');
        this.detSovSum('C');

    }

    public double getQ3() {
        return (double) (AH + AE + AC) / (BH + BE + BC) * 100;
    }

    public double getQH() {
        return (double) (AH / BH) * 100;
    }

    public double getQE() {
        return (double) (AE / BE) * 100;
    }

    public double getQC() {
        return (double) (AC / BC) * 100;
    }

    public double getSOV() {
        return (double) (sumSH + sumSE + sumSC) / (numH + numE + numC) * 100;
    }

    public double getSOVH() {
        return (double) (sumSH / numH) * 100;
    }

    public double getSOVE() {
        return (double) (sumSE / numE) * 100;
    }

    public double getSOVC() {
        return (double) (sumSC / numC) * 100;
    }

    private void detStruc(char input) {
        //correctly predicted input secondary struture
        int A = 0;
        //input secondary struture in observed
        int B = 0;
        //go through secondary structure sequence
        for (int i = 0; i < pred.length(); i++) {
            if (observ.charAt(i) == input) {
                B++;
                if (pred.charAt(i) == input) {
                    A++;
                }
            }
        }
        //write into global variables
        if (input == 'H') {
            AH = A;
            BH = B;
        }
        if (input == 'E') {
            AE = A;
            BE = B;
        }
        if (input == 'C') {
            AC = A;
            BC = B;
        }
    }

    private void detOver(char input) {
        //input: char for secondary strucure whichs segment overlapps are to be determined
        //reference to correct segment list
        ArrayList<VTupel> overlapps = OvC;
        ArrayList<ZTupel> noover = NvC;
        if (input == 'H') {
            overlapps = OvH;
            noover = NvH;
        }
        if (input == 'E') {
            overlapps = OvE;
            noover = NvE;
        }
        //list to keep track of segments da need to be added
        //phantom1 tuples are of the form (stao,endo,stap,-1) as they are missing the end position for the overlapp in the prediction
        ArrayList<VTupel> phantom1 = new ArrayList();
        //phantom2 tuples are of the form (stao,-1,stap,endp) as they are missing the end position for the overlapp in the observation
        ArrayList<VTupel> phantom2 = new ArrayList();
        //index for current segments
        int stao = 0;
        int endo;
        int stap = 0;
        int endp;
        //booleans to keep track of segments currenty opened
        boolean seqo = false;
        boolean seqp = false;
        //going through secondary structure observerd / prediction
        for (int i = 0; i < observ.length(); i++) {
            //open segment in observed
            if (observ.charAt(i) == input && seqo == false) {
                stao = i;
                seqo = true;
            }
            //close segment in observ and handle safing indexes
            if (observ.charAt(i) != input && seqo == true) {
                endo = i - 1;
                seqo = false;
                //overlapping segment in predicted goes on further or ends at same position
                if (seqp == true) {
                    //add to list of segments that need to be included later on 
                    phantom1.add(new VTupel(stao, endo, stap, -1));
                } else {
                    //check for overlap
                    if (phantom2.isEmpty()) {
                        //add to no overlapp list
                        noover.add(new ZTupel(stao, endo));
                    }
                }
                //copying overlapps from phantom2 into final overlapp reference
                for (int j = 0; j < phantom2.size(); j++) {
                    overlapps.add(new VTupel(phantom2.get(j).att1, endo, phantom2.get(j).att3, phantom2.get(j).att4));
                }
                //empty phantom2 for next segment
                phantom2.clear();
            }
            //open segment in predicted
            if (pred.charAt(i) == input && seqp == false) {
                stap = i;
                seqp = true;
            }
            //close segment in predicted and handle safing indexes if neccessary
            if (pred.charAt(i) != input && seqp == true) {
                endp = i - 1;
                seqp = false;
                //overlapping segment in observed goes on further 
                if (seqo == true && observ.charAt(i - 1) == input) {
                    //add to list of segments that need to be included later
                    phantom2.add(new VTupel(stao, -1, stap, endp));
                }
                //copying overlapps from phantom1 into final overlapp reference
                for (int j = 0; j < phantom1.size(); j++) {
                    overlapps.add(new VTupel(phantom1.get(j).att1, phantom1.get(j).att2, phantom1.get(j).att3, endp));
                }
                //empty phantom1 for next segment
                phantom1.clear();
            }
            //handling last position
            if (i == observ.length() - 1) {
                //case: segment in observed ends at last position
                if (seqo == true) {
                    endo = observ.length() - 1;
                    //overlapping segment also ends at last position
                    if (pred.charAt(endo) == input) {
                        phantom1.add(new VTupel(stao, endo, stap, -1));
                    }
                    //case: no overlapp
                    if (phantom2.isEmpty() && pred.charAt(i) != input) {
                        noover.add(new ZTupel(stao, endo));
                    }
                    for (int j = 0; j < phantom2.size(); j++) {
                        overlapps.add(new VTupel(phantom2.get(j).att1, endo, phantom2.get(j).att3, phantom2.get(j).att4));
                    }
                }
                //case:segment in predicted ends at last position
                if (seqp == true) {
                    endp = observ.length() - 1;
                    for (int j = 0; j < phantom1.size(); j++) {
                        overlapps.add(new VTupel(phantom1.get(j).att1, phantom1.get(j).att2, phantom1.get(j).att3, endp));
                    }
                }
            }
        }
    }

    private void detSovNumber(char input) {
        //getting correct reference
        ArrayList<VTupel> overlapps = OvC;
        ArrayList<ZTupel> noover = NvC;
        if (input == 'H') {
            overlapps = OvH;
            noover = NvH;
        }
        if (input == 'E') {
            overlapps = OvE;
            noover = NvE;
        }
        //countting lengths 
        int number = 0;
        //counting overlapps segment lengths 
        for (int i = 0; i < overlapps.size(); i++) {
            number = number + overlapps.get(i).att2 - overlapps.get(i).att1 + 1;
        }
        //
        for (int i = 0; i < noover.size(); i++) {
            number = number + noover.get(i).att2 - noover.get(i).att1 + 1;
        }
        if (input == 'H') {
            numH = number;
        }
        if (input == 'E') {
            numE = number;
        }
        if (input == 'C') {
            numC = number;
        }
    }

    private void detSovSum(char input) {
        //getting correct reference
        ArrayList<VTupel> overlapps = OvC;
        if (input == 'H') {
            overlapps = OvH;
        }
        if (input == 'E') {
            overlapps = OvE;
        }

        double SSum = 0;
        //going through all overlapps
        for (int i = 0; i < overlapps.size(); i++) {
            //getting start and end position for segments overlapping
            int s1start = overlapps.get(i).att1;
            int s1end = overlapps.get(i).att2;
            int s2start = overlapps.get(i).att3;
            int s2end = overlapps.get(i).att4;
            //calculating maximal overlapps and minimal overlapp
            double maxov = Math.max(s1end, s2end) - Math.min(s1start, s2start) + 1;
            double minov = Math.min(s1end, s2end) - Math.max(s1start, s2start) + 1;
            //updating SSum
            //System.out.println("Tupel " +input + " " + s1start + " " + s1end + " " + s2start + " " + s2end );
            SSum += ((double) ((minov + delta(s1start, s1end, s2start, s2end)) / maxov)) * (s1end - s1start + 1);
        }
        //safing result in correct global argument
        if (input == 'H') {
            sumSH = SSum;
        }
        if (input == 'E') {
            sumSE = SSum;
        }
        if (input == 'C') {
            sumSC = SSum;
        }
    }

    private double delta(int s1start, int s1end, int s2start, int s2end) {
        //calculating maximal overlapps and minimal overlapp
        int maxov = Math.max(s1end, s2end) - Math.min(s1start, s2start) + 1;
        int minov = Math.min(s1end, s2end) - Math.max(s1start, s2start) + 1;
        //calculating half of the sequence lengths and rounding it down (java automatically rounds down when deviding integers)
        int floor1 = (s1end - s1start + 1) / 2;
        int floor2 = (s2end - s2start + 1) / 2;
        //getting intermediate results as Math.min only compares 2 arguments
        int intermediate1 = Math.min(maxov - minov, minov);
        int intermediate2 = Math.min(floor1, floor2);
        //return final result from intermediate results
        return Math.min(intermediate1, intermediate2);
    }

    private void cutends() {
        //initialize cutting borders
        int start = 0;
        int end = pred.length();
        //find first useable position
        while (pred.charAt(start) == '-') {
            start++;
        }
        //find last useable position
        while (pred.charAt(end - 1) == '-') {
            end--;
        }
        //extract useful parts
        pred = pred.substring(start, end);
        observ = observ.substring(start, end);
    }
}
