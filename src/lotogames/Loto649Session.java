package lotogames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.time.LocalDateTime;

public class Loto649Session extends LotoSession {
	
	public static void main(String...args){
		Loto649Session thisgame = new Loto649Session();
		thisgame.playGame(8,true);
		thisgame.printGame();
		thisgame.checkDuplication();
		for(GameResult n: thisgame.game_results) {
			System.out.println(n.gameToString().trim());
		}
	}
	
	public ArrayList<GameResult> playGame(int no_lines, boolean unique_all_lines){
	//plays games by constraint of parameters
		
		LocalDateTime game_time = LocalDateTime.now();
		
		for (int i=0; i< no_lines; i++){
			ArrayList<Integer> local_line = this.playLine(unique_all_lines);
			this.games.add(local_line);
			Loto649Result local_result = new Loto649Result(local_line,game_time);
			this.game_results.add(local_result);		}
		return this.game_results;
	}
	

	protected ArrayList<Integer> playLine(boolean unique_for_game){
	//generates the numbers for a game line
		
		ArrayList<Integer> line = new ArrayList<Integer>();
		int this_number = this.getRandomNumber();
		
		while(unique_for_game && this.isNumberInGame(this_number))
			this_number = this.getRandomNumber();
		line.add(this_number);		
		
		for (int i = 1; i<line_size; i++){			
			this_number = this.getRandomNumber();			
			while (line.contains(this_number) || (this.isNumberInGame(this_number) && unique_for_game)){				
				this_number = this.getRandomNumber();
			}
			line.add(this_number);			
		}		
		Collections.sort(line);		
		return line;
	}
		
    protected boolean isNumberInGame(int num){
    	//checks if parameter exists in this.games ArrayList
    	
		for (ArrayList<Integer> line: this.games){			
			if(line.contains(num))				
				return true;
		}
		return false;
	}		
		
	private int getRandomNumber(){
		//just gets a number
		
		Random r = new Random();
		return (r.nextInt(49) + 1);
	}	
	
	
	public void printGame(){
		//prints the ArrayList<Integer> holding draw numbers
		
		StringBuilder printer = new StringBuilder();
		for (ArrayList<Integer> line: this.games){
			printer.append(Arrays.toString(line.toArray()));
			printer.append("\n");
		}		
		System.out.print(printer.substring(0,printer.length() - 1));
	}


	private void checkDuplication(){
		// this must be moved to unit tests?
		
		String result = "\nDuplication Test Result: No duplicates founds.";
		for (ArrayList<Integer> line: this.games){
			for(Integer num: line){
				for(ArrayList<Integer> line_search: this.games){
					for(Integer num_search: line_search){
						if (!line.equals(line_search) && num == num_search){
							result = "\nDuplication Test Result: At least 1 dup foud: " + num;
							break;
						}									
					}
				}
			}		
		}
		System.out.println(result);
	}
}
