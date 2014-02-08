package de.unikassel.ti.logic.project3;

import java.io.StringReader;

import de.unikassel.ti.logic.project3.model.Formula;

public class Tool {

    public static void main(String[] args) {

	    // parser p = new parser(new Scanner(new InputStreamReader(System.in)));
	    parser p = new parser(new Scanner(new StringReader("- EXISTS x ( R(f(x), g(g(c,x))) & -P(f(f(d))))")));
	    Formula f = null;
	    
	    
	    // 1. parse formula
		try {
		    f = (Formula) p.parse().value;
		    System.out.println("Input:\n" + f.toString());

		    // TODO: 2. convert formula to PNF. Has to return a formula

		    System.out.println("\nFormula in postive normal form:\n" + f.toString());
		    // TODO: 3. convert formula to prenex normal form. Has to return a formula
		    
		    System.out.println("\nFormula in prenex normal form:\n" + f.toString());
		    // TODO: 4. convert formula to Skolem normal form. Has to return a formula
		    
		    System.out.println("\nFormula in Skolem normal form:\n" + f.toString());
		}	    
		catch (Exception e) {
		    System.out.println("Something went seriously wrong: " + e.getMessage());
		    e.printStackTrace();
		    // if the formula cannot be parsed, no further steps are neccessary
		}
		
    }
	    
	    
}

