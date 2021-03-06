package Rest;

import java.util.ArrayList;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import User.User;
import User.UserFunction;
import Date.Date;

public interface RestInterface {
	
	public String DatesToHTML( Integer userID);
	
	public Date DatesToJSON(@QueryParam("usr") String usr, @QueryParam("id") int id);
	
	public Response UpdateDateJSON(Date D);
	
    public Response PutDateJSON(Date D, String usr);
    
    public Response DeleteDateJSON( int did, String usr);
    
    public ArrayList<User> UserToJSON();

    public ArrayList<Integer> InvUserToJSON(String usr, int id);
}