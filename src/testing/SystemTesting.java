package testing;

import lotogames.*;

public class SystemTesting {
	public static void main (String...args) {		
		
		//SystemTesting.testDbScriptReader();
		SystemTesting.testSplit();
		
		
	}
	
	public static void testDbScriptReader() {
		DbScriptReader ds = new DbScriptReader();		
		for (String k: ds.create_scripts.keySet())
			System.out.println(k + " " + ds.create_scripts.get(k));
		
		System.out.println(ds.getCreateScriptSql("game_loto649_draws"));
	}
	
	public static void testSplit() {
		String p = "this is a test string;Just checking ; if it all works;";
		
		String[] b = p.split(";");
		
		for (int i = 0; i < b.length; i++) {
			System.out.println(i + "->" + b[i]);
		}
	}
}
