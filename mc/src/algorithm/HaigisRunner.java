package algorithm;

import java.util.*;
import java.io.*;
import java.io.IOException;

public class HaigisRunner{

	public static void main(String[] args) throws IOException{
		String dir = "/Users/jgramm/jgramm/MEVA/Testing/";
		String name = "SIRT3 MEFs data from Haigis lab";
		String inputFile = dir + name + ".txt";
		//int columnTotal = reader.getColumnTotal(inputFile, "\t");
		
		DataStorage.iterations = 1000;
	
		LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData(inputFile, 1, 1, 3, "\t");
		DataStorage.usepValues = false;
	
		RunMEVAColumn.run(data, "Hypoxia/Normoxia");
		List<Pathway> analysis = RunMEVAColumn.getAnalysis();
		
		Writer writer = new Writer(new File(dir + name + "_p-value_analysis.txt"));
		writer.writeAnalysis(analysis);
		writer.closeWriter();

		System.out.println("Done");
		
	}
	
}