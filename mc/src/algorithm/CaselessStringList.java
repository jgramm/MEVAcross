package algorithm;

import java.util.ArrayList;
import java.util.List;


// This class is just a list wrapper to force elements in a list to be equal
// regardless of case
public class CaselessStringList extends ArrayList<String>{

	private static final long serialVersionUID = 3188636276876533047L;

	public CaselessStringList(){
		super();
	}
	
	public CaselessStringList(List<String> list){
		super(list);
	}	
	
	public CaselessStringList(ArrayList<String> list){
		super(list);
	}	

	
	// ignore case
	@Override 
	public boolean contains(Object o){
		String paramStr = (String) o;
		for(String s : this){
			if(paramStr.equalsIgnoreCase((String) s)){
				return true;
			}
		}
		return false;
		
	}
	
}
