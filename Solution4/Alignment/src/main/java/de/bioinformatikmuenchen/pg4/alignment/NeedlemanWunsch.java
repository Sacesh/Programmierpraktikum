package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.DPMatrixExporter;
import de.bioinformatikmuenchen.pg4.alignment.io.IAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import java.io.IOException;
import java.util.Collections;

public class NeedlemanWunsch extends AlignmentProcessor {

    private double[][] matrix;
    //Matrices that save whether any given field got its value from the specified direction
    private boolean[][] leftTopArrows;
    private boolean[][] leftArrows;
    private boolean[][] topArrows;
    private int xSize = -1;
    private int ySize = -1;
    private String querySequence;
    private String targetSequence;
    private String querySequenceId;
    private String targetSequenceId;
    private boolean freeShift = false;
    private double score;

    @Override
    public AlignmentResult align(Sequence seq1, Sequence seq2) {
        assert seq1 != null && seq2 != null;
        assert seq1.getSequence().length() > 0;
        assert seq2.getSequence().length() > 0;
        this.querySequence = seq1.getSequence();
        this.targetSequence = seq2.getSequence();
        this.querySequenceId = seq1.getId();
        this.targetSequenceId = seq2.getId();
        initMatrix(seq1.getSequence().length(), seq2.getSequence().length());
        fillMatrix(seq1.getSequence(), seq2.getSequence());
        AlignmentResult result = new AlignmentResult();
        //Calculate the alignment and add it to the result
        SequencePairAlignment alignment = backTracking();
//        System.out.println("##spa query: "+spa.queryAlignment);
        result.setAlignments(Collections.singletonList(alignment));
        this.score = matrix[xSize - 1][ySize - 1];
        result.setScore(this.score);
        result.setQuerySequenceId(seq1.getId());
        result.setTargetSequenceId(seq2.getId());
        return result;
    }

    /**
     * Initialize an alignment processor with a score-only output formatter Pr
     *
     * @param mode
     * @param algorithm
     */
    public NeedlemanWunsch(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost) {
        super(mode, algorithm, distanceMatrix, gapCost);
        assert gapCost instanceof ConstantGapCost;
        //AlignmentResult result = new AlignmentResult();
    }

    public NeedlemanWunsch(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost, IAlignmentOutputFormatter outputFormatter) {
        super(mode, algorithm, distanceMatrix, gapCost, outputFormatter);
        assert gapCost instanceof ConstantGapCost : "Classic Needleman Wunsch can't use affine gap cost";
        assert algorithm == AlignmentAlgorithm.NEEDLEMAN_WUNSCH;
    }

    public void initMatrix(int xSize, int ySize) {
        xSize++;
        ySize++;
        this.xSize = xSize;
        this.ySize = ySize;
        matrix = new double[xSize][ySize];
        leftArrows = new boolean[xSize][ySize];
        leftTopArrows = new boolean[xSize][ySize];
        topArrows = new boolean[xSize][ySize];
        if (!freeShift) {
            for (int i = 0; i < xSize; i++) {
                matrix[i][0] = gapCost.getGapCost(i);
            }
            for (int i = 0; i < ySize; i++) {
                matrix[0][i] = gapCost.getGapCost(i);
            }
        }
        //EXPLICITLY init the matrices
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                leftArrows[x][y] = false;
                leftTopArrows[x][y] = false;
                topArrows[x][y] = false;
            }
        }
    }

    public void fillMatrix(String seq1, String seq2) {
        final double compareThreshold = 0.0000001;
        for (int x = 1; x < xSize; x++) {
            for (int y = 1; y < ySize; y++) {
                char A = seq1.charAt(x - 1);
                char B = seq2.charAt(y - 1);
                double leftTopScore = matrix[x - 1][y - 1] + distanceMatrix.distance(A, B);
                double leftScore = matrix[x][y - 1] + gapCost.getGapCost(1);
                double topScore = matrix[x - 1][y] + gapCost.getGapCost(1);
                //Calculate the max score
                matrix[x][y] = Math.max(leftTopScore,
                        Math.max(leftScore, topScore));
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

    public String printMatrix() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t");
        for (int x = 0; x < querySequence.length(); x++) {
            builder.append(querySequence.charAt(x)).append("\t");
        }
        builder.append("\n");
        for (int y = 0; y <= targetSequence.length(); y++) {
            builder.append(y == 0 ? ' ' : targetSequence.charAt(y - 1)).append("\t");
            for (int x = 0; x <= querySequence.length(); x++) {
                builder.append(matrix[x][y]).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public SequencePairAlignment backTracking() {
        String queryAlignment = "";
        String targetAlignment = "";
        int x = xSize - 1;
        int y = ySize - 1;
//        System.out.println("start: "+x+", "+y);
        while (x >= 0 && y >= 0) {
            if (leftTopArrows[x][y]) {
//                System.out.println("leftTop "+(x-1)+", "+(y-1));
                queryAlignment += querySequence.charAt(x - 1);
                targetAlignment += targetSequence.charAt(y - 1);
                x--;
                y--;
            } else if (leftArrows[x][y]) {
//                System.out.println("left "+(x-1)+", "+y);
                queryAlignment += querySequence.charAt(x - 1);
                targetAlignment += '-';
                x--;
            } else if (topArrows[x][y]) {
//                System.out.println("top "+x+", "+(y-1));
                queryAlignment += '-';
                targetAlignment += targetSequence.charAt(y - 1);
                y--;
            } else if (x == 0) {
                while (y > 0) {
//                    System.out.println("top0 "+x+", "+(y-1));
                    queryAlignment += '-';
                    targetAlignment += targetSequence.charAt(y - 1);
                    y--;
                }
                break;
            } else if (y == 0) {
                while (x > 0) {
//                    System.out.println("left0 "+(x-1)+", "+y);
                    queryAlignment += querySequence.charAt(x - 1);
                    targetAlignment += '-';
                    x--;
                }
                break;
            }
        }
        //reverse the output:
        SequencePairAlignment spa = new SequencePairAlignment();
        spa.setQueryAlignment(new StringBuffer(queryAlignment).reverse().toString());
        spa.setTargetAlignment(new StringBuffer(targetAlignment).reverse().toString());
        return spa;//new SequencePairAlignment(new StringBuffer().reverse().toString(), new StringBuffer(spa.targetAlignment).reverse().toString());
    }

    public boolean setFreeShift(boolean freeShift) {
        this.freeShift = freeShift;
        return this.freeShift;
    }

    @Override
    public void writeMatrices(DPMatrixExporter exporter) {
        DPMatrixExporter.DPMatrixInfo info = new DPMatrixExporter.DPMatrixInfo();
        //Set sequences
        info.query = querySequence;
        info.target = targetSequence;
        //Set IDs
        info.queryId = querySequenceId;
        info.targetId = targetSequenceId;
        info.matrix = matrix;
        info.matrixPostfix = "matrix";
        info.leftArrows = leftArrows;
        info.topArrows = topArrows;
        info.topLeftArrows = leftTopArrows;
        info.score = score;
        try {
            exporter.write(info);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
