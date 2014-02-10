package de.unikassel.ti.logic.project3.herbrand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import de.unikassel.ti.logic.project3.TermEnumerator;
import de.unikassel.ti.logic.project3.helper.PermutationGenerator;
import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.FunctionSymbol;
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
	 * List of all variables in the formula, to prevent duplicates thru
	 * replacement.
	 */
	private ArrayList<String> variables = new ArrayList<String>();

	/**
	 * Get the list of all variables in the formula
	 * 
	 * @return
	 */
	public ArrayList<String> getVariables() {
		return variables;
	}

	/**
	 * Set the list of all variables in the formula
	 * 
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
	 * 
	 * @return
	 */
	public TermEnumerator getTermEnumerator() {
		return termEnumerator;
	}

	/**
	 * 
	 * @param termEnumerator
	 */
	public void setTermEnumerator(TermEnumerator termEnumerator) {
		this.termEnumerator = termEnumerator;
	}

	/**
	 * HerbrandExpander.
	 * 
	 * @param f
	 *            The Formula to expand.
	 */
	public HerbrandExpander(Formula f) {
		// Build a signature for our formula and create a termEnumerator with
		// it.
		Signature s = null;
		try {
			s = SignatureBuilder.build(f);

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.termEnumerator = new TermEnumerator(s);
		this.setUniverse(new ArrayList<Term>());

		// Get rid of the leading FORALL Quantifications, if there are any.
		this.formula = removeForall(f);
		
		// If there is no list of variables, collect them.
		if (variables.size() == 0) {
			collectVariables(this.formula);
		}

		// If there's still no list, there must be an error in the formula.
		if (variables.size() == 0) {
			throw new UnsupportedOperationException(
					"Cannot collect the variable names of the given formula in SkolemConverter.convert() : " +
							f.toString());
		}

		// Create a PermutationGenerator for our formula.
		this.setPermutation(new PermutationGenerator(variables.size()));
	}

	/**
	 * Collect all variable names, if they are not given by previous conversion
	 * step.
	 * 
	 * @param f
	 *            The formula, from which the variables should be collected.
	 */
	private void collectVariables(Formula f) {
		if (f instanceof ExistsQuantification) {
			throw new UnsupportedOperationException(
					"Formula is not in SkolemForm, in HerbrandExpander.collectVariables() : "
							+ f.toString());
		} else if (f instanceof ForallQuantification) {
			throw new UnsupportedOperationException(
					"Unexpected Formula in HerbrandExpander.collectVariables() : "
							+ f.toString());
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
			for (Term t : rf.getTerms()) {
				collectTerms(t);
			}
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in SkolemConverter.collectVariables() : "
							+ f.toString());
		}
	}

	/**
	 * Collect all variable names in the terms and subterms.
	 * 
	 * @param t
	 *            The Term, to check for new unique variable names.
	 */
	private void collectTerms(Term t) {
		if (t.getSubterms() == null || t.getSubterms().size() == 0) {
			String name = t.getTopSymbol().getName();
			if (!variables.contains(name)) {
				variables.add(name);
			}
		} else {
			for (Term st : t.getSubterms()) {
				/*
				 * if (st.getTopSymbol().getArity() != 0) { String name =
				 * st.getTopSymbol().getName(); if
				 * (!functionSymbols.contains(name)) {
				 * functionSymbols.add(name); } }
				 */
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

	public boolean hasNext() {
		return termEnumerator.hasNext();
	}

	/**
	 * 
	 * @return
	 */
	public Formula getNext() {
		// Create a new Formula-Instance, so that the given Formula stays
		// unchanged.
		Formula g = cloneFormula(this.formula);

		// Get the List with a new permutation and generate the corresponding
		// replacement rules.
		// EXAMPLE: [0, 0, 6, 4] if our formula has 4 variables...
		// - replace the first and second variable with the first term of the
		// universe
		// - replace the third variable with the seventh term of the universe
		// - replace the fourth variable with the fifth term of the universe
		ArrayList<Integer> currentPerm = permutation.getNext();
		replacementRules.clear();

		// Run thru our current permutation and generate the corresponding
		// replacement rules.
		// INFO: i is the variableIndex regarding the formula
		// currentPerm.get(i) is the termIndex regarding the universe
		for (int i = 0; i != currentPerm.size(); i++) {
			if (!hasNext()) {
				/*
				 * throw new UnsupportedOperationException(
				 * "Error in HerbrandExpander.getNext() : " + g.toString());
				 */
				return null;
			}

			while (currentPerm.get(i) >= universe.size()) {
				// expand our universe if necessary
				universe.add(termEnumerator.getNext());
			}

			// add rule
			replacementRules.put(variables.get(i),
					universe.get(currentPerm.get(i)));
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
	private Formula cloneFormula(Formula f) {
		if (f instanceof Conjunction) {
			Conjunction c = ((Conjunction) f);
			return new Conjunction(cloneFormula(c.getLeftArg()), cloneFormula(c.getRightArg()));
		} else if (f instanceof Disjunction) {
			Disjunction d = ((Disjunction) f);
			return new Conjunction(cloneFormula(d.getLeftArg()), cloneFormula(d.getRightArg()));
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = ((RelationFormula) f);
			Vector<Term> subTerms = new Vector<Term>();
			
			for (Term t: rf.getTerms()) {
				subTerms.add(cloneTerm(t));
			}
			
			return new RelationFormula(new String(rf.getName()), subTerms);
		} else if (f instanceof Negation) {
			Negation n = ((Negation) f);
			return new Negation(cloneFormula(n.getArg()));
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in HerbrandExpander.cloneFormula() : "
							+ f.toString());
		}
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	private Term cloneTerm(Term t) {
		if (t.getSubterms() == null || t.getSubterms().size() == 0) {
			return new Term(new FunctionSymbol(new String(t.getTopSymbol().getName()), new Integer(t.getTopSymbol().getArity())), null);
		} else {
			Vector<Term> subterms = new Vector<Term>();
			for (Term st : t.getSubterms()) {
				subterms.add(cloneTerm(st));
			}
			return new Term(new FunctionSymbol(new String(t.getTopSymbol().getName()), new Integer(t.getTopSymbol().getArity())), subterms);
		}
	}

	/**
	 * 
	 * @param f
	 * @return
	 */
	private Formula replace(Formula f) {
		if (f instanceof ExistsQuantification) {
			throw new UnsupportedOperationException(
					"Formula is not in SkolemForm, in HerbrandExpander.replace() : "
							+ f.toString());
		} else if (f instanceof ForallQuantification) {
			throw new UnsupportedOperationException(
					"Unexpected Formula in HerbrandExpander.replace() : "
							+ f.toString());
		} else if (f instanceof Conjunction) {
			Conjunction c = ((Conjunction) f);
			return new Conjunction(replace(c.getLeftArg()),
					replace(c.getRightArg()));
		} else if (f instanceof Disjunction) {
			Disjunction d = ((Disjunction) f);
			return new Disjunction(replace(d.getLeftArg()),
					replace(d.getRightArg()));
		} else if (f instanceof Negation) {
			Negation n = ((Negation) f);
			return new Negation(replace(n.getArg()));
		} else if (f instanceof RelationFormula) {
			RelationFormula rf = ((RelationFormula) f);

			for (int i = 0; i != rf.getTerms().size(); i++) {
				rf.getTerms().set(i, replaceTerms(rf.getTerms().get(i)));
			}

			return rf;
		} else {
			throw new UnsupportedOperationException(
					"Unexpected Formula in HerbrandExpander.replace() : "
							+ f.toString());
		}
	}

	/**
	 * Replace the terms, if their is a corresponding replacement rule.
	 * 
	 * @param t
	 *            The term to check for a defined replacement.
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
			for (int i = 0; i != t.getSubterms().size(); i++) {
				t.getSubterms().set(i, replaceTerms(t.getSubterms().get(i)));
			}
			return t;
		}
	}

}
