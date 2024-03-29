package de.unikassel.ti.logic.project3;

/*
import parser;
import scanner;
import formula;
*/

import java.io.StringReader;

import de.unikassel.ti.logic.project3.model.Formula;

public class ParseFormulaExample {

    public static void main(String[] args) {

	try {
	    // parser p = new parser(new Scanner(new InputStreamReader(System.in)));
	    parser p = new parser(new Scanner(new StringReader("- EXISTS x ( R(f(x), g(g(c,x))) & -P(f(f(d))))")));

	    Formula f = (Formula) p.parse().value;
	    System.out.println("Input: " + f.toString());

	}
	catch (Exception e) {
	    System.out.println("Something went seriously wrong: " + e.getMessage());
	    e.printStackTrace();
	}
    }
}

