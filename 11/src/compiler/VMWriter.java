package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class VMWriter {
	private BufferedWriter writer;
	
	public VMWriter(File outputFile) {
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the cmd string.
	 * This method can reduce the code.
	 * @param cmd the string need to write.
	 */
	private void write(String cmd) {
		try {
			this.writer.write(cmd+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * flush the writer.
	 */
	private void done() {
		try {
			this.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sometimes, we need to use the write to write string without "\n".
	 * So this write can help us do above action.
	 * @return
	 */
	public BufferedWriter getWriter() {
		return writer;
	}

	/***************************** Above method is assistant method. ******************************/

	public void writePush(String segment, int index) {
		write("push "+segment+" "+index);
		done();
	}
	
	public void writePop(String segment, int index){
		write("pop "+segment+" "+index);
		done();
	}
	
	/**
	 * Write arithmetic command.
	 * 
	 * <table border>
	 * <tr> <td>+</td> <td>add</td> </tr>
	 * <tr> <td>-</td> <td>sub</td> </tr>
	 * <tr> <td>--</td> <td>neg</td> </tr>
	 * <tr> <td>==</td> <td>eq</td> </tr>
	 * <tr> <td>&gt</td> <td>gt</td> </tr>
	 * <tr> <td>&lt</td> <td>lt</td> </tr>
	 * <tr> <td>&</td> <td>and</td> </tr>
	 * <tr> <td>|</td> <td>or</td> </tr>
	 * <tr> <td>~</td> <td>not</td> </tr>
	 * </table>
	 * @param operator 
	 */
	public void writeArithmetic(String operator) {
		switch (operator) {
		case "+":
			write("add");
			break;
		case "-":
			write("sub");
			break;
		// This represent the neg.
		case "--":
			write("neg");
			break;
		case "=":
			write("eq");
			break;
		case ">":
			write("gt");
			break;
		case "<":
			write("lt");
			break;
		case "&":
			write("and");
			break;
		case "|":
			write("or");
			break;
		case "~":
			write("not");
			break;
		default:
			throw new RuntimeException("Unknown operator!");
		}
		done();
	}
	
	public void writeLable(String label) {
		write("label "+label);
		done();
	}
	
	public void writeGoto(String label) {
		write("goto "+label);
		done();
	}
	
	public void writeIf(String label) {
		write("if-goto "+label);
		done();
	}
	
	public void writeCall(String name, int argsN) {
		write("call "+name+" "+argsN);
		done();
	}
	
	public void writeFunction(String name, int argsN) {
		write("function "+name+" "+argsN);
		done();
	}
	
	public void writeReturn() {
		write("return");
		done();
	}

	/**
	 * Close the out put stream.
	 */
	public void close() {
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}