package database_classes;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.Service;

import api_classes.Connection;
import api_classes.ConnectionInt;

@XmlType(factoryMethod="newInstance")
@XmlRootElement
public class Tab_restaurant {
	public Tab_restaurant(String urid, String ukate, String uadd){
		//Datenbankfelder
		rid = urid;
		kategorie = ukate;
		adresse = uadd;
		allRestaurants.add(this);
	}
	//Implementierung nur für JAX-WS, nicht in anderen Codes verwenden-> liefert
	//keine gültigen Objekte
	private Tab_restaurant(){}
	public static Tab_restaurant newInstance(){
		return new Tab_restaurant();
	}
	
	//Datenbank Felder
	@XmlElement
	private String rid;
	@XmlElement
	private String kategorie;
	@XmlElement
	private String adresse;
	
	//ArrayList in der alle Restaurants stehen
	public static ArrayList<Tab_restaurant> allRestaurants = new ArrayList<Tab_restaurant>();
	
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
		return con.save_restaurant(urid, uadresse, ukategorie);
	}
	
	/**
	 * Löschen eines bestimmten Restaurants, anhand der beiden Primary-Key Teile
	 * 
	 * @param uname Name des Restaurants
	 * @param ukategorie Kategorie des Retaurants
	 * @return Boolean Wert, ob die Operation erfolgreich war (true=success)
	 */
	public static boolean deleteRestaurant(String uname, String ukategorie){
		return con.deleteRestaurant(uname, ukategorie);
	}
	
	/**
	 * Alle Restaurants aus der Datenbank holen und in die ArrayList schreiben
	 * Die ArrayList heißt allRestaurants und ist static
	 */
	public static ArrayList<Tab_restaurant> getAllRestaurants(){
		return new ArrayList<Tab_restaurant>(Arrays.asList(con.getAllRestaurants()));
	}
}
