package org.broadinstitute.MEVA.algorithm;

import org.broadinstitute.MEVA.algorithm.Pathway;

import java.util.*;
import java.io.*;

public class Writer {

	File file;
	FileWriter fw;
	BufferedWriter bw;
	
	public Writer(File file) throws IOException{
		this.file = file;
		this.fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
	}
	
	public void writeLine(String s) throws IOException{
		bw.write(s);
		bw.newLine();
	}
	
	// This is the primary method for writing results
	// the others are mostly useless.
	// DON'T FORGET TO CLOSE THE WRITER WHEN YOU'RE DONE WITH IT!!!
	public void writeAnalysis(List<Pathway> pathways) throws IOException{
		
		bw.write("Name \t size \t members \t pValue \t FDR \t NES \t Information");
		bw.newLine();
		
		for(Pathway path : pathways){
			bw.write(path.toString());
			bw.newLine();
		
		}
	}
	
	
	public void writeAllAnalysesShort(String[] headers, List<List<Pathway>> allAnalyses) throws IOException{
		
		for(int i = 0; i < headers.length; i++){
			bw.write(headers[i] + "\t" + headers[i] + " Info\t" + headers[i] + " P-Value\t" + headers[i] + " FDR\t");
		}
		
		bw.newLine();

		
		for(int j = 0; j < allAnalyses.get(0).size(); j++){
			for(int i = 0; i < allAnalyses.size(); i++){

				if(j < allAnalyses.get(i).size()){
					bw.write(allAnalyses.get(i).get(j).getName() + "\t" + allAnalyses.get(i).get(j).getInfo() + "\t" + allAnalyses.get(i).get(j).getPValue() + "\t" + allAnalyses.get(i).get(j).getFDR() + "\t");
				}
			}
			bw.newLine();
		}
	}
	
	
	
	public void writePathway3(Map.Entry<String,ArrayList<String>> entry) throws IOException{
		bw.write(entry.getKey());
	}
	

	public void writePathways(LinkedHashMap<String,ArrayList<Pair<String,Double>>> results,
			ArrayList<String> pathways, ArrayList<String> specimens, double ESAverages) throws IOException{
		
		Iterator<String> iter = specimens.iterator();
		bw.write("Pathway");
		
		while(iter.hasNext()){
			String specimen = iter.next();
			bw.write("\t" + specimen + " P-Value \t" + specimen + " FDR");
		}
		
		for(String path : results.keySet()){
			ArrayList<Pair<String,Double>> pAndFDR = results.get(path);
			
			bw.newLine();
			bw.write(path);
			
			for(Pair<String,Double> p : pAndFDR){
				bw.write("\t"+ p.x + "\t" + (double) p.y / ESAverages);
			}
		}
	}
	
	
	public void writeList(String path, List<Double> scores) throws IOException{
		bw.write(path);
		for(Double d : scores){
			bw.write("\t" + Double.toString(d));
		}
		bw.newLine();
	}
	
	
	/*public void handleData(String file, HashMap<Pair<String,String>,Double> results,
			FalseDiscoveryRate FDR, List<String> samples, Set<String> pathways) throws IOException{

		Iterator<String> iter = samples.iterator();
		bw.write("Pathway");
		
		while(iter.hasNext()){
			String sample = iter.next();
			bw.write("\t" + sample + " P-Value \t" + sample + " FDR");
		}
		
		
		for(String pathway : pathways){
			bw.newLine();
			bw.write(pathway);
			
			for(String sample : samples){
				Pair<String,String> p = new Pair<String,String>(sample,pathway);
				
				if(results.containsKey(p)){

					String pValue = Double.toString(results.get(p));
					
					String fdr = "1";//Double.toString(FDR.getPathwayFDR(sample, pathway));
					
					bw.write("\t" + pValue + "\t" + fdr);
					
				} else {
					bw.write("\t\t");
				}
			}
		}
	}
	*/
	
	/*
	public void handleData2(String file, Map<String,Map<String,Double>> results, List<String> labels) throws IOException{
		Iterator<String> iter = labels.iterator();
		bw.write("Pathway\tPathway Size\tMutual members");
		
		int pathSize, members;
		
		while(iter.hasNext()){
			String sample = iter.next();
			bw.write("\t" + sample + " P-Value \t" + sample + " FDR \t" + sample + " NES");
		}
		
		Set<String> pathways = AllData.pathwayTitles;
	
		
		for(String pathway : pathways){
			bw.newLine();
			
			pathSize = AllData.pathways.get(pathway).size();
			members = AllData.members.containsKey(pathway) ? AllData.members.get(pathway) : 0;
			
			bw.write(pathway + "\t" + Integer.toString(pathSize) + "\t" + Integer.toString(members));
			
			for(String sample : AllData.samples){
							
				if (results.get(sample).containsKey(pathway)){
			
					bw.write("\t" + 
							Double.toString(results.get(sample).get(pathway)) + "\t" + 
							Double.toString(AllData.FDRPerSample.get(sample).get(pathway)) + "\t" + 
							Double.toString(AllData.allNESPerSample.get(sample).get(pathway)));
				} else {
					bw.write("\t\t\t");
				}
			}
			
		}
			
	}
	*/
	
	
	
	
	public void closeWriter() throws IOException{
		this.bw.close();
	}
		
}
