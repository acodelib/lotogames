package lotogames;

import java.nio.file.Path;
import java.util.ArrayList;


public class DbScriptReader {
//------------------------- ATTRIBUTES--------------------------------------------------------------	
	public ArrayList<Path> script_paths;
	public ArrayList<String> create_scripts;
//------------------------- CONSTRUCTORS -----------------------------------------------------------
	public DbScriptReader() {
		this.script_paths = new ArrayList<Path>();
		this.getScriptFileNames();
	}
	
//------------------------- METHODS ----------------------------------------------------------------
	private void getScriptFileNames() {
		
	}	
	
	public String getScriptFileText() {
		return "Salut";
	}
	
//------------------------TESTS---------------------------------------------------------------------- 	
	public static void main (String...args) {
		
	}
}

