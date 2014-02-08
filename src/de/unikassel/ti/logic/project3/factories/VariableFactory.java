package de.unikassel.ti.logic.project3.factories;


public class VariableFactory {

	private static long varCounter;
	private static String varPrefix;
	private static VariableFactory instance;
	
	private VariableFactory() {
		varPrefix = "X";
		varCounter = 0;
	}
	
	public static VariableFactory getFactoryInstance() {
		
		if (null == instance) {
			instance = new VariableFactory();
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
	
}
