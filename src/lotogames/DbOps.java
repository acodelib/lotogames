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
	Connection conn;
	
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
	
	public DbOps() throws SQLException {
		String url = "jdbc:sqlite:./Database/lotogames.db";		
		this.conn = DriverManager.getConnection(url);
		this.conn.setAutoCommit(false);
	}
	
	public void commitOps() throws SQLException {
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
	
	public void insertArchive649NewLines(ArchiveFetcher get_new_lines) throws SQLException, IOException {
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
	}

	public void refillArchive649(ArchiveFetcher get_arch) throws SQLException, IOException {
		//clears (***recreate) game_loto649_draws and fetches all archive	
			
		
		String sql_insrt  			= "INSERT INTO game_loto649_draws (date,n1,n2,n3,n4,n5,n6) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ins		= this.conn.prepareStatement(sql_insrt);
		String sql_clear   			= 	"DROP TABLE IF EXISTS game_loto649_draws;\r\n" + 
										"CREATE TABLE game_loto649_draws (\r\n" + 
										"	id INTEGER PRIMARY KEY,\r\n" + 
										"	date text,\r\n" + 
										"	n1 INTEGER,\r\n" + 
										"	n2 INTEGER,\r\n" + 
										"	n3 INTEGER,\r\n" + 
										"	n4 INTEGER,\r\n" + 
										"	n5 INTEGER,\r\n" + 
										"	n6 INTEGER\r\n" + 
										");";		
		//java.sql.Statement clear 	= this.conn.createStatement();
		PreparedStatement clear 	= this.conn.prepareStatement(sql_clear);
		ArrayList<String[]> lines 	= new ArrayList<String[]>();
		
			
		//get last inserted id from table with loto history		
		clear.execute();
		
		
						
		//get results from the web
		lines = get_arch.fetchNewLoto649(0);					
		for(String[] line: lines) {
			for (int i = 0; i< line.length;i++) {
				ins.setString(i+1, line[i]);				
			}
			ins.execute();
		}
		this.conn.commit();
	}
	
}

