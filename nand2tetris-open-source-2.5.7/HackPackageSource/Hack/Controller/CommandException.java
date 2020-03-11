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

/**
 * An exception for errors in command definitions.
 */
public class CommandException extends Exception
{
    /**
     * Constructs a new CommandException with the given message and command.
     */
    public CommandException(String message, String[] command) {
        super(message + ": " + commandString(command));
    }

    private static String commandString(String[] command) {
        StringBuffer message = new StringBuffer();
        for (int i = 0; i < command.length; i++)
            message.append(command[i] + " ");

        return message.toString();
    }
}
