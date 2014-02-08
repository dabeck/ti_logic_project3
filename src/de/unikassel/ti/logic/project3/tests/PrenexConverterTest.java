/**
 * 
 */
package de.unikassel.ti.logic.project3.tests;

import java.io.StringReader;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.converters.PrenexConverter;
import de.unikassel.ti.logic.project3.model.Formula;

public class PrenexConverterTest {
	
	@Test
	public void testRenameBoundVariables() {
		parser p = new parser(new Scanner(new StringReader(
		        "FORALL x R(f(x,y), g(x,z))")));
		Formula f = null;
		ArrayList<String> allVariables = new ArrayList<String>();

		try {
			f = (Formula) p.parse().value;
			PrenexConverter.convert(f);
			allVariables = PrenexConverter.getVariableList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals("[x0, y, z]", allVariables.toString());
	}
	
}
