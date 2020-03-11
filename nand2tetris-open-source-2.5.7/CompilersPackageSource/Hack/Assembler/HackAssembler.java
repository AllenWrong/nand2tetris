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

package Hack.Assembler;

import java.io.*;
import java.util.Hashtable;
import Hack.ComputerParts.*;
import Hack.Utilities.*;
import Hack.Translators.*;

/**
 * A translator from assmebly (.asm) to hack machine language (.hack)
 */
public class HackAssembler extends HackTranslator {

    // the reader of the comparison file
    private BufferedReader comparisonReader;

    // the name of the comparison .hack file
    private String comparisonFileName;

    // the symbol table
    private Hashtable symbolTable;

    // The comarison program array
    private short[] comparisonProgram;

    // The HackAssembler translator;
    private HackAssemblerTranslator translator;

    // Index of the next location for unrecognized labels
    private short varIndex;

    /**
     * Constructs a new HackAssembler with the size of the program memory
     * and .asm source file name. The given null value will be used to fill
     * the program initially. The compiled program can later be fetched
     * using the getProgram() method.
     * If save is true, the compiled program will be saved automatically into a ".hack"
     * file that will have the same name as the source but with the .hack extension.
     */
    public HackAssembler(String fileName, int size, short nullValue, boolean save)
     throws HackTranslatorException {
        super(fileName, size, nullValue, save);
    }

    /**
     * Constructs a new HackAssembler with the size of the program memory.
     * The given null value will be used to fill the program initially.
     * A non null sourceFileName specifies a source file to be loaded.
     * The gui is assumed to be not null.
     */
    public HackAssembler(HackAssemblerGUI gui, int size, short nullValue, String sourceFileName)
     throws HackTranslatorException {
        super(gui, size, nullValue, sourceFileName);

        gui.enableLoadComparison();
        gui.hideComparison();
    }

    protected String getSourceExtension() {
        return "asm";
    }

    protected String getDestinationExtension() {
        return "hack";
    }

    protected String getName() {
        return "Assembler";
    }

    protected void init(int size, short nullValue) {
        super.init(size, nullValue);
        translator = HackAssemblerTranslator.getInstance();
    }

    // Checks the given comparison file name and throws an AssemblerException
    // if not legal.
    private void checkComparisonFile(String fileName) throws HackTranslatorException {
        if (!fileName.endsWith("." + getDestinationExtension()))
            throw new HackTranslatorException(fileName + " is not a ." + getDestinationExtension()
                                              + " file");

        File file = new File(fileName);
        if (!file.exists())
            throw new HackTranslatorException("File " + fileName + " does not exist");
    }

    protected void restartCompilation() {
        super.restartCompilation();

        varIndex = Definitions.VAR_START_ADDRESS;

        if (gui != null)
            ((HackAssemblerGUI)gui).enableLoadComparison();
    }

    // opens the comparison file for reading.
    private void resetComparisonFile() throws HackTranslatorException {
        try {
            comparisonReader = new BufferedReader(new FileReader(comparisonFileName));

            if (gui != null) {
                TextFileGUI comp = ((HackAssemblerGUI)gui).getComparison();
                comp.reset();
                comp.setContents(comparisonFileName);

                comparisonProgram = new short[comp.getNumberOfLines()];
                for (int i = 0; i < comp.getNumberOfLines(); i++) {
					if (comp.getLineAt(i).length() != Definitions.BITS_PER_WORD) {
						throw new HackTranslatorException("Error in file "+comparisonFileName+": Line "+i+" does not contain exactly "+Definitions.BITS_PER_WORD+" characters");
					}
					try {
						comparisonProgram[i] = (short)Conversions.binaryToInt(comp.getLineAt(i));
					} catch (NumberFormatException nfe) {
						throw new HackTranslatorException("Error in file "+comparisonFileName+": Line "+i+" does not contain only 1/0 characters");
					}
				}
            }
        } catch (IOException ioe) {
            throw new HackTranslatorException("Error reading from file " + comparisonFileName);
        }
    }

    protected void initSource() throws HackTranslatorException {
        generateSymbolTable();
    }

    // Generates The symbol table by attaching each label with it's appropriate
    // value according to it's location in the program
    private void generateSymbolTable() throws HackTranslatorException {
        symbolTable = Definitions.getInstance().getAddressesTable();
        short pc = 0;
        String line;
        String label;

        try {
            BufferedReader sourceReader = new BufferedReader(new FileReader(sourceFileName));
            while((line = sourceReader.readLine()) != null) {

                AssemblyLineTokenizer input = new AssemblyLineTokenizer(line);

                if (!input.isEnd()) {
                    if (input.isToken("(")) {
                        input.advance(true);
                        label = input.token();
                        input.advance(true);
                        if (!input.isToken(")"))
                            error("')' expected");

                        input.ensureEnd();

                        symbolTable.put(label,new Short(pc));
                    }
                    else if (input.contains("["))
                        pc += 2;
                    else
                        pc++;
                }
            }

            sourceReader.close();
        } catch (IOException ioe) {
            throw new HackTranslatorException("Error reading from file " + sourceFileName);
        }
    }

    protected void initCompilation() throws HackTranslatorException {
        if (gui != null && (inFullCompilation || !compilationStarted))
            ((HackAssemblerGUI)gui).disableLoadComparison();
    }

    protected void successfulCompilation() throws HackTranslatorException {
        if (comparisonReader == null)
            super.successfulCompilation();
        else {
            if (gui != null)
                ((HackAssemblerGUI)gui).displayMessage("File compilation & comparison succeeded", false);
        }
    }

