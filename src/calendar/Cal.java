package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DeclareRoles({	Roles.ADMIN,	Roles.STUDENT,	Roles.JANITOR	})	
@RolesAllowed({})
@Stateless
public class Cal implements CalRemoteInterface, CalLokalInterface {
	
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	
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
		
		Journal j = new Journal("Start: Cal.createDate( Date date, String username)", "information", userID);
		em.persist(j);
		
		return d.getId();
	}

	@PermitAll
	@Override
	public Integer getDateID(Date date) {
		ArrayList<Date> allDates = getAllDatesInDB( -1);
		int index = allDates.indexOf(date);
		System.out.println("excecuted: getDateID()");
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
		
		return dateArray;
	}
	
	@PermitAll
	@Override
	public ArrayList<Date> getAllDatesInDB(Integer userID) {
		ArrayList<Date> li = (ArrayList<Date>) em.createQuery("FROM Date WHERE authorID = :cauthor").setParameter("cauthor", userID).getResultList();		
		System.out.println("excecuted: getAllDatesInDB()");
		
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
		return;
	}

	@PermitAll
	@Override
	public ArrayList<Date> searchNextFreeTermin( ArrayList<Integer> member,
			Calendar fromDate, Calendar toDate, Integer dateLength) {
		System.out.println("excecuted: searchNextFreeTermin()");
		return null;
	}

}
