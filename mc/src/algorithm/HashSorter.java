package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;


public class HashSorter {
	
	// sorts in descending order
	private static final class ValueComparator implements Comparator<Map.Entry<?, Double>> {
		public int compare(Map.Entry<?, Double> o1, Map.Entry<?, Double> o2) {
	        return o2.getValue().compareTo(o1.getValue());
		}
	}

	public static final Comparator<Map.Entry<?,Double>> SINGLE = new ValueComparator();
	
	//public static void main(String[] args){
	//	SINGLE.compare(new Map.Entry<K, Double>("Hello", 2.0), new Map.Entry<K, Double>("whats up", 3.0));
	//}
	
	
	// sorts a linkedhashmap by value
	public static LinkedHashMap<String,Double> sortData(LinkedHashMap<String,Double> data, boolean isReversed){
		
		final int size = data.size();
		LinkedHashMap<String,Double> finalData = new LinkedHashMap<String,Double>(size);
		
		// turn the map into a list of map entries
		final List<Map.Entry<String,Double>> mapView = new ArrayList<Map.Entry<String,Double>>(size);
		
		mapView.addAll(data.entrySet());
		final ArrayList<String> reusedList = new ArrayList<String>(size);
		
		// sort the collection of map entries
		Collections.sort(mapView, SINGLE);
		final ArrayList<String> keyView = reusedList;
		
		for (int i = 0; i < size; i++){
			keyView.add(i, mapView.get(i).getKey());
		}

		// this was for when I was reverse sorting as well to see the scores
		/*
		if(isReversed){
			Collections.reverse(keyView);
		}*/

		
		// replace the entries into the map
		for(String s : keyView){
			finalData.put(s, data.get(s));
		}
		
		return finalData;
	}
	
	
	
	
	public static LinkedHashMap<String,Double> shuffleData(LinkedHashMap<String,Double> data){
		List<String> keys = new ArrayList<String>(data.keySet());
		List<Double> values = new ArrayList<Double>(data.values());
		
		// randomly shuffle map values and entries
		Collections.shuffle(keys);
		Collections.shuffle(values);
		
		LinkedHashMap<String,Double> shuffledData = new LinkedHashMap<String,Double>();
		
		Iterator<String> iter1 = keys.iterator();
		Iterator<Double> iter2 = values.iterator();
		
		// then zip them back together 		
		while(iter1.hasNext() && iter2.hasNext()){
			shuffledData.put(iter1.next(), iter2.next());
		}
		
		return shuffledData;
		
	}
	
	
	
}
