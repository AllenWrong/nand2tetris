package com.vmtranslator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Parser {
	/** the file will be parsed*/
	private File file;
	private Scanner scanner;
	private String currentCommand;
	
	/** Constructor*/
	public Parser(File file, FileInputStream fiStream) {
		this.file=file;
		this.scanner = new Scanner(fiStream);
	}
	
	/**
	 * if the file has more command?
	 * @return true or false
	 */
	public boolean hasMoreCommands() {
		if(this.scanner.hasNextLine()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * get a new line, process it until we get a command
	 */
	public void advance() {
		if(hasMoreCommands()) {
			/* when we don't get the command loop*/
			do{
				this.currentCommand = this.scanner.nextLine();
			}while(!getCommand());
		}else {
			this.scanner.close();
		}
	}
	
	/**
	 * When we get a line, in fact it's not the command, it contains comment line,space line,space, comment.<br/>
	 * So we process those character in this method. What we should do is following:<br/>
	 * 1. Remove the comment line. <br/>
	 *    If we get the comment line, throw it away and read again. Sometimes, there are some space in the<br/>
	 *    head of the comment line. But generally, the first two characters of the comment line is "//".<br/>
	 * 2. Remove the space line. <br/>
	 *    If we get the space line, throw it and away again. Space line is easy to process. What we do is<br/>
	 *    compare the line with the "".<br/>
	 * 3. Throw the comment away. <br/>
	 *    Find the comment' position in the command, then process it. We can find the comment by "//". Then<br/>
	 *    throw the strings behind the "//" away. This looks easy.
	 * @return the command string
	 * @throws IOException 
	 */
	private boolean getCommand(){
		String stringLine = this.currentCommand;
		/* remove the space line*/
		if(stringLine.equals("")) {
			return false;
		}
		/* Remove the comment line*/
		String head = stringLine.substring(0, 2);  // get the first two characters
		String headChar = stringLine.trim().substring(0,2); // get the first two characters
		if(head.equals("//") || headChar.equals("//")) {
			return false;
		}
		/* Throw the comment away*/
		if(stringLine.contains("//")) {
			String subStr = stringLine.substring(0, stringLine.indexOf("//"));
			this.currentCommand = subStr.trim();
			return true;
		}
		this.currentCommand = stringLine.trim();
		return true;
	}
	
	/**
	 * get the type of command. Detail in the book.
	 * @return
	 */
	public CommandType commandType() {
		String commandLabel = "";
		if(this.currentCommand.contains(" ")) {
			commandLabel = this.currentCommand.substring(0, this.currentCommand.indexOf(" "));
		}else {
			commandLabel = this.currentCommand;
		}
		 
		switch (commandLabel) {
		case "add": case "sub": case "neg":
		case "eq":  case "gt":  case "lt":
		case "and": case "or":  case "not":
			return CommandType.C_ARITHMETIC;
		case "pop":
			return CommandType.C_POP;
		case "push":
			return CommandType.C_PUSH;
		case "label":
			return CommandType.C_LABEL;
		case "goto":
			return CommandType.C_GOTO;
		case "if-goto":
			return CommandType.C_IF;
		case "function":
			return CommandType.C_FUNCTION;
		case "call":
			return CommandType.C_CALL;
		case "return":
			return CommandType.C_RETURN;
		default:
			break;
		}
		return null;
	}
	
	/**
	 * get the first parameter of the current command. <br/>
	 * If the command is return, don't call the method.
	 * @return add,sub..etc
	 * 		   If the current command is arithmetic, return the command.
	 */
	public String args1() {
//		if(commandType().equals(CommandType.C_RETURN)) {
//			return null;
//		}
		if(commandType().equals(CommandType.C_ARITHMETIC)) {
			return this.currentCommand;
		}
		String args1 = "";
		if(commandType().equals(CommandType.C_LABEL)||
		   commandType().equals(CommandType.C_GOTO)||
		   commandType().equals(CommandType.C_IF)) {
			args1 = this.currentCommand.substring(this.currentCommand.indexOf(" ")+1, currentCommand.length());
		}else{
			args1 = this.currentCommand.substring(this.currentCommand.indexOf(" ")+1, this.currentCommand.lastIndexOf(" "));
		}
		return args1;
	}
	
	/**
	 * get the second parameter of the current command. <br/>
	 * Call the method only the command type is c_push,c_pop,c_function,c_call.
	 * @return
	 */
	public String args2() {
		return this.currentCommand.substring(this.currentCommand.lastIndexOf(" ")+1, this.currentCommand.length());
	}

	public File getFile() {
		return file;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public String getCurrentCommand() {
		return currentCommand;
	}
}
