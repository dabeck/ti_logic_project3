package de.unikassel.ti.logic.project3.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.unikassel.ti.logic.project3.helper.PermutationGenerator;

public class PermutationGeneratorTest {
	
	@Test
	public void testForN2() {
		
		// n = ...
		int numberOfVariables = 2;
		
		// Create our PermutationGenerator for a formula with n variables
		PermutationGenerator permutation = new PermutationGenerator(numberOfVariables);
		ArrayList<Integer> currentPerm = permutation.getNext();
		
		// Verification
		Assert.assertEquals("[0, 0]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[0, 1]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[1, 0]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[1, 1]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[0, 2]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[1, 2]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[2, 0]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[2, 1]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[2, 2]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[0, 3]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[1, 3]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[2, 3]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[3, 0]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[3, 1]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[3, 2]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[3, 3]", currentPerm.toString());
		currentPerm = permutation.getNext();
		Assert.assertEquals("[0, 4]", currentPerm.toString());
	}
}
