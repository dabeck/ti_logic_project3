package de.unikassel.ti.logic.project3.herbrand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Formula;

public class Gilmore {

	private static ISolver solver = SolverFactory.newDefault();
	
	// TODO: list of clauses
		
	
	// TODO: erzeuge Mapping von prädikatenlogischen Formeln auf aussagenlogische
	
	// TODO: umwandeln der Formel, die HerbrandExpander hergibt, in Klauseln
	
	private static ArrayList<ArrayList<Formula>> convertCnfToClauses(Formula f) {
		
		ArrayList<ArrayList<Formula>> result = null;
		
		if (f.getClass().equals(Conjunction.class)) {
			
		//	Conjunction con = f;
			
		}
		
		
		return result;
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
                    // check if negated
                    if (false) {
                        z *= -1;
                    }
                    valueToKey.put(formula, z);
                    counter++;
                }
            }
        }
//        valueToKey = ImmutableBiMap.copyOf(Collections.unmodifiableMap(solverMap));
    }


    // This function returns a formula for a specified key.
    public Formula getKeyOfValue(final Integer value) {
//        System.out.println("The key of value " + value + " is " +
//                (valueToKey.containsValue(value) ? valueToKey.inverse().get(value) : "not present") + ".");
        return (valueToKey.containsValue(value) ? valueToKey.inverse().get(value) : null);
    }


	public static void test() {
		
		int[] lits = null;
		VecInt arg0 = new VecInt(lits); 
		try {
			solver.addClause(arg0);
		} catch (ContradictionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
