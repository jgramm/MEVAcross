package org.broadinstitute.MEVA.network;

import java.util.List;

public class Node {

	 public String label;
     public String graphID;
     public String nodeType;
     public String ID;
     public List<String> connections;
     public Node(){}
     
     public String toString(){
     	String s = label + "\t" + nodeType + "\t" + graphID + "\t" + ID;
     	return s;
     }
	
	
	public Node(String name){
		this.label = label;
	}
	
}
