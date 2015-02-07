package algorithm;

import java.util.*;
import java.io.*;
public class ShowEnrichmentScoreValues {

	public static void main(String[] args) throws IOException{
		
		String dir = "/Users/jgramm/jgramm/MEVA/Testing/";
		String name = "09_1217_Haigis_normalized_data";
		String inputFile = dir + name + ".txt";
		
		Writer writer = new Writer(new File("/Users/jgramm/jgramm/MEVA/Testing/ShowEnrichmentScoreValues.txt"));
		
		Map<String,List<String>> pathways = DataReader.readMetPathways();
		
		EnrichmentScore ES;
		DataStorage storage;
		storage = DataStorage.getInstance();
		DataStorage.clear();
		LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData(inputFile, 1, 2, 7, "\t");
		
		System.out.println(data.size());
		
		storage.importData(data);
		storage.importPathways(pathways);
		
		DataStorage.iterations = 1000;
		
		String sample = name;
		ArrayList<Double> scores;
		
		//System.out.println(scores);
		
		Double[] dist = new Double[DataStorage.iterations];
		double score = 0;
	
		
		for(String path : pathways.keySet()){
			if(storage.getMembers(path) < DataStorage.minMetabolitesPerPath){
				//System.out.println("path members = ");
				//System.out.println(storage.getMembers(path));
				continue;
			}
			// compute an enrichment score
			ES = new EnrichmentScore(path, sample);
			scores = ES.storeEnrichmentIterations(data);
			writer.writeList(path,scores);
			
			
			if(path.contains("TCA")){
				// compute an enrichment score
				ES = new EnrichmentScore(path, sample);
				score = ES.enrich().x;
				// generate a null distribution
				dist = (new NullDist(path, score, sample)).distribute();
				
				System.out.println("TCA MEMBERS");
				System.out.println(DataStorage.getInstance().getMembers(path));
				System.out.println(DataStorage.getInstance().getMetabolitePathways().get(path).size());
			}
			
			
		}	
		String s = "TCA\t";
		for(int i = 0; i < dist.length; i++){
			s += Double.toString(dist[i]) + "\t";
		}
		
			
		writer.writeLine(s);
		
		writer.writeLine(Double.toString((new Significance(dist, score)).probability2()));
				
		
	
		writer.closeWriter();
		
		
	}
	
	
}
