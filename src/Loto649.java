import java.util.*;

public class Loto649 {

	public ArrayList<ArrayList<Integer>> games;
	public int line_size = 6;

	
	public Loto649(){
		this.games = new ArrayList<ArrayList<Integer>>();		
	}
	
	public Loto649(int line_size){
		this();
		this.line_size = line_size;
	}
	
	public static void main(String...args){
		Loto649 thisgame = new Loto649();
		thisgame.playGame(8,true);
		thisgame.printGame();
		thisgame.testDuplication();
		
	}
	
	public ArrayList<ArrayList<Integer>> playGame(int no_lines, boolean unique_all_lines){	
		for (int i=0; i< no_lines; i++){
			this.games.add(playLine(unique_all_lines));
		}
		return this.games;
	}
	

	private ArrayList<Integer> playLine(boolean unique_for_game){
	
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
		
    private boolean isNumberInGame(int num){
		for (ArrayList<Integer> line: this.games){			
			if(line.contains(num))				
				return true;
		}
		return false;
	}		
		
	private int getRandomNumber(){
		Random r = new Random();
		return (r.nextInt(49) + 1);
	}	
	
	
	public void printGame(){
		StringBuilder printer = new StringBuilder();
		for (ArrayList<Integer> line: this.games){
			printer.append(Arrays.toString(line.toArray()));
			printer.append("\n");
		}		
		System.out.print(printer.substring(0,printer.length() - 1));
	}

	// this must be moved to unit tests.
	private void testDuplication(){
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
