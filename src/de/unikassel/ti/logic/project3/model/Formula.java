package de.unikassel.ti.logic.project3.model;

public abstract class Formula {

    public abstract FormulaType getType();
    public abstract StringBuffer toString(StringBuffer s);

    // Rebuilds the AST (in the manner of composite design pattern)
    // into a 'Positive Normal Form' and returns the newly built AST.
    public abstract Formula transformToPositiveNF();
    
    // Rebuilds the AST (in the manner of composite design pattern)
    // into a Prenex Normal Form' and returns the newly built AST.
    // Warning: This operation can only be performed after the 'PenexConverter'
    // has renamed all unbound variables.
    public abstract Formula transformToPrenexNF();

	public Formula getLeftArg()
	{
		return null;
	}

	public Formula getRightArg()
	{
		return null;
	}

    public String toString() {
	    return toString(new StringBuffer()).toString();
    }
}
