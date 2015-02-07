package io;

import java.io.*;
import java.util.*;


public class GPMLParser {
  
    public static BufferedReader file;
    public static String line;
    public static List<Node> nodes = new ArrayList<Node>();
    public static List<Edge> edges = new ArrayList<Edge>();
    public static Iterator<String> iter;
    

    // removes any gpml nodes that are not listed as metabolites
    public static void filterNodes(){
    	List<Node> filteredNodes = new ArrayList<Node>();
    	
    	if(nodes.isEmpty()){
    		throw new RuntimeException("Node list is empty!");
    	} else {
    		for(Node n : nodes){
    			if(n.nodeType.contentEquals("Metabolite")){
        			filteredNodes.add(n);
        		}
    		}
    	}
    	
    	nodes = filteredNodes;
    	
    }
    
    // parses a gpml file
    public static void read(String fileName) throws IOException{
    	
    	nodes = new ArrayList<Node>();
    	edges = new ArrayList<Edge>();
    	String inputFile = "KEGG/" + fileName + ".gpml";
    	System.out.println(inputFile);
    	//InputStream is = new InputStream();
    	//file = new BufferedReader(new InputStreamReader(is));
    	file = new BufferedReader(new FileReader(new File(fileName)));
		line = file.readLine();
		
		// handle each case with the proper method
		outerloop:
		while(line != null){
			if(line.contains("<DataNode")){
				parseDataNode();
			} else {
				if(line.contains("<Line")){
					parseEdge();
				} else {
				if(line.contains("</Pathway")){
						line = file.readLine();
						break outerloop;
					} else {
						line = file.readLine();
					}
				}
			}
		}
	}

    public static void parseDataNode() throws IOException{
        Node node = new Node();

        while(line != null && !line.contains("</DataNode")){
            parseDataNodeLine(node, line);
            line = file.readLine();
        }
        
        nodes.add(node);
        
    }
    
    // dear lord, looking back on this, I'm surprised any of this works
    public static void parseDataNodeLine(Node node, String line) throws IOException{

    	if(line.contains("TextLabel=")){
            int index1 = line.indexOf("TextLabel=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            node.label = line.substring(index2, index3);
        }
        
        if(line.contains("GraphId=")){
            int index1 = line.indexOf("GraphId=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            node.graphID = line.substring(index2, index3);
        }
        
        if(line.contains("Type=")){
            int index1 = line.indexOf("Type=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            node.nodeType = line.substring(index2, index3);
        }
        
        if(line.contains("ID=")){
            int index1 = line.indexOf("ID=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            node.ID = line.substring(index2, index3);
        }
        
        if(line.contains("</DataNode")){
        	throw new IOException("DATA NODE POOP");
        }
    }
        
    
    
    
    public static void parseEdge() throws IOException{
        Edge edge = new Edge();
        
        while(line != null && !line.contains("</Line")){
            parseEdgeLine(edge, line);
            line = file.readLine();
        }

        edges.add(edge);
    }
    
    
    
    public static void parseEdgeLine(Edge edge, String line) throws IOException{
    	
    	if(line.contains("ArrowHead=")){
            int index1 = line.indexOf("ArrowHead=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            edge.arrowHead= line.substring(index2, index3);
        }
        
        if(line.contains("GraphId=")){
            int index1 = line.indexOf("GraphId=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            edge.graphID = line.substring(index2, index3);
        }
        
        if(line.contains("GraphRef=")){
            int index1 = line.indexOf("GraphRef=");
            int index2 = 1 + line.indexOf("\"", index1);
            int index3 = line.indexOf("\"", index2);
            
            edge.addNode(line.substring(index2, index3));
        }
    }
    
    
    
    public static final class Node{
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
     }
    
    
    public static class Edge{
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
    
    
}
