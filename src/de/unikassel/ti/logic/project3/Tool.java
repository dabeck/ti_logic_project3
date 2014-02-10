package de.unikassel.ti.logic.project3;

import java.io.StringReader;
import java.util.ArrayList;

import de.unikassel.ti.logic.project3.converters.PrenexConverter;
import de.unikassel.ti.logic.project3.converters.SkolemConverter;
import de.unikassel.ti.logic.project3.converters.SkolemToCNFConverter;
import de.unikassel.ti.logic.project3.herbrand.HerbrandExpander;
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

		    // 2. convert formula to PNF. Has to return a formula
		    Formula transformedToPositiveNF = f.transformToPositiveNF();
		    System.out.println("\nFormula in postive normal form:\n" + f.toString());
		    
		    // 3. convert formula to prenex normal form. Has to return a formula
		    Formula transformedToPrenexNF = PrenexConverter.convert(transformedToPositiveNF);		    
		    System.out.println("\nFormula in prenex normal form:\n" + f.toString());
		    
		    // 4. convert formula to Skolem normal form. Has to return a formula
		    SkolemConverter.setVariables(PrenexConverter.getVariableList());
		    Formula transformedToSkolemNF = SkolemConverter.convert(transformedToPrenexNF);
		    
		    System.out.println("\nFormula in Skolem normal form:\n" + f.toString());
		    
		    // 5. Transform to KNF
		    //Formula transformedToKnf = SkolemToCNFConverter.convert(transformedToSkolemNF);
		    System.out.println("\nFormula in KNF:\n" + f.toString());
		    
		    // 6. Herbrand Expansion and Gilmore
		    ArrayList<Formula> formulasExpanded = new ArrayList<>();

		    HerbrandExpander he = new HerbrandExpander(transformedToSkolemNF);
		    
		    while (true/*he.hasNext()*/) {
		    	Formula next = he.getNext();
		    }
		    
		}	    
		catch (Exception e) {
		    System.out.println("Something went seriously wrong: " + e.getMessage());
		    e.printStackTrace();
		    // if the formula cannot be parsed, no further steps are neccessary
		}
		
    }
	    
	    
}

