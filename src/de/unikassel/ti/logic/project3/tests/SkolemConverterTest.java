package de.unikassel.ti.logic.project3.tests;

import java.io.StringReader;

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
		Assert.assertEquals(
				"FORALL w (P(f(c0),c1,g(f(g(w)))) | Q(f0(w),c0))",
				f.toString());
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
		Assert.assertEquals(
				"FORALL y FORALL u P(c2,y,f1(y),u,f2(y,u),f3(y,u))",
				f.toString());
	}

}
