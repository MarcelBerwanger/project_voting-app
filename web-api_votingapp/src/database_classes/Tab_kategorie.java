package database_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import api_classes.Connection;

public class Tab_kategorie {
	
	public Tab_kategorie(String ukate){
		kategorie=ukate;
	}
	
	private String kategorie;
	private static ArrayList<Tab_kategorie> allKategories;
	
	/**
	 * Methode, die eine neue Kategorie speichert
	 * @param ukate Name der neuen Kategorie
	 * @return Bool-Wert; true = erfolgreich gespeichert & false = Fehler bzw. schon vorhanden
	 */
	public static boolean saveKategorie(String ukate){
		//Return als bool, ob diese Kategorie schon
		//existiert (false = Fehler)
		String stmt = "SELECT * FROM kategorie WHERE kategorie='"+ukate+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Schauen, ob der Benutzer schon ein Vote gegeben hat, wenn ja beendet die Methode mit return flase
		try{
			rs.next();
			rs.getString("kategorie");
		}catch(Exception e){return false;}
		
		//Voting in die Datenbank einf�gen
		stmt = "INSERT INTO kategorie (kategorie) ";
		stmt += "VALUES('"+ukate+"')";
		return con.writeData(stmt);
	}
	
	/**
	 * Methode die eine Kategorie l�scht
	 * @param ukate Kategorie die gel�scht werden soll
	 * @return Bool-Wert; true bedeutet erfolgreich gel�scht; flase bedeutet entweder nicht vorhanden oder nicht gel�scht
	 */
	public static boolean deleteKategorie(String ukate){
		//Return als bool, ob es diese Kategorie schon
		//existiert, damit es gel�scht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM kategorie WHERE kategorie='"+ukate+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		//Wenn das Restaurant nicht vorhanden ist, dann abbruch der Methode
		try{
			rs.next();
			rs.getString("kategorie");
		}catch(Exception e){return false;}
		
		//Eigentliches l�schen, nach der obigen "�berpr�fung".Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM kategorie WHERE pid='"+ukate+"'";
		return con.writeData(stmt);
	}
	
	/**
	 * Fragt alle Kategorien ab und liefert sie als ArrayList zur�ck
	 * @return ArrayList mit allen Kategorien
	 */
	public static ArrayList<Tab_kategorie> getAllKategories(){
		//Zuerst ArrayList clearen, damit keine doppelten vorkommen
		allKategories.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM kategorie";
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Alle abgefragten Restaurants in die ArrayList eintragen
		try{
			while(rs.next()){
				String kategorie =rs.getString("kategorie");
				allKategories.add(new Tab_kategorie(kategorie));
			}
		}catch(SQLException e){return null;}
		return allKategories;
	}
}