    protected int[] compileLineAndCount(String line) throws HackTranslatorException {
        int[] compiledRange = super.compileLineAndCount(line);

        // check comparison
        if (compiledRange != null && comparisonReader != null) {
            int length = compiledRange[1] - compiledRange[0] + 1;
            boolean compare = compare(compiledRange);

            if (inFullCompilation) {
                if (!compare) {
                    if (gui != null) {
                        programSize = destPC + length - 1;
                        showProgram(programSize);
                        gui.getSource().addHighlight(sourcePC, true);
                        gui.getDestination().addHighlight(destPC - 1, true);
                        ((HackAssemblerGUI)gui).getComparison().addHighlight(destPC - 1, true);
                        gui.enableRewind();
                        gui.enableLoadSource();
                    }
                }
            }
            else {
                if (compare)
                    ((HackAssemblerGUI)gui).getComparison().addHighlight(destPC + length - 2, true);
                else {
                    gui.getDestination().addHighlight(destPC - 1, true);
                    ((HackAssemblerGUI)gui).getComparison().addHighlight(destPC - 1, true);
                }
            }

            if (!compare)
                throw new HackTranslatorException("Comparison failure");
        }

        return compiledRange;
    }

    // Compares the given commands to the next commands in the comparison file.
    private boolean compare(int[] compiledRange) {
        boolean result = true;
        int length = compiledRange[1] - compiledRange[0] + 1;

        for (int i = 0; i < length && result; i++)
            result = (program[compiledRange[0] + i] == comparisonProgram[compiledRange[0] + i]);

        return result;
    }

    protected String getCodeString(short code, int pc, boolean display) {
        return Conversions.decimalToBinary(code, 16);
    }

    protected void fastForward() {
        ((HackAssemblerGUI)gui).disableLoadComparison();
        super.fastForward();
    }

    protected void hidePointers() {
        super.hidePointers();

        if (comparisonReader != null)
            ((HackAssemblerGUI)gui).getComparison().clearHighlights();
    }

    protected void end(boolean hidePointers) {
        super.end(hidePointers);
        ((HackAssemblerGUI)gui).disableLoadComparison();
    }

    protected void stop() {
        super.stop();
        ((HackAssemblerGUI)gui).disableLoadComparison();
    }

    protected void rewind() {
        super.rewind();

        if (comparisonReader != null) {
            ((HackAssemblerGUI)gui).getComparison().clearHighlights();
            ((HackAssemblerGUI)gui).getComparison().hideSelect();
        }
    }

    // If the line is a label, returns null.
    protected void compileLine(String line) throws HackTranslatorException {

        try {
            AssemblyLineTokenizer input = new AssemblyLineTokenizer(line);

            if (!input.isEnd() && !input.isToken("(")) {
                if (input.isToken("@")) {
                    input.advance(true);
                    boolean numeric = true;
                    String label = input.token();
                    input.ensureEnd();
                    try {
                        Short.parseShort(label);
                    } catch (NumberFormatException nfe) {
                        numeric = false;
                    }

                    if (!numeric) {
                        Short address = (Short)symbolTable.get(label);
                        if (address == null) {
                            address = new Short(varIndex++);
                            symbolTable.put(label, address);
                        }

                        addCommand(translator.textToCode("@" + address.shortValue()));
                    }
                    else
                        addCommand(translator.textToCode(line));
                }
                else { // try to compile normaly, if error - try to compile as compact assembly
                    try {
                        addCommand(translator.textToCode(line));
                    } catch (AssemblerException ae) {
                        int openAddressPos = line.indexOf("[");
                        if (openAddressPos >= 0) {
                            int lastPos = line.lastIndexOf("[");
                            int closeAddressPos = line.indexOf("]");

                            if (openAddressPos != lastPos || openAddressPos > closeAddressPos ||
                                openAddressPos + 1 == closeAddressPos)
                                throw new AssemblerException(
                                    "Illegal use of the [] notation");

                            String address = line.substring(openAddressPos + 1, closeAddressPos);
                            compileLine("@" + address);
                            compileLine(line.substring(0, openAddressPos).concat(
                                line.substring(closeAddressPos + 1)));
                        }
                        else
                            throw new AssemblerException(ae.getMessage());
                    }
                }
            }
        } catch (IOException ioe) {
            throw new HackTranslatorException("Error reading from file " + sourceFileName);
        } catch (AssemblerException ae) {
            throw new HackTranslatorException(ae.getMessage(), sourcePC);
        }
    }

    protected void finalizeCompilation() {
    }

    public void rowSelected(TextFileEvent event) {
        super.rowSelected(event);

        int[] range = rowIndexToRange(event.getRowIndex());
        if (range != null) {
            if (comparisonReader != null)
                ((HackAssemblerGUI)gui).getComparison().select(range[0], range[1]);
        }
        else {
            if (comparisonReader != null)
                ((HackAssemblerGUI)gui).getComparison().hideSelect();
        }
    }

    public void actionPerformed(HackTranslatorEvent event) {
        super.actionPerformed(event);

        switch (event.getAction()) {
            case HackTranslatorEvent.SOURCE_LOAD:
                comparisonFileName = "";
                comparisonReader = null;
                ((HackAssemblerGUI)gui).setComparisonName("");
                ((HackAssemblerGUI)gui).hideComparison();
                break;

            case HackAssemblerEvent.COMPARISON_LOAD:
                clearMessage();
                String fileName = (String)event.getData();
                try {
                    checkComparisonFile(fileName);
                    comparisonFileName = fileName;
                    saveWorkingDir(new File(fileName));
                    resetComparisonFile();
                    ((HackAssemblerGUI)gui).showComparison();
                } catch (HackTranslatorException ae) {
                    gui.displayMessage(ae.getMessage(), true);
                }
                break;
        }
    }
}
