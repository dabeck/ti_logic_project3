package de.unikassel.ti.logic.project3.model;

import java.lang.StringBuffer;

public class Conjunction extends Formula {

    private Formula left_arg, right_arg;

    public Conjunction(Formula f, Formula g) {
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
	return FormulaType.AND;
    }

    @Override public Formula transformToPositiveNF() {
        return this;
    }

    @Override public Formula transformToPrenexNF() {

        String quantorType = null;
        Formula currentQuantor = null;
        Formula le = null;
        Formula ri = null;
        String var = null;

        // left side of conjunction check
        if (getLeftArg().getClass().equals(ForallQuantification.class)) {
//            System.out.println("Left: Allquantor.");
            var = ((ForallQuantification) left_arg).getVariable();
            le = ((ForallQuantification) left_arg).getArg().transformToPrenexNF();
            quantorType = "forall";

        } else if (getLeftArg().getClass().equals(ExistsQuantification.class)) {
//            System.out.println("Left: Existenzquantor");
            var = ((ExistsQuantification) left_arg).getVariable();
            le = ((ExistsQuantification) left_arg).getArg().transformToPrenexNF();
            quantorType = "exist";

        } else if (getLeftArg().getClass().equals(RelationFormula.class)) {
//            System.out.println("Left: Relation formula.");
            le = ((RelationFormula) left_arg).transformToPrenexNF();

        } else {
            System.err.println("ERROR: unexpected object. Expected 'Quantor' or 'RelationFormula.'");
        }

        // right side of conjunction check
        if (getRightArg().getClass().equals(ForallQuantification.class)) {
//            System.out.println("Right: Allquantor.");
            var = ((ForallQuantification) right_arg).getVariable();
            ri = ((ForallQuantification) right_arg).getArg().transformToPrenexNF();
            quantorType = "forall";

        } else if (getRightArg().getClass().equals(ExistsQuantification.class)) {
//            System.out.println("Right: Existenzquantor.");
            var = ((ExistsQuantification) right_arg).getVariable();
            ri = ((ExistsQuantification) right_arg).getArg().transformToPrenexNF();
            quantorType = "exist";

        } else if (getRightArg().getClass().equals(RelationFormula.class)) {
//            System.out.println("Right: Relation formula.");
            ri = ((RelationFormula) right_arg).transformToPrenexNF();

        } else {
            System.err.println("ERROR: unexpected object. Expected 'Quantor' or 'RelationFormula.'");
        }

        if ("exist".equals(quantorType)) {
            currentQuantor =  new ExistsQuantification(var, new Conjunction(le, ri));
        } else if ("forall".equals(quantorType)) {
            currentQuantor=  new ForallQuantification(var, new Conjunction(le, ri));
        } else {
            System.err.println("ERROR: Expected quantor, but found " + quantorType);
        }
        return currentQuantor;
    }

    public StringBuffer toString(StringBuffer s) {
	boolean lpar = left_arg.getType().getPrecedence() < this.getType().getPrecedence();
	boolean rpar = right_arg.getType().getPrecedence() < this.getType().getPrecedence();

	if (lpar) {
	    s.append("(");
	}
	s = left_arg.toString(s);
	if (lpar) {
	    s.append(")");
	}
	s.append(" & ");
	if (rpar) {
	    s.append("(");
	}
	s = right_arg.toString(s);
	if (rpar) {
	    s.append(")");
	}
	return s;
    }
}
