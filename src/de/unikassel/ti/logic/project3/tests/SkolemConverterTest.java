package de.unikassel.ti.logic.project3.tests;

import java.io.InputStreamReader;
import java.io.StringReader;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.converters.SkolemConverter;
import de.unikassel.ti.logic.project3.model.Formula;

public class SkolemConverterTest {

	public static void main(String[] args) {
		int chosenTestExample = 1;
		Formula f = null;
		parser p;
		
		switch (chosenTestExample) {
		case 0:
			p = new parser(new Scanner(new StringReader("EXISTS x ( EXISTS y ( FORALL w ( EXISTS z ( P(f(x), y, g(f(g(w)))) | Q(z, x) ) ) ) )")));
			break;
		case 1:
			p = new parser(new Scanner(new StringReader("EXISTS x ( FORALL y ( EXISTS z ( FORALL u ( EXISTS v ( EXISTS w ( P(x, y, z, u, v, w) ) ) ) ) ) )")));
			break;
		default:
			p = new parser(new Scanner(new InputStreamReader(System.in)));
		}
	    
		try {
		    f = (Formula) p.parse().value;
		    System.out.println("Input:\n" + f.toString());

		    f = SkolemConverter.convert(f);
		    
		    System.out.println("\nFormula in Skolem normal form:\n" + f.toString());
		}	    
		catch (Exception e) {
		    System.out.println("Something went seriously wrong:\n" + e.getMessage());
		    e.printStackTrace();
		    // if the formula cannot be parsed, no further steps are neccessary
		}
	}

}
