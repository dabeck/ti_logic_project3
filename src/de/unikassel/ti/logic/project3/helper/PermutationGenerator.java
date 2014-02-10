package de.unikassel.ti.logic.project3.helper;

import java.util.ArrayList;

public class PermutationGenerator {

	private ArrayList<Integer> termIndices = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> permutations = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * 
	 * @param numberOfVariables
	 */
	public PermutationGenerator (int numberOfVariables) {
		// Initialize termIndices List.
		initTermIndices(numberOfVariables);
		
		this.permutations = new ArrayList<ArrayList<Integer>>();
	}

	/**
	 * 
	 * @param numberOfVariables
	 * @param start
	 */
	private void permute(int numberOfVariables, String start) {
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
	
	void initTermIndices(int numberOfVariables) {
		termIndices.clear();
		for (int i = 0; i != numberOfVariables; i++) {
			termIndices.add(i);
		}
	}
	
	/**
	 * Get the next permutation of the termIndices.
	 * 
	 * TODO: Check if there are permutations left,
	 * which wasn't used before. Otherwise get the
	 * next term from the herbrand universe and
	 * generate new permutations. (Info: Be sure to
	 * remember already returned permutations, to
	 * prevent duplicates.)
	 * 
	 * @return
	 */
	public ArrayList<Integer> getNext() {
		return null;
	}
	
}
