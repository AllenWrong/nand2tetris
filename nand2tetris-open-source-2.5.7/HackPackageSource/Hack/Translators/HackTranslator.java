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

package Hack.Translators;

import javax.swing.*;
import java.io.*;
import java.util.Hashtable;
import Hack.ComputerParts.*;
import java.awt.event.*;
import java.util.Vector;
import Hack.Translators.*;
import Hack.Utilities.*;

/**
 * This object provides translation services.
 */
public abstract class HackTranslator implements HackTranslatorEventListener, ActionListener,
                                                TextFileEventListener {

    // The delay in ms between each step in fast forward
    private static final int FAST_FORWARD_DELAY = 750;

    // the writer of the destination file
    private PrintWriter writer;

    // the name of the source file
    protected String sourceFileName;

    // the name of the destination file
    protected String destFileName;

    // The size of the program.
    protected int programSize;

    // The program array
    protected short[] program;

    // The source code array
    protected String[] source;

    // The gui of the HackTranslator
    protected HackTranslatorGUI gui;

    // times the fast forward process
    private Timer timer;

    // locked when single step in process
    private boolean singleStepLocked;

    // The Single Step task object
    private SingleStepTask singleStepTask;

    // The full compilation task object
    private FullCompilationTask fullCompilationTask;

    // The fast forward task object
    private FastForwardTask fastForwardTask;

    // The load source task object
    private LoadSourceTask loadSourceTask;

    // True when compilation started already with singlestep or fastforward
    protected boolean compilationStarted;

    // The index of the next location to compile into in the destination file.
    protected int destPC;

    // The index of the next location to compile in the source file.
    protected int sourcePC;

    // If true, change to the translator will be displayed in its GUI.
    private boolean updateGUI;

    // Maps between lines in the source files and their corresponding compiled lines
    // in the destination. The key is the pc of the source (Integer) and
    // the value is an int array of length 2, containing start and end pc of the destination
    // file.
    protected Hashtable compilationMap;

    // true only in the process of full compilation
    protected boolean inFullCompilation;

    // true only in the process of Fast Forward
    protected boolean inFastForward;

    /**
     * Constructs a new HackTranslator with the size of the program memory
     * and source file name. The given null value will be used to fill
     * the program initially. The compiled program can later be fetched
     * using the getProgram() method.
     * If save is true, the compiled program will be saved automatically into a destination
     * file that will have the same name as the source but with the destination extension.
     */
    public HackTranslator(String fileName, int size, short nullValue, boolean save)
     throws HackTranslatorException {
        if (fileName.indexOf(".") < 0)
            fileName = fileName + "." + getSourceExtension();

        checkSourceFile(fileName);

        source = new String[0];
        init(size, nullValue);

        loadSource(fileName);
        fullCompilation();

        if (save)
            save();
    }

    /**
     * Constructs a new HackTranslator with the size of the program memory.
     * The given null value will be used to fill the program initially.
     * A non null sourceFileName specifies a source file to be loaded.
     * The gui is assumed to be not null.
     */
    public HackTranslator(HackTranslatorGUI gui, int size, short nullValue, String sourceFileName)
     throws HackTranslatorException {
        this.gui = gui;
        gui.addHackTranslatorListener(this);
        gui.getSource().addTextFileListener(this);
        gui.setTitle(getName() + getVersionString());
        singleStepTask = new SingleStepTask();
        fullCompilationTask = new FullCompilationTask();
        fastForwardTask = new FastForwardTask();
        loadSourceTask = new LoadSourceTask();
        timer = new Timer(FAST_FORWARD_DELAY, this);
        init(size, nullValue);

        File workingDir = loadWorkingDir();
        gui.setWorkingDir(workingDir);

        if (sourceFileName == null) {
            gui.disableSingleStep();
            gui.disableFastForward();
            gui.disableStop();
            gui.disableRewind();
            gui.disableFullCompilation();
            gui.disableSave();
            gui.enableLoadSource();
            gui.disableSourceRowSelection();
        }
        else {
            loadSource(sourceFileName);
            gui.setSourceName(sourceFileName);
        }
    }

    /**
     * Returns the extension of the source file names.
     */
    protected abstract String getSourceExtension();

    /**
     * Returns the extension of the destination file names.
     */
    protected abstract String getDestinationExtension();

    /**
     * Returns the name of the translator.
     */
    protected abstract String getName();

    // Returns the version string
    private static String getVersionString() {
        return " (" + Definitions.version + ")";
    }

    /*
     * Compiles the given line, adds the compiled code to the program and returns the
     * start and end program index of the new compiled code in a 2-element array.
     * If the line is a directive (doesn't compile to any physical code), returns null.
     * If the line is not legal, throws a HackTranslatorException.
     */
    protected int[] compileLineAndCount(String line) throws HackTranslatorException {
        int[] result = null;

        int startPC = destPC;
        compileLine(line);
        int length = destPC - startPC;

        if (length > 0)
            result = new int[]{startPC, destPC - 1};

        return result;
    }

    /**
     * Compiles the given line and adds the compiled code to the program.
     * If the line is not legal, throws a HackTranslatorException.
     */
    protected abstract void compileLine(String line) throws HackTranslatorException;

    /**
     * initializes the HackTranslator.
     */
    protected void init(int size, short nullValue) {
        program = new short[size];
        for (int i = 0; i < size; i++)
            program[i] = nullValue;
        programSize = 0;
    }

    // Checks the given source file name and throws a HackTranslatorException
    // if not legal.
    private void checkSourceFile(String fileName) throws HackTranslatorException {
        if (!fileName.endsWith("." + getSourceExtension()))
            throw new HackTranslatorException(fileName + " is not a ." + getSourceExtension() +
                                              " file");

        File file = new File(fileName);
        if (!file.exists())
            throw new HackTranslatorException("file " + fileName + " does not exist");
    }

    // Checks the given destination file name and throws a HackTranslatorException
    // if not legal.
    private void checkDestinationFile(String fileName) throws HackTranslatorException {
        if (!fileName.endsWith("." + getDestinationExtension()))
            throw new HackTranslatorException(fileName + " is not a ." + getDestinationExtension()
                                              + " file");
    }

    /**
     * Restarts the compilation from the beginning of the source.
     */
    protected void restartCompilation() {
        compilationMap = new Hashtable();
        sourcePC = 0;
        destPC = 0;

        if (gui != null) {
            compilationStarted = false;
            gui.getDestination().reset();
            hidePointers();

            gui.enableSingleStep();
            gui.enableFastForward();
            gui.disableStop();
            gui.enableRewind();
            gui.enableFullCompilation();
            gui.disableSave();
            gui.enableLoadSource();
            gui.disableSourceRowSelection();
        }
    }

    // Loads the given source file and displays it in the Source GUI
    private void loadSource(String fileName) throws HackTranslatorException {
        String line;
        Vector formattedLines = new Vector();
        Vector lines = null;
        String errorMessage = null;

        try {
            if (gui != null) {
                gui.disableSingleStep();
                gui.disableFastForward();
                gui.disableStop();
                gui.disableRewind();
                gui.disableFullCompilation();
                gui.disableSave();
                gui.disableLoadSource();
                gui.disableSourceRowSelection();

                gui.displayMessage("Please wait...", false);
            }

            checkSourceFile(fileName);
            sourceFileName = fileName;

            lines = new Vector();
            BufferedReader sourceReader = new BufferedReader(new FileReader(sourceFileName));

            while((line = sourceReader.readLine()) != null) {
                formattedLines.addElement(line);

                if (gui != null)
                    lines.addElement(line);
            }

            sourceReader.close();

            source = new String[formattedLines.size()];
            formattedLines.toArray(source);

            if (gui != null) {
                String[] linesArray = new String[lines.size()];
                lines.toArray(linesArray);
                gui.getSource().setContents(linesArray);
            }

            destFileName = sourceFileName.substring(0,sourceFileName.indexOf('.')) +
                             "." + getDestinationExtension();

            initSource();
            restartCompilation();
            resetProgram();

            if (gui != null) {
                gui.setDestinationName(destFileName);
                gui.displayMessage("", false);
            }

        } catch (HackTranslatorException hte) {
            errorMessage = hte.getMessage();
        } catch (IOException ioe) {
            errorMessage = "error reading from file " + sourceFileName;
        }

        if (errorMessage != null) {
            if (gui != null)
                gui.enableLoadSource();

            throw new HackTranslatorException(errorMessage);
        }
    }

    /**
     * Initializes the source file.
     */
    protected abstract void initSource() throws HackTranslatorException;

    /**
     * Resets the program
     */
    protected void resetProgram() {
        programSize = 0;
        if (gui != null)
            gui.getDestination().reset();
    }

    /**
     * Initializes the compilation process. Executed when a compilation is started.
     */
    protected abstract void initCompilation() throws HackTranslatorException;

    /**
     * Finalizes the compilation process. Executed when a compilation is ended.
     */
    protected abstract void finalizeCompilation();

    /**
     * Executed when a compilation is successful.
     */
    protected void successfulCompilation() throws HackTranslatorException {
        if (gui != null)
            gui.displayMessage("File compilation succeeded", false);
    }

    // Translates the whole source. Assumes a legal sourceReader & writer.
    private void fullCompilation() throws HackTranslatorException {

        try {
            inFullCompilation = true;
            initCompilation();

            if (gui != null) {
                gui.disableSingleStep();
                gui.disableFastForward();
                gui.disableRewind();
                gui.disableFullCompilation();
                gui.disableLoadSource();

                gui.getSource().setContents(sourceFileName);
            }

            updateGUI = false;

            while(sourcePC < source.length) {
                int[] compiledRange = compileLineAndCount(source[sourcePC]);
                if (compiledRange != null) {
                    compilationMap.put(new Integer(sourcePC), compiledRange);
                }

                sourcePC++;
            }

            successfulCompilation();
            finalizeCompilation();

            programSize = destPC;

            if (gui != null) {
                showProgram(programSize);
                gui.getDestination().clearHighlights();
                gui.enableRewind();
                gui.enableLoadSource();
                gui.enableSave();
                gui.enableSourceRowSelection();
            }

            inFullCompilation = false;

        } catch (HackTranslatorException hte) {
            inFullCompilation = false;
            throw new HackTranslatorException(hte.getMessage());
        }
    }

    /**
     * Returns the string version of the given code in the given program location.
     * If display is true, the version is for display purposes. Otherwise, the
     * version should be the final one.
     */
    protected abstract String getCodeString(short code, int pc, boolean display);

    /**
     * Adds the given command to the next position in the program.
     * Throws HackTranslatorException if the program is too large
     */
    protected void addCommand(short command) throws HackTranslatorException {
        if (destPC >= program.length)
            throw new HackTranslatorException("Program too large");

        program[destPC++] = command;
        if (updateGUI)
            gui.getDestination().addLine(getCodeString(command, destPC - 1, true));
    }

    /**
     * Replaces the command in program location pc with the given command.
     */
    protected void replaceCommand(int pc, short command) {
        program[pc] = command;
        if (updateGUI)
            gui.getDestination().setLineAt(pc, getCodeString(command, pc, true));
    }

    /**
     * Displayes the first numOfCommands commands from the program in the dest window.
     */
    protected void showProgram(int numOfCommands) {
        gui.getDestination().reset();

        String[] lines = new String[numOfCommands];

        for (int i = 0; i < numOfCommands; i++)
            lines[i] = getCodeString(program[i], i, true);

        gui.getDestination().setContents(lines);
    }

    /**
     * starts the fast forward mode.
     */
    protected void fastForward() {
        gui.disableSingleStep();
        gui.disableFastForward();
        gui.enableStop();
        gui.disableRewind();
        gui.disableFullCompilation();
        gui.disableLoadSource();

        inFastForward = true;

        timer.start();
    }

    // Reads a single line from the source, compiles it and writes the result to the
    // detination.
    private void singleStep() {
        singleStepLocked = true;

        try {
            initCompilation();

            if (!compilationStarted)
                compilationStarted = true;

            gui.getSource().addHighlight(sourcePC, true);
            gui.getDestination().clearHighlights();
            updateGUI = true;
            int[] compiledRange = compileLineAndCount(source[sourcePC]);

            if (compiledRange != null) {
                compilationMap.put(new Integer(sourcePC), compiledRange);
            }

            sourcePC++;

            if (sourcePC == source.length) {
                successfulCompilation();
                programSize = destPC;
                gui.enableSave();
                gui.enableSourceRowSelection();
                end(false);
            }

            finalizeCompilation();

        } catch (HackTranslatorException ae) {
            gui.displayMessage(ae.getMessage(), true);
            end(false);
        }

        singleStepLocked = false;
    }

    /**
     * Hides all the pointers.
     */
    protected void hidePointers() {
        gui.getSource().clearHighlights();
        gui.getDestination().clearHighlights();
        gui.getSource().hideSelect();
        gui.getDestination().hideSelect();
    }

    /**
     * Ends the compilers operation (only rewind or a new source can activate the compiler
     * after this), with an option to hide the pointers as well.
     */
    protected void end(boolean hidePointers) {
        timer.stop();
        gui.disableSingleStep();
        gui.disableFastForward();
        gui.disableStop();
        gui.enableRewind();
        gui.disableFullCompilation();
        gui.enableLoadSource();

        inFastForward = false;

        if (hidePointers)
            hidePointers();
    }

    /**
     * Stops the fast forward mode.
     */
    protected void stop() {
        timer.stop();
        gui.enableSingleStep();
        gui.enableFastForward();
        gui.disableStop();
        gui.enableRewind();
        gui.enableLoadSource();
        gui.enableFullCompilation();

        inFastForward = false;
    }

    /**
     * Rewinds to the beginning of the compilation.
     */
    protected void rewind() {
        restartCompilation();
        resetProgram();
    }

    // Saves the program into the given dest file name.
    private void save() throws HackTranslatorException {
        try {
            writer = new PrintWriter(new FileWriter(destFileName));
            dumpToFile();
            writer.close();
        } catch (IOException ioe) {
            throw new HackTranslatorException("could not create file " + destFileName);
        }
    }

    /**
     * Returns the translated machine code program array
     */
    public short[] getProgram() {
        return program;
    }

    /**
     * Dumps the contents of the translated program into the destination file
     */
    private void dumpToFile() {
        for (short i = 0; i < programSize; i++)
            writer.println(getCodeString(program[i], i, false));
        writer.close();
    }

    // Clears the message display.
    protected void clearMessage() {
        gui.displayMessage("", false);
    }

    /**
     * Returns the range in the compilation map that corresponds to the given rowIndex.
     */
    protected int[] rowIndexToRange(int rowIndex) {
        Integer key = new Integer(rowIndex);
        return (int[])compilationMap.get(key);
    }

     // Returns the working dir that is saved in the data file, or "" if data file doesn't exist.
    protected File loadWorkingDir() {
        String dir = ".";

        try {
            BufferedReader r = new BufferedReader(new FileReader("bin/" + getName() + ".dat"));
            dir = r.readLine();
            r.close();
        } catch (IOException ioe) {}

        return new File(dir);
    }

    /**
     * Saves the given working dir into the data file.
     */
    protected void saveWorkingDir(File file) {
        try {
            PrintWriter r = new PrintWriter(new FileWriter("bin/" + getName() + ".dat"));
            r.println(file.getAbsolutePath());
            r.close();
        } catch (IOException ioe) {}

        gui.setWorkingDir(file);
    }

    /**
     * Called when a line is selected in the source file.
     */
    public void rowSelected(TextFileEvent event) {
        int index = event.getRowIndex();
        int[] range = rowIndexToRange(index);
        gui.getSource().addHighlight(index, true);
        gui.getSource().hideSelect();
        if (range != null) {
            gui.getDestination().clearHighlights();
            for (int i = range[0]; i <= range[1]; i++)
                gui.getDestination().addHighlight(i, false);
        }
        else
            gui.getDestination().clearHighlights();
    }

    /**
     * Called by the timer in constant intervals (when in run mode).
     */
    public void actionPerformed(ActionEvent e) {
        if (!singleStepLocked) {
            singleStep();
        }
    }

    /**
     * Throws a HackTranslatorException with the given message and the current line number.
     */
    protected void error(String message) throws HackTranslatorException {
        throw new HackTranslatorException(message, sourcePC);
    }

    public void actionPerformed(HackTranslatorEvent event) {
        Thread t;

        switch (event.getAction()) {
            case HackTranslatorEvent.SOURCE_LOAD:
                String fileName = (String)event.getData();
                File file = new File(fileName);
                saveWorkingDir(file);
                gui.setTitle(getName() + getVersionString() + " - " + fileName);
                loadSourceTask.setFileName(fileName);
                t = new Thread(loadSourceTask);
                t.start();
                break;

            case HackTranslatorEvent.SAVE_DEST:
                clearMessage();
                fileName = (String)event.getData();
                try {
                    checkDestinationFile(fileName);
                    destFileName = fileName;
                    file = new File(fileName);
                    saveWorkingDir(file);
                    gui.setTitle(getName() + getVersionString() + " - " + fileName);
                    save();
                } catch (HackTranslatorException ae) {
                    gui.setDestinationName("");
                    gui.displayMessage(ae.getMessage(), true);
                }
                break;

            case HackTranslatorEvent.SINGLE_STEP:
                clearMessage();
                if (sourceFileName == null)
                    gui.displayMessage("No source file specified", true);
                else if (destFileName == null)
                    gui.displayMessage("No destination file specified", true);
                else {
                    t = new Thread(singleStepTask);
                    t.start();
                }
                break;

            case HackTranslatorEvent.FAST_FORWARD:
                clearMessage();
                t = new Thread(fastForwardTask);
                t.start();
                break;

            case HackTranslatorEvent.STOP:
                stop();
                break;

            case HackTranslatorEvent.REWIND:
                clearMessage();
                rewind();
                break;

            case HackTranslatorEvent.FULL_COMPILATION:
                clearMessage();
                t = new Thread(fullCompilationTask);
                t.start();
                break;

        }
    }

    // The full compilation task
    class FullCompilationTask implements Runnable {

        public void run() {
            gui.displayMessage("Please wait...", false);

            try {
                restartCompilation();
                fullCompilation();
            } catch (HackTranslatorException ae) {
                end(false);
                gui.getSource().addHighlight(sourcePC, true);
                gui.displayMessage(ae.getMessage(), true);
            }
        }
    }

    // The single step task
    class SingleStepTask implements Runnable {
        public void run() {
            if (!singleStepLocked)
                singleStep();
        }
    }

    // The fast forward task
    class FastForwardTask implements Runnable {
        public void run() {
            fastForward();
        }
    }

    // The load source task
    class LoadSourceTask implements Runnable {
        private String fileName;

        public void run() {
            try {
                loadSource(fileName);
            } catch (HackTranslatorException ae) {
                gui.setSourceName("");
                gui.displayMessage(ae.getMessage(), true);
            }
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}
