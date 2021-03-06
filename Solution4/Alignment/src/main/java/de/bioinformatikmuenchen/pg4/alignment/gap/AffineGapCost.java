/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.gap;

/**
 *
 * @author koehleru
 */
public class AffineGapCost implements IGapCost {

    private double gapopen;
    private double gapextend;

    public AffineGapCost(double gapopen, double gapextend) {
        this.gapopen = gapopen;
        this.gapextend = gapextend;
    }

    @Override
    public double getGapCost(int length) {
        if(length <= 0) {
            return 0;
        }
        return gapopen + length * gapextend;
    }

    @Override
    public double getGapExtensionPenalty(int previousLength, int extendBy) {//why param previousLength??
        return extendBy * gapextend;
    }
}
