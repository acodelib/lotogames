import java.util.*;

public class Loto649Result extends GameResult {
	public void parseGameLine(ArrayList<Integer> numbers){
		//parses all numbers in Loto649 result numbers and inserts corespondent in result;
		int i = 0;
		for (Integer num: numbers){
			this.items.put("Line " + i,26);
		}
		
		
	}
	
	
		
}