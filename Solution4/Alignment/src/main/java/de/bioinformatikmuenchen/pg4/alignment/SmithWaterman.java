package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.IAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import java.util.ArrayList;

public class SmithWaterman extends AlignmentProcessor {

    private double[][] matrix;
    //Matrices that save whether any given field got its value from the specified direction
    private boolean[][] leftTopArrows;
    private boolean[][] leftArrows;
    private boolean[][] topArrows;
    private int xSize = -1;
    private int ySize = -1;
    private String seq1;
    private String seq2;

    public SmithWaterman(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost) {
        super(mode, algorithm, distanceMatrix, gapCost);
        //AlignmentResult result = new AlignmentResult();
    }

    public SmithWaterman(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost, IAlignmentOutputFormatter outputFormatter) {
        super(mode, algorithm, distanceMatrix, gapCost, outputFormatter);
        assert gapCost instanceof ConstantGapCost : "Classic Needleman Wunsch can't use affine gap cost";
        assert algorithm == AlignmentAlgorithm.NEEDLEMAN_WUNSCH;
    }

    @Override
    public AlignmentResult align(Sequence seq1, Sequence seq2) {
        this.seq1 = seq1.getSequence();
        this.seq2 = seq2.getSequence();
        initAndFillMatrix(seq1.getSequence(), seq2.getSequence());
        AlignmentResult result = new AlignmentResult();
        //Calculate the alignment and add it to the result
        ArrayList<SequencePairAlignment> list = new ArrayList<SequencePairAlignment>();
        list.add(backtracking());
        result.setAlignments(list);//result.setAlignments(Collections.singletonList(backtracking()));
        result.setScore(matrix[xSize - 1][ySize - 1]);
        System.out.println("######Score: " + matrix[xSize - 1][ySize - 1] + " ######\n");
        return result;
    }

    public void initAndFillMatrix(String s, String t) {
        //////  init matrix:
        xSize = s.length();
        ySize = t.length();
        matrix = new double[xSize][ySize];
        leftArrows = new boolean[xSize][ySize];
        leftTopArrows = new boolean[xSize][ySize];
        topArrows = new boolean[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                leftArrows[x][y] = false;
                leftTopArrows[x][y] = false;
                topArrows[x][y] = false;
            }
        }
        /////////   fill matrix:
        final double compareThreshold = 0.0000001;
        for (int x = 1; x < xSize; x++) {
            for (int y = 1; y < ySize; y++) {
                char A = seq1.charAt(x - 1);
                char B = seq2.charAt(y - 1);
                double leftTopScore = matrix[x - 1][y - 1] + distanceMatrix.distance(A, B);
                double leftScore = matrix[x - 1][y] + gapCost.getGapCost(1);
                double topScore = matrix[x][y - 1] + gapCost.getGapCost(1);
                //Calculate the max score
                matrix[x][y] = Math.max(0.0, Math.max(leftTopScore, Math.max(leftScore, topScore)));
                double maxScore = matrix[x][y];
                //Check which 'arrows' are set for the current field
                leftTopArrows[x][y] = Math.abs(leftTopScore - maxScore) < compareThreshold;
                leftArrows[x][y] = Math.abs(leftScore - maxScore) < compareThreshold;
                topArrows[x][y] = Math.abs(topScore - maxScore) < compareThreshold;
                //Assert this field has at least one arrow
                assert leftTopArrows[x][y] || leftArrows[x][y] || topArrows[x][y];
            }
        }
    }

    public SequencePairAlignment backtracking() {
        ////    find the cell which contains the maximal entry
        double maxEntry = -1;
        int x = 0;
        int y = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] >= maxEntry) {
                    x = i;
                    y = j;
                    maxEntry = matrix[i][j];
                }
            }
        }
        SequencePairAlignment spa = new SequencePairAlignment();
        while (x >= 0 && y >= 0 && matrix[x][y] != 0) {
            if (leftTopArrows[x][y]) {
                spa.queryAlignment += seq1.charAt(x - 1);
                spa.targetAlignment += seq2.charAt(y - 1);
                x--;
                y--;
            }
            if (leftArrows[x][y]) {
                spa.queryAlignment += seq1.charAt(x - 1);
                spa.targetAlignment += '-';
                y--;
            }
            if (topArrows[x][y]) {
                spa.queryAlignment += '-';
                spa.targetAlignment += seq2.charAt(y - 1);
                x--;
            }
        }
        return new SequencePairAlignment(new StringBuffer(spa.queryAlignment).reverse().toString(), new StringBuffer(spa.targetAlignment).reverse().toString());

    }
}
