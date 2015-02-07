package algorithm;

import java.util.*;


public class DataStorage {

	public static int iterations = 1001;
	public static int minMetabolitesPerPath = 6;
	private static LinkedHashMap<String,Double> data;
	private static Map<String,Integer> members;
	private static Map<String,Double> FDR;
	public static Map<String,Double> NES;
	private static Map<String,Double> pValues;
	private static List<Double> nullDistScores;
	public static Map<String,List<Double>> namedScores;
	private static Map<String,List<String>> metabolitePathways;
	private static List<Pathway> analysis;
	public static boolean usepValues = false;
	
	private DataStorage(){
		clear();
	}
	
	private static DataStorage singleton = new DataStorage();
	
	public static DataStorage getInstance(){
		return singleton;
	}

	// resets the data
	public static void clear(){
		data = new LinkedHashMap<String,Double>();
		FDR = new HashMap<String,Double>();
		NES = new HashMap<String,Double>();
		pValues = new HashMap<String,Double>();
		namedScores = new HashMap<String,List<Double>>();
		members = new HashMap<String,Integer>();
		nullDistScores = new ArrayList<Double>();
		namedScores = new HashMap<String,List<Double>>();
		analysis = new ArrayList<Pathway>();
	}
	
	
	// These two methods should be run before any other computations are done
	// store the pathways
	public void importPathways(Map<String,List<String>> pathways){
		if(metabolitePathways == null){
			metabolitePathways = pathways;
		}
	}
	
	// store the data
	public void importData(LinkedHashMap<String,Double> givenData){
		data = givenData;
	}
	
	
	
	
	
	
	
	// store a normalized enrichment score from the given pathway
	public void putNES(String path, double score){
		NES.put(path, score);
	}
	
	
	// normalize and store a null distribution with its pathway
	// probably outdated????
    // no longer using this
	public void putNamedNullDistScores(String path, List<Double> scores, double average){
		
		List<Double> normalizedScores = new ArrayList<Double>();
		for(Double d : scores){
			normalizedScores.add(d / average);
		}
		namedScores.put(path, normalizedScores);
	}
	
	
	// normalize and store a null distribution 
	public void putNullDistScores(List<Double> scores, double average){
		List<Double> normalizedScores = new ArrayList<Double>();
		for(Double d : scores){
			normalizedScores.add(d / average);
		}
		nullDistScores.addAll(normalizedScores);
	}
	
	// store a pValue with its pathway
	public void putPValue(String path, double pValue){
		pValues.put(path, pValue);
	}
	
	public Map<String,List<String>> getMetabolitePathways(){
		return metabolitePathways;
	}
	
	public LinkedHashMap<String,Double> getData(){
		return data;
	}
	
	
	
	// calculate how many elements of a given set are measured
	private void calculateMembers(){
		int hitCount;
		
		// for each pathway
		for(String pathway : metabolitePathways.keySet()){
			hitCount = 0;
		
			// count how many metabolites are in the pathway
			for(String met : data.keySet()){
				if(metabolitePathways.get(pathway).contains(met)){
					hitCount++;
				}
			}
			members.put(pathway, hitCount);
		}
	}
	
	public int getMembers(String s){
		if(members.size() == 0){
			calculateMembers();
			
		}
		return members.get(s);
	}
	
	/*System.out.println(s);
	System.out.println("indexes:");
	System.out.println(index1);
	System.out.println(index2);
	System.out.println(index3);
	System.out.println(index4);
	System.out.println("score");
	System.out.println(score);
	System.out.println("FDR1,2,total");
	System.out.println(maybeFDR1);
	System.out.println(maybeFDR2);
	System.out.println(individualFDR);
	*/
	
	// This should be run before computing pathway information
	///////////////////////////////////////////////// computeFDR2 is now preferred over this ///////////////////////////////////////////////// 
	public void computeFDR(){
		double score;
		Double[] ra = {1.}, NDScores, allNES;
		int index1, index2, index3, index4;
		double individualFDR, maybeFDR1, maybeFDR2;
		List<Double> ndList, NESList;
			
		NESList = new ArrayList<Double>(NES.values());
		Collections.sort(NESList);
		allNES = NESList.toArray(ra);
		//System.out.println("allNES: ");
		//System.out.println(NESList);
		
		ndList = nullDistScores;
		Collections.sort(ndList);
		NDScores = ndList.toArray(ra);	
		
		
		for(String s : NES.keySet()){
			score = NES.get(s);
			
			
			//System.out.println(s);
			//System.out.println(ndList);
			
			index1 = Arrays.binarySearch(allNES, score);
			index2 = Arrays.binarySearch(NDScores, score);
			
			if(index1 < 0){
				index1 = -index1 - 1;
			}
			
			if(index2 < 0){
				index2 = -index2 - 1;
			}
			
			index3 = allNES.length - index1;
			index4 = NDScores.length - index2;
			
			maybeFDR1 = index4 * allNES.length / (double) (index3 * NDScores.length);
			maybeFDR2 = index2 * allNES.length / (double) (index1 * NDScores.length);
	        individualFDR = Math.min(maybeFDR2, maybeFDR1);
			FDR.put(s,individualFDR);
			
		}
	}
	


