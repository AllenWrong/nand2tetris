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

package Hack.Controller;

import java.util.*;
import java.io.*;
import Hack.Utilities.*;

/**
 * A list of controller commands.
 * Loads a controller script file.
 */
public class Script {

    /**
     * Maximum arguments for a simulator script command.
     */
    public static final int MAX_SIMULATOR_COMMAND_ARGUMENTS = 4;

    /**
     * Maximum arguments for an output-list script command.
     */
    public static final int MAX_OUTPUT_LIST_ARGUMENTS = 20;

    // The list of commands
    private Vector commands;

    // The list of script line numbers that match the corresponding command
    private Vector lineNumbers;

    // The file name of the script
    private String scriptName;

    // The input stream tokenizer of the script file
    private ScriptTokenizer input;

    /**
     * Constructs a new script according to the given script file.
     * Simulator commands will be executed and variable values will be
     * fetched and set using the given hack simulator.
     */
    public Script(String scriptName)
     throws ScriptException, ControllerException {
        this.scriptName = scriptName;
        try {
            input = new ScriptTokenizer(new FileReader(scriptName));
        } catch (IOException ioe) {
            throw new ScriptException("Script " + scriptName + " not found");
        }

        commands = new Vector();
        lineNumbers = new Vector();
        buildScript();
    }

    // Builds the script according to the given script file name.
    private void buildScript() throws ScriptException, ControllerException {
        boolean repeatOpen = false;
        boolean whileOpen = false;
		boolean justOpened = false;
        boolean outputListPrepared = false;
        int currentCommandIndex = 0;
        Command command = null;
        int lineNumber = 0;

        while (input.hasMoreTokens()) {
            lineNumber = input.getLineNumber() - 1;
            input.advance();

            if (justOpened &&
				input.getTokenType() == ScriptTokenizer.TYPE_SYMBOL &&
                input.getSymbol() == '}') {
				scriptError("An empty " + (repeatOpen?"Repeat":"While") +
							" block is not allowed");
			}
			justOpened = false;
            switch (input.getTokenType()) {
                case ScriptTokenizer.TYPE_KEYWORD:
                    command = createControllerCommand();
                    if (command.getCode() == Command.REPEAT_COMMAND) {
                        if (repeatOpen || whileOpen)
                            scriptError("Nested Repeat and While are not allowed");
                        else {
                            repeatOpen = true;
							justOpened = true;
						}
                    }
                    else if (command.getCode() == Command.WHILE_COMMAND) {
                        if (repeatOpen || whileOpen)
                            scriptError("Nested Repeat and While are not allowed");
                        else {
                            whileOpen = true;
							justOpened = true;
						}
                    }
                    else if (command.getCode() == Command.OUTPUT_LIST_COMMAND)
                        outputListPrepared = true;
                    else if (command.getCode() == Command.OUTPUT_COMMAND && !outputListPrepared)
                        scriptError("No output list created");
                    break;

                case ScriptTokenizer.TYPE_IDENTIFIER:
                    command = createSimulatorCommand();
                    break;

                case ScriptTokenizer.TYPE_INT_CONST:
                    scriptError("A command cannot begin with " + input.getIntValue());

                case ScriptTokenizer.TYPE_SYMBOL:
                    if (input.getSymbol() == '}') {
                        if (!repeatOpen && !whileOpen)
                            scriptError("a '}' without a Repeat or While");
                        else {
                            if (repeatOpen) {
                                command = new Command(Command.END_REPEAT_COMMAND);
                                repeatOpen = false;
                            }
                            else if (whileOpen) {
                                command = new Command(Command.END_WHILE_COMMAND);
                                whileOpen = false;
                            }
                         }
                    }
                    else
                        scriptError("A command cannot begin with '" + input.getSymbol() + "'");
            }

            // sets the terminator of the command
            switch (input.getSymbol()) {
                case ',':
                    command.setTerminator(Command.MINI_STEP_TERMINATOR); break;
                case ';':
                    command.setTerminator(Command.SINGLE_STEP_TERMINATOR); break;
                case '!':
                    command.setTerminator(Command.STOP_TERMINATOR); break;
            }

            commands.addElement(command);
            lineNumbers.addElement(new Integer(lineNumber));
        }

        if (repeatOpen || whileOpen)
            scriptError("Repeat or While not closed");

        command = new Command(Command.END_SCRIPT_COMMAND);
        commands.addElement(command);
        lineNumbers.addElement(new Integer(lineNumber));
    }

