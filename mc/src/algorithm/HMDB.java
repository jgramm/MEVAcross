package algorithm;

import java.io.*;
import java.util.*;

public class HMDB {
	
	static boolean isInitialized = false;
	static HashMap<String,String> namesToHMDBs;
	static HashMap<String,List<String>> namesToSynonyms;
	static HashMap<String,List<String>> HMDBsToNames;
	static HashMap<String,List<String>> HMDBsToPathways;
	
	// initialize once and throwa flag indicating this
	public static void init(){
		if(isInitialized){
			return;
		}
		
		namesToHMDBs = new HashMap<String,String>();
		namesToSynonyms = new HashMap<String,List<String>>();
		HMDBsToNames = new HashMap<String,List<String>>();
		HMDBsToPathways = new HashMap<String,List<String>>();
		
		try{
			//InputStream is = HMDBLookup.class.getClassLoader().getResourceAsStream("hmdb_mapping.txt");
			//BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			BufferedReader reader = new BufferedReader(new FileReader(new File("C:/Users/James/workspace/MEVA3/resources/hmdb_mapping.txt")));
			
			String line = reader.readLine();
			String[] dataRow;
			String[] tempRow;
			List<String> tempList = new ArrayList<String>();
			
			while(line != null && line != ""){
				dataRow = line.split("\t");
				namesToHMDBs.put(dataRow[0], dataRow[1]);
				
				if(dataRow[3] != ""){
					tempRow = dataRow[3].split(";");
					
					tempList = new ArrayList<String>();
					for (int i = 0; i < tempRow.length; i++) {
						tempList.add(tempRow[i]);
					}
					namesToSynonyms.put(dataRow[0], tempList);
					HMDBsToNames.put(dataRow[1], tempList);
					
				}
				
				tempList = new ArrayList<String>();
				tempList.add(dataRow[0]);
				if(namesToSynonyms.containsKey(dataRow[0])){
					tempList.addAll(namesToSynonyms.get(dataRow[0]));
				}
				HMDBsToNames.put(dataRow[1], tempList);
				
				
				if(dataRow[2] != ""){
					tempRow = dataRow[2].split(";");
					tempList = new ArrayList<String>();
					for (int i = 0; i < tempRow.length; i++) {
						tempList.add(tempRow[i]);
					}
					HMDBsToPathways.put(dataRow[1], tempList);
				}
				
				
				line = reader.readLine();
			}
			
			reader.close();
			isInitialized = true;
			
			
		} catch(IOException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}
	
}
