package de.unikassel.ti.logic.project3.converters;

import java.io.StringReader;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.model.Formula;

public class SkolemConverterTest {

	public static void main(String[] args) {
	    // parser p = new parser(new Scanner(new InputStreamReader(System.in)));
	    parser p = new parser(new Scanner(new StringReader("EXISTS x ( EXISTS y ( FORALL w ( EXISTS z ( P(x,y,w) | Q(z,x) ) ) ) )")));
	    Formula f = null;
	    
		try {
		    f = (Formula) p.parse().value;
		    System.out.println("Input:\n" + f.toString());

		    f = SkolemConverter.convert(f);
		    
		    System.out.println("\nFormula in Skolem normal form:\n" + f.toString());
		}	    
		catch (Exception e) {
		    System.out.println("Something went seriously wrong: " + e.getMessage());
		    e.printStackTrace();
		    // if the formula cannot be parsed, no further steps are neccessary
		}
	}

}
