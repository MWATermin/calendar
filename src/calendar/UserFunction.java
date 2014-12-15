package calendar;

import java.util.ArrayList;

import javax.ejb.*;
import javax.persistence.*;

@Stateless
public class UserFunction implements UserFunctionRemoteInterface{
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	
	public UserFunction(){
	}
	
	public Integer createUser(String username, String password){
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		em.persist(u);
		return u.getId();
	}
		
	public Integer getUserID(String username){
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User WHERE username = :cuser").setParameter("cuser", username).getResultList();
		return li.get(0).getId();
	}
	
	public Boolean deleteUser(String username){
		User u = em.find(User.class, getUserID(username));
		if(u != null){
			em.remove(u);
			return true;
		}
		return false;
	}
	
	public Boolean deleteUser(Integer userID){
		User u = em.find(User.class, userID);
		if(u != null){
			em.remove(u);
			return true;
		}
		return false;
	}
	
	public ArrayList<User> getAllUser(){
		ArrayList<User> li = (ArrayList<User>) em.createQuery("FROM User").getResultList();
		return li;
	}
	
	public Boolean updateUser(User newUser, Integer userID){
		User u = em.find(User.class, userID);
		if(u != null){
			u.setPassword(newUser.getPassword());
			u.setUsername(newUser.getUsername());
			return true;
		}
		return false;
	}
}
