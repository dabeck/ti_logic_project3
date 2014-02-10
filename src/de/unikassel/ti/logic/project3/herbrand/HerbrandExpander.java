package de.unikassel.ti.logic.project3.herbrand;

import java.util.ArrayList;
import java.util.HashMap;

import de.unikassel.ti.logic.project3.TermEnumerator;
import de.unikassel.ti.logic.project3.helper.PermutationGenerator;
import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;
import de.unikassel.ti.logic.project3.model.Signature;
import de.unikassel.ti.logic.project3.model.Term;

public class HerbrandExpander {

	/**
	 * 
	 */
	private Formula formula;
	
	/**
	 * 
	 * @return
	 */
	public Formula getFormula() {
		return formula;
	}

	/**
	 * 
	 * @param f
	 */
	public void setFormula(Formula f) {
		this.formula = f;
	}	
	
	/**
	 * List of all variables in the formula, to prevent duplicates thru replacement.
	 */
	private ArrayList<String> variables = new ArrayList<String>();
	
	/**
	 * Get the list of all variables in the formula
	 * @return
	 */
	public ArrayList<String> getVariables() {
		return variables;
	}

	/**
	 * Set the list of all variables in the formula
	 * @param variables
	 */
	public void setVariables(ArrayList<String> variables) {
		this.variables = variables;
	}

	/**
	 * 
	 */
	private HashMap<String, Term> replacementRules = new HashMap<String, Term>();
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, Term> getReplacementRules() {
		return replacementRules;
	}

	/**
	 * 
	 * @param replacementRules
	 */
	public void setReplacementRules(HashMap<String, Term> replacementRules) {
		this.replacementRules = replacementRules;
	}

	/**
	 * 
	 */
	private PermutationGenerator permutation;
	
	/**
	 * 
	 * @return
	 */
	public PermutationGenerator getPermutation() {
		return permutation;
	}

	/**
	 * 
	 * @param permutation
	 */
	public void setPermutation(PermutationGenerator permutation) {
		this.permutation = permutation;
	}
	
	/**
	 * 
	 */
	private ArrayList<Term> universe;
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Term> getUniverse() {
		return universe;
	}

	/**
	 * 
	 * @param universe
	 */
	public void setUniverse(ArrayList<Term> universe) {
		this.universe = universe;
	}
	
	/**
	 * 
	 */
	private TermEnumerator termEnumerator;
	
	
	/**
	 * HerbrandExpander.
	 * 
	 * @param f
	 * @throws Exception 
	 */
	public HerbrandExpander(Formula f) {
		// Build a signature for our formula and create a termEnumerator with it.
		Signature s = null;
		try {
			s = SignatureBuilder.build(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.termEnumerator = new TermEnumerator(s);
		this.setUniverse(new ArrayList<Term>());
		
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
		
		// Create a PermutationGenerator for our formula.
		this.setPermutation(new PermutationGenerator(variables.size()));
		
		// Get rid of the leading FORALL Quantifications, if there are any.
		this.formula = removeForall(f);
		
	}
	
	/**
	 * Collect all variable names, if they are not given by previous conversion step.
	 * @param f The formula, from which the variables should be collected.
	 */
	private void collectVariables(Formula f) {
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
	private void collectTerms(Term t) {
		if (t.getSubterms().size() == 0) {
			String name = t.getTopSymbol().getName();
			if (!variables.contains(name)) {
				variables.add(name);
			}
		} else {
			for(Term st: t.getSubterms()) {
				/*if (st.getTopSymbol().getArity() != 0) {
					String name = st.getTopSymbol().getName();
					if (!functionSymbols.contains(name)) {
						functionSymbols.add(name);
					}
				}*/
				collectTerms(st);
			}
		}
	}

	
	/**
	 * 
	 * @param f
	 */
	private Formula removeForall(Formula f) {
		if (f instanceof ForallQuantification) {
			return removeForall(((ForallQuantification) f).getArg());
		} else {
			return f;
		}
	}

	/**
	 * 
	 * 
	 * TODO: 
	 * 
	 * @return
	 */
	public Formula getNext() {
		// TODO ...
		
		// Create a new Formula-Instance, so that the given Formula stays unchanged.
		RelationFormula rf = ((RelationFormula) this.formula);
		Formula g = new RelationFormula(rf.getName(), rf.getTerms());
		
		// Get the List with a new permutation and generate the corresponding replacement rules.
		// EXAMPLE: [0, 0, 6, 4] if our formula has 4 variables...
		//  - replace the first and second variable with the first term of the universe
		//  - replace the third variable with the seventh term of the universe
		//  - replace the fourth variable with the fifth term of the universe
		ArrayList<Integer> currentPerm = permutation.getNext();
		replacementRules.clear();
		
		// Run thru our current permutation and generate the corresponding replacement rules.
		// INFO: i is the variableIndex regarding the formula
		//       currentPerm.get(i) is the termIndex regarding the universe
		for (int i = 0; i != currentPerm.size(); i++) {
			while (currentPerm.get(i) >= universe.size()) {
				// expand our universe if necessary
				universe.add(termEnumerator.getNext());
			}
			
			// add rule
			replacementRules.put(
					variables.get(i),
					universe.get(currentPerm.get(i))
				);
		}
		
		// execute the replacements here, with the rules of above
		g = replace(g);
		
		
		// return the new generated Formula
		return g;
	}

	/**
	 * 
	 * @param f
	 * @return
	 */
	private Formula replace(Formula f) {
		if (f instanceof ExistsQuantification) {
			throw new UnsupportedOperationException(
					"Formula is not in SkolemForm, in HerbrandExpander.replace() : " + 
					f.toString());
		} else if (f instanceof ForallQuantification) {
			throw new UnsupportedOperationException(
					"Unexpected Formula in HerbrandExpander.replace() : " + 
					f.toString());
		} else if (f instanceof Conjunction) {
			Conjunction c = ((Conjunction) f);
			return new Conjunction(replace(c.getLeftArg()), replace(c.getRightArg()));
		} else if (f instanceof Disjunction) {
			Disjunction d = ((Disjunction) f);
			return new Disjunction(replace(d.getLeftArg()), replace(d.getRightArg()));
		} else if (f instanceof Negation) {
			Negation n = ((Negation) f);
			return new Negation(replace(n.getArg()));
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = ((RelationFormula) f);
			
			for(int i = 0; i != rf.getTerms().size(); i++) {
				rf.getTerms().set(i, replaceTerms(rf.getTerms().get(i)));
			}
			
			return rf;
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in HerbrandExpander.replace() : " + 
					f.toString());
		}
	}
	
	/**
	 * Replace the terms, if their is a corresponding replacement rule.
	 * @param t
	 * 				The term to check for a defined replacement.
	 * @return The term, whether it is replaced or not.
	 */
	private Term replaceTerms(Term t) {
		if (t.getSubterms().size() == 0) {
			if (replacementRules.containsKey(t.getTopSymbol().getName())) {
				return replacementRules.get(t.getTopSymbol().getName());
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
	
}
