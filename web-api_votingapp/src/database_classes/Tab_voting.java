package database_classes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.Service;

import api_classes.ConnectionInt;


public class Tab_voting {
	
	public Tab_voting(String urid, String upid){
		rid = urid;
		pid = upid;
	}
	//Implementierung nur f�r JAX-WS, nicht in anderen Codes verwenden-> liefert
	//keine g�ltigen Objekte
	private Tab_voting(){}
	public static Tab_voting newInstance(){
		return new Tab_voting();
	}
	
	@XmlElement
	private String rid;
	@XmlElement
	private String pid;
	
	//ACHTUNG:
	//nicht verwenden, dies ist eine tempor�re ArrayList zur zwischenspeicherung
	//die Daten sind nicht konsistent!
	public static ArrayList<Tab_voting> allVotes = new ArrayList<Tab_voting>();
	
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
	 * Das abgegebene Vote in der Datenbank speichern
	 * @param upid Benutzer, der das Vote abgegeben hat
	 * @param urid Restaurnat, f�r das gevotet wurde.
	 */
	public static boolean saveVote(String upid, String urid){
		return con.saveVote(upid, urid);
	}
	
	/**
	 * Methode, die alle Votes aus der Datenbank l�scht
	 * @return Boolean, ob l�schen erfolgreich oder nicht; true = success
	 */
	public static boolean deleteAllVotes(){
		return con.deleteAllVotes();
	}
	
	/**
	 * Diese Methode liefert die Votes f�r ein Restaurant zur�ck, welche in der Datenbank liegen
	 * @param urid Restaurant-ID
	 * @return ArraList mit allen Votes eines Restaurants; return null, wenn Tabelle leer!
	 */
	public static ArrayList<Tab_voting> getVotesRestaurant(String urid){
		return new ArrayList<Tab_voting>(Arrays.asList(con.getVotesRestaurant(urid)));
	}
	
	/**
	 * Diese Methode �berpr�ft, ob eine Person schon gevotet hat
	 * @param upid Personen ID
	 * @return Bool-Wert, der aussagt ob die Person schon gevotet hat; true = hat gevotet & false hat nicht gevotet
	 */
	public static boolean checkVote(String upid){
		return con.checkVote(upid);
	}
}
