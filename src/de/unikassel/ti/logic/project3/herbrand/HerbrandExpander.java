package de.unikassel.ti.logic.project3.herbrand;

import java.util.ArrayList;

import de.unikassel.ti.logic.project3.helper.PermutationGenerator;
import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;
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
	private ArrayList<Integer> replacementRules = new ArrayList<Integer>();
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Integer> getReplacementRules() {
		return replacementRules;
	}

	/**
	 * 
	 * @param replacementRules
	 */
	public void setReplacementRules(ArrayList<Integer> replacementRules) {
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
	 * HerbrandExpander.
	 * 
	 * @param f
	 */
	public HerbrandExpander(Formula f) {
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
		
		// TODO: create a new Formula-Instance, so that the given Formula stays unchanged
		// Formula g = new RelationFormula(null, null); // <- just pseudocode, still has to be implemented
		
		// get the List with a new permutation of the replacement rules
		// example: [0, 0, 6, 4] if our formula has 4 variables...
		// replace the first and second variable with the first term of the universe
		// replace the third variable with the seventh term of the universe
		// replace the fourth variable with the fifth term of the universe
		// replacementRules = permutation.getNext(); // <- this line should work, but the method has to be implemented
		
		
		// TODO: execute the replacement here, with the rules of above
		// replacement();
		
		
		// return the new generated Formula
		return null;
	}

	/**
	 * TODO: Execute the replacement rules here...
	 */
	private void replacement() {
		// hint: see replacement in SkolemConverter for example
		// this.replacementRules would be something similar to the SkolemConverter.replExists
	}
	
}
