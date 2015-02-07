package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


public class NullDist {

	ArrayList<String> metSet;
	LinkedHashMap<String,Double> data;
	EnrichmentScore nullES;
	int iterations;
	Double[] dist;
	String path, sample;
	double score, average;
	DataStorage storage;
	
	// class constructor
	// applications of this class should call NullDist.distribute() and nothing else
	public NullDist(String path, double score, String sample){
		
		storage = DataStorage.getInstance();
		iterations = DataStorage.iterations;
		dist = new Double[iterations];
		this.score = score;
		this.path = path;
		this.sample = sample;
	}
	
	
	// Generates a null distribution of enrichment scores
	private void generateNullDist(){ 
		nullES = new EnrichmentScore(path, sample);
		double s, totalScore = 0;
		int count = 0;
		for(int n = 0; n < iterations; n++){
			s = nullES.enrichScoreOnly();
			
			if(s * score > 0){
				totalScore += (s);
				count++;
			}
			
			dist[n] = s;
		
		}
		
		average = Math.abs(totalScore / (double) count);
	}
	
	
	
	public void putNES(){
		storage.putNES(path, score / average);
		//System.out.println("Putting NES: " + path + score / average + " score: " + score + " average: " + average);
	}
	
	/*public void putNullDist(){
		for (int i = 0; i < dist.length; i++){
			AllData.nullDistScores.get(sample).add(Math.abs(dist[i] / average));
		}
	}*/
	
	public void putNullDist(){
		List<Double> scores = Arrays.asList(dist);
		
		/*System.out.println(path);
		System.out.println(scores);
		System.out.println(average);
		*/
		storage.putNamedNullDistScores(path, scores, average);
		storage.putNullDistScores(scores, average);
	}
	
    // this is the method you would call from your main method
	public Double[] distribute(){
		this.generateNullDist();
		putNullDist();
		putNES();
		return dist;
	}	
	
}
