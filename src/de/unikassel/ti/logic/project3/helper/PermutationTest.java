package de.unikassel.ti.logic.project3.helper;

import java.util.ArrayList;
import java.util.Arrays;

public class PermutationTest {

	private static ArrayList<ArrayList<Integer>> permutations = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args) {
		ArrayList<Integer> termIndices = new ArrayList<Integer>(Arrays.asList(new Integer[] { 0, 1, 2 }));
		int numberOfVariables = 7;
		
		permute(termIndices, numberOfVariables, "");
		System.out.println(permutations.toString());
	}
	
	/**
	 * 
	 * 
	 * @see http://stackoverflow.com/questions/13157656/permutation-of-an-array-with-repetition-in-java
	 * 
	 * @param termIndices
	 * @param n
	 * @param start
	 */
	private static void permute(ArrayList<Integer> termIndices, int numberOfVariables, String start){
		if (start.length() >= numberOfVariables) {
			char[] charArray = start.toCharArray();
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			for (int i = 0; i != numberOfVariables; i++) {
				arrayList.add(Integer.parseInt(String.valueOf(charArray[i])));
			}
			if (!permutations.contains(arrayList)) {
				permutations.add(arrayList);
			}
		} else {
			for (Integer x : termIndices) {
				permute(termIndices, numberOfVariables, start + x);
			}
		}
	}

}
