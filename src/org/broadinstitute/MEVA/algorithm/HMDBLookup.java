package org.broadinstitute.MEVA.algorithm;

import java.io.*;
import java.util.*;

//import org.cytoscape.MEVA.internal.panels.DataPanel;

public class HMDBLookup {

	public static HashMap<String,String> namesToHMDB = new HashMap<String,String>();
	public static HashMap<String,String> HMDBToName = new HashMap<String,String>();
	public static HashMap<String,ArrayList<String>> HMDBToPathways = new HashMap<String,ArrayList<String>>();
	
	public static void main(String[] args){
		try{
			inputHMDBList();
		} catch(IOException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getLocalizedMessage());
		}
	}
	
	
	public static void inputHMDBList() throws IOException{
		
		InputStream is = HMDBLookup.class.getClassLoader().getResourceAsStream("hmdb_mapping.txt");
		if(is == null){
			System.out.println("Null input stream in data reader!");
		}
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader file = new BufferedReader(isr);
		
		String dataRow = file.readLine();
		
		String[] newSynonyms;
		String[] newPathways;
		ArrayList<String> finalPathways;
		
		while (dataRow != null) {
			if(dataRow == ""){
				dataRow = file.readLine();
				continue;
			}
			
			//System.out.println(dataRow);
			
			String[] dataArray = dataRow.split("\t");
			
			String name = dataArray[0];
			String hmdb = dataArray[1];	
			String synonyms = dataArray[3];
			String pathways = dataArray[2];
			
			newSynonyms = synonyms.split(";");
			newPathways = pathways.split(";");
			
			HMDBToName.put(hmdb, name);
			namesToHMDB.put(name, hmdb);
			
			for(int i = 0; i < newSynonyms.length; i++){
				namesToHMDB.put(newSynonyms[i].toLowerCase().replaceAll("\\s",""), hmdb);
			}


			finalPathways = new ArrayList<String>();
			for(int i = 0; i < newPathways.length; i++){
				finalPathways.add(newPathways[i]);
			}
			
			HMDBToPathways.put(hmdb, finalPathways);
			dataRow = file.readLine();
						
		}
		
		System.out.println("done loading hmdb mapping");
		file.close();
		
		importSecondHMDBList();
		
	}
	
	public static void importSecondHMDBList() throws IOException{
		InputStream is = HMDBLookup.class.getClassLoader().getResourceAsStream("KeggAndHMDB.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader file = new BufferedReader(isr);
		String dataRow = file.readLine();
		dataRow = file.readLine();
		
		while (dataRow!=null) {
			String[] dataArray = dataRow.split("\t");
			
			String HMDB = dataArray[0];
			String name = dataArray[2];
			
			if(!namesToHMDB.containsKey(name)){
				namesToHMDB.put(name, HMDB);
			}
			
			if(!HMDBToName.containsKey(HMDB)){
				HMDBToName.put(HMDB, name);
			}
			
			dataRow = file.readLine();
						
		}
		
		file.close();
		importThirdHMDBList();
	}
	
	public static void importThirdHMDBList() throws IOException{
		
		InputStream is = HMDBLookup.class.getClassLoader().getResourceAsStream("mastermapping.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader file = new BufferedReader(isr);
		
		String dataRow = file.readLine();
		dataRow = file.readLine();
		
		while (dataRow!=null) {
			String[] dataArray = dataRow.split("\t");
			
			String HMDB = dataArray[4];
			String name = dataArray[2];
			
			if(!namesToHMDB.containsKey(name)){
				namesToHMDB.put(name, HMDB);
			}
			
			if(!HMDBToName.containsKey(HMDB)){
				HMDBToName.put(HMDB, name);
			}
			
			dataRow = file.readLine();
						
		}
		
		file.close();
		
	}
	
	
	public static String getHMDBFromName(String name){
	
		if(namesToHMDB.isEmpty()){
			try{
				inputHMDBList();
			} catch(IOException ex){
				ex.printStackTrace();
				throw new RuntimeException(ex.getLocalizedMessage());
			}
		}
		
		return namesToHMDB.get(name.toLowerCase().replaceAll("\\s",""));
	}

	
	public static String getNameFromHMDB(String name){
		
		if(HMDBToName.isEmpty()){
			try{
				inputHMDBList();
			} catch(IOException ex){
				ex.printStackTrace();
				throw new RuntimeException(ex.getLocalizedMessage());
			}
		}
		
		return HMDBToName.get(name);
	}
	
	
	public static ArrayList<String> getPathwaysFromHMDB(String name){
		
		if(HMDBToPathways.isEmpty()){
			try{
				inputHMDBList();
			} catch(IOException ex){
				ex.printStackTrace();
				throw new RuntimeException(ex.getLocalizedMessage());
			}
		}
		
		return HMDBToPathways.get(name);
	}
}
