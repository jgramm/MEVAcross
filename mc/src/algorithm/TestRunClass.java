package algorithm;

import java.util.*;
import java.io.*;
import java.io.IOException;

public class TestRunClass {

	public static void main(String[] args) throws IOException{
		LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData("../resources/pilot.txt", 3, 0, 1, "\t");
		
		RunMEVAColumn.run(data, "poop");
		
		List<Pathway> analysis = RunMEVAColumn.getAnalysis();
		
		File file = new File("../resources/poop.txt");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		Writer writer = new Writer(file);
		
		writer.writeAnalysis(analysis);
		writer.closeWriter();
		
		
	}
	
}
