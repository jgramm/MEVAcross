package org.broadinstitute.MEVA.algorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

// ARGUMENTS TO MAIN METHOD
//0 - File to analyze
//1 - Type of analysis (p-value, z-score, fold change) anything that starts
///// with the letter p will be determined to be a p-value analysis.
//2 - Column HMDB id's are in
//3 - First column to run analysis on
//4 - OPTIONAL - Column to stop analysis, non-inclusive 
///// (e.g. start: 3, end: 6 will run columns 3,4,5)
//5 OPTIONAL - number of iterations
//6 OPTIONAL - directory to place analyses
//7 OPTIONAL - text file with names of custom pathways

public class CommandLineRunner {
	
	public static void main(String[] args) {
		try{
			for(int i = 0; i < args.length; i++){
				//System.out.println(args[i]);
			}
			
			DataStorage.iterations = 1000;
	
			String fileName = args[0];
			
			
			String delimiter;
			
			if(fileName.endsWith(".csv")){
				delimiter = ",";
			} else {
				delimiter = "\t";
			}
			
			String type = args[1];
			
			if(type.startsWith("p") || type.startsWith("P")){
				DataStorage.usepValues = true;
				System.out.println("Using p-values");
			} else {
				DataStorage.usepValues = false;
			}
			
			int metColumn = Integer.parseInt(args[2]);
			int startColumn = Integer.parseInt(args[3]);
			int endColumn;
			
			if(args.length > 4){
				endColumn = Integer.parseInt(args[4]);
			} else {
				endColumn = startColumn + 1;
			}
			
			if(endColumn <= startColumn){
				throw new RuntimeException("Starting column >= ending column");
			}
			
			if(args.length > 5){
				DataStorage.iterations = Integer.parseInt(args[5]);
				
				if(DataStorage.iterations < 300){
					System.out.println("Warning: NES algorithm not shown to be stable for ");
					System.out.println("less than approximately 250 iterations");
				}
			}
			
			File newDir = new File("THISISNOTAREALFILE");
			
			if(args.length > 6){
				newDir = new File(args[6]);
				newDir.mkdir();
			}
			

			if(args.length > 7){
				DataReader.readListOfPWFs(args[7]);
			}
			
			String[] labels = DataReader.getLabels(fileName, 0, delimiter);
			
			//System.out.println(labels.length);
			
			String[] headers = Arrays.copyOfRange(labels , startColumn, endColumn);
			
			List<List<Pathway>> allAnalyses = new ArrayList<List<Pathway>>();
			
			for(int i = startColumn; i < endColumn; i++){
				LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData(fileName, 1, metColumn, i, delimiter);
				
				System.out.println("Running: " + headers[i - startColumn]);
				RunMEVAColumn.run(data, headers[i - startColumn]);
				
				List<Pathway> analysis = RunMEVAColumn.getAnalysis();
				allAnalyses.add(analysis);
				
			}
			
			
			String name, dir;
			File f;
			int slashIndex = fileName.lastIndexOf(File.separator);
			
			if(slashIndex < 0){
				name = fileName;
			} else {
				name = fileName.substring(slashIndex, fileName.length());
			}
			
			if(name.contains(".")){
				name = name.substring(0,name.lastIndexOf("."));
			}
			
			if(newDir.getAbsolutePath().contains("THISISNOTAREALFILE")){
				f = new File("analyses");
				if(!f.exists()){
					f.mkdir();	
				}
				
				//System.out.println("C");
				File inputFile = new File("analyses/" + name);
				if (!inputFile.exists() || !inputFile.isDirectory()){
					System.out.println("making dir");
					System.out.println(inputFile.getAbsolutePath());
					inputFile.mkdir();
				}
	
				dir = "analyses" + "/" + name + "/";// + time + "/";
			} else {
				dir = newDir.getAbsolutePath();
				if(!newDir.exists()){
					newDir.createNewFile();
				}
			}
			
//			System.out.println(name);
	//		System.out.println(dir);
			System.out.println("Printing analyses at: " + (new File(dir)).getAbsolutePath());
			
			// Make analysis directory here
			// Make sub directory here
			// write each analysis file
			// write total analysis file
			
			
			
			Writer writer;
			
			if(allAnalyses.size() > 1){
				writer = new Writer(new File(dir + "/all_analyses.txt"));
				writer.writeAllAnalysesShort(headers, allAnalyses);
				writer.closeWriter();
			}
			
			for(int j = 0; j < allAnalyses.size(); j++){
				f = new File(dir + "/" + headers[j].replaceAll("[\\W]|_", "_") + ".txt");
				
				if(!f.exists()){
					f.createNewFile();
				}
				
				//System.out.println(headers[j]);
				//System.out.println(f.getAbsolutePath());
				writer = new Writer(f);
				
				writer.writeAnalysis(allAnalyses.get(j));
				
				writer.closeWriter();
			}
			
			
			
			
		} catch(IOException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		
		System.out.println("Done");
		
	}
	
}
