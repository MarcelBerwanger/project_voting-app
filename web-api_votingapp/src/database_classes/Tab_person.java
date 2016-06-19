package database_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import api_classes.Connection;

public class Tab_person {
	
	public Tab_person(String upid, String umail, String uklasse, String upass, String urole){
		pid = upid;
		email = umail;
		klasse = uklasse;
		passwort = upass;
		rolle = urole;
	}
	
	private String pid;
	private String email;
	private String klasse;
	private String passwort;
	private String rolle;
	
	public static ArrayList<Tab_person> allPersonen;
	
	/**
	 * Diese Methode liefert alle Freunde einer Person
	 * @return ArrayList mit allen Freunden; liefert null wenn keine Freunde vorhanden
	 */
	public ArrayList<Tab_freund>getAllFreunde(){
		return Tab_freund.getAllFreunde(pid);
	}
	
	/**
	 * Diese Methode liefert alle Bewerungen einer Person
	 * @return ArrayList mit allen Bewertungen einer Person
	 */
	public ArrayList<Tab_bewertung>getAllBewertungen(){
		return Tab_bewertung.getBewertungPerson(pid);
	}
	
	/**
	 * Diese Methode dient dazu einen neu erstellten Benutzer in
	 * der Datenbank zu speichern
	 * 
	 * Arbeitsweise: Statement -> fertiger String wird an die API geschickt
	 */
	public static boolean savePerson(String uname, String uemail, String uklasse, String upasswort, String urolle){
		//Return als bool, ob dieser Benutzer schon
		//existiert (false = existiert)
		String stmt = "SELECT pid FROM person WHERE pid='"+uname+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false wenn leer, sonst true
		try{
			rs.next();
			rs.getString("pid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Restaurant in die Datenbank einfügen
		stmt = "INSERT INTO person (pid, email, klasse, passwort, rolle) ";
		stmt += "VALUES('"+uname+"','"+uemail+"','"+uklasse+"','"+upasswort+"','"+urolle+"')";
		return con.writeData(stmt);
	}
	
	/**
	 * Wenn eine Peron/ ein Benutzer sein Profil löschen will, dann wird es über diese Methode erfolgen
	 * 
	 * @return Boolean, der eine Rückmeldung gibt, ob es funktioniert hat (true = success)
	 */
	public static boolean deletePerson(String upid){
		//Return als bool, ob dieses Restaurant schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM person WHERE pid='"+upid+"'";
		
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		//Wenn die Person nicht vorhanden ist, dann abbruch der Methode
		try{
			rs.next();
			rs.getString("pid");
		}catch(Exception e){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM person WHERE pid='"+upid+"'";
		return con.writeData(stmt);
	}
	
	public static ArrayList<Tab_person> getAllPersons(){
		//Zuerst ArrayList clearen, damit keine doppelten vorkommen
		allPersonen.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM person";
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Alle abgefragten Restaurants in die ArrayList eintragen
		try{
			while(rs.next()){
				String pid =rs.getString("PID");
				String mail = rs.getString("Email");
				String klass = rs.getString("Klasse");
				String pass = rs.getString("Passwort");
				String role = rs.getString("Rolle");
				allPersonen.add(new Tab_person(pid, mail, klass, pass, role));
			}
		}catch(SQLException e){return null;}
		return allPersonen;
	}
	
	/**
	 * Man schaut ob der Benutzer und das Passwort in der
	 * Tabelle vorhanden sind, sollte er vorhanden sein
	 * gibt es retrun true, wenn nicht dann return false
	 */
	public static boolean check_logIn(String ubenutzer, String upasswort){
		String stmt = "SELECT pid FROM Person WHERE pid='"+ubenutzer+"'AND passwort="+upasswort;
		//Ersetzten durch API-Aufruf!!!
		Connection con = new Connection();
		ResultSet rs = con.requestData(stmt);
		
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false wenn leer, sonst true
		try{
			rs.next();
			rs.getString("pid");
		}catch(Exception e){return false;}
		return true;
	}
}
