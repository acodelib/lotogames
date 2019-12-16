package lotogames;

import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
//import java.time.LocalDateTime;


public class Loto649Result extends GameResult {

	
	public Loto649Result(ArrayList<Integer> local_line, LocalDateTime game_time) {
		this.play_time = game_time;
		this.parseGameLine(local_line);
	}

	public void parseGameLine(ArrayList<Integer> numbers){
		//parses all numbers in Loto649 result numbers and inserts correspondent in result;
		int i = 0;
		for (Integer num: numbers){
			this.items.put("#No " + i,num);
			i ++;
		}					
	}
	
	public String gameToString(){
		//prints the result in human readable format

		StringBuilder printer = new StringBuilder("Game Played @");		
		printer.append(this.play_time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " #Numbers: " );
		
		Iterator<?> it = this.items.entrySet().iterator();		
		while(it.hasNext()){
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			printer.append(pair.getValue());
			printer.append(",");
		}
		printer.append("\n");
		return printer.substring(0,printer.length()-2).toString();
	}
	

//	public static void main(String...args) {
//		//local tester:		
//		ArrayList<Integer> m = new ArrayList<Integer>();
//		Integer[] x = {4,5,2,3,4,5};
//		Collections.addAll(m, x);
//		GameResult gr = new Loto649Result();
//		gr.parseGameLine(m);
//		gr.setPlayTime(LocalDateTime.now());
//		System.out.println(gr.gameToString());
//	}

}
			