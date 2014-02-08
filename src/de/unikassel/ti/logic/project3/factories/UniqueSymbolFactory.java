package de.unikassel.ti.logic.project3.factories;


public class UniqueSymbolFactory {

	private static long varCounter;
	private static long functionCounter;
	private static long constCounter;
	private static long relationCounter;

	private static String varPrefix;
	private static String functionPrefix;
	private static String constPrefix;
	private static String relationPrefix;

	private static UniqueSymbolFactory instance;
	
	private UniqueSymbolFactory() {
		varPrefix = "x";
		functionPrefix = "f";
		constPrefix = "c";
		relationPrefix = "R";

		varCounter = 0;
		functionCounter = 0;
		constCounter = 0;
		relationCounter = 0;
	}
	
	public static UniqueSymbolFactory getFactoryInstance() {
		
		if (null == instance) {
			instance = new UniqueSymbolFactory();
		}
		
		return instance;
	}
	
	/**
	 * generates a new variable
	 * 
	 * @return Variable string
	 */
	public String getNewVariableString() {

		return new String(varPrefix + varCounter++);
		
	}
	
	/**
	 * generates a new function symbol
	 * 
	 * @return Functionsymbol string
	 */
	public String getNewFunctionSymbolString() {
		
		return new String(functionPrefix + functionCounter++);
		
	}
	
	/**
	 * generates a new constant symbol
	 * 
	 * @return Constantsymbol string
	 */
	public String getNewConstSymbolString() {
		
		return new String(constPrefix + constCounter++);
		
	}
	
	/**
	 * generates a new relation symbol
	 * 
	 * @return Relationsymbol string
	 */
	public String getNewRelationSymbolString() {
		
		return new String(relationPrefix + relationCounter++);
		
	}
	
	/**
	 * Resets all counters to 0
	 */
	public void resetSymbolCounters() {
		varCounter = 0;
		functionCounter = 0;
		constCounter = 0;
		relationCounter = 0;
	}
}
