package io;

import io.GPMLParser;
import io.GPMLParser.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class WritePWFFile {

	public static void write(String fileName, List<GEdge> edges, List<GNode> nodes){

		System.out.println(fileName);
		
		HashMap<String,String> nodeMap = new HashMap<String,String>();
		HashMap<String,List<String>> connMap = new HashMap<String,List<String>>();
		HashMap<String,String> nameMap = new HashMap<String,String>();
		
		for (GNode gNode : nodes) {
			nameMap.put(gNode.ID, gNode.label);
			nodeMap.put(gNode.graphID, gNode.ID);
			connMap.put(gNode.ID, new ArrayList<String>());
		}
		
		try{
			List<String> temp1, temp2;
			String from, to;
			for (GEdge e : edges) {
				from = nodeMap.get(e.from);
				to = nodeMap.get(e.to);
				
				temp1 = connMap.get(from);
				temp2 = connMap.get(to);
				
				temp1.add(to);
				temp2.add(from);
				
				connMap.put(from, temp1);
				connMap.put(to, temp2);
			}
		} catch(Exception ex){
			return;
		}
		
		try{
			File f = new File(fileName);
			String fName = f.getName();
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:/Users/James/desktop/mc/resources/pwfs/" + fName)));
			
			List<String> temp3;
			for (String s : connMap.keySet()) {
				temp3 = connMap.get(s);
				s = nameMap.get(s) + "\t" + s + "\t";
				
				for (String id : temp3) {
					s += id + ";";
				}
			}
			
			writer.close();
		} catch(IOException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}
}
