package calendar;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
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
	
	public Integer createUser(String username, String password){
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		em.persist(u);
		
		journal.addJournalEntry("Start: UserFunction.createUser(String username, String password)", "Administrator function<br />created: " + username + "<br />ID: " + u.getId(), null);
		
		return u.getId();
	}
		
	public Integer getUserID(String username){
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User WHERE username = :cuser").setParameter("cuser", username).getResultList();
		
		//journal.addJournalEntry("Start: UserFunction.getUserID(String username)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		return li.get(0).getId();
	}
	
	public String getUsername(Integer userID) {
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User WHERE id = :cuserid").setParameter("cuserid", userID).getResultList();
		
		//journal.addJournalEntry("Start: UserFunction.getUsername(Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		return li.get(0).getUsername();
	}
	
	public Boolean deleteUser(String username){
		
		//journal.addJournalEntry("Start: UserFunction.deleteUser(String username)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		User u = em.find(User.class, getUserID(username));
		if(u != null){
			em.remove(u);
			return true;
		}
		return false;
	}
	
	public Boolean deleteUser(Integer userID){
		
		//journal.addJournalEntry("Start: UserFunction.deleteUser(Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		User u = em.find(User.class, userID);
		if(u != null){
			em.remove(u);
			return true;
		}
		return false;
	}
	
	public ArrayList<User> getAllUser(){
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User").getResultList();
		
		//journal.addJournalEntry("Start: UserFunction.getAllUser()", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));
		
		return li;
	}
	
	public Boolean updateUser(User newUser, Integer userID){
		//journal.addJournalEntry("Start: UserFunction.updateUser(User newUser, Integer userID)", "information", us.getUserID( userFunctionContext.getCallerPrincipal().getName()));

		User u = em.find(User.class, userID);
		if(u != null){
			u.setPassword(newUser.getPassword());
			u.setUsername(newUser.getUsername());
			return true;
		}
		return false;
	}
}
