package database_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import api_classes.Connection;

public class Tab_restaurant {
	
	private Tab_restaurant(String urid, String ukate, String uadd){
		rid = urid;
		kategorie = ukate;
		adresse = uadd;
		allRestaurants.add(this);
	}
	
	private String rid;
	private String kategorie;
	private String adresse;
	//ArrayList in der alle Restaurants stehen
	private static ArrayList<Tab_restaurant> allRestaurants;
	
	/**
	 * Diese Methode liefert die aktuellen Votes für das aktuelle Restaurant
	 * @return ArrayList mit allen Votes
	 */
	public ArrayList<Tab_voting> getAllVotes(){
		return Tab_voting.getVotesRestaurant(rid);
	}
	/**
	 * Diese Methode liefert alle Bewertungen für das aktuelle Restaurant
	 * @return ArrayList mit allen Bewerutngen
	 */
	public ArrayList<Tab_bewertung> getAllBewertungen(){
		return Tab_bewertung.getBewertungRestaurant(rid);
	}
	
	/**
	 * Das angelegte Restaurant wird in der Datenbank gespeichert
	 * 
	 * @param uname Name des Restaurants, was zugleich der Primary-Key ist.
	 * @param uadresse Adresse des Restaurants (alle Formate sind Zulässig)
	 * @param ukategorie Kategorie des Restaurants (auswählbar aus Spinner Liste)
	 * 
	 * @return Die Methode returnt false wenn das Restaurant schon existiert.
	 */
	public static boolean save_restaurant(String urid, String uadresse, String ukategorie){
		//Return als bool, ob dieses Restaurant schon
		//existiert (false = existiert)
		String stmt = "SELECT * FROM restaurant WHERE rid='"+urid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		//Wenn das Restaurant vorhanden ist, dann abbruch der Methode
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false = vorhanden
		try{
			rs.next();
			rs.getString("rid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Restaurant in die Datenbank einfügen
		stmt = "INSERT INTO restaurant (rid, kategorie, adresse) ";
		stmt += "VALUES('"+urid+"','"+ukategorie+"','"+uadresse+"')";
		return con.writeData(stmt);
	}
	
	/**
	 * Löschen eines bestimmten Restaurants, anhand der beiden Primary-Key Teile
	 * 
	 * @param uname Name des Restaurants
	 * @param ukategorie Kategorie des Retaurants
	 * @return Boolean Wert, ob die Operation erfolgreich war (true=success)
	 */
	public static boolean deleteRestaurant(String uname, String ukategorie){
		//Return als bool, ob dieses Restaurant schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM restaurant WHERE rid='"+uname+"' AND kategorie='"+ukategorie+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Wenn das Restaurant nicht vorhanden ist, dann abbruch der Methode
		try{
			rs.next();
			rs.getString("rid");
		}catch(Exception e){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM restaurant WHERE rid='"+uname+"' AND kategorie="+ukategorie+"'";
		return con.writeData(stmt);
	}
	/**
	 * Alle Restaurants aus der Datenbank holen und in die ArrayList schreiben
	 * Die ArrayList heißt allRestaurants und ist static
	 */
	public static ArrayList<Tab_restaurant> getAllRestaurants(){
		//Zuerst ArrayList clearen, damit keine doppelten vorkommen
		allRestaurants.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM restaurant";
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Alle abgefragten Restaurants in die ArrayList eintragen
		try{
			while(rs.next()){
				String rid =rs.getString("RID");
				String kat = rs.getString("Kategorie");
				String add = rs.getString("Adresse");
				allRestaurants.add(new Tab_restaurant(rid, kat, add));
			}
		}catch(SQLException e){return null;}
		return allRestaurants;
	}
}
