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
public class Tab_bewertung {
	
	/**
	 * Konstruktor dieser Klasse; ein Objekt soll nur von den get-Methoden erzeugt werden dürfen;
	 * 
	 * @param uben String; Benutzer-ID, sprich Benutzername
	 * @param uresId String; Restaurant-ID, sprich Name des Restaurants
	 * @param ustern Integer; Die Sterne, die vergeben wurden
	 * @param ubewer String; Die Bewertung/ Rezession als String Text
	 */
	public Tab_bewertung(String uben, String uresId, double ustern, String ubewer){
		benutzer=uben;
		restaurantId = uresId;
		sterne = ustern;
		bewertung_text = ubewer;
	}
	//Implementierung nur für JAX-WS, nicht in anderen Codes verwenden-> liefert
	//keine gültigen Objekte
	private Tab_bewertung(){}
	public static Tab_bewertung newInstance(){
		return new Tab_bewertung();
	}
	
	//Datenbank Felder
	@XmlElement
	private String benutzer;
	@XmlElement
	private String restaurantId;
	@XmlElement
	private double sterne;
	@XmlElement
	private String bewertung_text;
	
	//ACHTUNG!!!
	//Diese Array-List dienen nur als zwischenspeicher und werden
	//immer wieder überschrieben, ergo: kein konstanter Inhalt!!!
	public static ArrayList<Tab_bewertung> bewertungPerson = new ArrayList<Tab_bewertung>();
	public static ArrayList<Tab_bewertung> bewertungRestaurant = new ArrayList<Tab_bewertung>();
	
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
	 * Diese Methode speichert eine Berwertung eines Benutzters, der return-Wert gibt an, ob die
	 * Operation erfolgreich verlaufen ist.
	 * @param upid Der Benutzername als String
	 * @param urid Der Restaurantname als String
	 * @param usterne Die vergebenen Sterne  als int
	 * @param utext Der Bewertungstext als String
	 * @return Boolean, ob Operation erfolgreich verlaufen; true = erfolgreich
	 */
	public static boolean saveBewertung(String upid, String urid, double usterne, String utext){
		return con.saveBewertung(upid, urid, usterne, utext);
	}
	
	public static boolean deleteBewertung(String upid, String urid){
		return con.deleteBewertung(upid, urid);
	}
	
	/**
	 * Methode die alle Bewertungen für ein Restaurant aufruft
	 * @param urid Restaurant ID, sprich den Namen des Retaurants
	 * @return ArrayList mit allen Bewertungen dieses Retaurants ACHTUNG: Bei Fehler wird null returnt!!
	 */
	public static ArrayList<Tab_bewertung> getBewertungRestaurant(String urid){
		return new ArrayList<Tab_bewertung>(Arrays.asList(con.getBewertungRestaurant(urid)));
	}
	
	/**
	 * Methode, die alle Bewertungen einer Person als ArrayList zurückgibt
	 * @param upid PersonenID, sprich der Benutzername
	 * @return ArrayList mit allen Bewertungen einer Person vom Typ Tab_bewertung ACHTUNG: Bei Fehler wird null returnt!!
	 */
	public static ArrayList<Tab_bewertung> getBewertungPerson(String upid){
		return new ArrayList<Tab_bewertung>(Arrays.asList(con.getBewertungPerson(upid)));
	}
}
