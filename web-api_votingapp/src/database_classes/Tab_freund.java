package database_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import api_classes.Connection;

public class Tab_freund {
	
	public Tab_freund(String upid, String ufid){
		fid = ufid;
		pid = upid;
	}
	
	private String fid;
	private String pid;
	//ACHTUNG:
	//Diese ArrayList nicht verwenden, sie beinhaltet keine Konsistenten Daten;
	//dient nur der Temporären speicherung
	private static ArrayList<Tab_freund> allFriends;
	
	/**
	 * Diese Methode speichert eine Freundschaft einer Person
	 * @param upid Person die die Freundschaft 'gehört'
	 * @param ufid Freund der Person (andere PID)
	 * @return bool wert ob die speicherung erfolgreich verlaufen ist; true = erfolgreich
	 */
	public static boolean saveFreund(String upid, String ufid){
		//Return als bool, ob diese Freundschaft schon
		//existiert (false = existiert)
		String stmt = "SELECT pid, fid FROM freund WHERE pid='"+upid+"' AND fid='"+ufid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false vorhanden
		try{
			rs.next();
			rs.getString("pid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Restaurant in die Datenbank einfügen
		stmt = "INSERT INTO freund (pid, fid) ";
		stmt += "VALUES('"+upid+"','"+ufid+"')";
		return con.writeData(stmt);
	}
	
	/**
	 * Methode, die eine Freundschaft wieder löscht
	 * @param upid Person die die Freundschaft 'gehört'
	 * @param ufid Freund der Person (andere PID)
	 * @return Bool-Wert; true= erfolgreich gelöscht & false = Fehler/ nicht vorhanden
	 */
	public static boolean deleteFreund(String upid, String ufid){
		//Return als bool, ob dieses Restaurant schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM freund WHERE pid='"+upid+"' AND fid='"+ufid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Wenn die Freundschaft nicht vorhanden ist, dann abbruch der Methode
		try{
			rs.next();
			rs.getString("pid");
		}catch(Exception e){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM freund WHERE pid='"+upid+"' AND fid="+ufid+"'";
		return con.writeData(stmt);
	}
	
	/**
	 * Diese Methodeliefert für eine Person alle Freunde
	 * @param upid Person, deren Freundschaften geholt werden
	 * @return Gibt die ArrayList von Freunden einer Person zurück; liefert null, wenn keine Freunde vorhanden
	 */
	public static ArrayList<Tab_freund> getAllFreunde(String upid){		
		//ArrayList clearen, damit Daten stimmen
		allFriends.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM freunde WHERE pid='"+upid+"'";
		
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Alle abgefragten Freundschaften in die ArrayList eintragen
		try{
			while(rs.next()){
				String pid =rs.getString("pid");
				String fid = rs.getString("fid");
				allFriends.add(new Tab_freund(pid, fid));
			}
		}catch(SQLException e){return null;}
		return allFriends;
	}
}
