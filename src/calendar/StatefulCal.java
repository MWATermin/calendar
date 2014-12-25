package calendar;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.PermitAll;
import javax.ejb.*;

@Stateful
public class StatefulCal implements StatefulCalRemoteInterface{	
	
	public String username = "John";
	
	@EJB
	private CalLokalInterface cal;
	
	public StatefulCal(){
	}
	
	@Override
	public Integer createDate( Date date) {
		return cal.createDate(date, username);
	}

	@Override
	public Integer getDateID(Date date) {
		return cal.getDateID(date);
	}

	@Override
	public Boolean deleteDate(Integer dateID) {
		return cal.deleteDate(dateID, username);
	}

	@Override
	public ArrayList<Date> getDates(Date date, Integer timeRange) {
		return cal.getDates(date, timeRange, username);
	}
	
	@Override
	public ArrayList<Date> getAllDatesInDB() {
		return cal.getAllDatesInDB(username);
	}
	
	@Override
	public void updateDate(Integer dateID, Date newDate) {
		cal.updateDate(dateID, newDate);
	}

	@Override
	public ArrayList<Date> searchNextFreeTermin( ArrayList<String> member,
			Calendar fromDate, Calendar toDate, Integer dateLength) {
		return cal.searchNextFreeTermin(member, fromDate, toDate, dateLength);
	}
	
	@PostConstruct
	public void initCal(){
		System.out.println("GOOO!!!!!!!!");
	}
	
	@PreDestroy
	public void destroyCal(){
		System.out.println("KILL!!!!!!!!");
	}

	@Remove
	public void bye(){
		System.out.println("Session killed by Client!!!!!!!!");
	}
}
