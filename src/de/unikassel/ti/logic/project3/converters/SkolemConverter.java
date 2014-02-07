package de.unikassel.ti.logic.project3.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.FunctionSymbol;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;
import de.unikassel.ti.logic.project3.model.Term;

public class SkolemConverter {

	private static ArrayList<String> boundVariables;
	private static HashMap<String, String> unboundVariables;
	
	private static HashMap<String, String> replacedVariables = new HashMap<String, String>();
	
	
	/**
	 * 
	 * @param f
	 * @param rExistList List of Replacements. 
	 * @param rForallList List of variable names of all preceding FORALL's.
	 * @return
	 */
	private static Formula convert(Formula f, HashMap<String, Term> rExistList, ArrayList<String> rForallList) {		
		if (f instanceof ExistsQuantification) {
			ExistsQuantification ex = ((ExistsQuantification) f);
			// TODO: ECHTE replacements in Liste einfuegen
			rExistList.put(ex.getVariable(), new Term(new FunctionSymbol("s", 0), null));
			return convert(((ExistsQuantification) f).getArg(), rExistList, rForallList);
		} else if (f instanceof ForallQuantification) {
			ForallQuantification fa = (ForallQuantification) f;
			rForallList.add(fa.getVariable());
			return new ForallQuantification(fa.getVariable(), convert(((ForallQuantification) f).getArg(), rExistList, rForallList));
		} else if (f instanceof Conjunction) {
			// TODO
			return new Conjunction(f, f);
		} else if (f instanceof Disjunction) {
			// TODO
			return new Disjunction(f, f);
		} else if (f instanceof Negation) {
			// TODO
			return new Negation(f);
		} else if (f instanceof RelationFormula) {
			// TODO
			RelationFormula rf = ((RelationFormula) f);
			Vector<Term> terms = rf.getTerms();
			for (Term term : terms) {
				replace(term, rExistList, rForallList);
			}
			
			return new RelationFormula(rf.getName(), null);
		} else {
			throw new UnsupportedOperationException(f.toString());
		}
	}
	
	
	private static void replace(Term term, HashMap<String, Term> rExistList, ArrayList<String> rForallList) {
		// TODO
		if (term.getSubterms().isEmpty()) {
			if (rExistList.containsKey(term.getTopSymbol().getName())) {
				// hier Replacement vornehmen
				term = rExistList.get(term.getTopSymbol().getName());
			}
		} else {
			//term
		}
	}

	public static Formula convert(Formula f) {
		return convert(f, new HashMap<String, Term>(), new ArrayList<String>());
	}
	
	

	
	
	
	public static ArrayList<String> getBoundVariables() {
		return boundVariables;
	}

	public static void setBoundVariables(ArrayList<String> boundVariables) {
		SkolemConverter.boundVariables = boundVariables;
	}

	public HashMap<String, String> getUnboundVariables() {
		return unboundVariables;
	}

	public void setUnboundVariables(HashMap<String, String> unboundVariables) {
		this.unboundVariables = unboundVariables;
	}
	
}
