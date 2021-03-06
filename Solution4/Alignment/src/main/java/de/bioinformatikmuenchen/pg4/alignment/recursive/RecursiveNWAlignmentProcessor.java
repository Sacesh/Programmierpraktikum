/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive;

import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentAlgorithm;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentMode;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentProcessor;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.DPMatrixExporter;
import de.bioinformatikmuenchen.pg4.alignment.io.IAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.alignment.io.IDPMatrixExporter;
import de.bioinformatikmuenchen.pg4.alignment.io.ScoreOnlyAlignmentOutputFormatter;

/**
 *
 * @author koehleru
 */
public class RecursiveNWAlignmentProcessor extends AlignmentProcessor {

    private String querySequence;
    private String targetSequence;
    private long calculateRecursiveCount = 0;

    /**
     * Checks if the mode and algorithm is correct
     */
    private void checkModeAndAlgorithm() {
        assert mode == AlignmentMode.GLOBAL : "Illegal alignment mode: " + mode;
        assert algorithm == AlignmentAlgorithm.NEEDLEMAN_WUNSCH : "Illegal alignment mode: " + mode;
    }

    public RecursiveNWAlignmentProcessor(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost, IAlignmentOutputFormatter outputFormatter) {
        super(mode, algorithm, distanceMatrix, gapCost, outputFormatter);
        checkModeAndAlgorithm();
    }

    /**
     * Initialize an alignment processor with a score-only output formatter
     *
     * @param mode
     * @param algorithm
     */
    public RecursiveNWAlignmentProcessor(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost) {
        super(mode, algorithm, distanceMatrix, gapCost);
        checkModeAndAlgorithm();
    }

    public long getCalculateRecursiveCount() {
        return calculateRecursiveCount;
    }

    private double calculateScoreRecursive(int x, int y) {
        calculateRecursiveCount++;
        if (x == 0) {
            //System.out.println(""+y+ "  " + y*gapCost.getGapCost(1));
            return y * gapCost.getGapCost(1);
        } else if (y == 0) {
            return x * gapCost.getGapCost(1);
        } else { //We're not on the border
            double topScore = calculateScoreRecursive(x, y - 1) + gapCost.getGapCost(1);
            double leftScore = calculateScoreRecursive(x - 1, y) + gapCost.getGapCost(1);
            double leftTopScore = calculateScoreRecursive(x - 1, y - 1) + distanceMatrix.distance(querySequence.charAt(x - 1), targetSequence.charAt(y - 1));
            return Math.max(topScore, Math.max(leftScore, leftTopScore));
        }
    }

    @Override
    public AlignmentResult align(Sequence seq1Obj, Sequence seq2Obj) {
        querySequence = seq1Obj.getSequence();
        targetSequence = seq2Obj.getSequence();
        //This algorithm can only use constant gap costs
        assert gapCost instanceof ConstantGapCost : "Recursive implementation can use constant gap cost only";
        double score = calculateScoreRecursive(querySequence.length(), targetSequence.length()); //0-based indices
        //Debug: for every single field
//        System.out.print("\t\t");
//        for (int x = 0; x < seq1.length(); x++) {
//            System.out.print(seq1.charAt(x) + "\t");
//        }
//        System.out.println("");
//        for (int y = 0; y <= seq2.length(); y++) {
//            System.out.print((y == 0 ? ' ' :seq2.charAt(y-1))+ "\t");
//            for (int x = 0; x <= seq1.length(); x++) {
//                System.out.print(calculateScoreRecursive(x, y) + "\t");
//            }
//            System.out.println("");
//        }
        //Create and return the result
        AlignmentResult res = new AlignmentResult(score);
        res.setQuerySequenceId(seq1Obj.getId());
        res.setTargetSequenceId(seq2Obj.getId());
        return res;
    }

    private double[][] getMatrix() {
        //This takes a LONG time
        double[][] matrix = new double[querySequence.length()][targetSequence.length()];
        for (int x = 0; x < querySequence.length(); x++) {
            for (int y = 0; y < targetSequence.length(); y++) {
                matrix[x][y] = calculateScoreRecursive(x, y);
            }
        }
        return matrix;
    }

    @Override
    public void writeMatrices(IDPMatrixExporter exporter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
