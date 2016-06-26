package api_classes;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;

import database_classes.Tab_bewertung;
import database_classes.Tab_freund;
import database_classes.Tab_kategorie;
import database_classes.Tab_person;
import database_classes.Tab_restaurant;
import database_classes.Tab_voting;

@WebService(name="ConnectionService", endpointInterface="api_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface ConnectionInt {
	/**
	 * Methoden der Klasse: Tab_person
	 */
	@WebMethod boolean savePerson(String uname, String uemail, String uklasse, String upasswort, String urolle);
	@WebMethod boolean deletePerson(String upid);
	@WebMethod Tab_person[] getAllPersons();
	@WebMethod boolean check_logIn(String ubenutzer, String upasswort);
	
	/**
	 * Methoden der Klasse: Tab_restaurant
	 */
	@WebMethod boolean save_restaurant(String urid, String uadresse, String ukategorie);
	@WebMethod boolean deleteRestaurant(String uname, String ukategorie);
	@WebMethod Tab_restaurant[] getAllRestaurants();
	
	/**
	 * Methoden der Klasse: Tab_bewertung
	 */
	@WebMethod boolean saveBewertung(String upid, String urid, double usterne, String utext);
	@WebMethod boolean deleteBewertung(String upid, String urid);
	@WebMethod Tab_bewertung[] getBewertungRestaurant(String urid);
	@WebMethod Tab_bewertung[] getBewertungPerson(String upid);
	
	/**
	 * Methoden der Klasse: Tab_freund
	 */
	@WebMethod boolean saveFreund(String upid, String ufid);
	@WebMethod boolean deleteFreund(String upid, String ufid);
	@WebMethod Tab_freund[] getAllFreunde(String upid);
	
	/**
	 * Methoden der Klasse: Tab_kategorie
	 */
	@WebMethod boolean saveKategorie(String ukate);
	@WebMethod boolean deleteKategorie(String ukate);
	@WebMethod Tab_kategorie[] getAllKategories();
	
	/**
	 * Methoden der Klasse: Tab_voting
	 */
	@WebMethod boolean saveVote(String upid, String urid);
	@WebMethod boolean deleteAllVotes();
	@WebMethod Tab_voting[] getVotesRestaurant(String urid);
	@WebMethod boolean checkVote(String upid);
	
	/**
	 * Generelle Methoden
	 */
	void requestData(String ustmt);
	boolean writeData(String ustmt);
	void runService();
	void stopService();
	void databaseConnection();
}