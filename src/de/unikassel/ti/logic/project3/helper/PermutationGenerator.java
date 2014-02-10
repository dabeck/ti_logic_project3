package de.unikassel.ti.logic.project3.helper;

import java.util.ArrayList;

public class PermutationGenerator {

	private ArrayList<Integer> termIndices = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> permutations = new ArrayList<ArrayList<Integer>>();
	
	private ArrayList<ArrayList<Integer>> permHistory = new ArrayList<ArrayList<Integer>>();
	
	private int numberOfVariables = 0;
	private int returnIndex = -1;
	
	/**
	 * 
	 * @param numberOfVariables
	 */
	public PermutationGenerator (int numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
		//this.termIndices.add(termIndices.size());
		//permute("");
	}

	/**
	 * 
	 * @param numberOfVariables
	 * @param start
	 */
	private void permute(String start) {
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
				permute(start + x);
			}
		}
	}
	
	/**
	 * Get the next permutation of the termIndices.
	 * 
	 * @return The Permutation of the termIndices of the herbrand universe.
	 */
	public ArrayList<Integer> getNext() {
		
		// run thru our list of permutations
		do {
			returnIndex++;
			if (returnIndex == permutations.size()) {
				// we reached end of current permutations-list,
				// so we have to expand our termIndices-list,
				// re-generate our permutations-list and
				// reset out returnIndex
				termIndices.add(termIndices.size());
				permute("");
				returnIndex = 0;
			}
		} while (permHistory.contains(permutations.get(returnIndex)));
		
		permHistory.add(permutations.get(returnIndex));
		
		return permutations.get(returnIndex);
	}
	
}
