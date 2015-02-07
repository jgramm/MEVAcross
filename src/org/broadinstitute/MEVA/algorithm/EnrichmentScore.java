package org.broadinstitute.MEVA.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedHashMap;

public class EnrichmentScore {

	List<String> maybeLeading, leading;
	CaselessStringList pathway;
	LinkedHashMap<String,Double> data;
	double lenPath, lenDataSet, members, currentScore = 0, maxScore = 0;
	String path;
	DataStorage storage;
	
	public EnrichmentScore(String path, String sample){
		storage = DataStorage.getInstance();
		this.data = storage.getData();
		this.pathway = new CaselessStringList(storage.getMetabolitePathways().get(path));
		this.lenDataSet = (double) data.size();
		this.lenPath = (double) pathway.size();
		this.members = storage.getMembers(path);
		this.path = path;
	}
	
	
	// the list in the strings is the leading edge set, but we don't really use it ever,
	// so the method below is more useful.
	public Pair<Double,List<String>> enrichment(LinkedHashMap<String,Double> data){
		double score = 0., pMiss = 0., maxScore = 0., pHit = 0.;
		
		ArrayList<String> leadingEdgeSet = new ArrayList<String>();
		ArrayList<String> maybeLeading = new ArrayList<String>();
	
		lenDataSet = data.size();
		lenPath = pathway.size();
		
		
		
		
		// for every metabolite in the set
		for(String met : data.keySet()){
			//increase pHit if the metabolite in the data is in the given 
			// metabolite set, or increase pMiss
			
			double hitBonus = 1 / (double) members;
			
			if(pathway.contains(met)){
				pHit += hitBonus;// * data.get(met);
			} else {
				pMiss += 1 / (double) Math.abs(lenDataSet - lenPath);
			}
			
			// calculate this iteration's score
			score = pHit - pMiss;
			
			// no longer need leading edge set
			// queue up this metabolite to the leading edge set
			if(Math.abs(maxScore) > Math.abs(score)){
				maybeLeading.add(met);
			
			} else {
				// otherwise, store the new maxScore, add in the queue to 
				// the leading edge set, and add the current metabolite
				// to the leading edge set
				maxScore = score;
				leadingEdgeSet.addAll(maybeLeading);
				leadingEdgeSet.add(met);
			}
		}
		
		//System.out.println(path);
		//System.out.println(score);
		
		//maxScore = maxScore / (double) lenPath;
		
		return new Pair<Double,List<String>>(maxScore,leadingEdgeSet);	
	}

	public Pair<Double,List<String>> enrich(){
		//return new Pair<Double,List<String>>(enrichmentScoreOnlyMootha(HashSorter.sortData(this.data, 0)), new ArrayList<String>());
		return new Pair<Double,List<String>>(enrichmentScoreOnly(HashSorter.sortData(this.data, false)), new ArrayList<String>());
	}
	

	// same as above but without storing information on the leading edge subset
	public double enrichmentScoreOnly(LinkedHashMap<String,Double> data){
		double score = 0., pMiss = 0., maxScore = 0., pHit = 0.;
		
		lenDataSet = data.size();
		lenPath = pathway.size();
		double hitBonus = 1 / (double) members;
		double missBonus = 1 / (double) Math.abs(lenDataSet - lenPath);
		
		HashSet<Double> dataHashSet = new HashSet<Double>();
		dataHashSet.addAll(data.values());
		if((lenDataSet / 2) > dataHashSet.size()){
			return 0;
		}
		
		for(String met : data.keySet()){
			//increase pHit by 1  if the metabolite in the data is in the given 
			// metabolite set

		
			
			if(pathway.contains(met)){
				pHit += hitBonus;// *  data.get(met);
			} else {
				pMiss += missBonus;
			}
			
			// calculate this iteration's score
			score = pHit - pMiss;
			
			// store the score if it is a new maximum
			maxScore = (Math.abs(maxScore) > Math.abs(score)) ? maxScore : score;
		
		}
		
		//maxScore = maxScore / (double) lenPath;
		return maxScore;	
	}
	

	public double enrichmentScoreOnlyMootha(LinkedHashMap<String,Double> data){
		double score = 0., pMiss = 0., maxScore = 0., pHit = 0.;
		
		lenDataSet = data.size();
		lenPath = pathway.size();
		double hitBonus = Math.sqrt((data.size() - members) / (double) members);
		double missBonus = Math.sqrt(members / (double) (data.size() - members));
		
		
		
		for(String met : data.keySet()){
			//increase pHit by 1  if the metabolite in the data is in the given 
			// metabolite set

		
			
			if(pathway.contains(met)){
				pHit += hitBonus;// *  data.get(met);
			} else {
				pMiss += missBonus;
			}
			
			// calculate this iteration's score
			score = pHit - pMiss;
			
			// store the score if it is a new maximum
			maxScore = (Math.abs(maxScore) > Math.abs(score)) ? maxScore : score;
		
		}
		
		//maxScore = maxScore / (double) lenPath;
		return maxScore;	
	}

	
	
	public double enrichScoreOnly(){
		LinkedHashMap<String,Double> newData = data;
//		return this.enrichmentScoreOnlyMootha(HashSorter.shuffleData(newData));
		return this.enrichmentScoreOnly(HashSorter.shuffleData(newData));
	}
	

	
	
	
	
	public ArrayList<Double> storeEnrichmentIterations(LinkedHashMap<String,Double> data){
		return storeEnrich(HashSorter.sortData(this.data, false));
	}
	
    // this method used to generate data for mountain plots
	public ArrayList<Double> storeEnrich(LinkedHashMap<String,Double> data){
		double score = 0., pMiss = 0., pHit = 0.;
		
		LinkedHashMap<String,Double> newData = HashSorter.sortData(data, false);
		
		lenDataSet = data.size();
		lenPath = pathway.size();
		ArrayList<Double> scores = new ArrayList<Double>();
		
		System.out.println(path);
		
		if(path.contains("TCA")){
			System.out.println(pathway);
			System.out.println("poops");
		}
		
		if(path.contains("TCA")){
			for(String met : pathway){
				if(data.keySet().contains(met)){
					System.out.println(met);
				} else {
					System.out.println("MISSED: " + met);
				}
			}
		}
		
		// for every metabolite in the set
		for(String met : newData.keySet()){
			
			if(path.contains("TCA") && pathway.contains(met)){
				System.out.println(met);
			}
			//increase pHit if the metabolite in the data is in the given 
			// metabolite set, or increase pMiss
			
			double hitBonus = 1 / (double) members;
			
			if(pathway.contains(met)){
				pHit += hitBonus;// * data.get(met);
			} else {
				pMiss += 1 / (double) Math.abs(lenDataSet - lenPath);
			}
			
			// calculate this iteration's score
			score = pHit - pMiss;
			
			
			
			scores.add(score);
		}
		
		return scores;	
	}
	
	
	
}