    // creates and returns a simulator command.
    // called when the current token is an unrecognized command name.
    // Holds a String array (of simulator command an arguments) as an argument.
    private Command createSimulatorCommand()
     throws ControllerException, ScriptException {
        String[] args = readArgs(MAX_SIMULATOR_COMMAND_ARGUMENTS);

        // count args
        int count;
        for (count = 0; count < args.length && args[count] != null; count++);

        String[] trimmedArgs = new String[count];
        System.arraycopy(args, 0, trimmedArgs, 0, count);
        return new Command(Command.SIMULATOR_COMMAND, trimmedArgs);
    }

    // creates and returns a controller command.
    // called when the current token is a recognized command name.
    private Command createControllerCommand()
        throws ControllerException, ScriptException {

        Command command = null;

        switch (input.getKeywordType()) {
        case ScriptTokenizer.KW_OUTPUT_FILE:
            command = createOutputFileCommand();
            break;
        case ScriptTokenizer.KW_COMPARE_TO:
            command = createCompareToCommand();
            break;
        case ScriptTokenizer.KW_OUTPUT_LIST:
            command = createOutputListCommand();
            break;
        case ScriptTokenizer.KW_OUTPUT:
            command = createOutputCommand();
            break;
        case ScriptTokenizer.KW_ECHO:
            command = createEchoCommand();
            break;
        case ScriptTokenizer.KW_CLEAR_ECHO:
            command = createClearEchoCommand();
            break;
        case ScriptTokenizer.KW_BREAKPOINT:
            command = createBreakpointCommand();
            break;
        case ScriptTokenizer.KW_CLEAR_BREAKPOINTS:
            command = createClearBreakpointsCommand();
            break;
        case ScriptTokenizer.KW_REPEAT:
            command = createRepeatCommand();
            break;
        case ScriptTokenizer.KW_WHILE:
            command = createWhileCommand();
            break;
        }

        return command;
    }

    // creates and returns a controller output-file command.
    // Holds the output file name (String) as an argument.
    private Command createOutputFileCommand()
     throws ControllerException, ScriptException {
        input.advance();
        String[] args = readArgs(1);
        return new Command(Command.OUTPUT_FILE_COMMAND, args[0]);
    }

    // creates and returns a controller compare-to command.
    // Holds the comparison file name (String) as an argument.
    private Command createCompareToCommand()
     throws ControllerException, ScriptException {
        input.advance();
        String[] args = readArgs(1);
        return new Command(Command.COMPARE_TO_COMMAND, args[0]);
    }

    // creates and returns a controller output-list command.
    // Holds an array of VariableFormats as an argument.
    private Command createOutputListCommand()
     throws ControllerException, ScriptException {
        input.advance();
        String[] args = readArgs(MAX_OUTPUT_LIST_ARGUMENTS);

        // count args
        int count;
        for (count = 0; count < args.length && args[count] != null; count++);

        VariableFormat[] vars = new VariableFormat[count];

        for (int i = 0; i < count; i++) {
            int procentPos = args[i].indexOf('%');
            if (procentPos == -1) { // no % found - add default formatting
                procentPos = args[i].length();
                args[i] += "%B1.1.1";
            }

            // find var name
            String varName = args[i].substring(0, procentPos);

            // find format
            char format = args[i].charAt(procentPos + 1);
            if (format != VariableFormat.BINARY_FORMAT && format != VariableFormat.DECIMAL_FORMAT
                && format != VariableFormat.HEX_FORMAT && format != VariableFormat.STRING_FORMAT)
                scriptError("%" + format + " is not a legal format");

            // find padL
            int padL = 0;
            int dotPos1 = args[i].indexOf('.', procentPos);
            if (dotPos1 == -1)
                scriptError("Missing '.'");
            try {
                padL = Integer.parseInt(args[i].substring(procentPos + 2, dotPos1));
            } catch (NumberFormatException nfe) {
                scriptError("padL must be a number");
            }
            if (padL < 0)
                scriptError("padL must be positive");

            // find len
            int len = 0;
            int dotPos2 = args[i].indexOf('.', dotPos1 + 1);
            if (dotPos2 == -1)
                scriptError("Missing '.'");
            try {
                len = Integer.parseInt(args[i].substring(dotPos1 + 1, dotPos2));
            } catch (NumberFormatException nfe) {
                scriptError("len must be a number");
            }
            if (len < 1)
                scriptError("len must be greater than 0");

            // find padR
            int padR = 0;
            try {
                padR = Integer.parseInt(args[i].substring(dotPos2 + 1));
            } catch (NumberFormatException nfe) {
                scriptError("padR must be a number");
            }
            if (padR < 0)
                scriptError("padR must be positive");

            vars[i] = new VariableFormat(varName, format, padL, padR, len);
        }

        return new Command(Command.OUTPUT_LIST_COMMAND, vars);
    }

    // creates and returns a controller output command.
    // Holds no argument.
    private Command createOutputCommand()
     throws ControllerException, ScriptException {
        input.advance();
        checkTerminator();
        return new Command(Command.OUTPUT_COMMAND);
    }

