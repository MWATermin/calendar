package calendar;

import java.util.*;

import javax.ws.rs.QueryParam;

/*
//return = terminID
int createTermin(Termin termin)  statefull
kreiert  den Termin samt User durch aufruf der Stateless (siehe unten)
int createTermin(Termin termin, User user) stateless
kreiert den Termin

//return = terminID
int getTerminID(Termin termin)

Boolean deleteTermin(Int terminID)   statefull 
Boolean deleteTermin(Int terminID, User user)   stateless

//return = Liste aller Termine
//timeRange in Tage - 0 = heute, 1 = heute und morgen ...
List getTermine(Termin termin, Int timeRange) statefull
List getTermine(Termin termin, Int timeRange, User user) stateless

//return = aktuellen Wert f�r termin
Termin updateTermin(int terminID, Termin newTermin)

//return = Liste von Termin
List searchNextFreeTermin(List member, Date fromDate, Date toDate, int terminLength) 
*/
public interface CalInterface {
	
//	public String DatesToHTML(String usr);
	
	public Integer createDate( Date date, Integer userID);
	
	public Integer getDateID( Date date);
	
	public Boolean deleteDate(Integer dateID, Integer userID);
	
	public ArrayList<Date> getAllDatesInDB(Integer userID); // DSC: Ausgabe aller Date in der DB
	
	public ArrayList<Date> getDates( Date date, Integer timeRange, Integer userID);
	
	public void updateDate( Integer dateID, Date newDate);
	
	public ArrayList<Date> searchNextFreeTermin( ArrayList<Integer> member, java.util.Calendar fromDate, java.util.Calendar toDate, Integer dateLength);
}
