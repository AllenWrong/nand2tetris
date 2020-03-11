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

package Hack.VirtualMachine;

/**
 * A Virtual Machine interface. provides an interface for compiling
 * byte code to machine language - every implementation should
 * produce the proper commands for it's architectue.
 * It has 4 types of operations:
 * 1 - Arithmetic commands
 * 2 - Memory access commands
 * 3 - Program flow
 * 4 - Function calls
 */
public interface VirtualMachine {


    //----  The Arithmetic commands ---//
    // Each operation pops it's argument from the stack and pushes
    // the result back to the stack

    /**
     * integer addition (binary operation).
     */
    public abstract void add();

    /**
     * 2's complement integer substraction (binary operation)
     */
    public abstract void substract();

    /**
     * 2's complement negation (unary operation)
     */
    public abstract void negate();

    /**
     * Equalaty operation (binary operation). Returns(to the stack)
     * 0xFFFF as true,0x000 as false
     */
    public abstract void equal();

    /**
     * Greater than operation (binary operation). Returns(to the stack)
     * 0xFFFF as true,0x0000 as false
     */
    public abstract void greaterThan();

    /**
     * Less than operation (binary operation). Returns(to the stack)
     * 0xFFFF as true,0x0000 as false
     */
    public abstract void lessThan();

    /**
     * Bit wise "AND" (binary operation).
     */
    public abstract void and();

    /**
     * Bit wise "OR" (binary operation).
     */
    public abstract void or();

    /**
     * Bit wise "NOT" (unary operation).
     */
    public abstract void not();



    //----  Memory access commands ---//

    /**
     * Pushes the value of the given segment in the given entry to the stack
     */
    public abstract void push(String segment, short entry);

    /**
     * Pops an item from the stack into the given segment in the given entry
     */
    public abstract void pop(String segment, short entry);


    //----  Program flow commands ---//

    /**
     * Labels the current location in the function code. Only labeled location
     * can be jumped to from other parts of the function.
     * The label - l is 8 bits and is local to the function
     */
    public abstract void label(String l);

    /**
     * Goes to the label l
     * The label - l is 8 bits and is local to the function
     */
    public abstract void goTo(String l);

    /**
     * Pops a value from the stack and goes to the label l if the value
     * is not zero.
     * The label - l is 8 bits and is local to the function
     */
    public abstract void ifGoTo(String l);




    //----  Function calls commands ---//

    /**
     * Here Starts the code of a function according to the given function name
     * that has the given number of local variables.
     * @param functionName The function name
     * @param numberOfLocals The number of local variables
     */
    public abstract void function(String functionName, short numberOfLocals);

    /**
     * Returns the value of the function to the top of the stack.
     */
    public abstract void returnFromFunction();

    /**
     * Calls a function according to the given function number stating
     * that the given number of arguments have been pushed onto the stack
     * @param functionName The function name
     * @param numberOfArguments The number of arguments of the function
     */
    public abstract void callFunction(String functionName, short numberOfArguments);
}
