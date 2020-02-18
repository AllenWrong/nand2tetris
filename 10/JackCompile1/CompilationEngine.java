package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CompilationEngine {
	/** The target file. */
	private File inputFile;
	/** The output XML file*/
	private File outputFile;
	private JackTokenizer jackTokenizer;
	/** DOM object*/
	private Document document;
	/** Root element*/
	private Element root;
	/** the current extended element*/
	private Element curRoot;
	
	private BufferedWriter bufferedWriter;
	
	public CompilationEngine(File inputFile, File outputFile) throws FileNotFoundException {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.document = XmlUtils.getDocument();
		this.root = this.document.createElement("class");
		// At the beginning, let curRoot equals root.
		this.curRoot = this.root;
		this.jackTokenizer = new JackTokenizer(inputFile);
		
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
	}

	public File getInputFile() {
		return inputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}
	
	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}
	
	public JackTokenizer getJackTokenizer() {
		return jackTokenizer;
	}
	
	public Document getDocument() {
		return document;
	}

	public Element getRoot() {
		return root;
	}
	
	public Element getCurRoot() {
		return curRoot;
	}

	/**
	 * Create element and append it to root.
	 * @param object the element tag.
	 * @param textContent the element text content.
	 */
	private void createAndAppendElement(Object object, String textContent) {
		Element element = null;
		if(object instanceof KeyWord) {
			element = this.document.createElement(((KeyWord)object).toString().toLowerCase());
		}else if(object instanceof TokenType) {
			element = this.document.createElement(object.toString().toLowerCase());
		}else {
			element = this.document.createElement(object.toString());
		}
		element.setTextContent(" "+textContent+" ");
		this.curRoot.appendChild(element);
		XmlUtils.docSave(this.outputFile, this.root);
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
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.identifier());
		
		// Read next token className
		this.jackTokenizer.advance();
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.identifier());
		
		// Read next token "{"
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.symbol().equals("{")) {
			throw new RuntimeException("Syntax error on class declare, { expected at the end");
		}
		createAndAppendElement(this.jackTokenizer.tokenType(), "{");
		
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
		// Append it on the root
		Element endElement = this.document.createElement("symbol");
		endElement.setTextContent(" } ");
		this.root.appendChild(endElement);
		XmlUtils.docSave(this.outputFile, this.root);
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
		Element classVarDec = this.document.createElement("classVarDec");
		this.root.appendChild(classVarDec);
		this.curRoot = classVarDec;
		
		// Judge current token
		if(this.jackTokenizer.keyword().equals("static")) {
			// Append a element to root
			createAndAppendElement(this.jackTokenizer.tokenType(), "static");
		}else {
			createAndAppendElement(this.jackTokenizer.tokenType(), "field");
		}
		
		// Read next token type
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.keyword().matches(JackTokenizer.REEX_IDENTIFIER)) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.keyword()+" wrong data type");
		}
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.keyword());
		
		// Read next token varName
		this.jackTokenizer.advance();
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.identifier());
		
		// Read remaining token
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(",")) {
			do{
				createAndAppendElement(this.jackTokenizer.tokenType(), ",");
				this.jackTokenizer.advance();
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.identifier());
				this.jackTokenizer.advance();
			}while(!this.jackTokenizer.getCurToken().equals(";"));
			createAndAppendElement(this.jackTokenizer.tokenType(), ";");
		}else if(this.jackTokenizer.symbol().equals(";")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), ";");
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+" unexpected token!");
		}
		
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	/**
	 * When the compileClass method encounter a method or a function or a constructor, it will call this method.<br/>
	 * Series: constructor|function|method void|otherType subroutineName ( parameterList ) subrountineBody
	 */
	public void compileSubroutine() {
		// First of all, create subroutineDec tag and let curRoot point to this tag
		Element subrourineDec = this.document.createElement("subroutineDec");
		this.root.appendChild(subrourineDec);
		this.curRoot = subrourineDec;
		
		// compile first token: constructor|function|method
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.keyword());
		
		// compile second token: void|otherType
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+",void or other data type expected.");
		}
		
		// compile next token: subrountineName
		this.jackTokenizer.advance();
		if (this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", identifier expected.");
		}
		
		// compile forth token: (
		this.jackTokenizer.advance();
		if(this.jackTokenizer.symbol().equals("(")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), "(");
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", ( expected.");
		}
		
		// compile fifth token: parameterList|null
		this.jackTokenizer.advance();
		int label = 0;
		if(this.jackTokenizer.getCurToken().equals(")")) {
			// Create empty element and append it.
			createAndAppendElement("parameterList", "");
			createAndAppendElement(this.jackTokenizer.tokenType(), ")");
			// Tell the after statement this has been appended.
			label = 1;
		}else if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			compileParameterList();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected.");
		}
		// compile sixth token: )
		if(label == 0) {
			this.jackTokenizer.advance();
			createAndAppendElement(this.jackTokenizer.tokenType(), ")");
		}
		
		// compile next token: subroutineBody
		this.jackTokenizer.advance();
		if(!this.jackTokenizer.getCurToken().equals("{")) {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected.");
		}else {
			Element subroutineBody = this.document.createElement("subroutineBody");
			this.curRoot.appendChild(subroutineBody);
			this.curRoot = subroutineBody;
			createAndAppendElement(this.jackTokenizer.tokenType(), "{");
			
			// Read remaining body
			this.jackTokenizer.advance();
			// Variable declare
			if(this.jackTokenizer.getCurToken().equals("var")) {
				while(this.jackTokenizer.getCurToken().equals("var")) {
					compileVarDec();
					this.curRoot = (Element) this.curRoot.getParentNode();
					this.jackTokenizer.advance();
				}
			}
			
			// Statements
			while(!this.jackTokenizer.getQueue().peek().equals("}")) {
				compileStatements();
			}
		
			// When get the "}"
			this.jackTokenizer.advance();
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}
	}
	
	/**
	 * This method is called by compileSubrountine. And only when the compiled subroutine has parameter list, this
	 * method can be called.<br/>
	 * Series: type varName (, type varName)*
	 */
	public void compileParameterList() {
		// First of all, create parameterList tag and let curRoot point to this tag
		Element parameterList = this.document.createElement("parameterList");
		this.curRoot.appendChild(parameterList);
		this.curRoot = parameterList;
		
		// Append the token firstly.
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		
		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());	
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// Read other token,until ")"
		this.jackTokenizer.advance();
		while(!this.jackTokenizer.getCurToken().equals(")")) {
			// The next expected token is ",".
			if(this.jackTokenizer.getCurToken().equals(",")) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", \",\" expected");
			}
			this.jackTokenizer.advance();
			
			// The next expected token is identifier.
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.jackTokenizer.advance();
			
			// The next expected token is identifier.
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.jackTokenizer.advance();
		}
		
		// Add the ")" in the front of the queue.
		this.jackTokenizer.putBack();
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	/**
	 * The following series(or ruler) decide var only declare the same type at the same time. When other method
	 * call this method means that other method has get the "var" token.<br/>
	 * Series: var type varName (, varName)* ;
	 */
	public void compileVarDec() {
		// Create varDec tag and let curRoot point to it
		Element varDec = this.document.createElement("varDec");
		this.curRoot.appendChild(varDec);
		this.curRoot = varDec;
		
		// Append the current token firstly
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		
		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is identifier.
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// Read other token until ";"
		this.jackTokenizer.advance();
		while(!this.jackTokenizer.getCurToken().equals(";")){
			// The next expected token is ",".
			if(this.jackTokenizer.getCurToken().equals(",")) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected. \",\" expeced.");
			}
			this.jackTokenizer.advance();
			
			// The next expected token is identifier.
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else{
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			this.jackTokenizer.advance();
		}
		
		// Append ";"
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
	}
	
	/**
	 * When other method call the method means that program need to deal with statements next...
	 */
	public void compileStatements() {
		// Create statements tag and let curRoot point to it
		Element statements = this.document.createElement("statements");
		this.curRoot.appendChild(statements);
		this.curRoot = statements;
		
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
		this.curRoot = (Element) this.curRoot.getParentNode();
	}

	/**
	 * Series: do subroutineName ( expressionList ) ;
	 * Series: do className . subroutineName ( expressionList ) ;
	 * Series: do varName . subroutineName ( expressionList ) ;
	 */
	public void compileDo() {
		// Create doStatement tag and let curRoot point to it
		Element doStatement = this.document.createElement("doStatement");
		this.curRoot.appendChild(doStatement);
		this.curRoot = doStatement;
		
		// Append the current token firstly. In fact this token is "do".
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());

		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// Maybe the next expected token is "." or "(".
		if(this.jackTokenizer.getCurToken().equals(".")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			
			// The next expected token is identifier.
			this.jackTokenizer.advance();
			if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
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
		if(this.jackTokenizer.getCurToken().equals("(")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}

		// Expression list maybe null
		// This means the expressionList is null
		if(this.jackTokenizer.getQueue().peek().equals(")")) {
			createAndAppendElement("expressionList", "");
		}else if(this.isTerm(this.jackTokenizer.getQueue().peek())) {
			compileExpressionList();
		}
		
		// The next expected token is ")".
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(")")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected, ) expected.");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is ";".
		if(this.jackTokenizer.getCurToken().equals(";")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected, ) expected.");
		}
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	/**
	 * Series: let varName([expression])? = expression ;
	 */
	public void compileLet() {
		// Create letStatement tag and let curRoot point to it
		Element letStatement = this.document.createElement("letStatement");
		this.curRoot.appendChild(letStatement);
		this.curRoot = letStatement;

		// Append the current "let" token firstly.
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		
		// The next expected token is identifier.
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else{
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.jackTokenizer.advance();
		
		// The next expected token is "[" or "=".
		int startBrackets = 0;
		if(this.jackTokenizer.getCurToken().equals("[")) {
			startBrackets = ~startBrackets;
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else if (this.jackTokenizer.getCurToken().equals("=")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
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
		
		// The right brackets
		if(startBrackets == -1) {
			// next expected token is right brackets
			this.jackTokenizer.advance();
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			startBrackets = ~startBrackets;
			
			// next token "="?
			this.jackTokenizer.advance();
			if(this.jackTokenizer.getCurToken().equals("=")) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else {
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
		}
		
		// The end token is ";"
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(";")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected"); 
		}
		
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	public void compileWhile() {
		// First of all, create the whileStatement tag and let curRoot point to it.
		Element term = this.document.createElement("whileStatement");
		this.curRoot.appendChild(term);
		this.curRoot = term;

		// Append the current token firstly.
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		
		// next token "("
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals("(")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
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
		if (this.jackTokenizer.getCurToken().equals(")")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		} else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token "{"
		this.jackTokenizer.advance();
		if (this.jackTokenizer.getCurToken().equals("{")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		} else {
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
		if (this.jackTokenizer.getCurToken().equals("}")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		} else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	public void compileReturn() {
		// First of all, create the returnStatement tag and let curRoot point to it.
		Element term = this.document.createElement("returnStatement");
		this.curRoot.appendChild(term);
		this.curRoot = term;
		
		// Append the current token firstly.
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
	
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(";")) {
			this.jackTokenizer.putBack();
		}else if(this.isTerm(this.jackTokenizer.getCurToken())) {
			compileExpression();
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token ";"
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals(";")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	public void compileIf() {
		// First of all, create the returnStatement tag and let curRoot point to it.
		Element term = this.document.createElement("ifStatement");
		this.curRoot.appendChild(term);
		this.curRoot = term;
		
		// Append the current token firstly.
		createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		
		// next token "("
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals("(")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
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
		if(this.jackTokenizer.getCurToken().equals(")")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token "{"
		this.jackTokenizer.advance();
		if(this.jackTokenizer.getCurToken().equals("{")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
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
		if(this.jackTokenizer.getCurToken().equals("}")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		}else {
			throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
		}
		
		// next token maybe else or others
		if(this.jackTokenizer.getQueue().peek().equals("else")) {
			// Append the token
			this.jackTokenizer.advance();
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			
			// next token "{"
			this.jackTokenizer.advance();
			if(this.jackTokenizer.getCurToken().equals("{")) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else {
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
			if(this.jackTokenizer.getCurToken().equals("}")) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
		}
		
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	/**
	 * The first token of the expression is term. When other method call this method, a term has been advanced.<br/>
	 * Series: term (operator term)*
	 */
	public void compileExpression() {
		// Create expression tag, append it and let curRoot point to it
		Element expression = this.document.createElement("expression");
		this.curRoot.appendChild(expression);
		this.curRoot = expression;
		
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
			if(this.jackTokenizer.getCurToken().matches("\\+|-|\\*|/|\\&|\\||<|=|>")) {
				createAndAppendElement(this.jackTokenizer.tokenType(),this.jackTokenizer.getCurToken());
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+", unexpected");
			}
			// term
			this.jackTokenizer.advance();
			String curToken = this.jackTokenizer.getCurToken();
			if(this.isTerm(curToken))  {
				compileTerm();
			}
			
			// Judge if end.
			this.jackTokenizer.advance();
			endFlag = this.jackTokenizer.getCurToken().equals("]") ||
			 		  this.jackTokenizer.getCurToken().equals(")") ||
			 		  this.jackTokenizer.getCurToken().equals(";") ||
			 		  this.jackTokenizer.getCurToken().equals(",");
		}
		this.jackTokenizer.putBack();
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	/**
	 * Series: integerConstant|stringConstant|keywordConstant|varName|varName [ expression ]|subroutineCall|
	 * ( expression )|unaryOp term
	 */
	public void compileTerm() {
		// First of all, create an Term tag and let curRoot point to it.
		Element term = this.document.createElement("term");
		this.curRoot.appendChild(term);
		this.curRoot = term;
		
		// integer constant
		if(this.jackTokenizer.tokenType().equals(TokenType.INT_CONST)) {
			int num = Integer.parseInt(this.jackTokenizer.getCurToken());
			if(num>=0 && num<32767) {
				createAndAppendElement("integerConstant", num+"");
			}else {
				throw new RuntimeException("Integer over than max Integer: "+this.jackTokenizer.getCurToken());
			}
		// string constant
		}else if (this.jackTokenizer.tokenType().equals(TokenType.STRING_CONST)) {
			// throw the beginning symbol " and the end symbol "
			createAndAppendElement("stringConstant", this.jackTokenizer.getCurToken().substring(1,this.jackTokenizer.getCurToken().length()-1));
		// key word constant
		}else if (this.jackTokenizer.getCurToken().equals("true") ||
				  this.jackTokenizer.getCurToken().equals("false") ||
				  this.jackTokenizer.getCurToken().equals("null") ||
				  this.jackTokenizer.getCurToken().equals("this")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
		// varName...
		}else if (this.jackTokenizer.tokenType().equals(TokenType.IDENTIFIER)) {
			// Check the head of the queue.
			String head = this.jackTokenizer.getQueue().peek();
			// mean the structure is varName [expression]
			if(head.equals("[")) {
				// append varName
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				this.jackTokenizer.advance();
				// append "["
				createAndAppendElement(this.jackTokenizer.tokenType(), "[");
				this.jackTokenizer.advance();
				if(this.isTerm(this.jackTokenizer.getCurToken())) {
					compileExpression();
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				// append "]"
				this.jackTokenizer.advance();
				if(this.jackTokenizer.getCurToken().equals("]")) {
					createAndAppendElement(this.jackTokenizer.tokenType(), "]");
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
			// means the structure is subroutineName ( expressionList )
			}else if(head.equals("(")) {
				// append varName
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				this.jackTokenizer.advance();
				// append "("
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				if(this.jackTokenizer.getQueue().peek().matches(JackTokenizer.REEX_IDENTIFIER)) {
					compileExpressionList();
				}else if(this.jackTokenizer.getQueue().peek().equals(")")) {
					
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				
				// get next token
				this.jackTokenizer.advance();
				if(this.jackTokenizer.getCurToken().equals(")")) {
					createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}

			// means the structure is className|varName . subroutineName ( expressionList )
			}else if(head.equals(".")) {
				// append className of varName
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				this.jackTokenizer.advance();
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				
				// next token
				this.jackTokenizer.advance();
				if(this.jackTokenizer.getCurToken().matches(JackTokenizer.REEX_IDENTIFIER)) {
					// append the subroutineName first
					createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
					// next token
					this.jackTokenizer.advance();
					if(this.jackTokenizer.getCurToken().equals("(")) {
						// append it
						createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
					
						// Judge is next token is belong to expressionList.
						// The beginning of the expressionList is a expression and the beginning of the expression is a term
						if(this.isTerm(this.jackTokenizer.getQueue().peek())) {
							compileExpressionList();
						}else if(this.jackTokenizer.getQueue().peek().equals(")")) {
							createAndAppendElement("expressionList", "  ");
						}else{
							throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
						}
//						compileExpressionList();
						
						// next token
						this.jackTokenizer.advance();
						if(this.jackTokenizer.getCurToken().equals(")")) {
							createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
						}else {
							throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
						}
					}else {
						throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
					}
				}else {
					throw new RuntimeException("subroutine call error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
			// means the next token is a symbol, we just append the current identifier
			}else if(head.matches("\\+|-|\\*|/|\\&|\\||<|=|>")) {
				// append varName
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			// Means this term meet the end.
			}else if(head.equals(")") || head.equals("]") || head.equals(";") || head.equals(",")) {
				// append varName
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else {
				throw new RuntimeException("subroutine call error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
		// means the structure is ( expression )
		}else if(this.jackTokenizer.getCurToken().equals("(")) {
			// append "(" firstly
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
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
			if(this.jackTokenizer.getCurToken().equals(")")) {
				createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
		// Means this structure is "unaryOp term"
		}else if (this.jackTokenizer.getCurToken().matches("\\-|~")) {
			createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
			// The next expected token is term
			this.jackTokenizer.advance();
			if(this.isTerm(this.jackTokenizer.getCurToken())) {
				compileTerm();
			}else {
				throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
			}
		}else{
			throw new RuntimeException("Term token compile error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
		}
		
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
	
	/**
	 * Series: (expression ( , expression)*)?
	 */
	public void compileExpressionList() {
		// First of all, create the expressionList tag and let curRoot point to it.
		Element term = this.document.createElement("expressionList");
		this.curRoot.appendChild(term);
		this.curRoot = term;
		
		// next token
		this.jackTokenizer.advance();
		if(this.isTerm(this.jackTokenizer.getCurToken())) {
			compileExpression();
		}
		
		// next token maybe "," or ")"
		if(this.jackTokenizer.getQueue().peek().equals(",")) {
			String curToken = this.jackTokenizer.getQueue().peek();
			while(!curToken.equals(")")) {
				if(curToken.equals(",")) {
					this.jackTokenizer.advance();
					createAndAppendElement(this.jackTokenizer.tokenType(), this.jackTokenizer.getCurToken());
				}else {
					throw new RuntimeException("Syntax error: "+this.jackTokenizer.getCurToken()+ " unexpected.");
				}
				
				this.jackTokenizer.advance();
				if(this.isTerm(this.jackTokenizer.getCurToken())) {
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
		this.curRoot = (Element) this.curRoot.getParentNode();
	}
}
