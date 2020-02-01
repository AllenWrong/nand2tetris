package com.vmtranslator;

/**
 * Use the integer array to represent the virtual memory. <br/>
 * The register of the hack memory is 16 bit, max decimal is 65535, so one integer can be used to 
 * represent the value stored in the register.
 * The key is that how to allocate memory for different segment. <br/>
 * 
 * @author Thingcor
 *
 */
public class VirtualMemory {
	/**
	 * Every value in this array represent a register unit.The index of this array represent the 
	 * address of the unit. 
	 */
	int[] memory;
	
	public VirtualMemory() {
		this.memory = new int[24577];
	}
	
	/**
	 * get different segment basic address.<br/>
	 * Those segment contains SP,local,argument,this,that..
	 * @return
	 */
	public int getBasicAddress(String symbol) {
		switch (symbol) {
		case "SP":
			return this.memory[0];
		case "local":
			return this.memory[1];
		case "argument":
			return this.memory[2];
		case "this": case "pointer":
			return this.memory[3];
		case "that":
			return this.memory[4];
		default:
			break;
		}
		return -1;
	}
	
	/**
	 * get the unit value by address.
	 * @param address
	 * @return
	 */
	public int getValue(int address) {
		return this.memory[address];
	}
	
	/**
	 * get the unit value by symbol. <br/>
	 * Mainly used by temp. 
	 * @param symbol
	 * @return
	 */
	public int getValue(String symbol) {
		switch (symbol) {
		case "temp":
			return this.memory[5];
		default:
			break;
		}
		return -1;
	}
	
	/**
	 * change the unit value by address. 
	 * @param address
	 */
	public void setValue(int address) {
		
	}
	
	/**
	 * change the unit value by address.
	 * @param symbol
	 */
	public void setValue(String symbol) {
		
	}
	
}
