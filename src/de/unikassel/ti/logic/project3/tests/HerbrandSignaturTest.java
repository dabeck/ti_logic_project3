package de.unikassel.ti.logic.project3.tests;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.herbrand.SignatureBuilder;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.FunctionSymbol;
import de.unikassel.ti.logic.project3.model.Signature;
import de.unikassel.ti.logic.project3.parser;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created by Valentyn on 09.02.2014.
 */
public class HerbrandSignaturTest {

    @Test public void formulaTest() throws Exception {

        final String input = "FORALL x FORALL y FORALL z (R(f(x)) & S( g( h(y), y(u), r ), z))";
//        final String output = "WARNING";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Signature signature = SignatureBuilder.build(f);

        for (FunctionSymbol s : signature) {
            System.out.println((s.getName() + " " + s.getArity()));
        }

        System.out.println("Input : " + input);
        System.out.println("Output: " + f.toString());

    }
}
