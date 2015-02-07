package org.broadinstitute.MEVA.network;

public class Edge{
    public String graphID;
    public String from = "";
    public String to = "";
    public String arrowHead;
    public Edge(){}
    
    public String toString(){
    	String s = graphID + "\t" + from + "\t" + to + "\t" + arrowHead;
    	return s;
    }
    
    public void addNode(String node){
    	if(from == ""){
    		from = node;
    	} else {
    		if(to == ""){
    			to = node;
    		}
    	}
    }
}
