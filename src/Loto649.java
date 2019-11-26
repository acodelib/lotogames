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
	
	private int getRandomNumber(){
		Random r = new Random();
		return (r.nextInt(49) + 1);
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
				//System.out.println(this_number + " is_picked = " + true);				
				this_number = this.getRandomNumber();
			}
			line.add(this_number);
		}		
		return line;
	}
		
    private boolean isNumberInGame(int num){
		for (ArrayList<Integer> line: this.games){
			//System.out.println("Searching: " + Arrays.toString(line.toArray()));
			if(line.contains(num))				
				return true;
		}
		return false;
	}		
		
	public void playGame(int no_lines, boolean unique_all_lines){	
		for (int i=0; i< no_lines; i++){
			this.games.add(playLine(unique_all_lines));
		}
	}
	
	public void printGame(){
		StringBuilder printer = new StringBuilder();
		for (ArrayList<Integer> line: this.games){
			printer.append(Arrays.toString(line.toArray()));
			printer.append("\n");
		}
		System.out.print(printer);
	}

	private void testDuplication(){
		
	}

	public static void main(String...args){
		Loto649 thisgame = new Loto649();
		thisgame.playGame(8,true);
		thisgame.printGame();
		
	}

	

	

}
