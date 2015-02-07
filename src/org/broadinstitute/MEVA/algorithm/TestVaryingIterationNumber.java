package org.broadinstitute.MEVA.algorithm;

import java.util.*;
import java.io.*;
import java.io.IOException;

public class TestVaryingIterationNumber{

	public static void main(String[] args) throws IOException{

		float time = System.currentTimeMillis();
		String dir = "C:/Users/James/Downloads/";
		String name = "SIRT3_MEFs_FC";
		String inputFile = dir + name + ".txt";
		//int columnTotal = reader.getColumnTotal(inputFile, "\t");
		int metColumn = 1;
		int runColumn = 12;
		int headerLines = 1;
		String delimiter = "\t";
		String[] headers = DataReader.getLabels(inputFile, headerLines - 1, delimiter);
		String header = headers[runColumn];
		header = header.replaceAll("[\\W]", "_");
		DataStorage.usepValues = true;
		if(DataStorage.usepValues){
			header += "_P";
		}
		
		Writer writer = new Writer(new File("C:/Users/James/Desktop/" + header + "_Varying_Iteration_Number.txt"));
		String stuff;
		Boolean b = true;
		
		for(int iter = 1; iter <= 1000; ){
			System.out.println(iter);
			DataStorage.iterations = iter;
			LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData(inputFile, headerLines, metColumn, runColumn, delimiter);
			RunMEVAColumn.run(data, header);
			List<Pathway> analysis = RunMEVAColumn.getAnalysis();
			
			
			if(b){
				stuff = "";
				for(Pathway p : analysis){
					stuff += "\t" + p.getName();
				}
				writer.writeLine(stuff);
				b = false;
			}
			
			stuff = Integer.toString(iter);
			for(Pathway p : analysis){
				stuff +=  "\t" + p.getPValue();
			}
			
			writer.writeLine(stuff);
			
			if(iter >= 10){
				iter += 10;
			} else {
				iter++;
			}
				
		}
		
		writer.closeWriter();
		time = System.currentTimeMillis() - time;
		System.out.println("Total elapsed time: " + time / 1000);
	}
	
}