package compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Jack Token module can remove all comment and white space. Following Jack syntax principle, this module<br/>
 * can parse the input stream into Jack language token.
 * @author Thingcor
 * 
 */
public class JackTokenizer {
	/** the parsing file*/
	private File file;
	/** the using stream*/
	private Scanner scanner;
	/**
	 * the current line and this line only contain command.<br/>
	 */
	private String currentLine;
	/** 
	 * the queue contains all the token of the file.<br/>
	 * By ArrayDeque, we can add token in the head.
	 */
	private ArrayDeque<String> queue = new ArrayDeque<>();

	private String curToken;
	
	/**
	 * Builder symbol and keyword table. 
	 */
	public static final String[] symbolArr = {"{", "}", "(", ")", "[", "]", ".",
											  ",", ";", "+", "-", "*", "/", "&",
											  "|", "<", ">", "=", "~"};
	public static final String[] keyWordArr = {"class", "method", "int", "function", "boolean", 
												"constructor", "char", "void", "var", "static",
												"field", "let","do", "if", "else", "while", "return",
												"true", "false", "null", "this"};
	public static HashSet<String> symbolSet = new HashSet<>();
	public static HashSet<String> keyWordSet = new HashSet<>();
	static {
		for(String i:symbolArr) {
			symbolSet.add(i);
		}
		for(String i:keyWordArr) {
			keyWordSet.add(i);
		}
	}
	/** identifier regular expression.*/
	public static final String REEX_IDENTIFIER = "^[a-zA-Z_]{1}[a-zA-Z0-9_]*";
	
