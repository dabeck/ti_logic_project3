package de.unikassel.ti.logic.project3.herbrand;

import java.util.ArrayList;

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
