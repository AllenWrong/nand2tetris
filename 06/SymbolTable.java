package com.module;

import java.util.Hashtable;

public class SymbolTable {
	/** the string represent the symbol, the integer represent the decimal adddress of the symbol*/
	Hashtable<String, Integer> table;
	
	/**
	 * When we construct the symbol table, we need to add the predefined symbol.
	 */
	public SymbolTable() {
		this.table = new Hashtable<>();
		this.addEntry("SP", 0);
		this.addEntry("LCL", 1);
		this.addEntry("ARG", 2);
		this.addEntry("THIS", 3);
		this.addEntry("THAT", 4);
		for(int i=0;i<16;i++) {
			this.addEntry("R"+i, i);
		}
		this.addEntry("SCREEN", 16384);
		this.addEntry("KBD", 24576);
	}
	
	/** add an entry to symbol table (hash table)*/
	public void addEntry(String symbol, Integer address) {
		this.table.put(symbol, address);
	}
	
	/** Judge whether the symbol table contains the symbol*/
	public boolean contains(String symbol) {
		if(this.table.get(symbol) != null) {
			return true;
		};
		return false;
	}
	
	/** get the address of the given table*/
	public int getAddress(String symbol) {
		if(contains(symbol)) {
			return this.table.get(symbol);
		}
		return -1;
	}
}
