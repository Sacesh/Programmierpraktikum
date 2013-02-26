package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformaikmuenchen.pg4.common.alignment.SequencePairAlignment;

/**
 *
 * @author koehleru
 */
public class HTMLAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {

    public String format(AlignmentResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div>");
        for (SequencePairAlignment align : result.getAlignments()) {
            builder.append("<h3>" + result.getScore() + "</h3>");
            //First line
            builder.append("<pre>");
            builder.append(align.queryAlignment);
            builder.append("</pre>");
            //Second line in red
            builder.append("<pre style=\"color: red;\">");
            builder.append(align.matchLine);
            builder.append("</pre>");
            //Third line
            builder.append("<pre>");
            builder.append(align.targetAlignment);
            builder.append("</pre>");

            builder.append("</div>");
        }
        return "Alignment score: " + Double.toString(result.getScore());
    }
}