package calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	@EJB
	private JournalLocalInterface journal;
	
	
    @PermitAll
    @GET
    @Path("/dates")
    @Produces(MediaType.TEXT_HTML)
    public String DatesToHTML(@QueryParam("usr") Integer usr)
    {
    	int i, count;
    	String html = null;
    	ArrayList<Date> Dates;
 
    	
    	//if(!usr.toString().isEmpty() && !(Dates = cal.getAllDatesInDB(usr)).isEmpty())
    	if( !(Dates = cal.getAllDatesInDB(usr)).isEmpty())
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
    			html += "		<td>" + D.getAuthorID() 				+ "</td>";
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
    			html += "		<td><a href=\"../../../../../../../../../../Calendar_Rest/rest/dates?usr=" + usr.getId() + "\">" + usr.getUsername() + "</a></td>";
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
    
    
    @PermitAll
    @GET
    @Path("/journal")
    @Produces(MediaType.TEXT_HTML)
    public String JournalToHTML() {
    	String html = "";
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	ArrayList<Journal> journalList = journal.getJournalList();
    	
    	ListIterator li = journalList.listIterator();
    	
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
    			
    	boolean oddline = false;
    	String linecolor = "#E0F8E0";
    	while( li.hasNext())
    	{
    		Journal serverJournal = journalList.get( li.nextIndex()); // aktuelles Element holen
    		
    		html += "				<tr bgcolor='" + linecolor + "'>"
        			+ "					<td>" + serverJournal.getId() + "</td>"
        			+ "					<td>" + dateFormat.format( serverJournal.getJournalTimestamp().getTime()) + "</td>"
        			+ "					<td>" + serverJournal.getJournalDescription() + "</td>"
        			+ "					<td>" + serverJournal.getJournalInformation() + "</td>";
    		if ( serverJournal.getJournaluserID() != null ) {
    			html +=	"					<td>" + serverJournal.getJournaluserID() + "</td>"
    					+ "					<td>" + us.getUsername( serverJournal.getJournaluserID()) + "</td>";
    		}
    		else{
    			html +=	"					<td>NULL</td>"
    					+ "					<td>NULL</td>";
    		}
        	html += "				</tr>";
    		
    		li.next(); // List Iterator auf nächstes Element setzen
    		
    		if( !oddline)
    		{	
    			// Farbe für ungerade Zeile
    			oddline = true;
    			linecolor = "#E0ECF8";
    		}
    		else
    		{	
    			// Farbe für gerade Zeile
    			oddline = false;
    			linecolor = "#E0F8E0";
    		}	// if zu
    	}	// while zu
    	
    	html += "		</table>"
    			+ "	</div>"
    			+ " </body>"
    			+ "</html>";
    			
    	return html + home;
    } // JournalToHTML zu
} // Rest zu
