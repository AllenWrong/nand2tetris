package com.vmtranslator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class VMTranslator {
	private Parser parser;
	private CodeWriter codeWriter;
	
	/**
	 * In fact, we only need to construct one code writer as the output file is one.<br/>
	 * But parser orients file, in another word every file need a parser. So in this class<br/>
	 * every directory need construct one VMTranslator. So this class is designed by<br/>
	 * this principle.
	 * @param file
	 * @throws IOException
	 */
	public VMTranslator(File file) throws IOException {
		this.codeWriter = new CodeWriter(file);
	}
	
	public Parser getParser() {
		return parser;
	}
	
	public CodeWriter getCodeWriter() {
		return codeWriter;
	}
	
	/**
	 * use this function to start compiling file.
	 * @param file
	 * @throws IOException 
	 */
	public void doCompile(File file) throws IOException {
		if(file.isFile()) {
			this.parser = new Parser(file, new FileInputStream(file));
		}else {
			this.parseDirectory(file);
		}
	}
	
	/**
	 * Read the given catalog. <br/>
	 * This method will distinguish Sys.vm and do the related work.
	 * @param fileDirectory
	 * 						parsing file path
	 * @throws IOException 
	 */
	private void parseDirectory(File fileDirectory) throws IOException {
		File[] files = fileDirectory.listFiles();
		ArrayList<File> VMFileList = new ArrayList<>();
		
		// select the vm file from given directory
		for(File i : files) {
			String fileName = i.getName();
			String laterName = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(laterName.equals("vm")) {
				VMFileList.add(i);
			}else {
				continue;
			}
		}
		
		// firstly choose Sys.vm file to parse
		for(File i : VMFileList) {
			if(i.getName().equals("Sys.vm")) {
				this.parseFile(i);
				VMFileList.remove(i);
				break;
			}
		}
		
		// parse other vm file
		for(File i : VMFileList) {
			this.parseFile(i);
		}
	}
	
	/**
	 * @param file the file needed to parse
	 * @throws IOException 
	 */
	private void parseFile(File file) throws IOException {
		this.parser = new Parser(file, new FileInputStream(file));
		do {
			this.doWrite();
		}while(parser.hasMoreCommands());
	}

	/**
	 * Write assembly code.
	 * @throws IOException
	 */
	private void doWrite() throws IOException {
		// get a command
		this.parser.advance();
		CommandType commandType = this.parser.commandType();
		if(commandType.equals(CommandType.C_ARITHMETIC)) {
			codeWriter.writeArithmetic(this.parser);
		}else if(commandType.equals(CommandType.C_PUSH) || commandType.equals(CommandType.C_POP)) {
			codeWriter.writePushPop(this.parser);
		}else if(commandType.equals(CommandType.C_CALL)) {
			codeWriter.writeCall(this.parser);
		}else if(commandType.equals(CommandType.C_FUNCTION)) {
			codeWriter.writeFunction(this.parser);
		}else if(commandType.equals(CommandType.C_GOTO)) {
			codeWriter.writeGoto(this.parser);
		}else if(commandType.equals(CommandType.C_IF)) {
			codeWriter.writeIf(this.parser);
		}else if(commandType.equals(CommandType.C_LABEL)) {
			codeWriter.writeLabel(this.parser);
		}else if(commandType.equals(CommandType.C_RETURN)) {
			codeWriter.writeReturn(this.parser);
		}else {
			System.out.println("unknown command type!");
		}
		this.codeWriter.getWriter().flush();
	}
	
	public static void main(String[] args) throws IOException {
		// the parsing directory or file
		File file = new File("D:\\leaning_source\\nand2tetris\\projects\\08\\FunctionCalls\\StaticsTest");
		VMTranslator vmTranslator = new VMTranslator(file);
		vmTranslator.doCompile(file);
	}
}
