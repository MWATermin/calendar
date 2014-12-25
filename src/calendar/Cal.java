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
	private static String home = "<a href=\"../../../../../../../../../../Calendar_Rest/\">Back</a>";
	
    public Cal() {
       
    }
   
    /**
    @PermitAll
    @Override
    @GET
    @Path("/dates")
    @Produces(MediaType.TEXT_HTML)
    public String DatesToHTML(@QueryParam("usr") String usr)
    {
    	int i, count;
    	String html = null;
    	ArrayList<Date> Dates;
    	
    	
    	if(!usr.isEmpty() && !(Dates = getAllDatesInDB(usr)).isEmpty())
    	{
    		html =  "<h1>Dates: " + usr + "</h1></br>";
			html += "<table border=\"1\">";
			html += "	<tr>";
			html += "		<th>Id</th>";
			html += "		<th>Author</th>";
			html += "		<th>Description</th>";
			html += "		<th>Label</th>";
			html += "		<th>Place</th>";
			html += "		<th>Duration</th>";
			html += "		<th>Date</th>";
			html += "	</tr>";
    		
    		for( i=0, count = Dates.size(); i<count; i++)
    		{
    			Date D = Dates.get(i);
    			html += "	<tr>";
    			html += "		<td>" + D.getId() 						+ "</td>";
    			html += "		<td>" + D.getAuthor() 					+ "</td>";
    			html += "		<td>" + D.getDescription() 				+ "</td>";
    			html += "		<td>" + D.getLabel() 					+ "</td>";
    			html += "		<td>" + D.getPlace() 					+ "</td>";
    			html += "		<td>" + D.getDuration() 				+ "</td>";
    			html += "		<td>" + D.getDateAndTime().getTime() 	+ "</td>";
    			html += "	</tr>";
    		}

			html += "</table>";
    	}
    	else
    	{
    		html = "<b>Error: No valid user specified!</b>";
    	}
    		
    	
    	return html + "</br>" + home;
    }
    **/
    
	@PermitAll
	@Override
	public Integer createDate( Date date, String username) {
		Date d = date;
		System.out.println("excecuted: createDate()");
		d.setAuthor(username);
		System.out.println("pre: persist()"+ em);
		em.persist(d);
		System.out.println("post: persist()");
		return d.getId();
	}

	@PermitAll
	@Override
	public Integer getDateID(Date date) {
		ArrayList<Date> allDates = getAllDatesInDB("");
		int index = allDates.indexOf(date);
		System.out.println("excecuted: getDateID()");
		if(index >= 0){
			return index;
		}
		return null;
	}

	@PermitAll
	@Override
	public Boolean deleteDate(Integer dateID, String username) {
		Date d = em.find(Date.class, dateID);
		System.out.println("excecuted: deleteDate()");
		
		if(username.equals(d.getAuthor()) || username.equals("admin")) {
			em.remove(d);
			return true;
		}
		return false;
	}

	@PermitAll
	@Override
	public ArrayList<Date> getDates(Date date, Integer timeRange, String username) {
		System.out.println("excecuted: getDates()");
		ArrayList<Date> dateArray = new ArrayList<Date>();
		Calendar end = new GregorianCalendar(); 
		Calendar start = date.getDateAndTime(); 
		Date d = new Date();
		end.setTimeInMillis(date.getDateAndTime().getTimeInMillis() + timeRange * 1000 * 60);
		
		ArrayList<Date> list = getAllDatesInDB(username);
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
	public ArrayList<Date> getAllDatesInDB(String username) {
		ArrayList<Date> li = (ArrayList<Date>) em.createQuery("FROM Date WHERE author = :cauthor").setParameter("cauthor", username).getResultList();		
		System.out.println("excecuted: getAllDatesInDB()");
		
		return li;
	}

	@PermitAll
	@Override
	public void updateDate(Integer dateID, Date newDate) {
		Date d;
		System.out.println("excecuted: updateDate()");
		
		d = em.find(Date.class, dateID);
		d.setAuthor(newDate.getAuthor());
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
	public ArrayList<Date> searchNextFreeTermin( ArrayList<String> member,
			Calendar fromDate, Calendar toDate, Integer dateLength) {
		System.out.println("excecuted: searchNextFreeTermin()");
		return null;
	}

}
