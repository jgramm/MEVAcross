package algorithm;

import java.util.*;

//import org.cytoscape.MEVA.internal.StatUtils;


public class Significance{

	ArrayList<Double> dist = new ArrayList<Double>();
	Double[] dist2;
	double score;
	public static ArrayList<Double> tScores = new ArrayList<Double>();
	public static int tails = 2;
	
	public String getZero(){
		return "";
	}
	
	public String getOne(){
		return "";
	}


	public Significance(Double[] dist, double score){
		//Collections.addAll(this.dist, dist);
		dist2 = dist;
		Arrays.sort(dist2);
		this.dist.add(score);
		this.score = score;
		//Collections.sort(this.dist);
	}
	
	
	///// PLEASE READ ME BEFORE USING!
	// returns the estimated p value corresponding to the likelihood that the 
	// given score is a member of the given distribution OR if the score is
	// an extremum, returns the size of the distribution so the user can 
	// have an upper bound on the probability
	///// user can check for the two cases with (probability > 1)
	
	public double probability(){

		if(score == 0){
			return 1;
		}
		
		int index;

		
		//System.out.println("Start pathway significance");
		
		double average = average(dist2);
		//double sterr = StatUtils.stdev(dist2);// / Math.sqrt((double) dist2.length - 1);
		
		/*
		System.out.println("Score, average, sterr");
		System.out.println(score);
		System.out.println(average);
		System.out.println(sterr);
		*/
		
		//double tScore = (score - average) / sterr;

		//System.out.println("tscore");
		//System.out.println(tScore);
		//tScores.add(tScore);
		
		//StudentT2 tDist = new StudentT2(dist2.length - 1);
		//double pValue = tDist.cdf(- Math.abs(tScore)) * 2;
		//double pValue = .1;
		
		// get the corrected index of the score
		index = Arrays.binarySearch(dist2, score);
		//index = dist.indexOf(score);
		
		//System.out.println(index);
		
		index = (index < 0) ? (- index - 1) : index;  
		
		// what percent are lower than the given score?
		double lowEnd = index / (double) dist2.length;

		// what percent are higher than the given score?
		double highEnd = (dist2.length - index) / (double) dist2.length;
		
		
		if(tails == 1){
			return lowEnd;
		}
		//System.out.println("lowEnd: " + lowEnd);
		//System.out.println("highEnd: " + highEnd);
		
		// using ONE TAIL STATISTIC choose the lower percent as the answer
		double p = Math.min(lowEnd, highEnd);
	

		// if the score is an extremum, return the size of the distribution
		double prob = (p == 0.0) ? 1 / (double) dist2.length : p;
		
		//System.out.println("T DISTRIBUTION SCORE");
		//System.out.println((new TDistribution(dist2.length - 1)).cumulativeProbability(tScore));
		
		/*double[] dist3 = new double[dist2.length];
		for(int i = 0; i < dist2.length; i++){
			dist3[i] = dist2[i];
		}*/
		
		//System.out.println("End pathway");
		
		//double pValue = (new TTest()).tTest(score, dist3);
		//System.out.println(pValue);
		
		return prob;
	}

	
	public double probabilityForpValues(){

		int index;
		index = Arrays.binarySearch(dist2, score);
		index = (index < 0) ? (- index - 1) : index;  
		double lowEnd = index / (double) dist2.length;
//		double highEnd = (dist2.length - (index)) / (double) dist2.length;
		//double p = Math.min(lowEnd, highEnd);
		double prob = (lowEnd == 0.0) ? 1 / (double) dist2.length : lowEnd;
		
		
		/*System.out.println("index: " + index);
		System.out.println("lowEnd: " + lowEnd);
		System.out.println("highEnd: " + highEnd);
		//System.out.println("p: " + p);
		System.out.println("prob: " + prob);
		System.out.println();
		*/
		
		return prob;
	}
	
	public double probability2(){

		int index;
		index = Arrays.binarySearch(dist2, score);
		index = (index < 0) ? (- index - 1) : index;  
		double lowEnd = index / (double) dist2.length;
		double highEnd = (dist2.length - (index)) / (double) dist2.length;
		double p = Math.min(lowEnd, highEnd);
		double prob = (p == 0.0) ? 1 / (double) dist2.length : p;
		
		/*
		System.out.println("index: " + index);
		System.out.println("lowEnd: " + lowEnd);
		System.out.println("highEnd: " + highEnd);
		System.out.println("p: " + p);
		System.out.println("prob: " + prob);
		System.out.println();
		*/
		
		return prob;
	}
	
	public double probability3(){
		
		ArrayList<Double> pos1 = new ArrayList<Double>();
		ArrayList<Double> neg1 = new ArrayList<Double>();
		
		double posValues = 0.;
		double negValues = 0.;
		
		for(double d : dist2){
			if(d > 0){
				pos1.add(d);
				posValues += d;
			} else {
				neg1.add(d);
				negValues += d;
			}
		}
		
		double posAvg = posValues / (double) pos1.size();
		double negAvg = negValues / (double) neg1.size();
		
		
		
		
		//double posStErr = StatUtils.stdev(pos1);// / Math.sqrt((double) pos1.size() - 1);
		//double negStErr = StatUtils.stdev(neg1);// / Math.sqrt((double) neg1.size() - 1);
		
		//double sterr = StatUtils.stdev(dist2) / Math.sqrt((double) dist2.length - 1);
		//double tScore = (score > 0) ? (score - posAvg) / posStErr : (score - negAvg) / negStErr;
		//tScores.add(tScore);
		//double tScore = (score - average) / sterr;
		

		/*System.out.println("standard errors");
		System.out.println(sterr);
		System.out.println(posStErr);
		System.out.println(negStErr);
		

		System.out.println("Start pathway significance");
		System.out.println("Score");
		System.out.println(score);
		System.out.println();
		System.out.println("tscore");
		System.out.println(tScore);
		System.out.println("pos/neg averages");
		System.out.println(posAvg);
		System.out.println(negAvg);
		
		System.out.println("T DISTRIBUTION SCORE");
		
		System.out.println((new TDistribution(((score > 0) ? pos1.size() : neg1.size()) - 1)).cumulativeProbability(tScore));
		*/
		double[] dist3 = new double[dist2.length];
		for(int i = 0; i < dist2.length; i++){
			dist3[i] = dist2[i];
		}
		
		/*System.out.println();
		System.out.println("End pathway");
		System.out.println();
		 */

		//double pValue = (new TTest()).tTest(score, dist3);
		return 1.;
	}
	
	public static double average(Double[] data){
		double average = 0;
		double sum = 0;
		
		for(Double d : data){
			sum += d;
		}
		
		average = sum / (double) data.length;
		return average;
		
	}
}
