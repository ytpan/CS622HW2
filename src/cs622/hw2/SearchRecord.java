package cs622.hw2;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class SearchRecord {
	
	private HashMap<Timestamp, String> searchHistory = new HashMap<Timestamp, String>();
	
	public void saveSearch(String s) {
		Timestamp instant = Timestamp.from(Instant.now());
		searchHistory.put(instant, s);
	}
	
	public void displaySearchHistory() {
		
		//create a collection of search keywords and iterator
		HashMap<String, Integer> hm= new HashMap<String, Integer>();
		Collection<String> values = searchHistory.values();
		Iterator<String> it = values.iterator();
		
		//iterate through a collection of keywords
		while(it.hasNext()) {
			
			//s is the keyword
			String s = it.next();
			
			//if keyword existed, count plus 1, if not, put (keyword,1)
			if(!hm.containsKey(s)) {
				hm.put(s, 1);
			}else {
				hm.replace(s, hm.get(s), hm.get(s) + 1);	
			}
		}
		
		//printing results
		System.out.println("Search History:");
		System.out.printf("%-25s%-30s%s\n", "Keywords", "Time(s) Searched", "Timestamp");
		
		//loop through timestamps to find matching keyword, if matches, print timestamp 
		for(String s: hm.keySet()) {
			System.out.printf("%-33s", s);
			System.out.printf("%-15d", hm.get(s));
			for(Timestamp t: searchHistory.keySet()) {
				if(searchHistory.get(t).equals(s))
					System.out.print(t + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//erase search history
	public void eraseSearchHistory() {
		searchHistory.clear();
		System.out.println("Search history erased.");
	}
}
