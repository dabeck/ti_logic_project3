package de.unikassel.ti.logic.project3.model;

public abstract class Formula {

    public abstract FormulaType getType();
    public abstract StringBuffer toString(StringBuffer s);

    public abstract Formula transformToPositiveNF();

    public String toString() {
	return toString(new StringBuffer()).toString();
    }
}
