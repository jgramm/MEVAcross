package org.broadinstitute.MEVA.algorithm;

public class Pair<X,Y> {

	// just a tuple wrapper
	
	public final X x; 
	public final Y y; 
	  
	public Pair(X x, Y y) { 
		this.x = x; 
		this.y = y; 
	}

	@Override
	public String toString(){
		return x.toString() + "\t" + y.toString();
	}
	
	
	
	@Override
	public int hashCode(){
		return 1;
	}
}
