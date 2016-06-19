package database_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import api_classes.Connection;

/**
 * Diese Klasse soll nur von Restaurant & Personen-Konstruktor aufgerufen werden!
 * @author Marcel
 *
 */
public class Tab_bewertung {
	
	private Tab_bewertung(String uben, String uresId, double ustern, String ubewer){
		
		benutzer=uben;
		restaurantId = uresId;
		sterne = ustern;
		bewertung_text = ubewer;
	}
	
	private String benutzer;
	private String restaurantId;
	private double sterne;
	private String bewertung_text;
	
	//ACHTUNG!!!
	//Diese Array-List dienen nur als zwischenspeicher und werden
	//immer wieder überschrieben, ergo: kein konstanter Inhalt!!!
	private static ArrayList<Tab_bewertung> bewertungPerson;
	private static ArrayList<Tab_bewertung> bewertungRestaurant;

	/**
	 * Die abgegebene Bewertung wird in die Datenbank gespechert
	 */
	public static boolean saveBewertung(String ubenutzer, int urestaurantid, double usterne, String ubewertungstext){
		//Return als bool, ob dieser Benutzer schon
		//eine Bewertung hat (false = Fehler)
		String stmt = "SELECT pid, rid FROM bewertung WHERE pid='"+ubenutzer+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		if(rs!=null){return false;}
		
		//Bewertung in die Datenbank einfügen
		stmt = "INSERT INTO bewertung (pid, rid, sterne, beschreibung) ";
		stmt += "VALUES('"+ubenutzer+"','"+urestaurantid+"',"+usterne+",'"+ubewertungstext+"')";
		return con.writeData(stmt);
	}
	
	public static boolean deleteBewertung(String upid, String urid){
		//Return als bool, ob dieses Restaurant schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT pid, rid FROM bewertung WHERE rid='"+urid+"' AND pid='"+upid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		//Wenn das Restaurant nicht vorhanden ist, dann abbruch der Methode
		if(rs==null){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM restaurant WHERE rid='"+urid+"' AND pid="+upid+"'";
		return con.writeData(stmt);
	}
	
	/**
	 * Methode die alle Bewertungen für ein Restaurant aufruft
	 * @param urid Restaurant ID, sprich den Namen des Retaurants
	 * @return ArrayList mit allen Bewertungen dieses Retaurants ACHTUNG: Bei Fehler wird null returnt!!
	 */
	public static ArrayList<Tab_bewertung> getBewertungRestaurant(String urid){
		bewertungRestaurant.clear();
		String stmt = "SELECT * FROM bewertung WHERE rid='"+urid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		try {
			while(rs.next()){
				String tpid = rs.getString("pid");
				String trid = rs.getString("rid");
				int tstern = rs.getInt("sterne");
				String tbeschr = rs.getString("Beschreibung");
				bewertungRestaurant.add(new Tab_bewertung(tpid, trid, tstern, tbeschr));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return bewertungRestaurant;
	}
	
	/**
	 * Methode, die alle Bewertungen einer Person als ArrayList zurückgibt
	 * @param upid PersonenID, sprich der Benutzername
	 * @return ArrayList mit allen Bewertungen einer Person vom Typ Tab_bewertung ACHTUNG: Bei Fehler wird null returnt!!
	 */
	public static ArrayList<Tab_bewertung> getBewertungPerson(String upid){
		bewertungPerson.clear();
		String stmt = "SELECT * FROM Bewertung WHERE pid='"+upid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Auslesen des Resultsetes, sollte etwas schief gehen
		//wurd null returnt
		try {
			while(rs.next()){
				String tpid = rs.getString("pid");
				String trid = rs.getString("rid");
				int tstern = rs.getInt("sterne");
				String tbeschr = rs.getString("Beschreibung");
				bewertungPerson.add(new Tab_bewertung(tpid, trid, tstern, tbeschr));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return bewertungPerson;
	}
}
