package calendar;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import StatefulCal.StatefulCal;
import StatefulCal.StatefulCalRemoteInterface;
import User.UserFunction;
import User.UserFunctionRemoteInterface;

public class calendarClient {
	
	public static void main(String[] args) throws Exception {
		invokeStatelessBean();
	}

	private static void invokeStatelessBean() throws NamingException {
		//final CalRemoteInterface CalendarInterface = doLoopup();
		final StatefulCalRemoteInterface StatefulCal = Lookup();
		//final UserFunctionRemoteInterface userInterface = doLookup();
		
		System.out.println(StatefulCal.hellomessage());
		
//		String username = "gina";
//		String userRole = "student";
//		ArrayList<String> members = new ArrayList<String>();
//		members.add(username);
//		members.add("Michael");
//		members.add("Philipp");
//
//		int userID = userInterface.createUser(username, "123", userRole);
//		userInterface.createUser("ginaxyz", "lisa", userRole);
//
//		ArrayList<User> userList = userInterface.getAllUser();
//		System.out.println("getAllUser()");
//		
//		ListIterator<User> us = userList.listIterator();
//		System.out.println("UserCount: " + userList.size());
//		while( us.hasNext()) {
//			User serverUser = userList.get(us.nextIndex());
//			System.out.println( "ID: " + serverUser.getId() + " >> " + 
//								"Username: " + serverUser.getUsername() + " >> " + 
//								"Password: " + serverUser.getPassword());
//			us.next();	
//		}
//		System.out.println("\n\n");

	
		
//		/**
//		 * Stateful Test Area
//		 */
//		Calendar cal = new GregorianCalendar(2013,1,28,13,24,56);
//		
//		//Date date = new Date(cal, 30, userID, "cok", "suking", "gangban111", null);
//		Date date = new Date(cal, 30, "cok", "suking", "gangban111", null);
//		Integer myid = StatefulCal.createDate( date);
//		System.out.println("CalendarID1: " + myid + "\n");
//	 
//		
//		Date d = new Date(cal, 30, "bad", "beer", "gangban11", null);
//		StatefulCal.updateDate(myid, d);
//		System.out.println("updateDate(" + myid + ", d)");
//		/**
//		 * Stateful Test Area End
//		 */
		
		StatefulCal.bye();

		
		/**
		Calendar cal = new GregorianCalendar(2013,1,28,13,24,56);
		
		Date date = new Date(cal, 30, "bla", "cok", "suking", "gangban111", null);
		//int myid = CalenderInterface.createDate();
		Integer myid = CalendarInterface.createDate( date, username);
		System.out.println("CalendarID1: " + myid + "\n");
		
		cal = new GregorianCalendar(2014,1,28,13,25,56);
		date = new Date(cal, 30, "bla", "cok", "suking", "gangban222", null);
		myid = CalendarInterface.createDate( date);
		System.out.println("CalendarID2: " + myid + "\n");
		
		
		//Update Date Test-Case
		Date d = new Date(cal, 30, "blab", "bad", "beer", "gangban11", members);
		CalendarInterface.updateDate(myid, d);
		System.out.println("updateDate(" + myid + ", d)");
		
		
		d = new Date(cal, 30, username, "bad", "beer123", "gangban11", members);
		CalendarInterface.updateDate(myid, d);
		System.out.println("updateDate(" + myid + ", d)");
				
		// Print ArrayList<Date>
		ArrayList<Date> DateList;
		DateList = CalendarInterface.getAllDatesInDB( username);
		System.out.println("getAllDatesInDB()");
		
		ListIterator<Date> li = DateList.listIterator();
		System.out.println("size: " + DateList.size() + "\n");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while( li.hasNext()) {
			Date serverDate = DateList.get( li.nextIndex());
			System.out.println( "ID: " + serverDate.getId() + "\n" + 
 					"Description: " + serverDate.getDescription() + "\n" +
					"Author: " + serverDate.getAuthor() + "\n" +
					"Label: " + serverDate.getLabel() + "\n" +
					"Members: " + serverDate.getMembers() + "\n" +
					"Place: " + serverDate.getPlace() + "\n" +
					"Duration: " + serverDate.getDuration() + "\n" + 
					"DateAndTime: " + dateFormat.format( serverDate.getDateAndTime().getTime()) + "\n"
					);
			
			li.next();
			
		}**/
		// ENDE Print ArrayList<Date>
	}

/**	// Looks up and returns the proxy to remote interface
	private static CalRemoteInterface doLoopup() throws NamingException{
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		// The app is typically the ear name
		final String appName = "";
		// This is the module name of the deployed EJBs on the server
		final String moduleName = "Calendar_Rest";
		final String distinctName = "";
		// The EJB name which by default is the simple class name of the bean // implementation class
		final String beanName = Cal.class.getSimpleName();
		// the remote view fully qualified class name
		final String viewClassName = CalRemoteInterface.class.getName(); // let's do the lookup
		String lookupName = "ejb:" + appName + "/" + moduleName + "/"
				+ distinctName + "/" + beanName + "!" + viewClassName;
		System.out.println(lookupName);
		return (CalRemoteInterface) context.lookup(lookupName);
	} **/
	
	// Looks up and returns the proxy to remote interface
	private static StatefulCalRemoteInterface Lookup() throws NamingException{
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		// The app is typically the ear name
		final String appName = "";
		// This is the module name of the deployed EJBs on the server
		final String moduleName = "Calendar_Rest";
		final String distinctName = "";
		// The EJB name which by default is the simple class name of the bean // implementation class
		final String beanName = StatefulCal.class.getSimpleName();
		// the remote view fully qualified class name
		final String viewClassName = StatefulCalRemoteInterface.class.getName(); // let's do the lookup
		String lookupName = "ejb:" + appName + "/" + moduleName + "/"
				+ distinctName + "/" + beanName + "!" + viewClassName + "?stateful";
		System.out.println(lookupName);
		return (StatefulCalRemoteInterface) context.lookup(lookupName);
	}
	
	// Looks up and returns the proxy to remote interface
	private static UserFunctionRemoteInterface doLookup() throws NamingException{
		final Hashtable userJndiProperties = new Hashtable();
		userJndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		final Context userContext = new InitialContext(userJndiProperties);
		// The app is typically the ear name
		final String userAppName = "";
		// This is the module name of the deployed EJBs on the server
		final String userModuleName = "Calendar_Rest";
		final String userDistinctName = "";
		// The EJB name which by default is the simple class name of the bean // implementation class
		final String userBeanName = UserFunction.class.getSimpleName();
		// the remote view fully qualified class name
		final String userViewClassName = UserFunctionRemoteInterface.class.getName(); // let's do the lookup
		String userLookupName = "ejb:" + userAppName + "/" + userModuleName + "/"
				+ userDistinctName + "/" + userBeanName + "!" + userViewClassName;
		System.out.println(userLookupName);
		return (UserFunctionRemoteInterface) userContext.lookup(userLookupName);
	}
}
