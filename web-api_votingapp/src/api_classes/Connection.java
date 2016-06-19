//Anleitung/ Quelle: http://openbook.rheinwerk-verlag.de/java7/1507_13_003.html#top
package api_classes;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
@WebService(name="ConnectionService", endpointInterface="api_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public class Connection implements ConnectionInt{
	
	/*Konstruktoren der Klasse Connection, wird keine Url
	 * �bergeben, wird sie auf "http://localhost:8080/services"
	 * festgelegt.
	 */
	public Connection(){
		this("http://localhost:8080/services");
		databaseConnection();
	}
	public Connection(String gweburl){
		weburl=gweburl;
	}
	
	private String weburl;
	private Endpoint endpoint;
	
	//Datenbank-Verbindungsvariablen
	private String hostname = "localhost";
	private String port = "3306";
	private String dbname = "votingapp"; //Name der Datenbank!
	private java.sql.Connection con; //Wird in databaseConnection initialisiert
	
	public void databaseConnection(){
		String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname ;
		try {
			String user = "root";
			String password = "";
			try 
			{
				Class.forName( "com.mysql.jdbc.Driver" );
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Der WebService muss ver�ffentlich werden, dies Geschieht in dieser Methode
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
	//& die Parameter fehlen noch (Das was der Client haben will muss �bergeben werden)
	//Nicht Vergessen die Parameter der Methode mit @WebParam zu versehen & name setzen!!
	//(operationName="requestData")
	@Override
	public ResultSet requestData(String ustmt){
		ResultSet rs = null;
		try{
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(ustmt);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	//Es Fehlen noch die Parameter (XML datei und was er �berschreiben will)
	//Nicht Vergessen die Parameter der Methode mit @WebParam zu versehen & name setzen!!
	@Override
	public boolean writeData(String ustmt)
	{
		try{
			Statement stmt = con.createStatement();
			stmt.execute(ustmt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
