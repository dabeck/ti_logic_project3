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

	/**
	 * List of all FORALL variables.
	 */
	private static ArrayList<String> replForall = new ArrayList<String>();
	
	/**
	 * 
	 * @return
	 */
	public static ArrayList<String> getReplForall() {
		return replForall;
	}

	/**
	 * 
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
	 * 
	 * @return
	 */
	public static HashMap<String, Term> getReplExist() {
		return replExist;
	}

	/**
	 * 
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
	 * 
	 * @param f
	 * @return
	 */
	public static Formula convert(Formula f, ArrayList<String> v) {
		// TODO: replace when PrenexConverter is ready
		// Initialize list of variables of the given formula.
		collectVariables(f);
		//variables = v;
		
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
		
		return result;
	}
	
	private static void collectVariables(Formula f) {
		if (f instanceof ExistsQuantification) {
			ExistsQuantification ex = ((ExistsQuantification) f);
			if (!variablesContains(ex.getVariable())) {
				variables.add(ex.getVariable());
			}
			collectVariables(ex.getArg());
		} else if (f instanceof ForallQuantification) {
			ForallQuantification fa = ((ForallQuantification) f);
			if (!variablesContains(fa.getVariable())) {
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
	 * 
	 * @param t
	 */
	private static void collectTerms(Term t) {
		if (t.getSubterms().size() == 0) {
			String name = t.getTopSymbol().getName();
			if (!variablesContains(name)) {
				variables.add(name);
			}
		} else {
			for(Term st: t.getSubterms()) {
				collectTerms(st);
			}
		}
	}

	/**
	 * 
	 * @param f
	 * @return
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
	 * 
	 * @param t
	 * @return
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
	 * 
	 * @return
	 */
	private static Term getReplacement() {
		if (replForall.size() == 0) {
			return new Term(new FunctionSymbol(getNewVariableName(), 0), null);
		} else {
			Vector<Term> subTerms = new Vector<Term>();
			for(String s: replForall) {
				subTerms.add(new Term(new FunctionSymbol(s, 0), null));
			}
			return new Term(new FunctionSymbol(getNewVariableName(), replForall.size()), subTerms);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static String getNewVariableName() {
		String variable = null;
		
		// old random generation of new variable names
		//Random r = new Random();
		//do {
		//	constant = Character.toString((char) (97 + r.nextInt(25)));
		//} while(variablesContains(constant));
		
		for(int i = 0; i != 25; i++) {
			variable = Character.toString((char) (97 + i));
			if (!variablesContains(variable)) {
				break;
			}
		}
		
		// remember the new variable name for further replacements to prevent duplicates
		// TODO: initialize the variables-List with the variable names
		variables.add(variable);
		
		return variable;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private static boolean variablesContains(String name) {
		for (int i = 0; i != variables.size(); i++) {
			if (name.equals(variables.get(i))) {
				return true;
			}
		}
		return false;
	}

}
