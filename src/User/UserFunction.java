package User;

import java.util.ArrayList;

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

import calendar.Roles;
import Journal.JournalLocalInterface;


@Stateless(name = "UserFunction", mappedName = "UserFunction")
@SecurityDomain( "CalSecurity")
@DeclareRoles( { Roles.ADMIN, Roles.STUDENT, Roles.GUEST})
public class UserFunction implements UserFunctionRemoteInterface, UserFunctionLocalInterface{
	
	@Resource
	SessionContext userFunctionContext;
	
	@EJB
	private JournalLocalInterface journal;
	@EJB
	private UserFunctionLocalInterface us;
	
	
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	
	public UserFunction(){
	}
	
	@PermitAll
	@Override
	public Integer createUser(String username, String password, String role){
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setRole(role);
		em.persist(u);
		
		journal.addJournalEntry("Start: UserFunction.createUser(String username, String password, String role)", "Administrator function<br />created: " + username + "<br />ID: " + u.getId(), null);
		
		return u.getId();
	}
		
	@PermitAll
	@Override
	public Integer getUserID(String username){
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User WHERE username = :cuser").setParameter("cuser", username).getResultList();
		
		//journal.addJournalEntry("Start: UserFunction.getUserID(String username)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		return li.get(0).getId();
	}
	
	@PermitAll
	public String getUsername(Integer userID) {
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User WHERE id = :cuserid").setParameter("cuserid", userID).getResultList();
		
		//journal.addJournalEntry("Start: UserFunction.getUsername(Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		return li.get(0).getUsername();
	}
	
	@PermitAll
	public User getUser(Integer userID) {
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User WHERE id = :cuserid").setParameter("cuserid", userID).getResultList();
		//journal.addJournalEntry("Start: UserFunction.getUsername(Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		User myUser = new User();
		myUser.setId( li.get(0).getId());
		myUser.setUsername( li.get(0).getUsername());
		myUser.setPassword( li.get(0).getPassword());
		myUser.setRole( li.get(0).getRole());
		return myUser;
	}
	
	
	@PermitAll
	@Override
	public Boolean deleteUser(String username){
		
		//journal.addJournalEntry("Start: UserFunction.deleteUser(String username)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		User u = em.find(User.class, getUserID(username));
		if(u != null){
			em.remove(u);
			return true;
		}
		return false;
	}
	
	@PermitAll
	@Override
	public Boolean deleteUser(Integer userID){
		
		//journal.addJournalEntry("Start: UserFunction.deleteUser(Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		User u = em.find(User.class, userID);
		if(u != null){
			em.remove(u);
			return true;
		}
		return false;
	}
	
	@PermitAll
	@Override
	public ArrayList<User> getAllUser(){
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User").getResultList();
		
		//journal.addJournalEntry("Start: UserFunction.getAllUser()", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		return li;
	}
	
	@PermitAll
	@Override
	public Boolean updateUser(User newUser, Integer userID){
		//journal.addJournalEntry("Start: UserFunction.updateUser(User newUser, Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));

		User u = em.find(User.class, userID);
		if(u != null){
			u.setPassword( newUser.getPassword());
			u.setUsername( newUser.getUsername());
			u.setRole( newUser.getRole());
			return true;
		}
		return false;
	}
}
