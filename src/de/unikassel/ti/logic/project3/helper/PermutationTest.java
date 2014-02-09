package de.unikassel.ti.logic.project3.helper;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class PermutationTest {

	@Test
	public void test() {
		
		ArrayList<ArrayList<Integer>> p = PermutationGenerator.permute(2);
		
		System.out.println(p.toString());
		
		Assert.assertEquals("[[0, 0], [0, 1], [1, 0], [1, 1]]",p.toString());
	}
}
