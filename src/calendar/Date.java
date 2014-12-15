package calendar;

import java.io.*;
import java.util.*;
import javax.persistence.*;

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


@Entity
public class Date implements Serializable {
	
	// Variablendeklaration
	private Integer id;
	private Calendar dateAndTime;
	private Integer duration;
	private String author;
	private String place;
	private String label;
	private String description;
	private ArrayList<String> members;
	
	// Default Konstruktor
	public Date() {
		super();
	}
	
	// Konstruktor
	public Date( Calendar dateAndTime, Integer duration, 
			String author, String place, String label, String description, ArrayList<String> members) {
		super();
		this.dateAndTime = dateAndTime;
		this.duration = duration;
		this.author = author;
		this.place = place;
		this.label = label;
		this.description = description;
		this.members = members;
	}
	
	// Getters & Setters
	@Id
	@GeneratedValue // Sorgt daf�r, das ID automatisch erzeugt wird
	public Integer getId() {
		return id;
	}
	
	public void setId( Integer id) {
		this.id = id;
	}
	
	public Calendar getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime( Calendar dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration( Integer duration) {
		this.duration = duration;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor( String author) {
		this.author = author;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace( String place) {
		this.place = place;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel( String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description) {
		this.description = description;
	}
	
	public ArrayList<String> getMembers() {
		return members;
	}

	public void setMembers( ArrayList<String> members) {
		this.members = members;
	}
}