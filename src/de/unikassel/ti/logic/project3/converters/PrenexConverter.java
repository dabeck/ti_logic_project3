package de.unikassel.ti.logic.project3.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;
import de.unikassel.ti.logic.project3.model.Term;


public class PrenexConverter {
	
	private static ArrayList<String> variables = new ArrayList<String>();
	private static ArrayList<String> boundVariables = new ArrayList<String>();
	private static Map<String, String> freeVariables = new HashMap<String, String>();
	
	/**
	 * Collects all variables into our lists
	 * 
	 * @param f
	 *            formula
	 */
	private static void collectVariables(Formula f) {
		if (f instanceof Negation) {
			collectVariables(((Negation) f).getArg());
		} else if (f instanceof ExistsQuantification) {
			String var = ((ExistsQuantification) f).getVariable();
			
			if (!boundVariables.contains(var)) {
				boundVariables.add(var);
			}
			
			collectVariables(((ExistsQuantification) f).getArg());
		} else if (f instanceof ForallQuantification) {
			String var = ((ForallQuantification) f).getVariable();
			
			if (!boundVariables.contains(var)) {
				boundVariables.add(var);
			}
			
			collectVariables(((ForallQuantification) f).getArg());
		} else if (f instanceof Disjunction) {
			Disjunction d = (Disjunction) f;
			
			Formula leftArg = d.getLeftArg();
			Formula rightArg = d.getRightArg();
			
			collectVariables(leftArg);
			collectVariables(rightArg);
		} else if (f instanceof Conjunction) {
			Conjunction c = (Conjunction) f;
			
			Formula leftArg = c.getLeftArg();
			Formula rightArg = c.getRightArg();
			
			collectVariables(leftArg);
			collectVariables(rightArg);
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = (RelationFormula) f;

			for (Term t : rf.getTerms()) {
				variables.add(collectTerms(t));
			}

		}
		
	}
	

	/**
	 * Collect function symbols for terms/subterms
	 * 
	 * @param t
	 *            Term
	 * @return String symbol name
	 */
	private static String collectTerms(Term t) {
		if (t.getSubterms().size() > 0) {
			for (Term st : t.getSubterms()) {
				return collectTerms(st);
			}
		} else {
			return t.getTopSymbol().getName();
		}
		
		return null;
	}

	public static Formula convert(Formula f) {
		
		// TOOD:
		
		return f;
	}

}
