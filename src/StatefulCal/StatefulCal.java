package StatefulCal;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.SecurityDomain;

import Date.Date;
import Journal.JournalLocalInterface;
import StatelessCal.CalLokalInterface;
import User.UserFunctionLocalInterface;
import calendar.Roles;

@Stateful(name = "StatefulCal", mappedName = "StatefulCal") 
@SecurityDomain( "CalSecurity")
@DeclareRoles( { Roles.ADMIN, Roles.STUDENT, Roles.GUEST})
public class StatefulCal implements StatefulCalRemoteInterface, StatefulCalLocalInterface {	
	
	@Resource
	SessionContext statefulContext;
	
	
	public String username = null;
	
	@EJB
	private CalLokalInterface cal;
	@EJB
	private UserFunctionLocalInterface us;
	@EJB
	private JournalLocalInterface journal;

	
	public StatefulCal(){
		System.out.println("Konstruktor StatefulCal");
	}
	
	private String getUsername() {
		return statefulContext.getCallerPrincipal().getName();
	}
	
	@RolesAllowed(Roles.STUDENT)
	@Override
	public Integer createDate( Date date) {
		Integer userID = us.getUserID( getUsername());
		System.out.println("USERID Ausgabe: " + userID);
		System.out.println( statefulContext.getCallerPrincipal().getName());
		return cal.createDate(date, userID);
	}
	

	@RolesAllowed({Roles.ADMIN, Roles.STUDENT})
	public String hellomessage() {
		System.out.println("hellomessage");
		return "halloweltmessage";
	}
	

	@Override
	public Integer getDateID(Date date) {
		return cal.getDateID(date);
	}

	@RolesAllowed( {Roles.ADMIN, Roles.STUDENT})
	@Override
	public Boolean deleteDate(Integer dateID) {
		Integer userID = us.getUserID( getUsername());
		return cal.deleteDate(dateID, userID);
	}

	@RolesAllowed( {Roles.ADMIN, Roles.STUDENT})
	@Override
	public ArrayList<Date> getDates(Date date, Integer timeRange) {
		Integer userID = us.getUserID( getUsername());
		return cal.getDates(date, timeRange, userID);
	}
	
	@RolesAllowed( {Roles.ADMIN, Roles.STUDENT})
	@Override
	public ArrayList<Date> getAllDatesInDB() {
		Integer userID = us.getUserID( getUsername());
		return cal.getAllDatesInDB(userID);
	}
	
	@RolesAllowed( {Roles.ADMIN, Roles.STUDENT})
	@Override
	public void updateDate(Integer dateID, Date newDate) {
		cal.updateDate(dateID, newDate);
	}

	@RolesAllowed( {Roles.ADMIN, Roles.STUDENT})
	@Override
	public ArrayList<Date> searchNextFreeTermin( ArrayList<Integer> member,
			Calendar fromDate, Calendar toDate, Integer dateLength) {
		return cal.searchNextFreeTermin(member, fromDate, toDate, dateLength);
	}
	
	@PermitAll
	@PostConstruct
	public void initCal(){
		System.out.println("GOOO!!!!!!!!");
		journal.addJournalEntry("Start: StatefulCal.initCal()", "GOOO!!!!!!!!", null);
		
	}
	
	@PermitAll
	@PreDestroy
	public void destroyCal(){
		System.out.println("KILL!!!!!!!!");
		// Kein Journal an dieser Stelle m�glich, wirft IllegalStateException
	}

	@PermitAll
	@Remove
	public void bye(){
		System.out.println("Session killed by Client!!!!!!!!");
		journal.addJournalEntry("Start: StatefulCal.bye()", "Session killed by Client!!!!!!!!", null);
		
	}
}
