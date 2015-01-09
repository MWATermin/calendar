package Date;

import java.io.Serializable;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

import javax.persistence.*;

import com.sun.xml.internal.txw2.annotation.XmlElement;




/*
ID (Int) //autoincrement
Datum des Termins (Calendar)
Uhrzeit (Siehe oben)
Dauer (Int minuten)
Name des Erstellers (String)
Ort (String)
Terminname (String)
Beschreibung (String)
Teilnehmende Personen (List member)
*/


@Entity(name="Date")
@XmlElement
public class Date implements Serializable {
	
	// Variablendeklaration
	private Integer id;
	private Calendar dateAndTime;
	private Integer duration;
	private Integer authorID;	// ID zum Username
	private String place;
	private String label;
	private String description;
	private ArrayList<Integer> members;
	
	// Default Konstruktor
	public Date() {
	}
	
	// Konstruktor
	public Date( Calendar dateAndTime, Integer duration, 
			Integer authorID, String place, String label, String description, ArrayList<Integer> members) {
		this.dateAndTime = dateAndTime;
		this.duration = duration;
		this.authorID = authorID;
		this.place = place;
		this.label = label;
		this.description = description;
		this.members = members;
	}
	
	public Date( Calendar dateAndTime, Integer duration, 
			 String place, String label, String description, ArrayList<Integer> members) {
		this.dateAndTime = dateAndTime;
		this.duration = duration;
		this.place = place;
		this.label = label;
		this.description = description;
		this.members = members;
	}
	
	// Getters & Setters
	@Id
	@GeneratedValue // Sorgt daf�r, das ID automatisch erzeugt wird
	@XmlElement
	public Integer getId() {
		return id;
	}
	
	public void setId( Integer id) {
		this.id = id;
	}

	@XmlElement
	public Calendar getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime( Calendar dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	@XmlElement
	public Integer getDuration() {
		return duration;
	}

	public void setDuration( Integer duration) {
		this.duration = duration;
	}

	@XmlElement
	public Integer getAuthorID() {
		return authorID;
	}

	public void setAuthorID( Integer author) {
		System.out.println("setAuthorID: " + author);
		this.authorID = author;
	}

	@XmlElement
	public String getPlace() {
		return place;
	}

	public void setPlace( String place) {
		this.place = place;
	}

	@XmlElement
	public String getLabel() {
		return label;
	}

	public void setLabel( String label) {
		this.label = label;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription( String description) {
		this.description = description;
	}

	@XmlElement
	public ArrayList<Integer> getMembers() {
		return members;
	}

	public void setMembers( ArrayList<Integer> members) {
		this.members = members;
	}
}