package org.broadinstitute.MEVA.algorithm;

import java.io.*;
import java.util.*;


public class RunMEVAColumn {

	private static List<Pathway> analysis;
	
	public static List<Pathway> run(LinkedHashMap<String,Double> data, String sample) throws IOException{
		
		// this is just setup
		clear();
		Map<String,List<String>> pathways = PathwayStorage.allPathways;

		//System.out.println(pathways.keySet());
		
		EnrichmentScore ES;
		Double[] dist;
		double score, p;
		DataStorage storage;
		storage = DataStorage.getInstance();
		DataStorage.clear();
		
		
		storage.importData(data);
		storage.importPathways(pathways);
		
		
		// for each pathway in the list
		for(String path : pathways.keySet()){
			/*
			if(path.contains("CEs") || path.contains("DAGs")){
				System.out.println(path);
				System.out.println(storage.getMembers(path));
				System.out.println(DataStorage.minMetabolitesPerPath);
			}
			*/
			
			if(storage.getMembers(path) < DataStorage.minMetabolitesPerPath){
				
				
				continue;
			}
			// compute an enrichment score
			ES = new EnrichmentScore(path, sample);
			score = ES.enrich().x;
			// generate a null distribution
			dist = (new NullDist(path, score, sample)).distribute();
			// calculate a pvalue
			
			
			if(DataStorage.usepValues){
				Significance.tails = 1;
			} else {
				Significance.tails = 2;
			}
			
			p = (new Significance(dist, score)).probability();
			storage.putPValue(path, p);
			
		}
		
		/*
		BufferedWriter w;
		
		try{
			w = new BufferedWriter(new FileWriter("/Users/jgramm/jgramm/MEVA/Testing/NullDistScores.txt"));
		} catch (IOException ex){
			throw new RuntimeException();
		}
		
		System.out.println("NAMED SCORES SIZE");
		System.out.println(DataStorage.namedScores.size());
		w.write(DataStorage.namedScores.size());
		w.newLine();
		
		for(String pth : DataStorage.namedScores.keySet()){
			w.write(pth + "\t");
			w.write(DataStorage.NES.get(pth) + "\t\t");
			for(double d : DataStorage.namedScores.get(pth)){
				w.write(d + "\t");
			}
			w.newLine();
		}
		
		w.close();
		*/
		
		
		
		// calculate the fdr
		storage.computeFDR2();
		// compute an analysis of stored data
		analysis = storage.getAnalysis();
		
		//ShowEnrichmentScoreValues.main(null);
		
		//for(double d : Significance.tScores){
		//	System.out.println(d);
		//}
		
		return analysis;
	}
	
	
	public static List<Pathway> getAnalysis(){
		return analysis;
	}
	
	
	// resets information for repeated iterations of the run process
	public static void clear(){
		analysis = new ArrayList<Pathway>();
		DataStorage.clear();
	}
	
}
