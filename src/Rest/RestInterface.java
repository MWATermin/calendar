package Rest;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import Date.Date;

public interface RestInterface {
	
	public String DatesToHTML( Integer userID);
	
	public Date DatesToJSON(@QueryParam("usr") String usr, @QueryParam("id") int id);
	
	public Response UpdateDateJSON(Date D);
	
    public Response PutDateJSON(Date D);

}