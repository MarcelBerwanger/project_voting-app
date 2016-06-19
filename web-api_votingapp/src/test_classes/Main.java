package test_classes;

import java.net.URL;

import database_classes.Tab_person;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceRef;

import api_classes.Connection;
import api_classes.ConnectionInt;

public class Main {
	
	@WebServiceRef(wsdlLocation="http://192.168.137.1:8080/services?wsdl")
	static Connection service;
	private static URL url;
	private static QName qname;
	
	public static void main(String[] args){
		/**
		//Zuerst den Server Starten
		Connection con = new Connection();
		con.runService();
		
		//Connection testen
		//Für eine Verbindung braucht es eine URL
		try{
			url = new URL("http://localhost:8080/services?wsdl");
		}catch(Exception e){System.out.println("Fehler mit der URL");}
		
		//Der QName wird zum erstellen eines Services benötigt
		qname = new QName("http://api_classes/","ConnectionService");
		try{
			connTest();
		}catch(Exception e){
			e.printStackTrace();
			con.stopService();
		}**/
		
		System.out.println("Delete? - " +Tab_person.deletePerson("ben"));
		System.out.println("Gefunden? - "+Tab_person.check_logIn("ben", "1234"));
		System.out.println("Erstellt? - "+Tab_person.savePerson("ben", "ben@ben", "1AHW", "1234", "u"));
		System.out.println("Gefunden? - "+Tab_person.check_logIn("ben", "1234"));
	}
	
	/**
	 * Client joint auf die API und Aufruf einer Methode
	 */
	public static void connTest(){
		Service service = Service.create(url, qname);
		ConnectionInt con = service.getPort(ConnectionInt.class);
	}
}