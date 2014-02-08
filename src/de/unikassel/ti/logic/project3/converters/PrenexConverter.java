package de.unikassel.ti.logic.project3.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.unikassel.ti.logic.project3.factories.VariableFactory;
import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;
import de.unikassel.ti.logic.project3.model.Term;


public class PrenexConverter {
	
	private static VariableFactory varfac;
	private static ArrayList<String> variables = new ArrayList<String>();
	
	/**
	 * Collects all variables into our lists
	 * 
	 * @param f
	 *            formula
	 */
	private static void renameBoundVariables(Formula f,
	        Map<String, String> boundVariables) {

		if (boundVariables == null) {
			boundVariables = new HashMap<String, String>();
		}

		if (f instanceof Negation) {
			renameBoundVariables(((Negation) f).getArg(), boundVariables);
		} else if (f instanceof ExistsQuantification) {

			String var = ((ExistsQuantification) f).getVariable();
			String newVar = varfac.getNewVariableString();
			
			while (variables.contains(newVar)) {
				newVar = varfac.getNewVariableString();
			}
			variables.add(newVar);

			if (!boundVariables.containsKey(var)) {
				boundVariables.put(var, newVar);
			}
			((ExistsQuantification) f).setVariable(newVar);
			
			renameBoundVariables(((ExistsQuantification) f).getArg(),
			        boundVariables);

		} else if (f instanceof ForallQuantification) {
			
			String var = ((ForallQuantification) f).getVariable();
			String newVar = varfac.getNewVariableString();
			
			while (variables.contains(newVar)) {
				newVar = varfac.getNewVariableString();
			}
			variables.add(newVar);

			if (!boundVariables.containsKey(var)) {
				boundVariables.put(var, newVar);
			}
			((ForallQuantification) f).setVariable(newVar);
			
			renameBoundVariables(((ForallQuantification) f).getArg(),
			        boundVariables);
		} else if (f instanceof Disjunction) {
			Disjunction d = (Disjunction) f;
			
			Formula leftArg = d.getLeftArg();
			Formula rightArg = d.getRightArg();
			
			renameBoundVariables(leftArg, boundVariables);
			renameBoundVariables(rightArg, boundVariables);
		} else if (f instanceof Conjunction) {
			Conjunction c = (Conjunction) f;
			
			Formula leftArg = c.getLeftArg();
			Formula rightArg = c.getRightArg();
			
			renameBoundVariables(leftArg, boundVariables);
			renameBoundVariables(rightArg, boundVariables);
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = (RelationFormula) f;

			for (Term t : rf.getTerms()) {
				checkAndRenameTerms(t, boundVariables);
			}

		}
		
	}
	

	/**
	 * Rename function symbols for terms/subterms
	 * 
	 * @param t
	 *            Term
	 * @param boundVariables
	 * @return String symbol name
	 */
	private static void checkAndRenameTerms(Term t,
	        Map<String, String> boundVariables) {
		if (t.getSubterms().size() > 0) {
			for (Term st : t.getSubterms()) {
				checkAndRenameTerms(st, boundVariables);
			}
		} else {
			String symbolName = t.getTopSymbol().getName();

			if (boundVariables.containsKey(symbolName)) {
				String newName = boundVariables.get(symbolName);
				t.getTopSymbol().setName(newName);
			} else {
				variables.add(symbolName);
			}
		}
	}

	public static Formula convert(Formula f) {
		
		varfac = VariableFactory.getFactoryInstance();
		
		return f;
	}

}
