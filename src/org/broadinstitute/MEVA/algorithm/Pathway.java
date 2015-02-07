package org.broadinstitute.MEVA.algorithm;

public class Pathway {

	public static String dysregulated = "Dysregulated", notDysregulated = "Not Dysregulated";
	public static String enriched = "Enriched", depleted = "Depleted";
	
	private int size, members;
	private double pValue, FDR, NES;
	private String info, name;
	
    // if you change any of these data types, please adjust getClassN
	public Pathway(String name, String info, int size, int members, double pValue, 
			double FDR, double NES){
	
		this.name = name;
		this.size = size;
		this.members = members;
		this.pValue = pValue;
		this.FDR = FDR;
		this.NES = NES;
		//this.upOrDown = upOrDown;
		this.info = info;
		
	}
	
	public Object getNthValue(int n){
		switch(n){
		case 0: return name;
		case 1: return size;
		case 2: return members;
		case 3: return pValue;
		case 4: return FDR;
		case 5: return NES;
		case 6: return info;
		default: return name;
		}
	}
	// 
	
	public static java.lang.Class<?> getClassN(int n){
		
		switch(n){
		case 0: return String.class;
		case 1: return Integer.class;
		case 2: return Integer.class;
		case 3: return Double.class;
		case 4: return Double.class;
		case 5: return Double.class;
		case 6: return String.class;
		default: return String.class;
		}
	}
	
	public static int getFieldCount(){
		return 7;
	}
	
	public String getName(){
		return name;
	}
	
	public Integer getSize(){
		return size;
	}
	
	
	public Integer getMembers(){
		return members;
	}
	
	
	public Double getPValue(){
		return pValue;
	}
	
	
	
	public Double getFDR(){
		return FDR;
	}

	
	public Double getNES(){
		return NES;
	}
	
	
	public String getInfo(){
		return info;
	}

	
	
	
	@Override
	public String toString(){
		return name + "\t" + size + "\t" + members + "\t" + pValue + "\t" + FDR + "\t" + NES + "\t" + info;
	}
	
}
