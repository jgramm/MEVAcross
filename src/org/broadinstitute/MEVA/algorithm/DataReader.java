package org.broadinstitute.MEVA.algorithm;

import java.util.*;
import java.io.*;



public class DataReader {
	
	String metSetName, dataName;
	
	public DataReader(){}


	// This is the only method you should need for getting metabolite pathway lists
	public static LinkedHashMap<String,List<String>> readMetPathways() throws IOException{

		
		
		// USE THIS IF YOU WANT TO RUN FROM COMMAND LINE
		//BufferedReader file = new BufferedReader(new FileReader(new File("/Users/jgramm/jgramm/MEVA/src/main/resources/mastermapping.txt")));

		
		// load the pathway file from the jar
		
		/*
		InputStream is1 = DataReader.class.getClassLoader().getResourceAsStream("org/cytoscape/MEVA/internal/algorithm/resources/mastermapping.txt");
		InputStream is2 = DataReader.class.getClassLoader().getResourceAsStream("main/java/org/cytoscape/MEVA/internal/algorithm/resources/mastermapping.txt");
		InputStream is3 = DataReader.class.getClassLoader().getResourceAsStream("mastermapping.txt");
		InputStream is8 = DataReader.class.getClassLoader().getResourceAsStream("resources/mastermapping.txt");
		InputStream is4 = DataReader.class.getResourceAsStream("resources/mastermapping.txt");
		InputStream is5 = DataReader.class.getResourceAsStream("/resources/mastermapping.txt");
		
		InputStream is6 = DataReader.class.getResourceAsStream("/hello.txt");
		InputStream is7 = DataReader.class.getResourceAsStream("/resources/hello.txt");
		InputStream is10 = DataReader.class.getResourceAsStream("/main/java/org/cytoscape/MEVA/internal/algorithm/resources/mastermapping.txt");
		Enumeration en = DataReader.class.getClassLoader().getResources("");
		System.out.println("Enum");
		System.out.println(DataReader.class.getClassLoader().getResource("org/cytoscape/MEVA/internal/algorithm/DataReader.class"));
		System.out.println(DataReader.class.getClassLoader().getResource("org/cytoscape/MEVA/internal/algorithm/resources/mastermapping.txt"));
		System.out.println(DataReader.class.getResource("mastermapping.txt"));
		System.out.println(DataReader.class.getResource("resources/mastermapping.txt"));
		
		while(en.hasMoreElements()){
			System.out.println("enum");
			System.out.println();
			System.out.println(en.nextElement());
		}
		
		
		//InputStream is11 = DataReader.class.getResource("/resources/mastermapping.txt").openStream();
		//if(is11 == null){
			//System.out.println("is11 null");
		//}
		
		//System.out.println();
		
		if(is1 == null){
			System.out.println("Null input stream in data reader!1");
		}
		if(is2 == null){
			System.out.println("Null input stream in data reader!2");
		}
		if(is3 == null){
			System.out.println("Null input stream in data reader!3");
		}
		if(is4 == null){
			System.out.println("Null input stream in data reader!4");
		}
		if(is5 == null){
			System.out.println("Null input stream in data reader!5");
		}
		if(is6 == null){
			System.out.println("Null input stream in data reader!6");
		}
		if(is7 == null){
			System.out.println("Null input stream in data reader!7");
		}
		if(is8 == null){
			System.out.println("Null input stream in data reader!8");
		}
		if(is9 == null){
			System.out.println("Null input stream in data reader!9");
		}
		if(is10 == null){
			System.out.println("Null input stream in data reader!10");
		}

		System.out.println(DataReader.class.getResource("/"));
		
		//System.out.println(DataReader.class.getClassLoader().getResource(".").getPath());
		String s = DataReader.class.getClassLoader().getResource(".").getPath().substring(1);
		System.out.println(DataReader.class.getClassLoader().getResource(s).getPath());
		System.out.println(DataReader.class.getClassLoader().getResource(s + "/").getPath());
		
		*/
		
		InputStream is = DataReader.class.getClassLoader().getResourceAsStream("/mastermapping.txt");
		if(is == null){
			is = DataReader.class.getResourceAsStream("resources/mastermapping.txt");
		}
		if(is == null){
			System.out.println("Null input stream in data reader!");
		}
		
		//InputStream is4 = DataReader.class.getResourceAsStream("resources/mastermapping.txt");
		if(is == null){
			System.out.println("Null input stream in data reader!4");
		}
		
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader file = new BufferedReader(isr);
		
		
		
		
		String dataRow = file.readLine();
		dataRow = file.readLine();
		LinkedHashMap<String,List<String>> pathways = new LinkedHashMap<String,List<String>>();
		
		// for every line            ////////////////////////// NEED TO FIX BUG WITH EMPTY LINES AT END OF DATA /////////////////////////
		while (dataRow != null) {
			if(dataRow == ""){
				dataRow = file.readLine();
				continue;
			}
			
			// split the lines by tabs
			String[] dataArray = dataRow.split("\t");
			
			String path = dataArray[1];
			String met = dataArray[4];
			
			List<String> hmdbID;
			
			// if we have stored the path, add a new path equal to the old one with this element added, 
			// and remove the old one
			if (pathways.containsKey(path)){
				
				hmdbID = pathways.get(path);
				hmdbID.add(met);
				pathways.remove(path);
				pathways.put(path, hmdbID);
								
				
			} else {
				// otherwise just add the new path
				hmdbID = new ArrayList<String>();
				hmdbID.add(met);
				pathways.put(path,hmdbID);
			}
			
			dataRow = file.readLine();
						
		}
		
		//System.out.println(pathways.keySet());
		
		file.close();
		return pathways;
		
	}
	
	
	
	
	
