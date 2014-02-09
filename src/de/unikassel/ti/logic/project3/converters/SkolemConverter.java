package de.unikassel.ti.logic.project3.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import de.unikassel.ti.logic.project3.factories.UniqueSymbolFactory;
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

	/**
	 * List of all FORALL variables in the given formula.
	 */
	private static ArrayList<String> replForall = new ArrayList<String>();
	
	/**
	 * Get the list of all FORALL variables in the given formula.
	 * @return
	 */
	public static ArrayList<String> getReplForall() {
		return replForall;
	}

	/**
	 * Set the list of all FORALL variables in the given formula.
	 * @param replForall
	 */
	public static void setReplForall(ArrayList<String> replForall) {
		SkolemConverter.replForall = replForall;
	}
	
	/**
	 * List of all EXISTS variables and their corresponding replacement-term.
	 */
	private static HashMap<String, Term> replExist = new HashMap<String, Term>();
	
	/**
	 * Get the list of all EXISTS variables and their corresponding replacement-term.
	 * @return
	 */
	public static HashMap<String, Term> getReplExist() {
		return replExist;
	}

	/**
	 * Set the list of all EXISTS variables and their corresponding replacement-term.
	 * @param replExist
	 */
	public static void setReplExist(HashMap<String, Term> replExist) {
		SkolemConverter.replExist = replExist;
	}
	
	/**
	 * List of all variables in the formula, to prevent duplicates thru replacement.
	 */
	private static ArrayList<String> variables = new ArrayList<String>();
	
	/**
	 * Get the list of all variables in the formula
	 * @return
	 */
	public static ArrayList<String> getVariables() {
		return variables;
	}

	/**
	 * Set the list of all variables in the formula
	 * @param variables
	 */
	public static void setVariables(ArrayList<String> variables) {
		SkolemConverter.variables = variables;
	}

	/**
	 * List of all functionSymbols in the given formula.
	 */
	private static ArrayList<String> functionSymbols = new ArrayList<String>();
	
	/**
	 * Get the list of all functionSymbols in the given formula.
	 * @return
	 */
	public static ArrayList<String> getFunctionSymbols() {
		return functionSymbols;
	}

	/**
	 * Set the list of all functionSymbols in the given formula.
	 * @param functionSymbols
	 */
	public static void setFunctionSymbols(ArrayList<String> functionSymbols) {
		SkolemConverter.functionSymbols = functionSymbols;
	}

	/**
	 * Unique Symbol Factory, to generate unique constants, variables, functions.
	 */
	private static UniqueSymbolFactory varfac = null;
	
	/**
	 * Converts a given formula (must be in PrenexForm) to SkolemForm.
	 * @param f
	 * 				The formula (must be in PrenexForm) to convert.
	 * @return The formula in SkolemForm.
	 */
	public static Formula convert(Formula f) {
		varfac  = UniqueSymbolFactory.getFactoryInstance();
		
		// If there is no list of variables, collect them.
		if (variables.size() == 0) {
			collectVariables(f);
		}
		
		// If there's still no list, there must be an error in the formula.
		if (variables.size() == 0) {
			throw new UnsupportedOperationException(
					"Cannot collect the variable names of the given formula in SkolemConverter.convert() : " + 
					f.toString());
		}
		
		// Start replacing progress.
		Formula result = replaceExists(f);
		
		// Clear the lists.
		replExist.clear();
		replForall.clear();
		variables.clear();
		functionSymbols.clear();
		
		return result;
	}
	
	/**
	 * Collect all variable names, if they are not given by previous conversion step.
	 * @param f The formula, from which the variables should be collected.
	 */
	private static void collectVariables(Formula f) {
		if (f instanceof ExistsQuantification) {
			ExistsQuantification ex = ((ExistsQuantification) f);
			if (!variables.contains(ex.getVariable())) {
				variables.add(ex.getVariable());
			}
			collectVariables(ex.getArg());
		} else if (f instanceof ForallQuantification) {
			ForallQuantification fa = ((ForallQuantification) f);
			if (!variables.contains(fa.getVariable())) {
				variables.add(fa.getVariable());
			}
			collectVariables(fa.getArg());
		} else if (f instanceof Conjunction) {
			Conjunction c = ((Conjunction) f);
			collectVariables(c.getLeftArg());
			collectVariables(c.getRightArg());
		} else if (f instanceof Disjunction) {
			Disjunction d = ((Disjunction) f);
			collectVariables(d.getLeftArg());
			collectVariables(d.getRightArg());
		} else if (f instanceof Negation) {
			Negation n = ((Negation) f);
			collectVariables(n.getArg());
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = ((RelationFormula) f);
			for(Term t: rf.getTerms()) {
				collectTerms(t);
			}
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in SkolemConverter.collectVariables() : " + 
					f.toString());
		}
	}
	
	/**
	 * Collect all variable names in the terms and subterms.
	 * @param t The Term, to check for new unique variable names.
	 */
	private static void collectTerms(Term t) {
		if (t.getSubterms().size() == 0) {
			String name = t.getTopSymbol().getName();
			if (!variables.contains(name)) {
				variables.add(name);
			}
		} else {
			for(Term st: t.getSubterms()) {
				if (st.getTopSymbol().getArity() != 0) {
					String name = st.getTopSymbol().getName();
					if (!functionSymbols.contains(name)) {
						functionSymbols.add(name);
					}
				}
				collectTerms(st);
			}
		}
	}

	/**
	 * Replace all EXISTS quantifications, to convert to SkolemForm.
	 * @param f
	 * 				The formula (must be in PrenexForm) to convert.
	 * @return The formula in SkolemForm.
	 */
	private static Formula replaceExists(Formula f) {
		if (f instanceof ExistsQuantification) {
			ExistsQuantification ex = ((ExistsQuantification) f);
			
			replExist.put(ex.getVariable(), getReplacement());
			
			return replaceExists(ex.getArg());
		} else if (f instanceof ForallQuantification) {
			ForallQuantification fa = ((ForallQuantification) f);
			
			replForall.add(fa.getVariable());
			
			return new ForallQuantification(fa.getVariable(), replaceExists(fa.getArg()));
		} else if (f instanceof Conjunction) {
			Conjunction c = ((Conjunction) f);
			return new Conjunction(replaceExists(c.getLeftArg()), replaceExists(c.getRightArg()));
		} else if (f instanceof Disjunction) {
			Disjunction d = ((Disjunction) f);
			return new Disjunction(replaceExists(d.getLeftArg()), replaceExists(d.getRightArg()));
		} else if (f instanceof Negation) {
			Negation n = ((Negation) f);
			return new Negation(replaceExists(n.getArg()));
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = ((RelationFormula) f);
			
			for(int i = 0; i != rf.getTerms().size(); i++) {
				rf.getTerms().set(i, replaceTerms(rf.getTerms().get(i)));
			}
			
			return rf;
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in SkolemConverter.replaceExists() : " + 
					f.toString());
		}
	}
	
	/**
	 * Replace the terms, if their is a corresponding replacement rule.
	 * @param t
	 * 				The term to check for a defined replacement.
	 * @return The term, whether it is replaced or not.
	 */
	private static Term replaceTerms(Term t) {
		if (t.getSubterms().size() == 0) {
			if (replExist.containsKey(t.getTopSymbol().getName())) {
				return replExist.get(t.getTopSymbol().getName());
			} else {
				return t;
			}
		} else {
			for(int i = 0; i != t.getSubterms().size(); i++) {
				t.getSubterms().set(i, replaceTerms(t.getSubterms().get(i)));
			}
			return t;
		}
	}
	
	/**
	 * Get a correct replacement term, whether the FORALL list is emtpy or not.
	 * @return A correct replacement term.
	 */
	private static Term getReplacement() {
		if (replForall.size() == 0) {
			String newConstSymbolString = varfac.getNewConstSymbolString();
			while (functionSymbols.contains(newConstSymbolString) || variables.contains(newConstSymbolString)) {
				newConstSymbolString = varfac.getNewFunctionSymbolString();
			}
			// Insert in functionSymbol-List only, because constant = functionSymbol with arity 0.
			functionSymbols.add(newConstSymbolString);
			
			return new Term(new FunctionSymbol(newConstSymbolString, 0), null);
		} else {
			String newFunctionSymbolString = varfac.getNewFunctionSymbolString();
			while (functionSymbols.contains(newFunctionSymbolString)) {
				newFunctionSymbolString = varfac.getNewFunctionSymbolString();
			}
			functionSymbols.add(newFunctionSymbolString);
			
			Vector<Term> subTerms = new Vector<Term>();
			for(String s: replForall) {
				subTerms.add(new Term(new FunctionSymbol(s, 0), null));
			}
			
			return new Term(new FunctionSymbol(newFunctionSymbolString, replForall.size()), subTerms);
		}
	}

}
