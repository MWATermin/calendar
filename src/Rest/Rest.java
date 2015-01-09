package Rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.ejb3.annotation.SecurityDomain;

import Date.Date;
import Journal.Journal;
import Journal.JournalLocalInterface;
import StatelessCal.CalLokalInterface;
import User.User;
import User.UserFunctionLocalInterface;
import calendar.Roles;

@SecurityDomain( "CalSecurity")
@DeclareRoles( { Roles.ADMIN, Roles.STUDENT, Roles.GUEST})
@Path("/")
public class Rest implements RestInterface{

	private static String home = "<a href=\"../../../../../../../../../../Calendar_Rest/\">Back</a>";
	
	@EJB
	private CalLokalInterface cal;
	@EJB
	private UserFunctionLocalInterface us;
	@EJB
	private JournalLocalInterface journal;
	
	
    @PermitAll
    @GET
    @Path("/dates")
    @Produces(MediaType.TEXT_HTML)
    public String DatesToHTML(@QueryParam("usr") Integer usr)
    {
    	int i, count;
    	String html = "";
    	ArrayList<Date> Dates;
    	ArrayList<String> linecolor = new ArrayList<String>();
    	linecolor.add("#E0F8E0");
    	linecolor.add("#E0ECF8");

    	if( !(Dates = cal.getAllDatesInDB(usr)).isEmpty())
    	{
        	html+= "<html>"
        		+ "	<h2> Dates of: " + us.getUsername(usr) + "</h2>"
        		+ "	 <body>"
        		+ "		<div align='center'>"
       			+ "			<table border='1' cellpadding='2' cellspacing='0'>"
       			+ "				<tr bgcolor='#D8D8D8' align='left'>"
       			+ "					<td width='100'><b>ID</b></td>"
       			+ "					<td width='100'><b>Author</b></td>"
       			+ "					<td width='200'><b>Description</b></td>"
       			+ "					<td width='100'><b>Label</b></td>"
       			+ "					<td width='100'><b>Place</b></td>"
       			+ "					<td width='100'><b>Duration</b></td>"
        		+ "					<td width='100'><b>Date</b></td>"
        		+ "				</tr>";

    		for( i=0, count = Dates.size(); i<count; i++)
    		{
    			Date D = Dates.get(i);
    			
        		html+= "<tr bgcolor='" + linecolor.get(i%2) + "'>"
            		+ "	<td>" + D.getId() 						+ "</td>"
            		+ "	<td>" + us.getUsername(D.getAuthorID()) + "</td>"
           			+ "	<td>" + D.getDescription() 				+ "</td>"
           			+ "	<td>" + D.getLabel() 					+ "</td>"
		       		+ "	<td>" + D.getPlace() 					+ "</td>"
		       		+ "	<td>" + D.getDuration() 				+ "</td>"
	        		+ "	<td>" + D.getDateAndTime().getTime() 	+ "</td>"
	        		+ "	</tr>";
    		}
    		
        	html+=  "</table>"
            		+ "	 </div>"
            		+ " </body>"
            		+ "</html>";
    	}
    	else
    	{
        	html+= "<html>"
            		+ "	<h2> Dates of: " + us.getUsername(usr) + "</h2>"
            		+ "<div align='center'>"
            		+ " No Dates found for User: "+ us.getUsername(usr)
					+ "</div>"
            		+ "</html>";
    	}
    	
    	return html + "</br>" + home;
    }
    
    @PermitAll
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Date DatesToJSON(@QueryParam("usr") String usr, @QueryParam("id") int id)
    {
    	ArrayList<Date> Dates;
    	int UserId = us.getUserID(usr);
    	
    	if( !(Dates = cal.getAllDatesInDB(UserId)).isEmpty())
    	{ 
    		return Dates.get(id);
    	}
    	
    	return null;
    }
    
    @PermitAll
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response UpdateDateJSON(Date D)
    {
    	//cal.updateDate(D.getId(), D); GEHT NICHT WEIL DOOF
    	
    	return Response.status(201).entity(D.getLabel()).build();
    	
    }
    
    @PermitAll
    @PUT
    @Path("/put")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response PutDateJSON(Date D) //user ID per parameter übergeben
    {
    	cal.createDate(D, 1);
    	System.out.println(D.getLabel());
    	return Response.status(201).entity(D.getLabel()).build();
    }
    
