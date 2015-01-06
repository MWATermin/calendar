package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless(name = "Cal", mappedName = "Cal")
@SecurityDomain( "CalSecurity")
@DeclareRoles( { Roles.ADMIN, Roles.STUDENT, Roles.GUEST})
public class Cal implements CalRemoteInterface, CalLokalInterface {
	
	@Resource
	SessionContext CalContext;
	
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	
	@EJB
	private JournalLocalInterface journal;
	@EJB
	private UserFunctionLocalInterface us;
	
    public Cal() {
       
    }
    
	@PermitAll
	@Override
	public Integer createDate( Date date, Integer userID) {
		Date d = date;
		System.out.println("excecuted: createDate()");
		d.setAuthorID(userID);
		System.out.println("pre: persist()"+ em);
		em.persist(d);
		System.out.println("post: persist()");
		
		journal.addJournalEntry("Start: Cal.createDate( Date date, String username)", "information", us.getUserID( CalContext.getCallerPrincipal().getName()));
		
		return d.getId();
	}

	@PermitAll
	@Override
	public Integer getDateID(Date date) {
		ArrayList<Date> allDates = getAllDatesInDB( null);
		int index = allDates.indexOf(date);
		System.out.println("excecuted: getDateID()");
		
		journal.addJournalEntry("Start: Cal.getDateID(Date date)", "information", us.getUserID( CalContext.getCallerPrincipal().getName()));
		
		if(index >= 0){
			return index;
		}
		return null;
	}

	@PermitAll
	@Override
	public Boolean deleteDate(Integer dateID, Integer userID) {
		Date d = em.find(Date.class, dateID);
		System.out.println("excecuted: deleteDate()");
		
		journal.addJournalEntry("Start: Cal.deleteDate(Integer dateID, Integer userID)", "information", us.getUserID( CalContext.getCallerPrincipal().getName()));
		
		// if(userID.equals(d.getAuthorID()) || username.equals("admin")) {
		if( userID.equals( d.getAuthorID())) {
			em.remove(d);
			return true;
		}
		return false;
	}

	@PermitAll
	@Override
	public ArrayList<Date> getDates(Date date, Integer timeRange, Integer userID) {
		System.out.println("excecuted: getDates()");
		ArrayList<Date> dateArray = new ArrayList<Date>();
		Calendar end = new GregorianCalendar(); 
		Calendar start = date.getDateAndTime(); 
		Date d = new Date();
		end.setTimeInMillis(date.getDateAndTime().getTimeInMillis() + timeRange * 1000 * 60);
		
		ArrayList<Date> list = getAllDatesInDB(userID);
		ListIterator<Date> li = list.listIterator();
		while(li.hasNext()) {
					d = list.get(li.nextIndex());
					if(d.getDateAndTime().after(start) && d.getDateAndTime().before(end)) {
						dateArray.add(d);
					}
		}
		
		journal.addJournalEntry("Start: Cal.getDates(Date date, Integer timeRange, Integer userID)", "information", us.getUserID( CalContext.getCallerPrincipal().getName()));
		
		return dateArray;
	}
	
	@PermitAll
	@Override
	public ArrayList<Date> getAllDatesInDB(Integer userID) {
		ArrayList<Date> li;
		if( userID == null)
		{
			li = (ArrayList<Date>) em.createQuery("FROM Date").getResultList();
		}
		else {
			li = (ArrayList<Date>) em.createQuery("FROM Date WHERE authorID = :cauthor").setParameter("cauthor", userID).getResultList();
		}
				
		journal.addJournalEntry("Start: Cal.getAllDatesInDB(Integer userID)", "Client/REST Call", null);
		
		return li;
	}

	@PermitAll
	@Override
	public void updateDate(Integer dateID, Date newDate) {
		Date d;
		System.out.println("excecuted: updateDate()");
		
		d = em.find(Date.class, dateID);
		//d.setAuthorID(newDate.getAuthorID());
		d.setDateAndTime(newDate.getDateAndTime());
		d.setDescription(newDate.getDescription());
		d.setDuration(newDate.getDuration());
		d.setLabel(newDate.getLabel());
		d.setMembers(newDate.getMembers());
		d.setPlace(newDate.getPlace());
		
		journal.addJournalEntry("Start: Cal.updateDate(Integer dateID, Date newDate)", "information", us.getUserID( CalContext.getCallerPrincipal().getName()));
		
		
		return;
	}

	@PermitAll
	@Override
	public ArrayList<Date> searchNextFreeTermin( ArrayList<Integer> member,
			Calendar fromDate, Calendar toDate, Integer dateLength) {
		System.out.println("excecuted: searchNextFreeTermin()");
		journal.addJournalEntry("Start: Cal.searchNextFreeTermin( ArrayList<Integer> member, Calendar fromDate, Calendar toDate, Integer dateLength)", "information", us.getUserID( CalContext.getCallerPrincipal().getName()));
		
		return null;
	}

}
