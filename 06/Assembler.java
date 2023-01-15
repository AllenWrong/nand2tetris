package com.module;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * One Assembler represented a procedure of the file processing.
 * @author Thingcor
 *
 */
public class Assembler {
	/** store compiling file*/
	File file;
	/** store the compiled file*/
	File binFile;
	Parser parser;
	Code code;
	SymbolTable symbolTable;
	int freeVarAddress = 16;
	
	/** the stream to read/write the program file*/
	BufferedWriter writer;
	
	/** we think that one file has one symbol table, so we initialize the symbol table in the constructor*/
	public Assembler(File file) throws FileNotFoundException {
		this.file = file;
		String fileName = file.getName();
		String binFilePath = file.getParent()+"\\"+fileName.substring(0, fileName.indexOf("."))+".cmp";
		this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(binFilePath)));
		this.code = new Code();
		this.symbolTable = new SymbolTable();
	}
	
	/** 
	 * the first scan of the file
	 * in this procedure, we collect the symbol of l-command and add them to symbol table.
	 * @throws FileNotFoundException 
	 */
	public void firstScanFile() throws FileNotFoundException {
		this.parser = new Parser(this.file);
		do {
			parser.advance();
			CommandType commandType = parser.commandType();
			switch (commandType) {
				case L_COMMAND:
					String subL =  parser.currentCommand.substring(1, parser.currentCommand.length() - 1);
					if (!parser.isDigit(subL)) {
						this.symbolTable.addEntry(subL, parser.address);
					}
					break;
				default:
					break;
			}
		} while (parser.hasMoreCommands());
	}
	
	/** 
	 * The second scan of the file
	 * In this method, we translate the program file. Remember that the l-command needn't to translate.
	 * @throws IOException 
	 */
	public void secondScanFile() throws IOException {
		for (String validInstruction : parser.validInstructionSet) {
			parser.currentCommand = validInstruction;
			CommandType commandType = parser.commandType();
			switch (commandType) {
				case A_COMMAND:
					String subStr =  parser.currentCommand.substring(1, parser.currentCommand.length());
					if (parser.isDigit(subStr)) {
						System.out.println(parser.currentCommand+"\t\t"+parser.symbol());
						writer.write(parser.symbol()+"\n");
					} else {
						int address = 0;
						if (this.symbolTable.contains(subStr)) {
							address = this.symbolTable.getAddress(subStr);
						} else {
							this.symbolTable.addEntry(subStr, this.freeVarAddress);
							address = this.freeVarAddress++;
						}

						String addressStr = parser.paddingZero(Integer.toBinaryString(address));
						System.out.println(parser.currentCommand+"\t\t"+addressStr);
						writer.write(addressStr+"\n");
					}
					break;
				case C_COMMAND:
					System.out.print(parser.currentCommand+"\t\t");
					String dest = code.dest(parser.dest());
					String comp = code.comp(parser.comp());
					String jump = code.jump(parser.jump());
					String binCmd = "111"+comp+dest+jump;
					System.out.println(binCmd);
					writer.write(binCmd+"\n");
					break;
				case L_COMMAND:
					System.out.println(parser.currentCommand);
					break;
				default:
					break;
			}
		}

		this.writer.flush();
	}
	
	public static void main(String[] args) throws Exception {
		// auto test
		AutoTest autoTest = new AutoTest();
		autoTest.start();
	}
}