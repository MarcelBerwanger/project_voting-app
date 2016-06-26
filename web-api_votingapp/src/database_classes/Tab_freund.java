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
public class Tab_freund {
	
	public Tab_freund(String upid, String ufid){
		fid = ufid;
		pid = upid;
	}
	//Implementierung nur f�r JAX-WS, nicht in anderen Codes verwenden-> liefert
	//keine g�ltigen Objekte
	private Tab_freund(){}
	public static Tab_freund newInstance(){
		return new Tab_freund();
	}
	
	@XmlElement
	private String fid;
	@XmlElement
	private String pid;
	//ACHTUNG:
	//Diese ArrayList nicht verwenden, sie beinhaltet keine Konsistenten Daten;
	//dient nur der Tempor�ren speicherung
	public static ArrayList<Tab_freund> allFriends = new ArrayList<Tab_freund>();
	
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
	 * Diese Methode speichert eine Freundschaft einer Person
	 * @param upid Person die die Freundschaft 'geh�rt'
	 * @param ufid Freund der Person (andere PID)
	 * @return bool wert ob die speicherung erfolgreich verlaufen ist; true = erfolgreich
	 */
	public static boolean saveFreund(String upid, String ufid){
		return con.saveFreund(upid, ufid);
	}
	
	/**
	 * Methode, die eine Freundschaft wieder l�scht
	 * @param upid Person die die Freundschaft 'geh�rt'
	 * @param ufid Freund der Person (andere PID)
	 * @return Bool-Wert; true= erfolgreich gel�scht & false = Fehler/ nicht vorhanden
	 */
	public static boolean deleteFreund(String upid, String ufid){
		return con.deleteFreund(upid, ufid);
	}
	
	/**
	 * Diese Methodeliefert f�r eine Person alle Freunde
	 * @param upid Person, deren Freundschaften geholt werden
	 * @return Gibt die ArrayList von Freunden einer Person zur�ck; liefert null, wenn keine Freunde vorhanden
	 */
	public static ArrayList<Tab_freund> getAllFreunde(String upid){		
		return new ArrayList<Tab_freund>(Arrays.asList(con.getAllFreunde(upid)));
	}
}
