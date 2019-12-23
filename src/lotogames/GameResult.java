package lotogames;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.time.LocalDateTime;



public abstract class GameResult{
	protected LinkedHashMap<String,Integer> items;	
	protected LocalDateTime play_time;
	protected String game_type;
	
	public GameResult(){
		this.items 		= new LinkedHashMap<String,Integer>();		
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
	public abstract String gameToStringSimple();
	public abstract void parseGameLine(ArrayList<Integer> numbers);
	
}