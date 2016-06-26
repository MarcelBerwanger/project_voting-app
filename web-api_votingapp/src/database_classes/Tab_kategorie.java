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
public class Tab_kategorie {
	
	public Tab_kategorie(String ukate){
		kategorie=ukate;
	}
	//Implementierung nur für JAX-WS, nicht in anderen Codes verwenden-> liefert
	//keine gültigen Objekte
	private Tab_kategorie(){}
	public static Tab_kategorie newInstance(){
		return new Tab_kategorie();
	}
	
	//Static Array, welches zur temporären Speicherung verwendet wird
	public static ArrayList<Tab_kategorie> allKategories = new ArrayList<Tab_kategorie>();
	
	@XmlElement
	private String kategorie;
	
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
	 * Methode, die eine neue Kategorie speichert
	 * @param ukate Name der neuen Kategorie
	 * @return Bool-Wert; true = erfolgreich gespeichert & false = Fehler bzw. schon vorhanden
	 */
	public static boolean saveKategorie(String ukate){
		return con.saveKategorie(ukate);
	}
	
	/**
	 * Methode die eine Kategorie löscht
	 * @param ukate Kategorie die gelöscht werden soll
	 * @return Bool-Wert; true bedeutet erfolgreich gelöscht; flase bedeutet entweder nicht vorhanden oder nicht gelöscht
	 */
	public static boolean deleteKategorie(String ukate){
		return con.deleteKategorie(ukate);
	}
	
	/**
	 * Fragt alle Kategorien ab und liefert sie als ArrayList zurück
	 * @return ArrayList mit allen Kategorien
	 */
	public static ArrayList<Tab_kategorie> getAllKategories(){
		return new ArrayList<Tab_kategorie>(Arrays.asList(con.getAllKategories()));
	}
}
