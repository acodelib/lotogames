package lotogames;

import java.util.*;


public abstract class LotoSession {

	public ArrayList<ArrayList<Integer>> games;
	public int line_size = 6;
	public ArrayList<GameResult> game_results;
	
	public LotoSession() {
		this.games = new ArrayList<ArrayList<Integer>>();		
		this.game_results = new ArrayList<GameResult>();
	}
	
	public LotoSession(int line_size){
		this();
		this.line_size = line_size;
	}

	public abstract ArrayList<GameResult> playGame (int no_lines, boolean unique_all_lines);
	protected abstract ArrayList<Integer> playLine(boolean unique_for_game);
	protected abstract boolean isNumberInGame(int num);
	public abstract void printGame();
	
}