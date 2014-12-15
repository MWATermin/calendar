package calendar;

import java.io.*;
import java.util.*;
import javax.persistence.*;

@Entity
public class User implements Serializable{
	// Variablendeklaration
	private Integer id;
	private String username;
	private String password;
	
	public User(){
		super();
	}
	
	public User(String username, String password){
		super();
		this.username = username;
		this.password = password;
	}
			
	// Getters & Setters
	@Id
	@GeneratedValue 
	public Integer getId() {
		return id;
	}
	
	public void setId( Integer id) {
		this.id = id;
	}
	
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
}
