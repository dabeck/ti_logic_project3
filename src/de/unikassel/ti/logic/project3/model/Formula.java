package de.unikassel.ti.logic.project3.model;

import java.util.SortedSet;

public abstract class Formula {

    public abstract FormulaType getType();
    public abstract StringBuffer toString(StringBuffer s);

    public abstract Formula transformToPositiveNF();
    public abstract Formula transformToPrenexNF();

//    public abstract SortedSet<FunctionSymbol> collectFunctionSymbols();

    public String toString() {
	    return toString(new StringBuffer()).toString();
    }
}
