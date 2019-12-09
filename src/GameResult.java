import java.util.ArrayList;
import java.util.HashMap;
import java.time.*;


public abstract class GameResult{
	public HashMap<String,Integer> items;	
	
	public GameResult(){
		this.items = new HashMap<String,Integer>();
	}
	
	public GameResult(ArrayList<Integer> numbers){
		this();		
		this.parseGameLine(numbers);
	}
	
	public abstract void parseGameLine(ArrayList<Integer> numbers);
}