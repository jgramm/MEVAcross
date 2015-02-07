package algorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class LevineRunner {

	public static void main(String[] args) throws IOException{

		String dir = "/Users/jgramm/jgramm/MEVA/Testing/relevinedataforvampeanalysis/";
		String name = "male_plasma_wilcoxon_p_values";
		String inputFile = dir + name + ".csv";//int columnTotal = reader.getColumnTotal(inputFile, "\t");
		
		DataStorage.usepValues = true;
		DataStorage.iterations = 1000;

		int[] arrayColumns = {2,3,4,5,6,7,8,9,10,11};
		List<Integer> columns = new ArrayList<Integer>();
		for(int num: arrayColumns){
			columns.add(num);
		}
		
		
		String[] headers = Arrays.copyOfRange(DataReader.getLabels(inputFile, 0, ","), 0, 12);
		
		List<List<Pathway>> allAnalyses = new ArrayList<List<Pathway>>();
		
		for(int i : columns){
			if(i <= 6){
				DataStorage.usepValues = true;
			} else {
				DataStorage.usepValues = false;
			}
			LinkedHashMap<String,Double> data = DataReader.parseTwoColumnsOfData(inputFile, 1, 1, i, ",");
		
			
			RunMEVAColumn.run(data, headers[i]);

			//System.out.println(data);
			System.out.println(headers[i]);
			
			List<Pathway> analysis = RunMEVAColumn.getAnalysis();
			allAnalyses.add(analysis);
			
		
		}
		
		
		Writer writer = new Writer(new File(dir + name + "_analysis.txt"));
		
		
		
		String[] newHeaders = new String[columns.size()];
		for(int i = 0; i < columns.size(); i++){
			newHeaders[i] = headers[columns.get(i)];
		}	
		
		//writer.writeAnalysis(analysis);
		writer.writeAllAnalysesShort(newHeaders, allAnalyses);
		writer.closeWriter();
		
		System.out.println("Done");
		
	}
	
}
