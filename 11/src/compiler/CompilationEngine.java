package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CompilationEngine {
	/***************************************************/
	private File inputFile;
	private File outputFile;
	private JackTokenizer jackTokenizer;
	/** The symbol table.*/
	private SymbolTable sTable;
	private VMWriter vmWriter;
	private int expressionNum;
	private String className;
	/** Used to distinguish the label.*/
	private int index;
	
	public CompilationEngine(File inputFile) throws FileNotFoundException {
		this.inputFile = inputFile;
		String path = inputFile.getParent();
		String prefixFileName = inputFile.getName().split("\\.")[0];
		this.outputFile = new File(path+"\\"+prefixFileName+".vm");
		if(outputFile.exists()) {
			outputFile.delete();
		}
		this.jackTokenizer = new JackTokenizer(inputFile);
		this.sTable = new SymbolTable();
		this.vmWriter = new VMWriter(outputFile);
		this.index = 0;
	}

	public File getInputFile() {
		return inputFile;
	}

	public JackTokenizer getJackTokenizer() {
		return jackTokenizer;
	}

	/**
	 * Judge whether or not the curToken is a term 
	 * @param curToken the token need to judge
	 * @return
	 */
	private boolean isTerm(String curToken) {
		if( curToken.matches("^[0-9]+") ||
			    (curToken.substring(0, 1).equals("\"") && curToken.substring(0, 1).equals("\""))||
			    curToken.equals("true") || curToken.equals("false") ||
			    curToken.equals("null") || curToken.equals("this") ||
			    curToken.matches(JackTokenizer.REEX_IDENTIFIER) ||
			    curToken.equals("(") || curToken.equals("-") ||
			    curToken.equals("~")) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Judge whether or not the curToken is the beginning of a statement
	 * @param curToken
	 * @return
	 */
	private boolean isStatement(String curToken) {
		if(curToken.equals("let")  ||
		   curToken.equals("if") ||
		   curToken.equals("else") ||
		   curToken.equals("while") ||
		   curToken.equals("do") ||
		   curToken.equals("return")) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Transform the kind of the variable from the symbol table to the segment name of the virtual memory.
	 * @param kind
	 * @return none when the kind is unknown.
	 */
	public String transKind(String kind) {
		switch (kind) {
		case "arg":
			return "argument";
		case "var":
			return "local";
		case "static":
			return "static";
		case "field":
			return "this";
		default:
			return "none";
		}
	}
	
	/***************************************************************************************************/
	
	/**
	 * Token series: class className { content }.
	 */
	public void compileClass() {
		// Read a token "class"
		this.jackTokenizer.advance();
		String curToken = this.jackTokenizer.keyword();
		if(!curToken.equals("class")) {
			throw new RuntimeException("Syntax error on token \"class\"");
		}
		
		// Read next token className
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+" unexpected token!");
		}else {
			this.className = this.jackTokenizer.getCurToken();
		}
		
		
		// Read next token "{"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.symbol().equals("{")) {
			throw new RuntimeException("Syntax error on class declare, { expected at the end");
		}
		
		/*==========================================================================================*/
		/* The content parsing start*/
		this.jackTokenizer.advance();
		while(!this.jackTokenizer.getCurToken().equals("}")) {
			if(this.jackTokenizer.getCurToken().equals("static") ||
			   this.jackTokenizer.getCurToken().equals("field")) {
				compileClassVarDec();
			}else if (this.jackTokenizer.getCurToken().equals("constructor") ||
					  this.jackTokenizer.getCurToken().equals("function") ||
					  this.jackTokenizer.getCurToken().equals("method")) {
				compileSubroutine();
			}else {
				throw new RuntimeException("Unknown class declare!");
			}
			this.jackTokenizer.advance();
		}
		/*==========================================================================================*/
		
		// Read end token "}"
		if(!this.jackTokenizer.symbol().equals("}")) {
			throw new RuntimeException("Syntax error on the file end, } expected at the end");
		}
	}
	
	/**
	 * Compile the static variable or field. <br/>
	 * Static variable series: static type varName (,varName)*<br/>
	 * Field series: field typevarName (,varName)*<br/>
	 * This method should be called by compileClass method. So in this method, we just need to distinguish
	 * the current token.<br/>
	 */
	public void compileClassVarDec() {
		// First of all, create classVarDec tag and let curRoot point to this tag
		
		// The following variable used to construct the symbol table.
		String name = "";
		String kind = "";
		String type = "";
		
		// Judge current token
		if(this.jackTokenizer.keyword().equals("static")) {
			kind = "static";
		}else {
			kind = "field";
		}
		
		// Read next token type
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.keyword().matches(JackTokenizer.REEX_IDENTIFIER)) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.keyword()+" wrong data type");
		}
		type = this.jackTokenizer.getCurToken();
		
		// Read next token varName
		this.jackTokenizer.advance();
		name = this.jackTokenizer.getCurToken();
		this.sTable.define(name, type, kind);
		
		// Read remaining token
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(",")) {
			do{
				this.jackTokenizer.advance();
				name = this.jackTokenizer.getCurToken();
				this.sTable.define(name, type, kind);
				this.jackTokenizer.advance();
			}while(!this.jackTokenizer.getCurToken().equals(";"));
		}else if(this.jackTokenizer.symbol().equals(";")) {
			
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+" unexpected token!");
		}
		
	}
	
	/**
	 * When the compileClass method encounter a method or a function or a constructor, it will call this method.<br/>
	 * Series: constructor|function|method void|otherType subroutineName ( parameterList ) subrountineBody
	 */
	public void compileSubroutine() {
		boolean isConstructor = this.jackTokenizer.getCurToken().equals("constructor") ? true : false;
		boolean isMethod = this.jackTokenizer.getCurToken().equals("method") ? true : false;
		
		try {
			// Write the class name.
			this.vmWriter.getWriter().write("function "+className+".");
			this.vmWriter.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// compile second token: void|otherType
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+",void or other data type expected.");
		}
		
		// compile next token: subrountineName
		this.jackTokenizer.advance();
		if (this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			try {
				// Write function name.
				this.vmWriter.getWriter().write(this.jackTokenizer.getCurToken());
				this.vmWriter.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", identifier expected.");
		}
		
		// Compile forth token: (
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.symbol().equals("(")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", ( expected.");
		}
		
		// Compile fifth token: parameterList|null
		this.jackTokenizer.advance();
		int label = 0;
		if(this.jackTokenizer.getCurToken().equals(")")) {
			// Tell the behind statement the right bracket has been compiled.
			label = 1;
		}else if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			// If the is a method, the first argument is "this".
			if(isMethod) {
				this.sTable.define("this", className, "arg");
			}
			compileParameterList();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected.");
		}
		// compile sixth token: )
		if(label == 0) {
			this.jackTokenizer.advance();
		}
		
		// compile next token: subroutineBody
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals("{")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected.");
		}else {
			// Read remaining body
			this.jackTokenizer.advance();
			// Variable declare
			if(this.jackTokenizer.getCurToken().equals("var")) {
				while(this.jackTokenizer.getCurToken().equals("var")) {
					compileVarDec();
					this.jackTokenizer.advance();
				}
			
				// Write the local variable number.
				try {
					this.vmWriter.getWriter().write(" "+this.sTable.varCount("var")+"\n");
					this.vmWriter.getWriter().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			// No local variables.
			}else {
				try {
					this.vmWriter.getWriter().write(" 0\n");
					this.vmWriter.getWriter().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// Allocate memory code. Only for constructor.
			if(isConstructor) {
				this.vmWriter.writePush("constant", this.sTable.varCount("field"));
				this.vmWriter.writeCall("Memory.alloc", 1);
				this.vmWriter.writePop("pointer", 0);
			}
			
			// Get the current object code.
			if(isMethod) {
				this.vmWriter.writePush("argument", 0);
				this.vmWriter.writePop("pointer", 0);
			}
			
			// Statements
			while(!this.jackTokenizer.getQueue().peek().equals("}")) {
				compileStatements();
			}
		
			// When get the "}"
			this.jackTokenizer.advance();
		}
		
		this.sTable.clear();
	}
	
	/**
	 * This method is called by compileSubrountine. And only when the compiled subroutine has parameter list, this
	 * method can be called.<br/>
	 * Series: type varName (, type varName)*
	 */
	public void compileParameterList() {
		// The following variable is used to construct symbol table.
		String name = null;
		String type = null;
		String kind = "arg";
		
		// Append the token firstly.
		type = this.jackTokenizer.getCurToken();
		
		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			name = this.jackTokenizer.getCurToken();
			this.sTable.define(name, type, kind);
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// Read other token,until ")"
		this.jackTokenizer.advance();
		while(!this.jackTokenizer.getCurToken().equals(")")) {
			// The next expected token is ",".
			if(!this.jackTokenizer.getCurToken().equals(",")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", \",\" expected");
			}
			this.jackTokenizer.advance();
			
			// The next expected token is identifier.
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				type = this.jackTokenizer.getCurToken();
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.jackTokenizer.advance();
			
			// The next expected token is identifier.
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				name = this.jackTokenizer.getCurToken();
				this.sTable.define(name, type, kind);
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.jackTokenizer.advance();
		}
		
		// Add the ")" in the front of the queue.
		this.jackTokenizer.putBack();
	}
	
	/**
	 * The following series(or ruler) decide var only declare the same type at the same time. When other method
	 * call this method means that other method has get the "var" token.<br/>
	 * Series: var type varName (, varName)* ;
	 */
	public void compileVarDec() {
		// The following variables used to construct symbol table.
		String name = null;
		String type = null;
		String kind = "var";

		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			type = this.jackTokenizer.getCurToken();
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is identifier.
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			name = this.jackTokenizer.getCurToken();
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.sTable.define(name, type, kind);
		
		// Read other token until ";"
		this.jackTokenizer.advance();
		while(!this.jackTokenizer.getCurToken().equals(";")){
			// The next expected token is ",".
			if(!this.jackTokenizer.getCurToken().equals(",")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected. \",\" expeced.");
			}
			this.jackTokenizer.advance();
			
			// The next expected token is identifier.
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				name = this.jackTokenizer.getCurToken();
				this.sTable.define(name, type, kind);
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.jackTokenizer.advance();
		}
		
		// Last token is ";", do nothing.
	}
	
	/**
	 * When other method call the method means that program need to deal with statements next...
	 */
	public void compileStatements() {
		while(!this.jackTokenizer.getCurToken().equals("}")) {
			switch (this.jackTokenizer.getCurToken()) {
			case "var": compileVarDec(); break;
			case "let": compileLet(); break;
			case "do": compileDo(); break;
			case "if": compileIf(); break;
			case "while": compileWhile(); break;
			case "return": compileReturn(); break;
			default:
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected.");
			}
			this.jackTokenizer.advance();
		}
		this.jackTokenizer.putBack();
	}

	/**
	 * Series: do subroutineName ( expressionList ) ;
	 * Series: do className . subroutineName ( expressionList ) ;
	 * Series: do varName . subroutineName ( expressionList ) ;
	 */
	public void compileDo() {
		// Use this string to store the method name called.
		String callName = null;
		String objectName = null;
		
		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			callName = this.jackTokenizer.getCurToken();
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// Judge if the the call name is a class name.
		boolean isClassCall = true;
		String firstChar = callName.charAt(0)+"";
		if(!firstChar.matches("[A-Z]") && this.jackTokenizer.getCurToken().equals(".")) {
			objectName = callName;
			callName = this.sTable.typeOf(callName);
			isClassCall = false;
			this.expressionNum++;
		// Means this is method.
		}else if (!firstChar.matches("[A-Z]") && !this.jackTokenizer.getCurToken().equals(".")) {
			// Push the current object into stack.
			this.vmWriter.writePush("pointer", 0);
			// Let parameter number plus.
			this.expressionNum++;
			// Let the call name add the class name.
			callName = this.className+"."+callName;
			
		// Means this is class call.
		}else {}
		
		// Maybe the next expected token is "." or "(".
		if(this.jackTokenizer.getCurToken().equals(".")) {
			// Append the dot.
			callName += this.jackTokenizer.getCurToken();
			
			// The next expected token is identifier.
			this.jackTokenizer.advance();
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				// Append the subroutine name.
				callName += this.jackTokenizer.getCurToken();
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
		}else if(this.jackTokenizer.getCurToken().equals("(")){
			this.jackTokenizer.putBack();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is "(".
		if(!this.jackTokenizer.getCurToken().equals("(")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}

		if(!isClassCall) {
			String segmenName = this.transKind(this.sTable.kindOf(objectName));
			this.vmWriter.writePush(segmenName, this.sTable.indexOf(objectName));
		}
		
		// Expression list maybe null
		if(this.isTerm(this.jackTokenizer.getQueue().peek())) {
			compileExpressionList();
		}
		
		// The next expected token is ")".
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals(")")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected, ) expected.");
		}
		
		// The next expected token is ";".
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals(";")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected, ) expected.");
		}
		
		// Push the object to stack.

		
		this.vmWriter.writeCall(callName, this.expressionNum);
		this.expressionNum = 0; // clear this variable.
		this.vmWriter.writePop("temp", 0);
	}
	
	/**
	 * Series: let varName([expression])? = expression ;
	 */
	public void compileLet() {
		// Used to store the variable name.
		String varName = null;
		boolean isArr = false;
		
		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			varName = this.jackTokenizer.getCurToken();
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is "[" or "=".
		int startBrackets = 0;
		if(this.jackTokenizer.getCurToken().equals("[")) {
			isArr = true;
			startBrackets = ~startBrackets;
		}else if (this.jackTokenizer.getCurToken().equals("=")) {

		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is term.
		String curToken = this.jackTokenizer.getCurToken();
		if(this.isTerm(curToken)) {
			compileExpression();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		if(isArr) {
			// Write array name.
			String segmentName = this.transKind(this.sTable.kindOf(varName));
			this.vmWriter.writePush(segmentName, this.sTable.indexOf(varName));
			// Compute address.
			this.vmWriter.writeArithmetic("+");
		}
		// The right brackets
		if(startBrackets == -1) {
			// next expected token is right brackets
			this.jackTokenizer.advance();
			startBrackets = ~startBrackets;
			
			// next token "="?
			this.jackTokenizer.advance();
			if(!this.jackTokenizer.getCurToken().equals("=")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			
			// next expected tokens is expression
			this.jackTokenizer.advance();
			if(this.isTerm(this.jackTokenizer.getCurToken())){
				compileExpression();
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			
			// next expected token is ";"
			if(!this.jackTokenizer.getQueue().peek().equals(";")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.vmWriter.writePop("temp", 0);
		}
		
		// The end token is ";"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals(";")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected"); 
		}
		
		String segmentName = this.transKind(this.sTable.kindOf(varName));
		if(isArr) {
			this.vmWriter.writePop("pointer", 1);
			this.vmWriter.writePush("temp", 0);
			this.vmWriter.writePop("that", 0);
		}else {
			this.vmWriter.writePop(segmentName, this.sTable.indexOf(varName));
		}
	}
	
	public void compileWhile() {
		// Memory the index of the start label.
		// Which is corresponding to the end label.
		int originIndex = this.index;
		this.vmWriter.writeLable("WHILE_EXP"+index);
		
		// next token "("
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals("(")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next tokens expression
		this.jackTokenizer.advance();
		if(this.isTerm(this.jackTokenizer.getCurToken())) {
			compileExpression();
		}else if (this.jackTokenizer.getCurToken().equals(")")) {
			this.jackTokenizer.putBack();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token  ")"
		this.jackTokenizer.advance();
		if (!this.jackTokenizer.getCurToken().equals(")")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// This code segment is to judge the condition of the loop.
		this.vmWriter.writeArithmetic("~");
		this.vmWriter.writeIf("WHILE_END"+originIndex);
		
		
		// next token "{"
		this.jackTokenizer.advance();
		if (!this.jackTokenizer.getCurToken().equals("{")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token statements
		this.jackTokenizer.advance();
		if (this.isStatement(this.jackTokenizer.getCurToken())) {
			compileStatements();
		}else if (this.jackTokenizer.getCurToken().equals("}")) {
			this.jackTokenizer.putBack();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token "}"
		this.jackTokenizer.advance();
		if (!this.jackTokenizer.getCurToken().equals("}")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.vmWriter.writeGoto("WHILE_EXP"+originIndex);
		this.vmWriter.writeLable("WHILE_END"+originIndex);
	}
	
	public void compileReturn() {
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(";")) {
			this.jackTokenizer.putBack();
			this.vmWriter.writePush("constant", 0);
		}else if(this.isTerm(this.jackTokenizer.getCurToken())) {
			compileExpression();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token ";"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals(";")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		this.vmWriter.writeReturn();
	}
	
	public void compileIf() {
		// next token "("
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals("(")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token expression
		this.jackTokenizer.advance();
		if(this.isTerm(this.jackTokenizer.getCurToken())) {
			compileExpression();
		}else if(this.jackTokenizer.getCurToken().equals(")")) {
			this.jackTokenizer.putBack();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token ")"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals(")")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// Write if code.
		int originIfIndex = ++index;
		this.vmWriter.writeIf("IF_TRUE"+originIfIndex);
		this.vmWriter.writeGoto("IF_FALSE"+originIfIndex);
		this.vmWriter.writeLable("IF_TRUE"+originIfIndex);
		
		// next token "{"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals("{")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next tokens statements
		this.jackTokenizer.advance();
		if(this.isStatement(this.jackTokenizer.getCurToken())) {
			compileStatements();
		}else if (this.jackTokenizer.getCurToken().equals("}")) {
			this.jackTokenizer.putBack();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token "}"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals("}")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// Write label code.
		if(this.jackTokenizer.getQueue().peek().equals("else")) {
			// Only the next is else, we need to set this label.
			this.vmWriter.writeGoto("IF_END"+originIfIndex);
		}
		this.vmWriter.writeLable("IF_FALSE"+originIfIndex);
		
		// next token maybe else or others
		if(this.jackTokenizer.getQueue().peek().equals("else")) {
			// Append the token
			this.jackTokenizer.advance();
			
			// next token "{"
			this.jackTokenizer.advance();
			if(!this.jackTokenizer.getCurToken().equals("{")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			
			// next tokens "statements"
			this.jackTokenizer.advance();
			if(this.isStatement(this.jackTokenizer.getCurToken())) {
				compileStatements();
			}else if(this.jackTokenizer.getCurToken().equals("}")) {
				this.jackTokenizer.putBack();
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			
			// next token "}"
			this.jackTokenizer.advance();
			if(!this.jackTokenizer.getCurToken().equals("}")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			
			// Write the if statement exit gate.
			this.vmWriter.writeLable("IF_END"+originIfIndex);
		}
		
	}
	
	/**
	 * The first token of the expression is term. When other method call this method, a term has been advanced.<br/>
	 * Series: term (operator term)*
	 */
	public void compileExpression() {
		// Compile current token
		compileTerm();
		
		// The next expected token is operator.
		// If the next token is "]" or ";" or ")" or ",", end this compile. 
		this.jackTokenizer.advance();
		Boolean endFlag = this.jackTokenizer.getCurToken().equals("]") ||
				 		  this.jackTokenizer.getCurToken().equals(")") ||
				 		  this.jackTokenizer.getCurToken().equals(";") ||
				 		  this.jackTokenizer.getCurToken().equals(",");
		while (!endFlag) {
			// Operator
			String op = null;
			if(this.jackTokenizer.getCurToken().matches("\\+|-|\\*|/|\\&|\\||<|=|>")) {
				// Memory the op.
				op = this.jackTokenizer.getCurToken();
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			// Term
			this.jackTokenizer.advance();
			String curToken = this.jackTokenizer.getCurToken();
			if(this.isTerm(curToken))  {
				compileTerm();
			}
			
			if(op.equals("*")){
				this.vmWriter.writeCall("Math.multiply", 2);
			}else if(op.equals("/")) {
				this.vmWriter.writeCall("Math.divide", 2);
			}else {
				this.vmWriter.writeArithmetic(op);
			}
			
			// Judge if end.
			this.jackTokenizer.advance();
			endFlag = this.jackTokenizer.getCurToken().equals("]") ||
			 		  this.jackTokenizer.getCurToken().equals(")") ||
			 		  this.jackTokenizer.getCurToken().equals(";") ||
			 		  this.jackTokenizer.getCurToken().equals(",");
		}
		this.jackTokenizer.putBack();
	}
	
	/**
	 * Series: integerConstant|stringConstant|keywordConstant|varName|varName [ expression ]|subroutineCall|
	 * ( expression )|unaryOp term
	 */
	public void compileTerm() {
		// Integer constant
		if(this.jackTokenizer.tokenType().equals(TokenType.INT_CONST)) {
			int num = Integer.parseInt(this.jackTokenizer.getCurToken());
			if(num>=0 && num<32767) {
				this.vmWriter.writePush("constant", num);
			}else {
				throw new RuntimeException("Integer over than max Integer: "+this.jackTokenizer.getCurToken());
			}
		// String constant
		}else if (this.jackTokenizer.tokenType().equals(TokenType.STRING_CONST)) {
			// throw the beginning symbol " and the end symbol "
			String stripString = this.jackTokenizer.getCurToken().substring(1, this.jackTokenizer.getCurToken().length()-1);
			// Character it.
			char[] charString = stripString.toCharArray();
			// Apply memory.
			this.vmWriter.writePush("constant", charString.length);
			this.vmWriter.writeCall("String.new", 1);
			// Write char.
			for(int i = 0;i<charString.length;i++) {
				int asci = charString[i];
				this.vmWriter.writePush("constant", asci);
				this.vmWriter.writeCall("String.appendChar", 2);
			}
		// Key word constant
		}else if (this.jackTokenizer.getCurToken().equals("true") ||
				  this.jackTokenizer.getCurToken().equals("false") ||
				  this.jackTokenizer.getCurToken().equals("null") ||
				  this.jackTokenizer.getCurToken().equals("this")) {
			String curToken = this.jackTokenizer.getCurToken();
			switch (curToken) {
			case "true":
				this.vmWriter.writePush("constant", 0);
				this.vmWriter.writeArithmetic("~");
				break;
			case "false":
				this.vmWriter.writePush("constant", 0);
				break;
			case "this":
				this.vmWriter.writePush("pointer", 0);
				break;
			case "null":
				this.vmWriter.writePush("constant", 0);
				break;
			default:
				break;
			}
		// varName...
		}else if (this.jackTokenizer.tokenType().equals(TokenType.IDENTIFIER)) {
			// Used to memory the subroutine name.
			String subName = this.jackTokenizer.getCurToken();
			
			// Check the head of the queue.
			String head = this.jackTokenizer.getQueue().peek();
			// mean the structure is varName [expression]
			if(head.equals("[")) {
				// Get "[".
				this.jackTokenizer.advance();
				// Next token.
				this.jackTokenizer.advance();
				if(this.isTerm(this.jackTokenizer.getCurToken())) {
					compileExpression();
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				// Write array name.
				String segmentName = this.transKind(this.sTable.kindOf(subName));
				this.vmWriter.writePush(segmentName, this.sTable.indexOf(subName));
				// append "]"
				this.jackTokenizer.advance();
				if(!this.jackTokenizer.getCurToken().equals("]")) {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				//Compute address.
				this.vmWriter.writeArithmetic("+");
				this.vmWriter.writePop("pointer", 1);
				this.vmWriter.writePush("that", 0);
			// means the structure is subroutineName ( expressionList )
			}else if(head.equals("(")) {
				// Get the "(".
				this.jackTokenizer.advance();
				if(this.jackTokenizer.getQueue().peek().matches(JackTokenizer.REEX_IDENTIFIER)) {
					compileExpressionList();
				}else if(this.jackTokenizer.getQueue().peek().equals(")")) {
					
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				
				// get next token
				this.jackTokenizer.advance();
				if(!this.jackTokenizer.getCurToken().equals(")")) {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				
				this.vmWriter.writeCall(subName, this.expressionNum);
				this.expressionNum=0;  // Clear it.
			// means the structure is className|varName . subroutineName ( expressionList )
			}else if(head.equals(".")) {
				boolean isClassCall = true;
				String objectName = null;
				if(subName.matches("[a-z]+")) {
					isClassCall = false;
					objectName = subName;
					subName = this.sTable.typeOf(subName);
				}
				
				if(!isClassCall) {
					String segmentName = this.transKind(this.sTable.kindOf(objectName));
					this.vmWriter.writePush(segmentName, this.sTable.indexOf(objectName));
					this.expressionNum++;
				}
				this.jackTokenizer.advance();
				// Append subroutine name.
				subName += this.jackTokenizer.getCurToken();
				
				// next token
				this.jackTokenizer.advance();
				if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
					// Append subroutine name.
					subName += this.jackTokenizer.getCurToken();
					
					// next token
					this.jackTokenizer.advance();
					if(this.jackTokenizer.getCurToken().equals("(")) {
						// Judge is next token is belong to expressionList.
						// The beginning of the expressionList is a expression and the beginning of the expression is a term
						if(this.isTerm(this.jackTokenizer.getQueue().peek())) {
							compileExpressionList();
						}else if(this.jackTokenizer.getQueue().peek().equals(")")) {

						}else{
							throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
						}
						
						// next token
						this.jackTokenizer.advance();
						if(!this.jackTokenizer.getCurToken().equals(")")) {
							throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
						}
					}else {
						throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
					}
					
					// Write the call code.
					this.vmWriter.writeCall(subName, this.expressionNum);
					this.expressionNum = 0; // clear this variable.
				}else {
					throw new RuntimeException("subroutine call error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
			// means the next token is a symbol, we just append the current identifier
			}else if(head.matches("\\+|-|\\*|/|\\&|\\||<|=|>")) {
				if(this.jackTokenizer.getCurToken().matches("[0-9]+")) {
					this.vmWriter.writePush("constant", Integer.parseInt(this.jackTokenizer.getCurToken()));
				}else {
					String segmentName = this.transKind(this.sTable.kindOf(this.jackTokenizer.getCurToken()));
					this.vmWriter.writePush(segmentName, this.sTable.indexOf(this.jackTokenizer.getCurToken()));
				}
			// Means this term meet the end.
			}else if(head.equals(")") || head.equals("]") || head.equals(";") || head.equals(",")) {
				String segmentName = this.transKind(this.sTable.kindOf(this.jackTokenizer.getCurToken()));
				this.vmWriter.writePush(segmentName, this.sTable.indexOf(this.jackTokenizer.getCurToken()));
			}else {
				throw new RuntimeException("subroutine call error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
		// means the structure is ( expression )
		}else if(this.jackTokenizer.getCurToken().equals("(")) {
			// next token
			this.jackTokenizer.advance();
			String curToken = this.jackTokenizer.getCurToken();
			if(this.isTerm(curToken)) {
				compileExpression();
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
			// next token
			this.jackTokenizer.advance();
			if(!this.jackTokenizer.getCurToken().equals(")")) {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
		// Means this structure is "unaryOp term"
		}else if (this.jackTokenizer.getCurToken().matches("\\-|~")) {
			String op = this.jackTokenizer.getCurToken().equals("-") ? "--":"~";
			
			// Means that the next is an expression.
			if(this.jackTokenizer.getQueue().peek().equals("(")) {
				this.jackTokenizer.advance();
				compileExpression();
			// Means that the next is a term.
			}else if(this.jackTokenizer.getQueue().peek().matches(JackTokenizer.REEX_IDENTIFIER)||
					 this.jackTokenizer.getQueue().peek().matches("[0-9]+")) {
				this.jackTokenizer.advance();
				compileTerm();
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
			
			// Write code.
			this.vmWriter.writeArithmetic(op);
		}else{
			throw new RuntimeException("Term token compile error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
		}
		
	}
	
	/**
	 * Series: (expression ( , expression)*)?
	 */
	public void compileExpressionList() {
		// Next token
		this.jackTokenizer.advance();
		if(this.isTerm(this.jackTokenizer.getCurToken())) {
			compileExpression();
		}
		// Count the number of local variable number.
		this.expressionNum++;
		
		// next token maybe "," or ")"
		if(this.jackTokenizer.getQueue().peek().equals(",")) {
			String curToken = this.jackTokenizer.getQueue().peek();
			while(!curToken.equals(")")) {
				if(curToken.equals(",")) {
					this.jackTokenizer.advance();
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				
				this.jackTokenizer.advance();
				if(this.isTerm(this.jackTokenizer.getCurToken())) {
					// Count the number of local variable number.
					this.expressionNum++;
					compileExpression();
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				curToken = this.jackTokenizer.getQueue().peek();
			}
		}else if (this.jackTokenizer.getQueue().peek().equals(")")) {
			
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
		}
	}
}
