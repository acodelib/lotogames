package lotogames;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;


public class DbOps {
	
//------------------------------------------------------------ ATTRIBUTES -----------------------------------------------------------------------------------	
	private Connection conn;
	public DbScriptReader local_sql_scripts;
	
//------------------------------------------------------------ CONSTRUCTORS -----------------------------------------------------------------------------------	
	public DbOps() {
		this.instantiationRoutine();
	}
	
	public DbOps(DbScriptReader reader) {
		this.instantiationRoutine();
		this.local_sql_scripts = reader;
	}
	
	private void instantiationRoutine() {
		try {
			String url = "jdbc:sqlite:./Database/lotogames.db";		
			this.conn = DriverManager.getConnection(url);
			this.conn.setAutoCommit(false);			
		}catch(SQLException e) {
			System.out.println("DbOps component can't connect to the SqlLite file. Original System erro: " + e.toString()); 
		}
	}
//------------------------------------------------------------ METHODS -----------------------------------------------------------------------------------	
	private String[] splitScriptIntoStatements(String sql_script) {
		String[] stmts = sql_script.split(";");
		
		return stmts;
	}
	
	private int getNewIx(String optional_text) throws SQLException {
		//get new ID from table GAME CORE KEYS
		
		int ix = 0;
		
		String ins_sql = "INSERT INTO game_core_ix (optional) VALUES (?)";
		String get_sql = "SELECT max(rowid) FROM game_core_ix"; //rowid is standard sqlite implicit cursor
		
	
		PreparedStatement ins = this.conn.prepareStatement(ins_sql);
		java.sql.Statement get_ix = this.conn.createStatement();
		
		ins.setString(1,optional_text);
		ins.execute();
		ResultSet rs_ix = get_ix.executeQuery(get_sql);
		
		while(rs_ix.next())			
			ix = rs_ix.getInt(1);
		
		return ix;				
	}
	
	public void commitOps() throws SQLException {
		//calls the JDBC API default commit. Meant to be used independently at most convenient times
		this.conn.commit();
	}
	
	public void saveGameResult(GameResult result, int session_id, int game_id) throws SQLException {
		//saves a GameResult to database. requires game_id!
		
		String sql = "INSERT INTO game_plays (session_id,date,game_id,game_type,value_name,value) VALUES (?,?,?,?,?,?)";		
		PreparedStatement ins = this.conn.prepareStatement(sql);
		
		String game_datetime = result.play_time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				
		Iterator<?> it = result.items.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<?, ?> pair = (Map.Entry<?, ?> )it.next();			
			ins.setInt(1, session_id);
			ins.setString(2,game_datetime);
			ins.setInt(3, game_id);
			ins.setString(4, result.game_type);			
			ins.setString(5, (String)pair.getKey());
			ins.setInt(6, (int)pair.getValue());
			ins.execute();
		}		
	}
	
	
	
	public void insertArchive649NewLines(WebArchiveFetcher get_new_lines) throws SQLException, IOException {
		//gets max of id in game_loto649_draws. then checks the web if any new ones were played. if so fetch them in
		
		int last_ck 	  			= 0;				
		String sql_insrt  			= "INSERT INTO game_loto649_draws (date,n1,n2,n3,n4,n5,n6) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ins		= this.conn.prepareStatement(sql_insrt);
		String sql_max    			= "Select max(id) FROM game_loto649_draws";
		java.sql.Statement get_ck   = this.conn.createStatement();
		ArrayList<String[]> lines 	= new ArrayList<String[]>();
		
			
		//get last inserted id from table with loto history		
		ResultSet rs_ck = get_ck.executeQuery(sql_max);
		while(rs_ck.next())
			last_ck = rs_ck.getInt(1);
		
						
		//get results from the web
		lines = get_new_lines.fetchNewLoto649(last_ck);
		if (lines.size() == 0) {
			System.out.println("@insertAcrchive649NewLines --> No new games to insert");			
			return;
		}			
		for(String[] line: lines) {
			for (int i = 0; i< line.length;i++) {
				ins.setString(i+1, line[i]);			
			}
			ins.execute();
		}
		this.conn.commit();
		System.out.println("@insertAcrchive649NewLines --> Inserted " + lines.size() + " new lines");
	}

	public void refillArchive649(WebArchiveFetcher get_arch) throws SQLException, IOException {
		//clears (***recreate) game_loto649_draws and fetches all archive	
	
		String sql_insrt  			= "INSERT INTO game_loto649_draws (date,n1,n2,n3,n4,n5,n6) VALUES (?,?,?,?,?,?,?)";		
		String sql_clear   			= this.local_sql_scripts.getCreateScriptSql("game_loto649_draws");		
		ArrayList<String[]> lines 	= new ArrayList<String[]>();
		
			
		//run all statements
		for(String btch: this.splitScriptIntoStatements(sql_clear)) {			
			this.conn.createStatement().execute(btch);			
		}
		PreparedStatement ins = this.conn.prepareStatement(sql_insrt); //ins is setup here because we need to be sure the exists
	
		//get ALL results from the web
		lines = get_arch.fetchNewLoto649(0);					
		for(String[] line: lines) {
			for (int i = 0; i< line.length;i++) {
				ins.setString(i+1, line[i]);				
			}
			ins.execute();			
		}
		this.conn.commit();
	}

//------------------------------------------------------local testing ---------------------------------------------------------------------------------------
	public static void main(String...args) throws SQLException{
		DbOps loto_db = new DbOps();
		DatabaseMetaData meta = loto_db.conn.getMetaData();		
		System.out.println("SQL LITE connection successfull: "+ meta);		
		Loto649Session thisgame;
		
		int a = 1;
		while (a <2) {
			thisgame = new Loto649Session();			
			int i = 1;		
			int ix =  loto_db.getNewIx("LotoGame");			
			for (GameResult r: thisgame.playGame(8, true)) {			
				loto_db.saveGameResult(r, ix,i);				
				System.out.println(r.gameToStringSimple().trim());
				i++;
			}			
			a++;
		}
		loto_db.commitOps();
	}	
	
}

