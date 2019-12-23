package lotogames;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.sql.DatabaseMetaData;

public class DbOps {
	Connection conn;
	
	public static void main(String...args) throws SQLException{
		DbOps loto_db = new DbOps();
		DatabaseMetaData meta = loto_db.conn.getMetaData();
		
		System.out.println("SQL LITE connection successfull: "+ meta);		
		Loto649Session thisgame = new Loto649Session();
		
		int i = 1;
		
		for (GameResult r: thisgame.playGame(8, true)) {
			int ix =  loto_db.getNewIx("LotoGame");
			loto_db.saveGameResult(r, ix,i);			
			for(GameResult n: thisgame.game_results) {
				System.out.println(n.gameToString().trim());
			}
			i++;
		}
		
	}	
	
	public DbOps() throws SQLException {
		String url = "jdbc:sqlite:./Database/lotogames.db";
		this.conn = DriverManager.getConnection(url);
	}
	
	public void saveGameResult(GameResult result, int session_id, int game_id) throws SQLException {
		//saves a GameResult to database. requires game_id!
		
		String sql = "INSERT INTO game_plays (session_id,date,game,value_name,value) VALUES (?,?,?,?,?)";		
		PreparedStatement ins = this.conn.prepareStatement(sql);
		
		String game_datetime = result.play_time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				
		Iterator<?> it = result.items.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<?, ?> pair = (Map.Entry<?, ?> )it.next();			
			ins.setInt(1, session_id);
			ins.setString(2,game_datetime);
			ins.setInt(3, game_id);
			ins.setString(4, (String)pair.getKey());
			ins.setInt(5, (int)pair.getValue());
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
}