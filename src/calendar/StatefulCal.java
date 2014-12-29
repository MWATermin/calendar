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
	
	public Integer userID =  1;
	
	@EJB
	private CalLokalInterface cal;

	
	
	public StatefulCal(){
	}
	
	@Override
	public Integer createDate( Date date) {
		
		System.out.println( statefulContext.getCallerPrincipal().getName());
		return cal.createDate(date, userID);
	}

	@Override
	public Integer getDateID(Date date) {
		return cal.getDateID(date);
	}

	@Override
	public Boolean deleteDate(Integer dateID) {
		return cal.deleteDate(dateID, userID);
	}

	@Override
	public ArrayList<Date> getDates(Date date, Integer timeRange) {
		return cal.getDates(date, timeRange, userID);
	}
	
	@Override
	public ArrayList<Date> getAllDatesInDB() {
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
