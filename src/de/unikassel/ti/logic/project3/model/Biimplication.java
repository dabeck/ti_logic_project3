package de.unikassel.ti.logic.project3.model;

import java.lang.StringBuffer;

public class Biimplication extends Formula {

    private Formula left_arg, right_arg;
 
    public Biimplication(Formula f, Formula g) {
	left_arg = f;
	right_arg = g;
    }

    public Formula getLeftArg() {
	return left_arg;
    }

    public Formula getRightArg() {
	return right_arg;
    }

    public FormulaType getType() {
	return FormulaType.BIIMP;
    }

    // transforms 'A <-> B' to '(A & B) | (-A & -B)'
    public Formula transformToPositiveNF() {
        return new Disjunction(
            new Conjunction(left_arg.transformToPositiveNF(), right_arg.transformToPositiveNF()),
            new Conjunction(new Negation(left_arg.transformToPositiveNF()), new Negation(right_arg.transformToPositiveNF())));
    }

    public StringBuffer toString(StringBuffer s) {
	s = left_arg.toString(s);
	s.append(" <-> ");
	s = right_arg.toString(s);
	return s;
    }
}
