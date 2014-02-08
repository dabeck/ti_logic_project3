package de.unikassel.ti.logic.project3.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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
	 * List of all EXISTS variables and their corresponding replacement-term.
	 */
	private static HashMap<String, Term> replExist = new HashMap<String, Term>();
	
	/**
	 * List of all variables in the formula, to prevent duplicates thru replacement.
	 */
	private static ArrayList<String> variables = new ArrayList<String>();
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public static Formula convert(Formula f) {

		if (f instanceof ExistsQuantification) {
			ExistsQuantification ex = ((ExistsQuantification) f);
			
			replExist.put(ex.getVariable(), getReplacement());
			
			return convert(ex.getArg());
		} else if (f instanceof ForallQuantification) {
			ForallQuantification fa = ((ForallQuantification) f);
			
			replForall.add(fa.getVariable());
			
			return new ForallQuantification(fa.getVariable(), convert(fa.getArg()));
		} else if (f instanceof Conjunction) {
			Conjunction c = ((Conjunction) f);
			return new Conjunction(convert(c.getLeftArg()), convert(c.getRightArg()));
		} else if (f instanceof Disjunction) {
			Disjunction d = ((Disjunction) f);
			return new Disjunction(convert(d.getLeftArg()), convert(d.getRightArg()));
		} else if (f instanceof Negation) {
			Negation n = ((Negation) f);
			return new Negation(convert(n.getArg()));
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = ((RelationFormula) f);
			
			for(int i = 0; i != rf.getTerms().size(); i++) {
				rf.getTerms().set(i, replace(rf.getTerms().get(i)));
			}
			
			return rf;
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in SkolemConverter.convert() : " + 
					f.toString());
		}
	}
	
	private static Term getReplacement() {
		if (replForall.size() == 0) {
			return new Term(new FunctionSymbol(randomConstant(), 0), null);
		} else {
			Vector<Term> subTerms = new Vector<Term>();
			for(String s: replForall) {
				subTerms.add(new Term(new FunctionSymbol(s, 0), null));
			}
			return new Term(new FunctionSymbol("f", replForall.size()), subTerms);
		}
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	private static Term replace(Term t) {
		
		if (t.getSubterms().size() == 0) {
			if (replExist.containsKey(t.getTopSymbol().getName())) {
				return replExist.get(t.getTopSymbol().getName());
			} else {
				return t;
			}
		} else {
			for(int i = 0; i != t.getSubterms().size(); i++) {
				t.getSubterms().set(i, replace(t.getSubterms().get(i)));
			}
			return t;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static String randomConstant() {
		Random r = new Random();
		String constant;
		
		do {
			constant = Character.toString((char) (97 + r.nextInt(25)));
		} while(variables.contains(constant));
		
		// remember the new variable name for further replacements to prevent duplicates
		// TODO: initialize the variables-List with the variable names
		variables.add(constant);
		
		return constant;
	}

}
