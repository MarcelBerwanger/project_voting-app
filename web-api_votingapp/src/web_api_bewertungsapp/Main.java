package web_api_bewertungsapp;

import java.net.URL;

import data_classes.Connection;
import data_classes.ConnectionInt;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceRef;

public class Main {
	
	@WebServiceRef(wsdlLocation="http://192.168.137.1:8080/services?wsdl")
	static Connection service;
	private static URL url;
	private static QName qname;
	
	public static void main(String[] args){
		//Zuerst den Server Starten
		Connection con = new Connection();
		con.runService();
		
		//Connection testen
		//Für eine Verbindung braucht es eine URL
		try{
			url = new URL("http://192.168.137.1:8080/services?wsdl");
		}catch(Exception e){System.out.println("Fehler mit der URL");}
		
		//Der QName wird zum erstellen eines Services benötigt
		qname = new QName("http://data_classes/","ConnectionService");
		try{
			//connTest();
		}catch(Exception e){
			e.printStackTrace();
			con.stopService();
		}
	}
	
	/**
	 * Client Join auf die API und Aufruf einer Methode
	 */
	public static void connTest(){
		Service service = Service.create(url, qname);
		ConnectionInt con = service.getPort(ConnectionInt.class);
		System.out.println(con.requestData());
	}
}