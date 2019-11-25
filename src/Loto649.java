import java.util.*;

public class Loto649 {
	public ArrayList<ArrayList<Integer>> games;
	public int line_size = 6;
	
	public Loto649(){
		this.games = new ArrayList<ArrayList<int>>();
		
	}
	
	private int getRandomNumber(){
		Random r = new Random();
		return (r.nextInt(49) + 1);
	}
	
	private int[] playLine(boolean unique_for_game){
		ArrayList<int> line = new ArrayList<int>();
		int this_number = this.getRandomNumber();
		line.add(this_number);
		System.out.println(line.get(0));		
		
		for (int i = 1; i<6; i++){			
			this_number = this.getRandomNumber();
			boolean is_picked = line.contains(this_number);			
			while (is_picked){
				this_number = this.getRandomNumber();
				is_picked = line.contains(this_number);				
			}
			line.add(this_number);
		}		
		return line;
	}
	
	
		
	public int playGame(int no_lines, boolean unique_all_lines){
		int number;
		for (int i = 0; i< no_lines; i++){
			int[] this_line = new int[6];
			this.games.add(this_line);
			for (int j =1; j<= 6; j++){
				boolean unique_in_line = true;
				while (true){
					int a = 1;
				}
			}
			
		}
		return 1;
	}
	
	
	
	public static void main(String...args){
		Loto649 thisgame = new Loto649();
		System.out.println(Arrays.toString(thisgame.playLine(true)));
		
	}
	
	
}