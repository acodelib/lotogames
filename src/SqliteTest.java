import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

public class SqliteTest {
	public static void main(String...args) throws SQLException{
		String url = "jdbc:sqlite:C:/DIRECT/source_code/lotogames/Database/lotogames.db";
		Connection conn = DriverManager.getConnection(url);
		DatabaseMetaData meta = conn.getMetaData();
		
		System.out.println(meta);
	}
}