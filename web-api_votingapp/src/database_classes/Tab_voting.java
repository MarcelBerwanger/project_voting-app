package database_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import api_classes.Connection;


public class Tab_voting {
	
	private Tab_voting(String urid, String upid){
		rid = urid;
		pid = upid;
	}
	
	private String rid;
	private String pid;
	//ACHTUNG:
	//nicht verwenden, dies ist eine temporäre ArrayList zur zwischenspeicherung
	//die Daten sind nicht konsistent!
	private static ArrayList<Tab_voting> allVotes;
	
	/**
	 * Das abgegebene Vote in der Datenbank speichern
	 * @param upid Benutzer, der das Vote abgegeben hat
	 * @param urid Restaurnat, für das gevotet wurde.
	 */
	public static boolean saveVote(String upid, String urid){
		//Return als bool, ob dieser Benutzer schon
		//ein Vote gegeben hat (false = Fehler)
		String stmt = "SELECT pid FROM bewertung WHERE pid='"+upid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Schauen, ob der Benutzer schon ein Vote gegeben hat, wenn ja beendet die Methode mit return flase
		try{
			rs.next();
			rs.getString("pid");
		}catch(Exception e){return false;}
		
		//Voting in die Datenbank einfügen
		stmt = "INSERT INTO voting (pid, rid) ";
		stmt += "VALUES('"+upid+"','"+urid+"')";
		return con.writeData(stmt);
	}
	
	/**
	 * Diese Methode liefert die Votes für ein Restaurant zurück, welche in der Datenbank liegen
	 * @param urid Restaurant-ID
	 * @return ArraList mit allen Votes eines Restaurants; return null, wenn Tabelle leer!
	 */
	public static ArrayList<Tab_voting> getVotesRestaurant(String urid){
		//Clearen, damit keine doppelten Vorkommen
		allVotes.clear();
		
		//Statement, welches alle votings in der Datenbank abrafgt
		String stmt = "SELECT * FROM voting";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		try{
			while(rs.next()){
					String restaurant = rs.getString("rid");
					String person = rs.getString("pid");
					allVotes.add(new Tab_voting(restaurant, person));
				}
		}catch(SQLException e){return null;}
		return allVotes;
	}
	
	/**
	 * Methode, die alle Votes aus der Datenbank löscht
	 * @return Boolean, ob löschen erfolgreich oder nicht; true = success
	 */
	public static boolean deleteAllVotes(){
		//Statement zum Löschen der Votes
		String stmt = "DELETE * FROM voting";
		
		//Durch API Aufruf ersetzen!!
		Connection con = new Connection();
		return con.writeData(stmt);
	}
	
	/**
	 * Diese Methode überprüft, ob eine Person schon gevotet hat
	 * @param upid Personen ID
	 * @return Bool-Wert, der aussagt ob die Person schon gevotet hat; true = hat gevotet & false hat nicht gevotet
	 */
	public static boolean checkVote(String upid){
		//Statement, welches alle votings in der Datenbank abrafgt
		String stmt = "SELECT * FROM voting WHERE pid='"+upid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Wenn die Person schon gevotet hat, dann return true, sonst false
		try{
			rs.next();
			rs.getString("pid");
		}catch(Exception e){return true;}
		return false;
	}
}
