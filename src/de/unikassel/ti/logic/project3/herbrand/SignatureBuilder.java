package de.unikassel.ti.logic.project3.herbrand;

import de.unikassel.ti.logic.project3.model.*;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Valentyn on 09.02.2014.
 */
public class SignatureBuilder {

    private static ArrayList<FunctionSymbol> functionSymbols = new ArrayList<FunctionSymbol>();

    public static Signature build(Formula f) throws Exception {

        buildSignature(f);

        Signature newSig = new Signature();

        for (FunctionSymbol s : functionSymbols) {
            newSig.add(s);
        }

        return newSig;
    }

    private static void buildSignature(Formula formula) throws Exception {

        if (formula.getClass().equals(ForallQuantification.class)) {
            final ForallQuantification allQuantor = (ForallQuantification) formula;
            final Formula arg = allQuantor.getArg();

            buildSignature(arg);
        }

        else if (formula.getClass().equals(ExistsQuantification.class)) {
            throw new Exception("ERROR: EXISTS not allowed. Skolemize it bitch!");
        } else if (formula.getClass().equals(Disjunction.class)) {
            final Disjunction disjunction = (Disjunction) formula;

            final Formula leftArg = disjunction.getLeftArg();
            final Formula rightArg = disjunction.getRightArg();

            buildSignature(leftArg);
            buildSignature(rightArg);

        } else if (formula.getClass().equals(Conjunction.class)) {
            final Conjunction conjunction = (Conjunction) formula;

            final Formula leftArg = conjunction.getLeftArg();
            final Formula rightArg = conjunction.getRightArg();

            buildSignature(leftArg);
            buildSignature(rightArg);

        } else if (formula.getClass().equals(Negation.class)) {
            final Negation negation = (Negation) formula;
            final Formula arg = negation.getArg();
            buildSignature(arg);

        } else if (formula.getClass().equals(RelationFormula.class)) {
            final RelationFormula relation = (RelationFormula) formula;
            final Vector<Term> terms = relation.getTerms();
            for (Term t : terms) {
                final FunctionSymbol topSymbol = t.getTopSymbol();

                if (!functionSymbols.contains(topSymbol)) {
                    functionSymbols.add(topSymbol);
                }

                collectFunctionSymbols(t);
            }
        }
    }

    private static void collectFunctionSymbols(Term t) {
        if (t.getSubterms().size() > 0) {
            for (Term st : t.getSubterms()) {
                final FunctionSymbol topSymbol = st.getTopSymbol();
                if (!functionSymbols.contains(topSymbol)) {
                    functionSymbols.add(topSymbol);
                }
                collectFunctionSymbols(st);
            }
        } else {
            final FunctionSymbol symbol = t.getTopSymbol();

            if (!functionSymbols.contains(symbol)) {
                functionSymbols.add(symbol);
            }

            return;
        }
    }

}
