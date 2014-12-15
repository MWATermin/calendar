package calendar;

import java.util.*;

import javax.ejb.*;
import javax.persistence.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.annotation.security.*;

@DeclareRoles({	Roles.ADMIN,	Roles.STUDENT,	Roles.JANITOR	})	
@RolesAllowed({})	
@Stateless
@Path("")
public class Cal implements CalRemoteInterface {
	
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	private static String home = "<a href=\"../../../../../../../../../../Calendar_Rest/\">Back</a>";
	
    public Cal() {
       
    }
    
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
    
	@PermitAll
	@Override
	public Integer createDate( Date date, String username) {
		Date d = new Date();
		System.out.println("excecuted: createDate()");
		
		d = date;
		d.setAuthor(username);
		d.setMembers(date.getMembers());
		d.setDateAndTime(date.getDateAndTime());
		d.setDescription(date.getDescription());
		d.setDuration(date.getDuration());
		d.setLabel(date.getLabel());
		d.setPlace(date.getPlace());
		em.persist(d);
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
