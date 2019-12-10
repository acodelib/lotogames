package lotogames;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;



public abstract class GameResult{
	protected HashMap<String,Integer> items;	
	protected LocalDateTime play_time;
	
	public GameResult(){
		this.items 		= new HashMap<String,Integer>();		
	}
	
	public GameResult(ArrayList<Integer> numbers, LocalDateTime play_time){
		this();		
		this.parseGameLine(numbers);
		this.setPlayTime(play_time);
	}
	
	public void setPlayTime(LocalDateTime play_time){		
		this.play_time = play_time;		
	}
	
	public abstract String gameToString();
	public abstract void parseGameLine(ArrayList<Integer> numbers);
	
}