package datenbank;
import java.util.ArrayList;
/**
 * Dient zur Abfrage alle Restaurants, da diese beim Voting und bei der Bewertung
 * aufgelistet werden müssen. 
 * @author Marcel
 *
 */
public class tab_Restaurants {
	
	public tab_Restaurants(String uname, String ukate, int uid){
		name = uname;
		kategorie = ukate;
		restaurantId = uid;
		allRestaurants.add(this);
	}
	
	private String name;
	private String kategorie;
	private int restaurantId;
	private static ArrayList<tab_Restaurants> allRestaurants;
	
	/**
	 * Alle Restaurants aus der Datenbanl holen und in die ArrayList schreiben
	 */
	public static void getAll(){
		//Statement loschchicken
		//Ergebnis Interpretieren bzw in allRestaurants einbringen
	}
}
