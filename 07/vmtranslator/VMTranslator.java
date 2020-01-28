package com.vmtranslator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VMTranslator {
	private Parser parser;
	private CodeWriter codeWriter;
	
	public VMTranslator(File file) throws FileNotFoundException {
		this.parser = new Parser(file, new FileInputStream(file));
		this.codeWriter = new CodeWriter(file);
	}
	
	public Parser getParser() {
		return parser;
	}
	
	public CodeWriter getCodeWriter() {
		return codeWriter;
	}

	public void doWrite() throws IOException {
		// get a command
		this.parser.advance();
		CommandType commandType = this.parser.commandType();
		if(commandType.equals(CommandType.C_ARITHMETIC)) {
			codeWriter.writeArithmetic(this.parser);
		}else if(commandType.equals(CommandType.C_PUSH) || commandType.equals(CommandType.C_POP)) {
			codeWriter.writePushPop(this.parser);
		}
		this.codeWriter.getWriter().flush();
	}
	
	
	public static void main(String[] args) throws IOException {
		// *** change the file ***
		File file = new File("C:\\Users\\Thingcor\\Desktop\\StackTest.vm");
		VMTranslator vmTranslator = new VMTranslator(file);
		Parser parser = vmTranslator.getParser();
		do {
			vmTranslator.doWrite();
		}while(parser.hasMoreCommands());
	}
}
