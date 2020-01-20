package com.module;

import java.util.Hashtable;

public class SymbolTable {
	/** the string represent the symbol, the integer represent the decimal adddress of the symbol*/
	Hashtable<String, Integer> table;
	
	public SymbolTable() {
		this.table = new Hashtable<>();
	}
	
	/** add an entry to (symbol table)hashtable*/
	public void addEntry(String symbol, Integer address) {
		
	}
	
	/** Judge whether the symbol table contains the symbol*/
	public boolean contains() {
		return false;
	}
	
	/** get the address of the given table*/
	public int getAddress(String symbol) {
		return 0;
	}
}
