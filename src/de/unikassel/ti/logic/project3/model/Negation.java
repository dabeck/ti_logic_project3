package de.unikassel.ti.logic.project3.model;

import java.lang.StringBuffer;

public class Negation extends Formula {

    private Formula arg;

    public Negation(Formula f) {
	arg = f;
    }

    public Formula getArg() {
	return arg;
    }

    public FormulaType getType() {
	return FormulaType.NEG;
    }

    // transforms '- FORALL x R(f(z))' to 'EXISTS x -R(f(z))'
    // transforms '- EXISTS x R(f(z))' to 'FORALL x -R(f(z))'
    @Override public Formula transformToPositiveNF() {
        Formula fo = null;
        if (arg.getClass().equals(ForallQuantification.class)) {
            fo = new ExistsQuantification(
                ((ForallQuantification) arg).getVariable(),
                new Negation( ((ForallQuantification) arg).getArg().transformToPositiveNF()) );

        } else if (arg.getClass().equals(ExistsQuantification.class)) {
            fo = new ForallQuantification(
                ((ExistsQuantification) arg).getVariable(),
                new Negation( ((ExistsQuantification) arg).getArg().transformToPositiveNF()) );

        } else if (arg.getClass().equals(Disjunction.class)) {
            fo = new Conjunction(new Negation(((Disjunction) arg).getLeftArg().transformToPositiveNF()),
                    new Negation(((Disjunction) arg).getRightArg().transformToPositiveNF()));

        } else if (arg.getClass().equals(Conjunction.class)) {
            fo = new Disjunction(new Negation(((Conjunction) arg).getLeftArg().transformToPositiveNF()),
                    new Negation(((Conjunction) arg).getRightArg().transformToPositiveNF()));

        } else {
            System.out.println("Error: Expected '-FORALL' or '-EXISTS' here, otherwise not implemented yet.");
        }
        return fo;
    }

    @Override public Formula transformToPrenexNF() {
        return this;
    }

    public StringBuffer toString(StringBuffer s) {
	boolean par = arg.getType().getPrecedence() < this.getType().getPrecedence();

	s.append("-");
	if (par) {
	    s.append("(");
	}
	s = arg.toString(s);
	if (par) {
	    s.append(")");
	}
	return s;
    }
}
