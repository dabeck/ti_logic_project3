package de.unikassel.ti.logic.project3.herbrand;

import java.util.ArrayList;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;

public class Gilmore {

	private static ISolver solver = SolverFactory.newDefault();
	private static ArrayList<ArrayList<Formula>> conjunctionsForCnf = new ArrayList<ArrayList<Formula>>();
	
	
	// TODO: erzeuge Mapping von prädikatenlogischen Formeln auf aussagenlogische
	
	// TODO: umwandeln der Formel, die HerbrandExpander hergibt, in Klauseln
	
	// TODO: list of clauses
	private static ArrayList<Formula> convertCnfToClauses(Formula f) {
				
		if (f.getClass().equals(Conjunction.class)) {
			
			Conjunction con = (Conjunction) f;
			Formula leftArg = con.getLeftArg();
			Formula rightArg = con.getRightArg();
			
			// case 1: both args are predicates
			if (leftArg.getClass().equals(RelationFormula.class) || leftArg.getClass().equals(Negation.class) && (rightArg.getClass().equals(RelationFormula.class) || rightArg.getClass().equals(Negation.class))) {
				ArrayList<Formula> newForLeft = new ArrayList<Formula>();
				newForLeft.add(leftArg);
				conjunctionsForCnf.add(newForLeft);
				
				ArrayList<Formula> newForRight = new ArrayList<Formula>();
				newForRight.add(rightArg);
				conjunctionsForCnf.add(newForRight);
			} 
			// left branch is disjunction and right branch is conjunction
			else if (leftArg.getClass().equals(Disjunction.class) && rightArg.getClass().equals(Conjunction.class)) {
				convertCnfToClauses(rightArg);
				
				ArrayList<Formula> newClauseForLeft = convertCnfToClauses(leftArg);
				conjunctionsForCnf.add(newClauseForLeft);
			}
			
			// left branch is conjunction and right branch is disjunction
			else if (leftArg.getClass().equals(Conjunction.class) && rightArg.getClass().equals(Disjunction.class)) {
				
				convertCnfToClauses(leftArg);

				ArrayList<Formula> newClause = convertCnfToClauses(rightArg);
				conjunctionsForCnf.add(newClause);
				
			} 
			// TODO: more cases
		} // end of Conjunction handling

		// Disjunction Handling
		else if (f.getClass().equals(Disjunction.class)) {
			
			Disjunction dis = (Disjunction) f;
			Formula leftArg = dis.getLeftArg();
			Formula rightArg = dis.getRightArg();
			// Negation Handling
			if (f.getClass().equals(Negation.class)) {
				Negation n = (Negation) f;
				
				ArrayList<Formula> newClause = new ArrayList<Formula>();
				newClause.add(n);
				return newClause;				
				
			} // end of Negation handling
				
			// both branches are RelationalFormulas?
			else if ((leftArg.getClass().equals(RelationFormula.class) || leftArg.getClass().equals(Negation.class) ) && rightArg.getClass().equals(RelationFormula.class) || rightArg.getClass().equals(Negation.class)) {
				ArrayList<Formula> newClause = new ArrayList<Formula>();
				newClause.add(leftArg);
				newClause.add(rightArg);
				return newClause;
			} 
			// left branch is RelationalFormula and right branch is Disjunction
			else if (leftArg.getClass().equals(RelationFormula.class) && rightArg.getClass().equals(Disjunction.class) ) {
				ArrayList<Formula> newClause = new ArrayList<Formula>();
				newClause.add(leftArg);
				newClause.addAll(convertCnfToClauses(rightArg));
				return newClause;
			}
			// right branch is RelationalFormula and left branch is Disjunction
			else if (leftArg.getClass().equals(Disjunction.class) && rightArg.getClass().equals(RelationFormula.class)) {
				ArrayList<Formula> newClause = new ArrayList<Formula>();
				newClause.add(rightArg);
				newClause.addAll(convertCnfToClauses(leftArg));
				
				return newClause;
			} 
			// both branches are Disjunctions
			else if (leftArg.getClass().equals(Disjunction.class) && rightArg.getClass().equals(Disjunction.class)) {
				
				ArrayList<Formula> newClause = new ArrayList<Formula>();
				newClause.addAll(convertCnfToClauses(leftArg));
				newClause.addAll(convertCnfToClauses(rightArg));
				return newClause;
				
			}
			
			
		} // end of Disjunction Handling
		// handling of relational formulas
		else if (f.getClass().equals(RelationFormula.class)) {
			
			ArrayList<Formula> newClause = new ArrayList<Formula>();
			newClause.add(f);
			return newClause;
			
		}
		return null;
		
	}

    static Integer counter = 1;

//    static Map<Formula, Integer> solverMap = new HashMap<>();
    static BiMap<Formula, Integer> valueToKey = HashBiMap.create();

    private static void mapTheList(ArrayList<ArrayList<Formula>> input) {
        // reset the counter
        counter = 1;

        for (ArrayList<Formula> clause : input) {
            for (Formula formula : clause) {
                if (valueToKey.containsKey(formula)) {
                    continue;
                } else {
                    int z  = counter;
                    // check if the formula is negated
                    if (formula.getClass().equals(Negation.class)) {
                        z *= -1;
                    }
                    valueToKey.put(formula, z);
                    counter++;
                }
            }
        }
    }


    // This function returns a formula for a specified key.
    public Formula getKeyOfValue(final Integer value) {
        return (valueToKey.containsValue(value) ? valueToKey.inverse().get(value) : null);
    }


	
}
