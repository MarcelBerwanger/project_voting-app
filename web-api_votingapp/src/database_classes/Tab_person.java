package database_classes;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sql.rowset.WebRowSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.Service;

import api_classes.Connection;
import api_classes.ConnectionInt;


@XmlType(factoryMethod="newInstance")
@XmlRootElement
public class Tab_person {
	public Tab_person(String upid, String umail, String uklasse, String upass, String urole){
		//Datenbankfelder
		pid = upid;
		email = umail;
		klasse = uklasse;
		passwort = upass;
		rolle = urole;
	}
	//Implementierung nur für JAX-WS, nicht in anderen Codes verwenden-> liefert
	//keine gültigen Objekte
	private Tab_person(){}
	public static Tab_person newInstance(){
		return new Tab_person();
	}
	//Static Array, welches zur temporären Speicherung verwendet wird
	public static ArrayList<Tab_person> allPersonen = new ArrayList<Tab_person>();
	
	//Datenbank Felder
	@XmlElement
	private String pid;
	@XmlElement
	private String email;
	@XmlElement
	private String klasse;
	@XmlElement
	private String passwort;
	@XmlElement
	private String rolle;
	
	//Felder der Connection
	private static URL url = establish_con();
	private static Service service;
	private static ConnectionInt con;
	
	private static URL establish_con(){
		//Initialisierung der Connection
		try{
			url = new URL(Connection_var.url);
			service = Service.create(url ,Connection_var.qname);
			con = service.getPort(ConnectionInt.class);
		}catch(Exception e){e.printStackTrace();}
		return url;
	}
	
	
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
		return con.savePerson(uname, uemail, uklasse, upasswort, urolle);
	}
	
	/**
	 * Wenn eine Peron/ ein Benutzer sein Profil löschen will, dann wird es über diese Methode erfolgen
	 * 
	 * @return Boolean, der eine Rückmeldung gibt, ob es funktioniert hat (true = success)
	 */
	public static boolean deletePerson(String upid){
		return con.deletePerson(upid);
	}
	
	/**
	 * Diese Methode liefert eine ArrayList mit allen verfügbaren Personen in der Datenbank 
	 * @return ArrayList vom Typ Tab_person mit allen Personen
	 */
	public static ArrayList<Tab_person> getAllPersons(){
		ArrayList<Tab_person> as = new ArrayList<Tab_person>(Arrays.asList(con.getAllPersons()));
		return as;
	}
	
	/**
	 * Man schaut ob der Benutzer und das Passwort in der
	 * Tabelle vorhanden sind, sollte er vorhanden sein
	 * gibt es retrun true, wenn nicht dann return false
	 */
	public static boolean check_logIn(String ubenutzer, String upasswort){
		return con.check_logIn(ubenutzer, upasswort);
	}
}
