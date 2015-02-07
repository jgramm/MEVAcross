package org.broadinstitute.MEVA.algorithm;

import java.util.*;
import java.io.*;

public class ShubasRunner{

	public static void main(String[] args) throws IOException{
		String dir = "C:/Users/James/Downloads/";
		String name = "Suzanne_sigma_cells_data";
		String inputFile = dir + name + ".txt";
		System.out.println(inputFile);
		//int columnTotal = reader.getColumnTotal(inputFile, "\t");
		
		DataStorage.iterations = 1000;
	
		System.out.println(inputFile);

		LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData(inputFile, 1, 1, 4, "\t");
	
		RunMEVAColumn.run(data, "Hypoxia/Normoxia");

		List<Pathway> analysis = RunMEVAColumn.getAnalysis();
		
		
		Writer writer = new Writer(new File(dir + name + "_analysis.txt"));
		
		writer.writeAnalysis(analysis);
		//writer.writeAllAnalysesShort(headers, allAnalyses);
		writer.closeWriter();
		System.out.println("Done");
		
		
	}
	
}