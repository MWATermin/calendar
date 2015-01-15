package User;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@Entity(name="User")
public class User implements Serializable{
	// Variablendeklaration
	private Integer	id;
	private String 	username;
	private String 	password;
	private String 	role;
	private ArrayList<Integer> DateIDs;
	
	public User(){
		super();
	}
	
	public User(String username, String password, String role){
		super();
		this.username 	= username;
		this.password 	= password;
		this.role 		= role;
	}
			
	// Getters & Setters
	@Id
	@GeneratedValue 
	@XmlElement
	public Integer getId() {
		return id;
	}
	
	public void setId( Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@XmlElement
	public ArrayList<Integer> getDateIDs(){
		return DateIDs;
	}
	
	public void setDateIDs( ArrayList<Integer> Dates){
		DateIDs = Dates;
	}
}