    @PermitAll
    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_HTML)
    public String UserToHTML()
    {
    	int i = 0;
    	int count = 0;
    	String html = "";
    	ArrayList<User> list = null;
    	ArrayList<String> linecolor = new ArrayList<String>();
    	linecolor.add("#E0F8E0");
    	linecolor.add("#E0ECF8");
    	
    	if(!(list = us.getAllUser()).isEmpty())
    	{
        	html+= "<html>"
            		+ "	<h2> User </h2>"
            		+ "	 <body>"
           			+ "			<table border='1' cellpadding='2' cellspacing='0'>"
           			+ "				<tr bgcolor='#D8D8D8' align='left'>"
           			+ "					<td width='100'><b>User</b></td>"
           			+ "				</tr>";
    		
    		for( i=0, count = list.size(); i<count; i++)
    		{
    			User usr   = list.get(i);
    			html+= "<tr bgcolor='" + linecolor.get(i%2) + "'>"
	    			+ "	 <td><a href=\"../../../../../../../../../../Calendar_Rest/rest/dates?usr=" + usr.getId() + "\">" + usr.getUsername() + "</a></td>"
	    			+ "	</tr>";
    		}

        	html+=  "</table>"
            		+ " </body>"
            		+ "</html>";
    	}	
    	else
    	{
        	html+= "<html>"
            		+ "	<h2> User </h2>"
            		+ "  <div align='center'>"
            		+ "   No User was found "
					+ "  </div>"
            		+ "</html>";
    	}
    	
    	return html + home;
    }
    
    
    @PermitAll
    @GET
    @Path("/journal")
    @Produces(MediaType.TEXT_HTML)
    public String JournalToHTML() {
    	String html = "";
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ArrayList<Journal> journalList = journal.getJournalList();
    	ListIterator li = journalList.listIterator();
    	ArrayList<String> linecolor = new ArrayList<String>();
    	linecolor.add("#E0F8E0");
    	linecolor.add("#E0ECF8");
    	int i = 0;
    	
    	// HTML Ausgabe
    	html += "<html>"
    			+ "	<head>"
    			+ "		<title>Journal</title>"
    			+ "	</head>"
    			+ "	<body>"
    			+ "		<div align='center'>"
    			+ "			<table border='1' cellpadding='2' cellspacing='0'>"
    			+ "				<tr bgcolor='#D8D8D8' align='left'>"
    			+ "					<td width='100'><b>Journal ID</b></td>"
    			+ "					<td width='150'><b>Zeitstempel</b></td>"
    			+ "					<td width='200'><b>Beschreibung</b></td>"
    			+ "					<td width='200'><b>Informationen</b></td>"
    			+ "					<td width='100'><b>User ID</b></td>"
    			+ "					<td width='100'><b>Username</b></td>"
    			+ "				</tr>";

    	while( li.hasNext())
    	{
    		Journal serverJournal = journalList.get( li.nextIndex()); // aktuelles Element holen
    		
    		html+="	<tr bgcolor='" + linecolor.get(i%2) 										+ "'>"
        		+ "	<td>" + serverJournal.getId()											 	+ "</td>"
        		+ "	<td>" + dateFormat.format( serverJournal.getJournalTimestamp().getTime()) 	+ "</td>"
        		+ "	<td>" + serverJournal.getJournalDescription() 								+ "</td>"
        		+ "	<td>" + serverJournal.getJournalInformation() 								+ "</td>";
    		if ( serverJournal.getJournaluserID() != null ) {
    			html+="<td>" + serverJournal.getJournaluserID() 					+ "</td>"
    				+ "<td>" + us.getUsername( serverJournal.getJournaluserID()) 	+ "</td>";
    		}
    		else{
    			html+="<td>NULL</td>"
    				+ "<td>NULL</td>";
    		}
    		
        	html += "</tr>";
    		i++;
    		li.next(); 
    	}	
    	
    	html += "</table>"
    			+ "	</div>"
    			+ " </body>"
    			+ "</html>";
    			
    	return html + home;
    } // JournalToHTML zu
} // Rest zu
