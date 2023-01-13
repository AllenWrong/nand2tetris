package com.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Thingcor
 * This is the Parser module, represented by the Parser class. 
 * One parser class represented a scan.
 */
public class Parser {
	/** the file will be parsed*/
	File file;
	Scanner scanner;
	String currentCommand;
	/** count the address of the instructions*/
	int address = 0;
	
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
	 * get a new line
	 */
	public void advance() {
		if(hasMoreCommands()) {
			/* when we don't get the command loop*/
			do{
				this.currentCommand = this.scanner.nextLine();
			}while(!getCommand());
			// when we get a a-command or c-command, let the address++;
			if(this.commandType().equals(CommandType.A_COMMAND) || this.commandType().equals(CommandType.C_COMMAND)) {
				this.address++;
			}
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
		String headChar = stringLine.trim().substring(0,2); // get the first two characters
		if(headChar.equals("//")) {
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
	 * By the first character of the command, we distinguish the command type
	 * @return command type
	 */
	public CommandType commandType() {
		if(this.currentCommand.substring(0, 1).equals("@")) {
			return CommandType.A_COMMAND;
		}else if(this.currentCommand.substring(0, 1).equals("(")) {
			return CommandType.L_COMMAND;
		}else if(this.currentCommand.contains(";") || this.currentCommand.contains("=")) {
			return CommandType.C_COMMAND;
		}else {
			return CommandType.UNKNOW;
		}
	}
	
	/**
	 * Judge whether the symbol is integer symbol
	 * @param symbol
	 * @return
	 */
	public boolean isDigit(String symbol) {
		for(int i = 0;i<symbol.length();i++) {
			if(!Character.isDigit(symbol.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Padding the string to get the 15 bit address
	 * @param str
	 * @return
	 */
	public String paddingZero(String str) {
		while(str.length()<=15) {
			str="0"+str;
		}
		return str;
	}
	
	/**
	 * get the symbol or the decimal address string.
	 * @return  null
	 * 			if the command is c command or unknown command 
	 */
	public String sysmbol() {
		/**
		 * basically, whatever the command is a-command or l-command, if the symbol is represented
		 * by address, we return the binary form of the address, if the symbol is represented by
		 * string, we return the string. 
		 * When we first scan the file, if we get the binary string of the address we don't do 
		 * anything, if we get the string symbol we add the string and it's binary address to the 
		 * symbol table.
		 * When we second scan the file, if we get the binary string of the address we translate it
		 * , if we get the string symbol we get it's address from hash table. 
		 */
		if (commandType().equals(CommandType.A_COMMAND)) {
			String command = this.currentCommand;
			String subStr =  command.substring(1, command.length());
			// if the a-command is represented by address, we do the following action
			if(isDigit(subStr)) {
				Long address = Long.parseLong(subStr);
				String addressStr = Long.toBinaryString(address);
				return paddingZero(addressStr);
			}else {
				return subStr;
			}
		}
		if(commandType().equals(CommandType.L_COMMAND)) {
			String command = this.currentCommand;
			String subStr = command.substring(1, command.length()-1);
			// if the L-command is represented by address, we do the following action
			if(isDigit(subStr)) {
				Long address = Long.parseLong(subStr);
				String addressStr = Long.toBinaryString(address);
				return paddingZero(addressStr);
			}else {
				return subStr;
			}
		}
		return null;
	}
	
	/**
	 * get the dest symbol of c command
	 * @return dest if the command has dest domain
	 * 		   ""   if the command has not dest domain
	 * 		   null if the command is not the c command
	 */
	public String dest() {
		if(commandType().equals(CommandType.C_COMMAND)) {
			String command = this.currentCommand;
			if(command.contains("=")) {
				return command.substring(0, command.indexOf("="));
			}else {
				return "";
			}
		}
		return null;
	}
	
	/**
	 * get the comp domain of the c command
	 * @return comp if the command has comp domain     
	 * 		   ""   if the command has not comp domain 
	 *		   null if the command is not the c command
	 */
	public String comp() {
		if(commandType().equals(CommandType.C_COMMAND)) {
			String command = this.currentCommand;
			if(command.contains("=") && command.contains(";")) {
				return command.substring(command.indexOf("=")+1, command.indexOf(";"));
			}else if (command.contains(";")) {
				return command.substring(0, command.indexOf(";"));
			}else if (command.contains("=")) {
				return command.substring(command.indexOf("=")+1, command.length());
			}else {
				return "";
			}
		}
		return null;
	}

	/**
	 * get the jump domain of the c command
	 * @return jump if the command has jump domain     
	 *         ""   if the command has not jump domain 
	 *         null if the command is not the c command
	 */
	public String jump() {
		if(commandType().equals(CommandType.C_COMMAND)) {
			String command = this.currentCommand;
			if(command.contains(";")) {
				return command.substring(command.indexOf(";")+1, command.length());
			}else {
				return "";
			}
		}
		return null;
	}
}
