package lotogames;

import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;


public class DbScriptReader {
//------------------------- ATTRIBUTES--------------------------------------------------------------	
	public HashMap<String,String> create_scripts;	
	
	private final String DB_DIR =  "./Database/Scripts/";
	
//------------------------- CONSTRUCTORS -----------------------------------------------------------
	public DbScriptReader()  {
		this.create_scripts = new HashMap<String,String>();
		this.getScriptFileNames("create",this.create_scripts);
	}
	
//------------------------- METHODS ----------------------------------------------------------------
	private void getScriptFileNames(String file_type, HashMap<String,String> storage_attribute) {
		//look in target dir and get all file names
		
		try {
			DirectoryStream<Path> db_dir = Files.newDirectoryStream(Paths.get(this.DB_DIR));
			for (Path file_path: db_dir) {
				String fp_name = file_path.toString().replace("\\", "/");				
				
				if(fp_name.endsWith(".sql") && fp_name.contains(file_type))
					//this.create_scripts[fp_name.toString()] = fp_name.toString();
					storage_attribute.put(this.getObjectNameFromFileName(fp_name,file_type), fp_name);
			}
		}catch(IOException e) {
			System.out.println("Can't load file names from directory Database/Scripts. System message:" + e.toString());
		}		
		
	}	
	
	private String getObjectNameFromFileName(String fn, String file_type) {
		//parses a file path for a create script and returns the object name 
		String object_name 	= "";
		int start 			= 0;
		int end 			= 0;
		
		start = new String(this.DB_DIR + file_type + "_").length();
		end = fn.indexOf(".sql");		
		object_name = fn.substring(start, end);
		
		return object_name;		
	}
	
	public String getCreateScriptSql(String object_name) {
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(this.create_scripts.get(object_name))));
		}catch(IOException e) {
			System.out.println("Can't load file names from directory Database/Scripts. System message:" + e.toString());
		}
		return result;
	}
		
	
	
//------------------------ LOCAL TESTS---------------------------------------------------------------------- 	
	public static void main (String...args) {
		DbScriptReader ds = new DbScriptReader();		
		for (String k: ds.create_scripts.keySet())
			System.out.println(k + " " + ds.create_scripts.get(k));
	}
}

