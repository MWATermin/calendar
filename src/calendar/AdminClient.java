package calendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import StatelessCal.Cal;
import StatelessCal.CalRemoteInterface;
import User.User;
import User.UserFunction;
import User.UserFunctionRemoteInterface;

public class AdminClient {

	public static void main(String[] args) throws Exception {
		invokeStatelessBean();
	}

	private static Properties getUserProperties() {
		// EJB Properties ( ersetzt "jboss-ejb-client.properties" )
		final Properties ejbProperties = new Properties();
		ejbProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		ejbProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		ejbProperties.put("endpoint.name", "client-endpoint");
	    ejbProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
	    ejbProperties.put("remote.connections", "default");
	    ejbProperties.put(Context.PROVIDER_URL, "localhost");
	    ejbProperties.put("remote.connection.default.port", "8080");
	    ejbProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
	    //ejbProperties.put("remote.connection.default.username", "admin");
	    //ejbProperties.put("remote.connection.default.password", "admin");
	    ejbProperties.put(Context.SECURITY_PRINCIPAL, "admin");
	    ejbProperties.put(Context.SECURITY_CREDENTIALS, "admin");
	    ejbProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
	    ejbProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
	    
	    return ejbProperties;
	}
	
	private static void out( String output) {
		System.out.println( output);
	}
	
	private static void printAllUser( UserFunctionRemoteInterface userInterface) throws IOException
	{
		PrintStream o = System.out;
		ArrayList<User> userList = userInterface.getAllUser();
		ListIterator<User> us = userList.listIterator();
		out( "\nListe aller Benutzer:\n");
		//System.out.println("UserCount: " + userList.size());
		out("|-----|--------------------|--------------------|--------------------|");
		o.printf("|%5s|%20s|%20s|%20s|\n", "ID", "Username", "Password", "Rolle");
		out("|-----|--------------------|--------------------|--------------------|");
		while( us.hasNext()) {
			User serverUser = userList.get(us.nextIndex());
			o.printf("|%5d|%20s|%20s|%20s|\n", 	serverUser.getId(), 
												serverUser.getUsername(), 
												serverUser.getPassword(), 
												serverUser.getRole());
			
			/*System.out.println( "ID: " + serverUser.getId() + "\t" + 
								"Username: " + serverUser.getUsername() + "%5s\t" + 
								"Password: " + serverUser.getPassword() + "\t" +
								"Rolle: " + serverUser.getRole()); */
			us.next();	
		}
		out("|-----|--------------------|--------------------|--------------------|");
		System.out.println("\n");
	}
	
	private static void caseCreateUser( UserFunctionRemoteInterface userInterface) throws IOException {
			BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
			String username = "";
			String password = "";
			String role = Roles.STUDENT;
			
			out( "\nBenutzer anlegen:\n" 
					+ "\tUsername: ");
			username = in.readLine();
			out("\tPassword: ");
			password = in.readLine();
			out("\tBenutzerrolle [Standard: student]\n"
					+ "\tPress Enter for Standard\n");
			String input = in.readLine();
			if( input.isEmpty()) {
				out( "\nBenutzerrolle: Standard\n");
			}
			else {
				out( "\nBenutzerrolle: eigen\n");
				role = input;
			}
			
			out( "lege Benutzer an...\n");
			userInterface.createUser( username, password, role);
			out( "...Benutzer angelegt\n");
			
			return;			
	}
	
	private static void editUser( UserFunctionRemoteInterface userInterface) throws IOException
	{
		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
		String reader = "";
		Integer userToEdit = null;
		printAllUser( userInterface);
		
		out( "Welcher Benutzer(ID) soll editiert werden?\n" +
				"\tUserID: ");
		userToEdit = Integer.parseInt( in.readLine());
		User localUser = userInterface.getUser( userToEdit);
		out("INFO: [Enter] um bisheriges übernehmen.\n");
		
		out( "old Username: " + localUser.getUsername() + "\n");
		out( "new Username: ");
		reader = in.readLine();
		if( !reader.isEmpty())
			localUser.setUsername( reader);
		reader = "";
		
		out( "old Password: " + localUser.getPassword() + "\n");
		out( "new Password: ");
		reader = in.readLine();
		if( !reader.isEmpty())
			localUser.setPassword( reader);
		reader = "";
		
		out( "old Role: " + localUser.getRole() + "\n");
		out( "new Role: ");
		reader = in.readLine();
		if( !reader.isEmpty())
			localUser.setRole( reader);
		reader = "";
		
		out( "Update User mit ID=" + userToEdit + " ...");
		userInterface.updateUser( localUser, userToEdit);
		out( "... finished Update User mit ID=" + userToEdit);
		
		return;
	}
	
