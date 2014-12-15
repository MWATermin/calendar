package calendar;

import java.util.*;

public interface UserFunctionInterface {
	public Integer createUser(String username, String password);
	
	public Integer getUserID(String username); 
	
	public Boolean deleteUser(String username);
	
	public Boolean deleteUser(Integer userID);
	
	public ArrayList<User> getAllUser();
	
	public Boolean updateUser(User newUser, Integer userID);
}
