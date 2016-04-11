//Anleitung/ Quelle: http://openbook.rheinwerk-verlag.de/java7/1507_13_003.html#top
package data_classes;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

/*
 * Jede Klasse die einen WebService darstellt muss die folgenden 2
 * Parameter besitzen.
 * @WebService legt den namen des Webservices fest
 * @SOAPBinding legt die Art der Nachrichten fest, es gibt (Dokument oder RPC)
 * 
 * weiters muss jede Methode mit @WebMethod gekennzeichnet sein, in @WebMethod
 * legt das Feld operationName fest wie der Name der Methode in der API lautet.
 * Wenn die Methode einen Returntyp aufweist muss man sie neben @WebMethod mit
 * @WebResult kennzeichnen, weider legt das Feld name (in Klammern) den Namen des
 * Ergenisses in der Api fest.
 */
@WebService(name="ConnectionService", endpointInterface="data_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public class Connection implements ConnectionInt{
	
	/*Konstruktoren der Klasse Connection, wird keine Url
	 * übergeben, wird sie auf "http://localhost:8080/services"
	 * festgelegt.
	 */
	public Connection(){this("http://192.168.137.1:8080/services");}
	public Connection(String gweburl){
		weburl=gweburl;
	}
	
	private String weburl;
	private Endpoint endpoint;
	
	//Der WebService muss veröffentlich werden, dies Geschieht in dieser Methode
	//die URL des Webservices steht in dem feld weburl
	//Hinter der URL muss noch "?wsdl" stehen
	public void runService(){
		endpoint = Endpoint.publish(weburl, this);
	}
	
	//Stopt den Service in dem Try/Catch Block
	public void stopService(){
		endpoint.stop();
	}
	//Der returnwert muss noch festgelegt werden (XML als string)
	//& die Parameter fehlen noch (Das was der Client haben will muss übergeben werden)
	//Nicht Vergessen die Parameter der Methode mit @WebParam zu versehen & name setzen!!
	//(operationName="requestData")
	@Override
	public String requestData()
	{
		//Aufruf der DB Klasse
		return "Hello I'm the webservice 4AHWIWebServices";
	}
	
	//Es Fehlen noch die Parameter (XML datei und was er überschreiben will)
	//Nicht Vergessen die Parameter der Methode mit @WebParam zu versehen & name setzen!!
	@Override
	public void writeData()
	{
		
	}
}
