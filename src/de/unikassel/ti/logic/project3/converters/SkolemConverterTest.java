package de.unikassel.ti.logic.project3.converters;

import java.io.StringReader;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.model.Formula;

public class SkolemConverterTest {

	public static void main(String[] args) {
		
		// parser p = new parser(new Scanner(new InputStreamReader(System.in)));
	    //parser p = new parser(new Scanner(new StringReader("EXISTS x ( FORALL w ( EXISTS z ( P(x,w) | Q(z,x) )  ) )")));
		parser p = new parser(new Scanner(new StringReader("P(x)")));
	    Formula f = null;
		
		try {
			f = (Formula) p.parse().value;
			
			// Skolem-Convertion
			f = SkolemConverter.convert(f);
			
			System.out.println("Input:\n" + f.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
