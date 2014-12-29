package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Rest implements RestInterface{

	private static String home = "<a href=\"../../../../../../../../../../Calendar_Rest/\">Back</a>";
	
	@EJB
	private CalLokalInterface cal;
	@EJB
	private UserFunctionLocalInterface us;
	
    @PermitAll
    @GET
    @Path("/dates")
    @Produces(MediaType.TEXT_HTML)
    public String DatesToHTML(@QueryParam("usr") String usr)
    {
    	int i, count;
    	String html = null;
    	ArrayList<Date> Dates;
    	
    	if(!usr.isEmpty() && !(Dates = cal.getAllDatesInDB(usr)).isEmpty())
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
    		html = "<b>Error: No Dates for " + usr + "!</b>";
    	}
    		
    	
    	return html + "</br>" + home;
    }
    
    @PermitAll
    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_HTML)
    public String UserToHTML()
    {
    	int i = 0;
    	int count = 0;
    	String html;
    	
    	ArrayList<User> list = null;
    	
    	if(!(list = us.getAllUser()).isEmpty())
    	{
    		html =  "<h1>User:</h1></br>";
			html += "<table border=\"1\">";
			html += "	<tr>";
			html += "		<th>User</th>";
			html += "	</tr>";
    		
    		for( i=0, count = list.size(); i<count; i++)
    		{
    			User usr   = list.get(i);
    			html += "	<tr>";
    			html += "		<td><a href=\"../../../../../../../../../../Calendar_Rest/rest/dates?usr=" + usr.getUsername() + "\">" + usr.getUsername() + "</a></td>";
    			html += "	</tr>";
    		}

			html += "</table>";
    	}
    	else
    	{
    		html = "<b>Error: No user!</b>";
    	}
    	
    	return html + home;
    }
}