	private static void deleteUser( UserFunctionRemoteInterface userInterface) throws IOException
	{
		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
		Integer userToDelete = null;
		printAllUser( userInterface);
		
		out( "Welcher Benutzer(ID) soll gelöscht werden?\n" +
				"\tUserID: ");
		userToDelete = Integer.parseInt( in.readLine());
		if( userToDelete != null)
		{
			out( "lösche Benutzer mit ID=" + userToDelete + " ...\n");
			userInterface.deleteUser( userToDelete);
			out( "...Benutzer mit ID=" + userToDelete + " gelöscht\n");
		}
		
		return;	
	}
	
	private static void invokeStatelessBean() throws NamingException {
		
		final UserFunctionRemoteInterface userInterface = UserInterfaceLookup();
		//final CalRemoteInterface Cal = CalInterfaceLookup();
		
		// Konsoleneingaben einlesen mit "in.readline()"
		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
		
		boolean schleife = true;
		while( schleife)
		{
			out( "Was soll gemacht werden?\n" 
					+ "\t1 - neuen Benutzer anlegen\n"
					+ "\t2 - einen Benutzer editieren\n"
					+ "\t3 - einen Benutzer löschen\n"
					+ "\t4 - zeige Alle Benutzer\n"
					+ "\t9 - exit");
			
			try{
				switch( in.readLine()) {
					case "1":
						out( "neuen Benutzer anlegen ausgewählt\n");
						caseCreateUser( userInterface);
						break;
						
					case "2":
						out("einen Benutzer editieren ausgewählt\n");
						editUser( userInterface);
						break;
						
					case "3":
						out("einen Benutzer löschen ausgewählt\n");
						deleteUser( userInterface);						
						break;
						
					case "4":
						out("zeige alle Benutzer:\n");
						printAllUser( userInterface);						
						break;
						
					case "9":
						out( "exit ausgewählt");
						schleife = false;
						break;
						
					default:
						schleife = false;
						break;
				}
			}
			catch(IOException e) {
				out( "IOException aufgetreten\n");
				out( e.getMessage());
				e.printStackTrace();
				break;
			}
			
		}
		out( "schleife beendet\n");
		//userInterface.createUser( "gina", "lisa", "student");
		
	}
	
	// Looks up and returns the proxy to remote interface
	private static CalRemoteInterface CalInterfaceLookup() throws NamingException{	
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		final Context context = new InitialContext( jndiProperties);
		//final Context context = new InitialContext( getUserProperties());
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
	}
	
	// Looks up and returns the proxy to remote interface
	private static UserFunctionRemoteInterface UserInterfaceLookup() throws NamingException {
		final Hashtable userJndiProperties = new Hashtable();
		userJndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context userContext = new InitialContext( userJndiProperties);
		//final Context userContext = new InitialContext( getUserProperties());
		// The app is typically the ear name
		final String userAppName = "";
		// This is the module name of the deployed EJBs on the server
		final String userModuleName = "Calendar_Rest";
		final String userDistinctName = "";
		// The EJB name which by default is the simple class name of the bean //
		// implementation class
		final String userBeanName = UserFunction.class.getSimpleName();
		// the remote view fully qualified class name
		final String userViewClassName = UserFunctionRemoteInterface.class.getName(); // let's do the lookup
		String userLookupName = "ejb:" + userAppName + "/" + userModuleName
				+ "/" + userDistinctName + "/" + userBeanName + "!"	+ userViewClassName;
		System.out.println(userLookupName);
		return (UserFunctionRemoteInterface) userContext.lookup(userLookupName);
	}
	
	
	
}
