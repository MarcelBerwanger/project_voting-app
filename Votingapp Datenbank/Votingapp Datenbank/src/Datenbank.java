import java.sql.*;

public class Datenbank {

	public static void main( String args[] )
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Opened database successfully");

		try {
			stmt = c.createStatement(); 

			String sql = "CREATE TABLE Person " +
					"(PID INT PRIMARY KEY NOT NULL," +
					" Benutzername TEXT NOT NULL, " + 
					" Klasse TEXT NOT NULL, " + 
					" Passwort TEXT NOT NULL, " + 
					" Rolle CHAR NOT NULL DEFAULT ('U')," +
					"FOREIGN KEY (Rolle) REFERNECES Rolle (Rolle))"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}    
		catch (SQLException e) {}
	}
}