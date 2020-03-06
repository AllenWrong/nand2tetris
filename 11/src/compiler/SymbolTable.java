package compiler;

import java.util.HashMap;

public class SymbolTable {
	public static final int INDEX_TYPE = 0;
	public static final int INDEX_KIND = 1;
	public static final int INDEX_NUM = 2;
			
	private HashMap<String, Object[]> classTable;
	private HashMap<String, Object[]> subroutineTable;
	/** The index of the static type variable in the scope of class.*/
	private int staticIndex;
	/** The index of the field type variable in the scope of class.*/
	private int fieldIndex;
	/** The index of the argument in the scope of the subroutine.*/
	private int argIndex;
	/** The index of the local variable in the scope of the subroutine.*/
	private int varIndex;
	
	/** Constructor*/
	public SymbolTable() {
		this.classTable = new HashMap<>();
		this.staticIndex = 0;
		this.fieldIndex = 0;
		
		this.subroutineTable = new HashMap<>();
		this.argIndex = 0;
		this.varIndex = 0;
	}
	
	/**	Clear the subroutine table, when program compiles a new subroutine.*/
	public void reMakeSubTable() {
		this.subroutineTable.clear();
	}
	
	/**
	 * Judge if the key set of the class table contains the "name" identifier.
	 * @param name
	 * @return
	 */
	private boolean isInClassTable(String name) {
		return this.classTable.keySet().contains(name);
	}
	
	/**
	 * Judge if the key set of the subroutine table contains the "name" identifier.
	 * @param name
	 * @return
	 */
	private boolean isInSubTable(String name) {
		return this.subroutineTable.keySet().contains(name);
	}
	
	/********************Above code is not the main code.*************************************************/

	/**
	 * 
	 * @param name
	 * @param type
	 * @param kind
	 */
	public void define(String name, String type, String kind) {
		switch (kind) {
		case "static":
			if(null == this.classTable.get(name)) {
				this.classTable.put(name, new Object[]{type,kind,this.staticIndex++});
			}
			break;
		case "field":
			if(null == this.classTable.get(name)) {
				this.classTable.put(name, new Object[]{type,kind,this.fieldIndex++});
			}
			break;
		case "arg":
			if(null == this.subroutineTable.get(name)) {
				this.subroutineTable.put(name, new Object[]{type,kind,this.argIndex++});
			}
			break;
		case "var":
			if(null == this.subroutineTable.get(name)) {
				this.subroutineTable.put(name, new Object[]{type,kind,this.varIndex++});
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * Count of the current variable in the corresponding scope.
	 * @param kind
	 * @return
	 */
	public int varCount(String kind) {
		switch (kind) {
		case "static":
			return this.staticIndex;
		case "field":
			return this.fieldIndex;
		case "arg":
			return this.argIndex;
		case "var":
			return this.varIndex;
		default:
			break;
		}
		return 0;
	}
	
	/**
	 * Return the kind of the identifier.
	 * @param name identifier name
	 * @return kind or null
	 */
	public String kindOf(String name) {
		// Judge.
		if(this.isInClassTable(name)) {
			return (String) this.classTable.get(name)[INDEX_KIND];
		}else if(this.isInSubTable(name)) {
			return (String) this.subroutineTable.get(name)[INDEX_KIND];
		}else {
			return null;
		}
	}
	
	/**
	 * Get the index_type of the identifier "name"
	 * @param name
	 * @return index or 0
	 */
	public String typeOf(String name) {
		if(this.isInClassTable(name)) {
			return (String) this.classTable.get(name)[INDEX_TYPE]; 
		}else if(this.isInSubTable(name)) {
			return (String) this.subroutineTable.get(name)[INDEX_TYPE];
		}else {
			return null;
		}
	}
	
	/**
	 * Get the index_num of the identifier "name" 
	 * @param name
	 * @return index or 0
	 */
	public int indexOf(String name) {
		if(this.isInClassTable(name)) {
			return (int) this.classTable.get(name)[INDEX_NUM]; 
		}else if(this.isInSubTable(name)) {
			return (int) this.subroutineTable.get(name)[INDEX_NUM];
		}else {
			return 0;
		}
	}
	
	/**
	 * Clear the subroutine table.
	 */
	public void clear() {
		this.subroutineTable.clear();
		this.argIndex = 0;
		this.varIndex = 0;
	}
}
