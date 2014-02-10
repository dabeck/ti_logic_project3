package de.unikassel.ti.logic.project3.herbrand;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

public class Gilmore {

	private static ISolver solver = SolverFactory.newDefault();
	
	// TODO: list of clauses
		
	
	// TODO: erzeuge Mapping von prädikatenlogischen Formeln auf aussagenlogische
	
	// TODO: umwandeln der Formel, die HerbrandExpander hergibt, in Klauseln
	
	
	
	
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