    // creates and returns a controller echo command.
    // Holds the echoed string as an argument.
    private Command createEchoCommand()
     throws ControllerException, ScriptException {
        input.advance();
        String[] args = readArgs(1);
        return new Command(Command.ECHO_COMMAND, args[0]);
    }

    // creates and returns a controller Clear-echo command.
    // Holds no argument.
    private Command createClearEchoCommand()
     throws ControllerException, ScriptException {
        input.advance();
        checkTerminator();
        return new Command(Command.CLEAR_ECHO_COMMAND);
    }

    // creates and returns a controller breakpoint command.
    // Holds a Breakpoint object as an argument.
    private Command createBreakpointCommand()
     throws ControllerException, ScriptException {
        input.advance();
        String[] args = readArgs(2);

        // count args
        int count;
        for (count = 0; count < args.length && args[count] != null; count++);

        if (count < 2)
            scriptError("Not enough arguments");

        String value = args[1];
        if (value.startsWith("%S"))
            value = value.substring(2);
        else if (args[1].startsWith("%"))
            value = Conversions.toDecimalForm(value);

        Breakpoint breakpoint = new Breakpoint(args[0], value);

        return new Command(Command.BREAKPOINT_COMMAND, breakpoint);
    }

    // creates and returns a controller clear-breakpoints command.
    // Holds no argument.
    private Command createClearBreakpointsCommand()
     throws ControllerException, ScriptException {
        input.advance();
        checkTerminator();
        return new Command(Command.CLEAR_BREAKPOINTS_COMMAND);
    }

    // creates and returns a controller repeat command.
    // Holds the repeat quantity (Integer) as an argument.
    private Command createRepeatCommand()
     throws ScriptException, ControllerException {
        input.advance();
        int repeatNum = 0;

        if (input.getTokenType() == ScriptTokenizer.TYPE_INT_CONST) {
            repeatNum = input.getIntValue();
            if (repeatNum < 1)
                scriptError("Illegal repeat quantity");
            input.advance();
        }

        if (!(input.getTokenType() == ScriptTokenizer.TYPE_SYMBOL &&
              input.getSymbol() == '{'))
                scriptError("Missing '{' in repeat command");

        return new Command(Command.REPEAT_COMMAND, new Integer(repeatNum));
    }

    // creates and returns a controller While command.
    // Holds a ScriptCondition object as an argument.
    private Command createWhileCommand()
     throws ScriptException, ControllerException {

        input.advance();
        ScriptCondition condition = null;
        try {
            condition = new ScriptCondition(input);
        } catch (ScriptException se) {
            scriptError(se.getMessage());
        }

        if (!(input.getTokenType() == ScriptTokenizer.TYPE_SYMBOL &&
              input.getSymbol() == '{'))
                scriptError("Missing '{' in while command");

        return new Command(Command.WHILE_COMMAND, condition);
    }

    // Reads string arguments from the given input and returns them as a string array.
    // If the given maxArgs count is exceeded, an exception is thrown.
    private String[] readArgs(int maxArgs)
     throws ControllerException, ScriptException {
        String[] args = new String[maxArgs];

        // fill the temp args holder with the following tokens
        int i = 0;
        while (input.hasMoreTokens() && input.getTokenType() != ScriptTokenizer.TYPE_SYMBOL
               && i < maxArgs) {
            args[i++] = input.getToken();
            input.advance();
        }

        checkTerminator();

        if (i == 0)
            scriptError("Missing arguments");

        return args;
    }

    // Checks that the current token is a terminator symbol. If not, an exception is thrown.
    private void checkTerminator()
     throws ScriptException {
        if (input.getTokenType() != ScriptTokenizer.TYPE_SYMBOL) {
            if (input.hasMoreTokens())
                scriptError("too many arguments");
            else
                scriptError("Script ends without a terminator");
        }
        else if (input.getSymbol() != ',' && input.getSymbol() != ';' && input.getSymbol() != '!')
            scriptError("Illegal terminator: '" + input.getSymbol() + "'");
    }

    // Throws a script exception with the given message.
    private void scriptError(String message) throws ScriptException {
        throw new ScriptException(message, scriptName, input.getLineNumber());
    }

    /**
     * Returns the command at the given index.
     * Assumes a legal index.
     */
    public Command getCommandAt(int index) {
        return (Command)commands.elementAt(index);
    }

    /**
     * Returns the script line number of the command at the given index.
     * Assumes a legal index.
     */
    public int getLineNumberAt(int index) {
        return ((Integer)lineNumbers.elementAt(index)).intValue();
    }

    /**
     * Returns the number of commands in the script.
     */
    public int getLength() {
        return commands.size();
    }
}