    // this is the FDR computation currently being used
	public void computeFDR2(){
		double score, individualFDR;
		Double[] posNDScores, negNDScores, posNES, negNES, ra = {0.};
		int index1, index2;
		List<Double> posNDScoreList = new ArrayList<Double>(), negNDScoreList = new ArrayList<Double>(), posNESList = new ArrayList<Double>(), negNESList = new ArrayList<Double>(); 
		
		for(Double d : nullDistScores){
			if(d > 0){
				posNDScoreList.add(d);
			} else {
				negNDScoreList.add(d);
			}
		}
		
		for(Double d : NES.values()){
			if(d > 0){
				posNESList.add(d);
			} else {
				negNESList.add(d);
			}
		}
		
		
		
		Collections.sort(posNDScoreList);
		Collections.sort(negNDScoreList);
		Collections.sort(posNESList);
		Collections.sort(negNESList);
		
		posNDScores = posNDScoreList.toArray(ra);
		negNDScores = negNDScoreList.toArray(ra);
		posNES = posNESList.toArray(ra);
		negNES = negNESList.toArray(ra);
		
		for(String s : NES.keySet()){
			score = NES.get(s);
		
			// for positive scores
			if(score > 0){
				index1 = Arrays.binarySearch(posNES, score);
				index2 = Arrays.binarySearch(posNDScores, score);
				
				//System.out.println(s);
				//System.out.println(score);
				
				// get proper index if score not found in list
				index1 = (index1 < 0) ? (-index1 - 1) : index1;
				index2 = (index2 < 0) ? (-index2 - 1) : index2;
				
				// subtract off index from length to get number of elements
				// greater than this element
				index1 = posNES.length - index1;
				index2 = posNDScores.length - index2;
				
				//System.out.println(index1);
				//System.out.println(index2);
				
				try{
				    // formula for fdr
					individualFDR = index2 * posNES.length / (double) (index1 * posNDScores.length);
				} catch (Exception ex){
					System.out.println("positive crap");
					individualFDR = 2;
				}
			
				// same thing but for negative scores
			} else {
				index1 = Arrays.binarySearch(negNES, score);
				index2 = Arrays.binarySearch(negNDScores, score);
				
				//System.out.println(s);
				//System.out.println(score);
				
				index1 = (index1 < 0) ? (-index1 - 1) : index1;
				index2 = (index2 < 0) ? (-index2 - 1) : index2;

				//System.out.println(index1);
				//System.out.println(index2);
				
				try{
					individualFDR = index2 * negNES.length / (double) (index1 * negNDScores.length);
				} catch (ArithmeticException ex){
					System.out.println("anti-crap");
					individualFDR = 3;
				}
			}
			
			if(Double.isInfinite(individualFDR)){
				//System.out.println("Infinite FDR");
				individualFDR = 0.;
			} else if(Double.isNaN(individualFDR)){
				//System.out.println("NaN FDR");
				individualFDR = 0.;
			} else {
				//System.out.println("FDR FOR THIS IS: ");
				//System.out.println(individualFDR);
				individualFDR = Math.min(individualFDR / 2., 1.);
			}
			
			//System.out.println(individualFDR);
						
			FDR.put(s, individualFDR);
			
		}
		
		//System.out.println("SIZES");
		//System.out.println(posNES.length + "\t" + posNDScores.length + "\t" + negNES.length + "\t" + negNDScores.length);
		
	}
	
	public double harmonic(int n){
		double h = 0.;
		
		for(double i  = 1.; i <= n; i++){
			h += 1 / i; 
		}
		
		return h;
	}
	
	public double BenjaminiHochbergYekutieli(){
		
		ArrayList<Double> allpValues = new ArrayList<Double>();

		// store and sort pvalues
		allpValues.addAll(pValues.values());
		Collections.sort(allpValues);
		double alpha = .05;
		double cutoff = .05;
		double h = 1.;

		// get nth harmonic number
		h = harmonic(allpValues.size());
		//h = .2 * h;
		
		//System.out.println("Start BHY");
		for(int i = 1; i <= allpValues.size(); i++){
			//System.out.println("cutoff" + i * alpha / (h * allpValues.size()));
			//System.out.println("p(k)" + allpValues.get(i));
			
			// for each iteration, adjust cutoff until
			// the kth pValue is greater than it
			cutoff = i * alpha / (h * allpValues.size());
			if(cutoff < allpValues.get(i - 1)){
				break;
			}
		}
		return cutoff;
	}
	
	
	// build the pathway data structures
	public void computePathways(){
		Pathway p;
		analysis = new ArrayList<Pathway>();
		//double cutoff = BenjaminiHochbergYekutieli();
		
		for(String s : metabolitePathways.keySet()){
			// if we measure at least four metabolites in the pathway
			if(members.get(s) >= DataStorage.minMetabolitesPerPath){
				// build the pathway
				
				String info;
				if(usepValues){
					if(pValues.get(s) <= .05 && FDR.get(s) <= .25){
						info = Pathway.dysregulated;
					} else {
						info = Pathway.notDysregulated;
					}
				} else {
					if(NES.get(s) > 0){
						info = Pathway.enriched;
					} else {
						info = Pathway.depleted;
					}
				}
				
				//String significant = pValues.get(s) < cutoff ? Pathway.significant : Pathway.notSignificant;
				p = new Pathway(s, info, metabolitePathways.get(s).size(),
						members.get(s),	pValues.get(s), FDR.get(s), 
						NES.get(s));
				// and store it
				analysis.add(p);
			}
		}
	}
	
	public List<Pathway> getAnalysis(){
		if(analysis.size() == 0){
			computePathways();
		}
		//System.out.println();
		//System.out.println("HEY LOOK AT ME OVER HERE!!!");
		//System.out.println("BHY CUTOFF IS : " + BenjaminiHochbergYekutieli());
		return analysis;
	}
	
}
