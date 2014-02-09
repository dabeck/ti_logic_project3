package de.unikassel.ti.logic.project3.helper;

import java.util.ArrayList;

public class PermutationGenerator {

	private static ArrayList<Integer> termIndices = new ArrayList<Integer>();
	private static ArrayList<ArrayList<Integer>> permutations = new ArrayList<ArrayList<Integer>>();
	
	public static ArrayList<ArrayList<Integer>> permute(int numberOfVariables) {
		// Initialize termIndices List.
		initTermIndices(numberOfVariables);
		
		// Generate permutation.
		permute(numberOfVariables, "");
		
		return permutations;
	}

	public static void permute(int numberOfVariables, String start) {
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
				permute(numberOfVariables, start + x);
			}
		}
	}
	
	private static void initTermIndices(int numberOfVariables) {
		termIndices.clear();
		for (int i = 0; i != numberOfVariables; i++) {
			termIndices.add(i);
		}
	}
	
	
	
}
