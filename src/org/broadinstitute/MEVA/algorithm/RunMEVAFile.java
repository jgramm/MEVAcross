package org.broadinstitute.MEVA.algorithm;

import java.io.*;
import java.util.*;


public class RunMEVAFile {

		
	public static void run(File inputFile, File outputFile) throws IOException{
		
		List<List<Pathway>> analyses = new ArrayList<List<Pathway>>();
		LinkedHashMap<String,Double> data;
		String sample;
		
		int labelLine = 0;
		String delimiter = "\t";
		String[] labels = DataReader.getLabels(inputFile.getPath(), labelLine, delimiter);
		
		DataStorage.clear();
		
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		Writer writer = new Writer(outputFile);
		
		int firstDataColumn = 1;
		
		for(int i = firstDataColumn; i < DataReader.getColumnTotal(inputFile.getPath(), delimiter); i++){
			
			data = DataReader.parseTwoColumnsOfData(inputFile.getPath(), 0, 0, i, delimiter);
			sample = labels[i];
			RunMEVAColumn.run(data, sample);
			analyses.add(RunMEVAColumn.getAnalysis());
			RunMEVAColumn.clear();
			
		}	
		
		writer.writeAllAnalysesShort(Arrays.copyOfRange(labels, firstDataColumn, labels.length), analyses);
		
	}
	
	
}
