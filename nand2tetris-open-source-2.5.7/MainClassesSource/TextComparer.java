/********************************************************************************
 * The contents of this file are subject to the GNU General Public License      *
 * (GPL) Version 2 or later (the "License"); you may not use this file except   *
 * in compliance with the License. You may obtain a copy of the License at      *
 * http://www.gnu.org/copyleft/gpl.html                                         *
 *                                                                              *
 * Software distributed under the License is distributed on an "AS IS" basis,   *
 * without warranty of any kind, either expressed or implied. See the License   *
 * for the specific language governing rights and limitations under the         *
 * License.                                                                     *
 *                                                                              *
 * This file was originally developed as part of the software suite that        *
 * supports the book "The Elements of Computing Systems" by Nisan and Schocken, *
 * MIT Press 2005. If you modify the contents of this file, please document and *
 * mark your changes clearly, for the benefit of others.                        *
 ********************************************************************************/

import java.io.*;

/**
 * A text file comparer. Receives two text file names as command line arguments
 * and compares them line by line (ignoring spaces). An error is displayed
 * in case of a comparison failure. A "success" message is displayed in
 *  case of successful comparison.
 */
public class TextComparer {

  /**
   * The command line Text Comparer program.
   */
  public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java TextComparer <file name> <file name>");
            System.exit(-1);
        }

        BufferedReader reader1 = null;
        BufferedReader reader2 = null;

        try {
            reader1 = new BufferedReader(new FileReader(args[0]));
        } catch (IOException ioe) {
            System.err.println("Cannot open " + args[0]);
            System.exit(-1);
        }

        try {
            reader2 = new BufferedReader(new FileReader(args[1]));
        } catch (IOException ioe) {
            System.err.println("Cannot open " + args[1]);
            System.exit(-1);
        }

        String line1, line2;
        int count = 0;

        try {
            while ((line1 = reader1.readLine()) != null) {

                line1 = removeSpaces(line1);

                line2 = reader2.readLine();

                if (line2 == null) {
                    System.out.println("Second file is shorter (only " + count + " lines)");
                    System.exit(-1);
                }
                else {
                    line2 = removeSpaces(line2);
                    if (!line1.equals(line2)) {
                        System.out.println("Comparison failure in line " + count + ":");
                        System.out.println(line1);
                        System.out.println(line2);
                        System.exit(-1);
                    }
                }

                count++;
            }

            if (reader2.readLine() != null) {
                System.out.println("First file is shorter (only " + count + " lines)");
                System.exit(-1);
            }
        } catch (IOException ioe) {
            System.err.println("IO error while reading files");
            System.exit(-1);
        }

        try {
            reader1.close();
            reader2.close();
        } catch (IOException ioe) {
            System.err.println("Could not close files");
            System.exit(-1);
        }

        System.out.println("Comparison ended successfully");
    }

    // Removes the spaces from the given string.
    private static String removeSpaces(String sourceLine) {
        StringBuffer line;
        int k;
        int i=0;
        int j=0 ;
        line = new StringBuffer(sourceLine);
        while (j<line.length()) {
            if (line.charAt(j) == ' ')
                j++;
            else {
                if (i != j)
                    line.setCharAt(i,line.charAt(j));
                i++;
                j++;
            }
        }
        line.setLength(i);

        return line.toString().trim();
    }
}
