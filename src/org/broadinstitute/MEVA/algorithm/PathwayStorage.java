package org.broadinstitute.MEVA.algorithm;

import java.util.*;
import java.io.*;

public class PathwayStorage {

	
	private static Map<String,List<String>> originalPathways;
	public static Map<String,List<String>> customPathways;
	public static Map<String,File> customPathwayFiles;
	public static Map<String,List<String>> allPathways;
	public static String STORED_PATHWAYS = "STORED_PATHWAYS";
    public static Properties storedPathwayProps = new Properties();	
	
	
	private PathwayStorage() {
		//clear();
		
		try{
			originalPathways = DataReader.readMetPathways();
		} catch(IOException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		
		customPathways = new HashMap<String,List<String>>();
		customPathwayFiles = new HashMap<String,File>();
		allPathways = new HashMap<String,List<String>>();
		allPathways.putAll(originalPathways);
		allPathways.putAll(customPathways);
	}
	
	private static PathwayStorage singleton = new PathwayStorage();
	
	public static PathwayStorage getInstance(){
		return singleton;
	}

	public Map<String,List<String>> getPathways(){
		return allPathways;
	}
	
	
	
	public static void parsePWF(){
		
		
/*		
		        //2. Find if the CyProperty already exists, if not create one with default value.
		CyProperty<Properties> storedPathwayProperty = null;
		CySessionManager sessionManager;
		sessionManager = Services.sessionManager;
		CySession session = sessionManager.getCurrentSession();
		if(session.equals(null)){
			System.out.println("session null");
		}
		
       //3. Get all properties and loop through to find your own.
       Set<CyProperty<?>> props = session.getProperties();
       if(props.equals(null)){
    	   System.out.println("props null");
       }
       boolean flag = false;
       
       for (CyProperty<?> prop : props) {
           if (prop.getName() != null){
               if (prop.getName().equals(STORED_PATHWAYS)) {
            	   	storedPathwayProperty = (CyProperty<Properties>) prop;
            	   	flag = true;
					break;
               }
           }
       }
       */
       /*
       //4. If the property does not exists, create nodeBorderWidthProperty
       if (!flag){
               storedPathwayProps.setProperty(storedPathwayProperty);
               nodeBorderWidthProperty = new 
                               SimpleCyProperty(NodeBorderWidthInPaths, 
                                               nodeBorderWidthProps, Float.TYPE, CyProperty.SavePolicy.SESSION_FILE_AND_CONFIG_DIR );
       }
       //5. If not null, property exists, get value from it and set NodeBorderWidthInPathsValue
       else{
               nodeBorderWidthProps = nodeBorderWidthProperty.getProperties();
               NodeBorderWidthInPathsValue = Double.valueOf((String)nodeBorderWidthProps.get(NodeBorderWidthInPaths));
       }
       
       */
	}
	
	
	
	
	
	
	public void addPathway(String pathwayName, List<String> pathway, File file){
		customPathways.put(pathwayName, pathway);
		allPathways = new HashMap<String,List<String>>();
		allPathways.putAll(originalPathways);
		allPathways.putAll(customPathways);
		customPathwayFiles.put(pathwayName, file);
//		allPathways.put(pathwayName, pathway);
	}
	
	
	public void removePathways(String name){
		customPathways.remove(name);
		allPathways = new HashMap<String,List<String>>();
		allPathways.putAll(originalPathways);
		allPathways.putAll(customPathways);
	}
	
	
	
	
	
}