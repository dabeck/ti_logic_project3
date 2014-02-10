package de.unikassel.ti.logic.project3.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.unikassel.ti.logic.project3.helper.PermutationGenerator;

public class PermutationGeneratorTest {

	@Test
	public void testGetNext() {
		// n = ...
		int numberOfVariables = 2;
		
		// Create our PermutationGenerator for a formula with n variables
		PermutationGenerator permutation = new PermutationGenerator(numberOfVariables);
		ArrayList<Integer> currentPerm = permutation.getNext();
		
		// Verification
		System.out.println(currentPerm.toString());
		Assert.assertEquals("[0, 0]", currentPerm.toString());
	}
}
