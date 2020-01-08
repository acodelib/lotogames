package lotogames;

import java.io.IOException;
import java.sql.SQLException;


public class GameCore {
	public GameCore() {
		//read configs
		//set attributes
	}
	
	public static void main (String...args) throws SQLException, IOException {
		//instantiate game core
		//start GameSessions thread
		//start Mailer thread
		
		GameCore the_core = new GameCore();
		//the_core.importResultsFromWebArchive649();
		the_core.refillArchive649();
	}
	
	public void runGameSessions() {
		//start game thread
	}
	
	public void runMailer() {
		//start thread with mail listener and communicator
	}
	
	public void importResultsFromWebArchive649() throws SQLException, IOException {
		DbOps db = new DbOps();
		WebArchiveFetcher af = new WebArchiveFetcher();
		
		db.insertArchive649NewLines(af);
	}
	
	public void refillArchive649() throws SQLException, IOException {		
		WebArchiveFetcher af = new WebArchiveFetcher();
		DbScriptReader dsr = new DbScriptReader();
		DbOps db = new DbOps(dsr);
		db.refillArchive649(af);
	}
	
	
	
	
}
