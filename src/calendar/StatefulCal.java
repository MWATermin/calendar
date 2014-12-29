package calendar;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateful
@SecurityDomain("other")
@PermitAll
public class StatefulCal implements StatefulCalRemoteInterface{	
	
	@Resource
	SessionContext statefulContext;
	
	
	public String username = null;
	
	@EJB
	private CalLokalInterface cal;
	@EJB
	private UserFunctionLocalInterface us;
	
	
	public StatefulCal(){
		System.out.println("Konstruktor StatefulCal");
	}
	
	private String getUsername() {
		return statefulContext.getCallerPrincipal().getName();
	}
	
	
	@Override
	public Integer createDate( Date date) {
		Integer userID = us.getUserID( getUsername());
		System.out.println("USERID Ausgabe: " + userID);
		System.out.println( statefulContext.getCallerPrincipal().getName());
		return cal.createDate(date, userID);
	}

	@Override
	public Integer getDateID(Date date) {
		return cal.getDateID(date);
	}

	@Override
	public Boolean deleteDate(Integer dateID) {
		Integer userID = us.getUserID( getUsername());
		return cal.deleteDate(dateID, userID);
	}

	@Override
	public ArrayList<Date> getDates(Date date, Integer timeRange) {
		Integer userID = us.getUserID( getUsername());
		return cal.getDates(date, timeRange, userID);
	}
	
	@Override
	public ArrayList<Date> getAllDatesInDB() {
		Integer userID = us.getUserID( getUsername());
		return cal.getAllDatesInDB(userID);
	}
	
	@Override
	public void updateDate(Integer dateID, Date newDate) {
		cal.updateDate(dateID, newDate);
	}

	@Override
	public ArrayList<Date> searchNextFreeTermin( ArrayList<Integer> member,
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
