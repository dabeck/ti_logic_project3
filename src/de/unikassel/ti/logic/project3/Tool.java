package de.unikassel.ti.logic.project3;

/*
import parser;
import scanner;
import formula;
*/

import java.io.StringReader;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.minisat.core.Solver;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

import de.unikassel.ti.logic.project3.model.Formula;

public class Tool {

    public static void main(String[] args) {

	    // parser p = new parser(new Scanner(new InputStreamReader(System.in)));
	    parser p = new parser(new Scanner(new StringReader("- EXISTS x ( R(f(x), g(g(c,x))) & -P(f(f(d))))")));
	    Formula f = null;
	    
	    
	    // 1. parse formula
		try {
		    // parser p = new parser(new Scanner(new InputStreamReader(System.in)));

		    f = (Formula) p.parse().value;
		    System.out.println("Input: " + f.toString());

		}
		catch (Exception e) {
		    System.out.println("Something went seriously wrong: " + e.getMessage());
		    e.printStackTrace();
		}
	    
	    // TODO: 2. convert formula to PNF
	    
	    // TODO: 3. convert formular to prenex normal form
	    
	    // TODO: 4. convert formula to Skolem normal form
	    
	    // TODO:
    }
	    
	    
}

