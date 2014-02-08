package de.unikassel.ti.logic.project3.converters;

import de.unikassel.ti.logic.project3.factories.UniqueSymbolFactory;
import de.unikassel.ti.logic.project3.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PrenexConverter {

    private static UniqueSymbolFactory symbolFactory;
    private static ArrayList<String> variables = new ArrayList<String>();
    private static ArrayList<String> renamedVariables = new ArrayList<String>();
    private static Formula root;

    /**
     * Collects all variables into our lists
     *
     * @param f              The formula
     * @param boundVariables Map of bound variables
     */
    private static void renameBoundVariables(Formula f, Map<String, String> boundVariables) {

        if (boundVariables == null) {
            boundVariables = new HashMap<String, String>();
            variables.clear();
        }

        if (f instanceof Negation) {
            renameBoundVariables(((Negation) f).getArg(), boundVariables);
        } else if (f instanceof ExistsQuantification) {

            String var = ((ExistsQuantification) f).getVariable();
            String newVar = symbolFactory.getNewVariableString();

            while (variables.contains(newVar)) {
                newVar = symbolFactory.getNewVariableString();
            }
            variables.add(newVar);

            if (!boundVariables.containsKey(var)) {
                boundVariables.put(var, newVar);
                renamedVariables.add(newVar);
            }
            ((ExistsQuantification) f).setVariable(newVar);

            renameBoundVariables(((ExistsQuantification) f).getArg(),
                    boundVariables);

        } else if (f instanceof ForallQuantification) {

            String var = ((ForallQuantification) f).getVariable();
            String newVar = symbolFactory.getNewVariableString();

            while (variables.contains(newVar)) {
                newVar = symbolFactory.getNewVariableString();
            }
            variables.add(newVar);

            if (!boundVariables.containsKey(var)) {
                boundVariables.put(var, newVar);
                renamedVariables.add(newVar);
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
     * @param t              Term
     * @param boundVariables Map of bound variables
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

    /**
     * Create existence conclusion for formula f.
     * <p/>
     * Free variables get bound by existence quantification.
     * When traversing the formula
     * tree, a variable is tested whether she is in the collection of the bound variables. If she's not,
     * the variable is free and gets further bound by a new existence quantification inserted as a new
     * root of the formula.
     *
     * @param f The formula
     */
    private static void existanceConclusion(Formula f) {
        if (f instanceof Negation) {
            existanceConclusion(((Negation) f).getArg());
        } else if (f instanceof ExistsQuantification) {
            existanceConclusion(((ExistsQuantification) f).getArg());
        } else if (f instanceof ForallQuantification) {
            existanceConclusion(((ForallQuantification) f).getArg());
        } else if (f instanceof Conjunction) {
            existanceConclusion(((Conjunction) f).getLeftArg());
            existanceConclusion(((Conjunction) f).getRightArg());
        } else if (f instanceof Disjunction) {
            existanceConclusion(((Disjunction) f).getLeftArg());
            existanceConclusion(((Disjunction) f).getRightArg());
        } else if (f instanceof RelationFormula) {
            RelationFormula rf = (RelationFormula) f;

            for (Term t : rf.getTerms()) {
                checkForFreeVarAndCreateExists(t);
            }
        }
    }

    /**
     * @param t Term to check
     */
    private static void checkForFreeVarAndCreateExists(Term t) {
        if (t.getSubterms().size() > 0) {
            for (Term st : t.getSubterms()) {
                checkForFreeVarAndCreateExists(st);
            }
        } else {
            String symbolName = t.getTopSymbol().getName();

            if (!renamedVariables.contains(symbolName)) {

                root = new ExistsQuantification(
                        symbolName, root);
            }
        }
    }

    public static Formula convert(Formula f) {
        root = f;
        symbolFactory = UniqueSymbolFactory.getFactoryInstance();
        renameBoundVariables(f, null);
        existanceConclusion(f);
        // IMPORTANT: after existence conclusion, the next operation has to be performed on the new "root"!!!!!!
        Formula transformedToPrenexNF = root.transformToPrenexNF();

//		return transformedToPrenexNF;
        return root;
    }

    public static ArrayList<String> getVariableList() {
        return variables;
    }

}
