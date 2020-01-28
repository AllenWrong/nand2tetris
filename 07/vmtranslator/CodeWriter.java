package com.vmtranslator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CodeWriter {
	/** The parsing file*/
	private File file;
	private File outputFile;
	private BufferedWriter writer;
	/** This variable is used to distinguish the l-command, */
	int i = 0;
	
	/** constructor, initialize the writer*/
	public CodeWriter(File file) throws FileNotFoundException {
		this.setFile(file);
		setFileName(file.getPath());
		this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
	
	}
	
	/**
	 * This function need the input file path.
	 * @param fileName 
	 * 				  the input file path
	 */
	public void setFileName(String fileName) {
		
		String outputFilePath = fileName.substring(0,fileName.lastIndexOf("\\"))+"\\"+fileName.substring(fileName.lastIndexOf("\\")+1, fileName.indexOf("."))+".asm";
		this.outputFile = new File(outputFilePath);
	}
	
	/**
	 * The code of get top value of stack. 
	 * @throws IOException 
	 */
	private void getTopSP() throws IOException {
		this.writer.write("// get the top element of stack\r\n"
				        + "@SP\r\n"
				        + "M=M-1\r\n"
						+ "A=M\r\n"
				        + "D=M\r\n");
	}
	
	/**
	 * The code of push value into stack top.
	 * @throws IOException
	 */
	private void pushValueIntoStack(String value) throws IOException {
		this.writer.write("// push the value into stack\r\n"
						+ "@SP\r\n"
						+ "A=M\r\n"
						+ "M="+value+"\r\n"
						+ "@SP\r\n" 
						+ "M=M+1\r\n");
	}
	
	/**
	 * Compute the address by basic address, and get the value in the unit by address.
	 * @param arg1 
	 * 			  Say what segment
	 * @param arg2
	 * 			  Say the offset
	 * @throws IOException
	 */
	private void doPushByArg(String arg1,String arg2) throws IOException {
		this.writer.write("@"+arg1+"\r\n"
						+ "D=M\r\n"
						+ "@"+arg2+"\r\n"
						+ "A=D+A\r\n"
						+ "D=M\r\n");
		pushValueIntoStack("D");
	}
	
	/**
	 * Store temporary value in general register. 
	 * @param reg 
	 * 			 target register
	 * @throws IOException
	 */
	private void storeValueInGR(String reg) throws IOException {
		this.writer.write("// store the result temporarily\r\n"
						+ "@"+reg+"\r\n"
						+ "M=D\r\n");
	}
	
	/**
	 * Get the the top two element of stack and store them in R13 and R14
	 * @throws IOException
	 */
	private void getTopTwoElementOfStackAndStoreThemInReg() throws IOException {
		// get the top first element of stack
		getTopSP();
		storeValueInGR("R14");
		// get the top second element of stack
		getTopSP();
		storeValueInGR("R13");
	}
	
	/**
	 * As so far, the R3 store the address and D store the data. This operation is to store the top<br/>
	 * into memory.
	 * @throws IOException 
	 */
	private void storeTheRegValueIntoMemory() throws IOException {
		this.writer.write("// store the top value\r\n"
						+ "@R13\r\n"
						+ "A=M\r\n"
						+ "M=D\r\n");
	}
	
	/**
	 * Get value from R13. This mainly used in arithmetic.
	 * @throws IOException
	 */
	private void getValueFromR13() throws IOException {
		this.writer.write("@R13\r\n"
						+ "D=M\r\n");
	}
	
	/**
	 * Let value in R13 add value in R14.
	 * @throws IOException
	 */
	private void R13AddR14() throws IOException {
		getValueFromR13();
		this.writer.write("@R14\r\n"
						+ "D=D+M\r\n");
	}
	
	/**
	 * Let value in R13 minus value in R14
	 * @throws IOException 
	 */
	private void R13MinusR14() throws IOException {
		getValueFromR13();
		this.writer.write("@R14\r\n"
						+ "D=D-M\r\n");
	}
	
	/**
	 * write the arithmetic command.
	 * @param 
	 * @throws IOException 
	 */
	public void writeArithmetic(Parser parser) throws IOException {
		// get the command operation type
		String commandLabel = parser.args1();
		switch (commandLabel) {
		case "add":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			R13AddR14();
			pushValueIntoStack("D");
			this.writer.write("\r\n");
			break;
		case "sub":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			R13MinusR14();
			pushValueIntoStack("D");
			this.writer.write("\r\n");
			break;
		case "eq":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			R13MinusR14();
			this.writer.write("@EQ"+i+"\r\n"
							+ "D;JEQ\r\n");
			pushValueIntoStack("0");
			this.writer.write("@ENDEQ"+i+"\r\n"
							+ "0;JMP\r\n"
							+ "(EQ"+i+")\r\n");
			// -1 represent true, 0 represent false
			pushValueIntoStack("-1");
			this.writer.write("(ENDEQ"+(i++)+")\r\n");
			this.writer.write("\r\n");
			break;
		case "gt":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			R13MinusR14();
			this.writer.write("@GT"+i+"\r\n"
							+ "D;JGT\r\n");
			pushValueIntoStack("0");
			this.writer.write("@ENDGT"+i+"\r\n"
							+ "0;JMP\r\n"
							+ "(GT"+i+")\r\n");
			pushValueIntoStack("-1");
			this.writer.write("(ENDGT"+(i++)+")\r\n");
			this.writer.write("\r\n");
			break;
		case "lt":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			R13MinusR14();
			this.writer.write("@LT"+i+"\r\n"
							+ "D;JLT\r\n");
			pushValueIntoStack("0");
			this.writer.write("@ENDLT"+i+"\r\n"
							+ "0;JMP\r\n"
							+ "(LT"+i+")\r\n");
			pushValueIntoStack("-1");
			this.writer.write("(ENDLT"+(i++)+")\r\n");
			this.writer.write("\r\n");
			break;
		case "and":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			getValueFromR13();
			this.writer.write("@R14\r\n"
							+ "D=D&M\r\n");
			pushValueIntoStack("D");
			this.writer.write("\r\n");
			break;
		case "or":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopTwoElementOfStackAndStoreThemInReg();
			getValueFromR13();
			this.writer.write("@R14\r\n"
							+ "D=D|M\r\n");
			pushValueIntoStack("D");
			this.writer.write("\r\n");
			break;
		case "not":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopSP();
			this.writer.write("D=!D\r\n");
			pushValueIntoStack("D");
			this.writer.write("\r\n");
			break;
		case "neg":
			this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
			getTopSP();
			this.writer.write("@0\r\n"
							+ "D=A-D\r\n");
			pushValueIntoStack("D");
			this.writer.write("\r\n");
			break;
		default:
			break;
		}
	}
	
	/**
	 * write the push/pop command.
	 * @throws IOException 
	 */
	public void writePushPop(Parser parser) throws IOException {
		if(parser.commandType().equals(CommandType.C_PUSH)) {
			String arg1 = parser.args1();
			String arg2 = parser.args2();
			switch (arg1) {
			case "argument":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				doPushByArg("ARG", arg2);
				this.writer.write("\r\n");
				break;
			case "local":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				doPushByArg("LCL", arg2);
				this.writer.write("\r\n");
				break;
			case "static":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@"+this.file.getName().substring(0, this.file.getName().indexOf(".")+1)+arg2+"\r\n"
								+ "D=M\r\n");
				pushValueIntoStack("D");
				this.writer.write("\r\n");
				break;
			case "constant":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@"+arg2+"\r\n"
								+ "D=A\r\n");
				pushValueIntoStack("D");
				this.writer.write("\r\n");
				break;
			case "this":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				doPushByArg("THIS", arg2);
				this.writer.write("\r\n");
				break;
			case "that":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				doPushByArg("THAT", arg2);
				this.writer.write("\r\n");
				break;
			case "pointer":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@THIS\r\n"
								+ "D=A\r\n"
								+ "@"+arg2+"\r\n"
								+ "A=D+A\r\n"
								+ "D=M\r\n");
				pushValueIntoStack("D");
				this.writer.write("\r\n");
				break;
			case "temp":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				// Because the temp segment store the content directly, so we needn't to compute the address.
				this.writer.write("@R5\r\n"
								+ "D=A\r\n"
								+ "@"+arg2+"\r\n"
								+ "A=D+A\r\n"
								+ "D=M\r\n");
				pushValueIntoStack("D");
				this.writer.write("\r\n");
				break;
			default:
				break;
			}
		}else if(parser.commandType().equals(CommandType.C_POP)) {
			String arg1 = parser.args1();
			String arg2 = parser.args2();
			switch (arg1) {
			case "argument":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@ARG\r\n"
								+ "D=M\r\n"
								+ "@"+arg2+"\r\n"
								+ "D=D+A\r\n");
				storeValueInGR("R13");
				getTopSP();
				storeTheRegValueIntoMemory();
				this.writer.write("\r\n");
				break;
			case "local":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@LCL\r\n"
								+ "D=M\r\n"
								+ "@"+arg2+"\r\n"
								+ "D=D+A\r\n");
				storeValueInGR("R13");
				getTopSP();
				storeTheRegValueIntoMemory();
				this.writer.write("\r\n");
				break;
			case "static":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				getTopSP();
				this.writer.write("@"+this.file.getName().substring(0, this.file.getName().indexOf(".")+1)+arg2+"\r\n"
								+ "M=D\r\n");
				this.writer.write("\r\n");
				break;
			case "constant":
				// May be pop constant is not exist
				break;
			case "this":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@THIS\r\n"
								+ "D=M\r\n"
								+ "@"+arg2+"\r\n"
								+ "D=D+A\r\n");
				storeValueInGR("R13");
				getTopSP();
				storeTheRegValueIntoMemory();
				this.writer.write("\r\n");
				break;
			case "that":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@THAT\r\n"
								+ "D=M\r\n"
								+ "@"+arg2+"\r\n"
								+ "D=D+A\r\n");
				storeValueInGR("R13");
				getTopSP();
				storeTheRegValueIntoMemory();
				this.writer.write("\r\n");
				break;
			case "pointer":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@THIS\r\n"
								+ "D=A\r\n"
								+ "@"+arg2+"\r\n"
								+ "D=D+A\r\n");
				storeValueInGR("R13");
				getTopSP();
				storeTheRegValueIntoMemory();
				this.writer.write("\r\n");
				break;
			case "temp":
				this.writer.write("// vm command:"+parser.getCurrentCommand()+"\r\n");
				this.writer.write("@5\r\n"
								+ "D=A\r\n"
								+ "@"+arg2+"\r\n"
								+ "D=D+A\r\n");
				storeValueInGR("R13");
				getTopSP();
				storeTheRegValueIntoMemory();
				this.writer.write("\r\n");
				break;
			default:
				break;
			}
		}else {
			System.out.println("this command is not push and pop!");
		}
	}
	
	/**
	 * close the file output stream. 
	 * @throws IOException 
	 */
	public void close() throws IOException {
		this.writer.close();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public BufferedWriter getWriter() {
		return writer;
	}

	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}
}
