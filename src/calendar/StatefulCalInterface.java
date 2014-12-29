package calendar;

import java.util.ArrayList;

public interface StatefulCalInterface {
	
	public Integer createDate( Date date);
	
	public Integer getDateID( Date date);
	
	public Boolean deleteDate( Integer dateID);
	
	public ArrayList<Date> getAllDatesInDB();
	
	public ArrayList<Date> getDates( Date date, Integer timeRange);
	
	public void updateDate( Integer dateID, Date newDate);
	
	public ArrayList<Date> searchNextFreeTermin( ArrayList<Integer> member, java.util.Calendar fromDate, java.util.Calendar toDate, Integer dateLength);
	
	public void bye();
}
