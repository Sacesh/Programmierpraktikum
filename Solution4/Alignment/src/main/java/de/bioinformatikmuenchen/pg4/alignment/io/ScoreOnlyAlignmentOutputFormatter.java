package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author koehleru
 */
public class ScoreOnlyAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {
    private DecimalFormat numberFormat = new DecimalFormat();

    public ScoreOnlyAlignmentOutputFormatter() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        numberFormat.setMinimumFractionDigits(4);
        numberFormat.setMinimumFractionDigits(4);
        numberFormat.setDecimalFormatSymbols(dfs);
    }

    public String format(AlignmentResult result) {
         return result.getQuerySequenceId() + " " + result.getTargetSequenceId() + " " + numberFormat.format(result.getScore());
    }
}