	// This is the primary method you should need for reading in data
	public static LinkedHashMap<String,Double> parseTwoColumnsOfData(String fileName, 
			int headerLines, int rowOne, int rowTwo, String delimiter) throws IOException{
		System.out.println(fileName);
		BufferedReader file =  new BufferedReader(new FileReader(fileName));
		String dataRow = file.readLine();
	
		for(; headerLines > 0; headerLines--){
			dataRow = file.readLine();
		}

		LinkedHashMap<String,Double> data = new LinkedHashMap<String,Double>();
		
		// for every line
		while (dataRow != null){
			
			if(dataRow == ""){
				dataRow = file.readLine();
				continue;
			}
			
			String[] dataArray = dataRow.split(delimiter);
			
			// if the line contains at least as many columns as the second column we specify 
			// and the second specified column is numeric
			// store the two columns as a data pair
			if(dataArray.length >=  rowTwo && NumberUtils.isNumber(dataArray[rowTwo])){
				data.put(dataArray[rowOne], Double.parseDouble(dataArray[rowTwo]));
			}
			dataRow = file.readLine();
		}
		
		file.close();		
		return data;
	}
		
	
	
	// reads in a pwf and adds it to the list of pathways for Meva analysis
	public static void readPWF(File inputFile) throws IOException{
		BufferedReader file = new BufferedReader(new FileReader(inputFile));
		
		String line = file.readLine();
		String name = line;
		line = file.readLine();
		
		ArrayList<String> pathway = new ArrayList<String>();
		
		
		while(line != null){
			
			String[] dataArray = line.split("\t");
			if(dataArray[1] == null){
				continue;
			} else {
				pathway.add(dataArray[1]);
			}
			
			line = file.readLine();
		}
		
		
		//System.out.println(name);
		// add the name of the pathway, the metabolites, and the file location to pathway storage
		PathwayStorage.getInstance().addPathway(name, pathway, inputFile);
		
		file.close();
	}

		
		
	
	public static void readListOfPWFs(String fileName){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String line = reader.readLine();
			
			while(line != null){
				readPWF(new File(line));
				line = reader.readLine();
			}			
			
			reader.close();
			
		} catch(IOException ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			throw new RuntimeException();
		}
	}
	
	
	
	
	// get number of columns in a file
	public static int getColumnTotal(String fileName, String delimiter) throws IOException{
		int columnTotal = 0;
		
		BufferedReader file =  new BufferedReader(new FileReader(fileName));
		
		String dataRow = file.readLine();
		int int1 = dataRow.split(delimiter).length;
		dataRow = file.readLine();
		int int2 = dataRow.split(delimiter).length;
		dataRow = file.readLine();
		int int3 = dataRow.split(delimiter).length;
		
		columnTotal = Math.max(int1, Math.max(int2, int3));
		
		file.close();
		return columnTotal;
	}
		
	
	
	
	
	// get the columnNames
	public static String[] getLabels(String fileName, int labelLine, 
			String delimiter) throws IOException{
		
		BufferedReader file =  new BufferedReader(new FileReader(fileName));
		String dataRow = file.readLine();
		for(; labelLine > 0; labelLine--){
			dataRow = file.readLine();
		}
		
		String[] labels = dataRow.split(delimiter);
		//ArrayList<String> labels = new ArrayList<String>(Arrays.asList(dataRow.split(delimiter)));
	
		file.close();
		return labels;
	
	}
	
	
	// get the number of lines in the file
	public static int getSize(String fileName, int headerLines) throws IOException{
		
		BufferedReader file =  new BufferedReader(new FileReader(fileName));
		String dataRow = "";
		
		for(; headerLines > 0; headerLines--){
			dataRow = file.readLine();
		}
		
		int i = 1;
		while(dataRow != null){
			i++;
			dataRow = file.readLine();
		}
		
		file.close();
		return i;
	}

	
	// reads the MSEA file containing all of the available pathways into a 
	// LinkedHashMap containing the names of the pathways, followed by a list 
	// of all metabolites in each particular pathway
	public static LinkedHashMap<String,ArrayList<String>> readMetPathways2() throws IOException{
		BufferedReader tabFile =  new BufferedReader(new FileReader("mset_pathway.txt"));
		String dataRow = tabFile.readLine();
		dataRow = tabFile.readLine();
		LinkedHashMap<String,ArrayList<String>> pathways = new LinkedHashMap<String,ArrayList<String>>();
		
		while (dataRow != null){
			String[] dataArray = dataRow.split("\t");
			// metabolites in this list are separated by "; " so this will separate
			// them by finding that string. then puts them in an arraylist
			ArrayList<String> metNames = new ArrayList<String>(Arrays.asList(dataArray[2].split("; ")));
			// add each pathway with metabolites into a linkedhashmap
			pathways.put(dataArray[1], metNames);
			dataRow = tabFile.readLine();
		}

		tabFile.close();
		return pathways;
	}

	
	
	

	
	public static List<String> getGPMLFileNames() throws IOException{
		
		List<String> names = new ArrayList<String>();
		
		InputStream is = DataReader.class.getClassLoader().getResourceAsStream("KEGG/gpml names.txt");
		if(is == null){
			System.out.println("Null input stream in data reader!");
		}
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader file = new BufferedReader(isr);

		String line = file.readLine();
		
		while(line != null){
			names.add(line.substring(0, line.length() - 4));	
			line = file.readLine();
		}
		
		file.close();
		return names;
	}

	
}