	/**
	 * Initialize this file and the stream. <br/>
	 * In this project, we use the jack file to test instead of the directory.
	 * @param file
	 */
	public JackTokenizer(File file) {
		this.file = file;
		try {
			this.scanner = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.characterTheFile();
	}

	public File getFile() {
		return file;
	}

	public Scanner getScanner() {
		return scanner;
	}
	
	public String getCurrentLine() {
		return currentLine;
	}
	
	public ArrayDeque<String> getQueue() {
		return queue;
	}

	public String getCurToken() {
		return curToken;
	}
	
	/** put back the current token in the head of the queue.*/
	public void putBack() {
		this.queue.addFirst(this.curToken);
	}
	
	/**********************************************************************************************
																								  *
																								  */
	/**
	 * When we get a line, in fact it's not the command, it contains comment line,space line,space, comment.
	 * So we process those character in this method. What we should do is following:<br/>
	 * 1. Remove the comment line. <br/>
	 *    If we get the comment line, throw it away and read again. Sometimes, there are some space in the
	 *    head of the comment line. But generally, the first two characters of the comment line is "//".<br/>
	 * 2. Remove the space line. <br/>
	 *    If we get the space line, throw it away and read again. Space line is easy to process. What we do is
	 *    compare the line with the "".<br/>
	 * 3. Throw the in-line comment away. <br/>
	 *    Find the comment' position in the command, then process it. We can find the comment by "//". Then
	 *    throw the strings behind the "//" away. This looks easy.<br/>
	 * 4. Throw API comment. <br/>
	 *    If we get the API comment line, throw is away and read again. In fact, if the first three character
	 *    is "/**" or the first character is "*" or the first two character is "*\/", we can throw the line.
	 * 5. Multiple line comment.<br/>
	 *    This is easy. And by add a condition we can complete this.<br/>
	 * @return <b>true</b> this method get a command <br/>
	 *         <b>false</b> don't get command 
	 * @throws IOException 
	 */
	private boolean getCommand(){
		String stringLine = this.currentLine;
		// remove the space line
		if(stringLine.equals("")) {
			return false;
		}
		// Remove the comment line
		String firstChar = stringLine.substring(0,1);
		String head = "";
		head = stringLine.length()>2 ? stringLine.substring(0, 2) : "";
		String threeChar = "";
		threeChar = stringLine.length()>=3 ? stringLine.substring(0,3) : "";
		if(head.equals("//") || firstChar.equals("*") || head.equals("*/") || threeChar.equals("/**")) {
			return false;
		}
		// Throw the in-line comment away
		if(stringLine.contains("//")) {
			String subStr = stringLine.substring(0, stringLine.indexOf("//"));
			this.currentLine = subStr.trim();
			return true;
		}
		this.currentLine = stringLine.trim();
		return true;
	}
	
	/**
	 * read a line and process it to a clear command.
	 * @return <b>true</b> if read a line. <br/>
	 *         <b>false</b> don't read a line.
	 */
	private boolean readCommand() {
		if (this.scanner.hasNext()) {
			this.currentLine = scanner.nextLine().trim();
			if(!getCommand()) {
				readCommand();
			}
			return true;
		}else {
			return false;
		}
	}

	/**
	 * character the current line and add them to queue.
	 * @throws InterruptedException 
	 */
	private void characterTheCurrentLine() throws InterruptedException {
		if (currentLine != null) {
			String line = currentLine;
			// Character the current line
			char[] cArr = line.toCharArray();
			/*
			 * This buffer is used to store the Strings which is stitched by the cArr.
			 * And by using the buffer we can split the strings by world.
			 */
			StringBuffer lineBuffer = new StringBuffer();
			for(char c:cArr) {
				// If the char is a operator, we add the space in the head 
				if(symbolSet.contains(c+"")) {
					lineBuffer.append(" "+c+" ");
				}else {
					lineBuffer.append(c);
				}
			}
			String[] strings = lineBuffer.toString().split(" ");
			
			// Set a label to process string constant.
			// When we first meet a """, we negate the label. when we second meet the """ ,we negate the label.
			int label = 0;
			StringBuffer strConstantBuffer = new StringBuffer();
			for(String i:strings) {
				if(i == " " || i.equals("")) {
					continue;
				}
				if((i.charAt(0)+"").equals("\"") || (i.charAt(i.length()-1)+"").equals("\"")) {
					label = ~label;
					if(label == -1) {
						strConstantBuffer.append(i+" ");
					}else {
						strConstantBuffer.append(i);
						this.queue.offer(strConstantBuffer.toString());
						strConstantBuffer.setLength(0);
						continue;
					}
					continue;
				}
				if(label == -1) {
					strConstantBuffer.append(i+" ");
					continue;
				}
				this.queue.offer(i);
			}
		}
	}
	
	/**
	 * character this file. And save the character in the queue.<br/>
	 * In this view, one file corresponds one queue. Like this situation, in this project, we can<br/>
	 * say this object is file-level object.
	 */
	private void characterTheFile() {
		// read a line and character this line.
		while(readCommand()) {
			try {
				characterTheCurrentLine();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * As so far, what we do is inner operation, those operation needn't to be known by other user.
	 **********************************************************************************************/
	
	/**
	 * Because we have transformed the program file to characters, we can judge if there are more <br/>
	 * tokens by queue is empty or not.
	 * @return 
	 */
	public boolean hasMoreToken() {
		if(!this.queue.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Read a token.
	 */
	public void advance() {
		if(hasMoreToken()) {
			this.curToken = this.queue.poll();
		}
	}
	
	public TokenType tokenType() {
		if(keyWordSet.contains(this.curToken)) {
			return TokenType.KEYWORD;
		}
		if(symbolSet.contains(this.curToken)) {
			return TokenType.SYMBOL;
		}
		if(this.curToken.matches(REEX_IDENTIFIER)) {
			return TokenType.IDENTIFIER;
		}
		if(this.curToken.matches("^[0-9]+")) {
			return TokenType.INT_CONST;
		}
		// Because this condition is complex, so we put it in the end.
		String head = this.curToken.charAt(0)+"";
		String end = this.curToken.charAt(this.curToken.length()-1)+"";
		if(head.equals("\"") && end.equals("\"")) {
			return TokenType.STRING_CONST;
		}
		return null;
	}
	
	/**
	 * test method
	 * @return
	 */
	public void test() {
		int i = queue.size();
		while(i>=0) {
			System.out.println(queue.poll());
			i--;
		}
	}
	
	public String keyword() {
		return this.curToken;
	}
	
	public String symbol() {
		return this.curToken;
	}
	
	public String identifier() {
		return this.curToken;
	}
	
	public int intVal() {
		return Integer.parseInt(this.curToken);
	}
	
	public String stringVal() {
		return this.curToken;
	}
}
