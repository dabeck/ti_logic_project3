package de.unikassel.ti.logic.project3.tests;

import java.io.StringReader;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.converters.SkolemConverter;
import de.unikassel.ti.logic.project3.model.Formula;

public class SkolemConverterTest {

	@Test
	public void testFormula1() {
		parser p = new parser(new Scanner(new StringReader(
		        "EXISTS x ( EXISTS y ( FORALL w ( EXISTS z ( P(f(x), y, g(f(g(w)))) | Q(z, x) ) ) ) )")));
		Formula f = null;

		try {
			f = (Formula) p.parse().value;
			f = SkolemConverter.convert(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(f.toString());
		Assert.assertEquals("FORALL w (P(f(c0),c1,g(f(g(w)))) | Q(f0(w),c0))", f.toString());
	}
	
	@Test
	public void testFormula2() {
		parser p = new parser(new Scanner(new StringReader(
		        "EXISTS x ( FORALL y ( EXISTS z ( FORALL u ( EXISTS v ( EXISTS w ( P(x, y, z, u, v, w) ) ) ) ) ) )")));
		Formula f = null;

		try {
			f = (Formula) p.parse().value;
			f = SkolemConverter.convert(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(f.toString());
		Assert.assertEquals("FORALL y FORALL u P(c2,y,f1(y),u,f2(y,u),f3(y,u))", f.toString());
	}



	@Test
	public void testFormula3() {
		parser p = new parser(new Scanner(new StringReader(
		        "- EXISTS x ( R(f(x), g(h(c,x))) & -P(f(f(d))))")));
		Formula f = null;

		try {
			f = (Formula) p.parse().value;
			f = SkolemConverter.convert(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(f.toString());
		Assert.assertEquals(
				"-(R(f(c3),g(h(c,c3))) & -P(f(f(d))))",
				f.toString());
	}


    // Distributive Property:
    // Rule of replacement 1
    @Test public void testFormula4() {

        final String string = "P(x) | (Q(y) & R(z))";
        final String output = "(P(x) | Q(y)) & (P(x) | R(z))";

        parser p = new parser(new Scanner(new StringReader(string)));

        Formula f =  null;
        Formula fo = null;
        try {
            f = (Formula) p.parse().value;
            fo = SkolemConverter.convert(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Input : " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }
}
