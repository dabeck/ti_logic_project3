package de.unikassel.ti.logic.project3.model;

import java.util.Vector;
import java.lang.StringBuffer;

public class RelationFormula extends Formula {

    private Vector<Term> terms;
    private String name;

    public RelationFormula(String s, Vector<Term> ts) {
	terms = ts;
	name = s;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Term> getTerms() {
	return terms;
    }

    public FormulaType getType() {
	return FormulaType.REL;
    }

    @Override public Formula transformToPositiveNF() {
        return this;
    }

    @Override public Formula transformToPrenexNF() {
        return this;
    }

    public StringBuffer toString(StringBuffer s) {
	s.append(name);
	if (terms.size() > 0) {
	    s.append("(");
	    s.append(terms.get(0).toString());
	    for (int i=1; i< terms.size(); i++) {
		s.append(",");
		s.append(terms.get(i).toString());
	    }
	    s.append(")");
	}
	return s;
    }
}
