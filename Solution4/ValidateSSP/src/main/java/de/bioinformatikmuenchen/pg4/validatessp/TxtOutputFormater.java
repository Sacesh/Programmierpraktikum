/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validatessp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author schoeffel
 */
public class TxtOutputFormater {

    public String format(Detailed input) {
        if (input.results.isEmpty()) {
            return "No Validation results";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.results.size(); i++) {
            builder.append(input.results.get(i).att1).append("\n");
            builder.append("AS ").append(input.results.get(i).att2).append("\n");
            builder.append("PS ").append(input.results.get(i).att3).append("\n");
            builder.append("SS ").append(input.results.get(i).att4).append("\n");
            builder.append("\n");
        }
        return builder.toString();
    }

    public String format(Summary input) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Summary:\n");
        builder.append("Validation Value\tSD\tMean\tMin\tMax\tQuan50\tQuan5\tQuan95\n\n");
        builder.append("> Q3\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> SOV\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> QH\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> QE\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> QC\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> SOVH\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> SOVE\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        builder.append("> SOVC\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("\n\n");
        
        
        
        return builder.toString();
    }

    public void print(Summary input, File summary) {
        try {
            FileWriter out = new FileWriter(summary);
            BufferedWriter output = new BufferedWriter(out);
            output.write(format(input));
            output.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void print(Detailed input, File detailfile) {
        try {
            FileWriter out = new FileWriter(detailfile);
            BufferedWriter output = new BufferedWriter(out);
            output.write(format(input));
            output.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}