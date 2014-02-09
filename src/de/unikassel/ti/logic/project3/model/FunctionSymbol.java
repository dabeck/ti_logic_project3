package de.unikassel.ti.logic.project3.model;

import java.util.SortedSet;
import java.util.TreeSet;

public class FunctionSymbol {
	
	private String name;
	private int arity;
	
	public FunctionSymbol(String n, int a) {
		name = n;
		arity = a;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getArity() {
		return arity;
	}
	
	public String toString() {
		return name;
	}

    @Override
	public boolean equals(Object s) {
		return name.equals(((FunctionSymbol) s).getName()) && arity == ((FunctionSymbol) s).getArity();
	}
}
