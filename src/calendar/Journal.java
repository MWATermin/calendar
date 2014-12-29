package calendar;


import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Journal {
	
	// Variablendeklaration	
	private Integer id;					// ID des Journal Eintrags
	private Calendar JournalTimestamp;	// Zeitstempel des Journal Eintrags
	private String JournalDescription;	// Beschreibung des Journal Eintrags
	private String JournalInformation; 	// Informationen zum Journal Eintrag
	private Integer JournaluserID;		// UserID zum User der Journal Eintrag verursacht
	

	// Default Konstruktor
	public Journal() {
	}
	
	public Journal( String description, String information, Integer userID) {
		this.JournalTimestamp = Calendar.getInstance();
		this.JournalDescription = description;
		this.JournalInformation = information;
		this.JournaluserID = userID;
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
	
	public Calendar getJournalTimestamp() {
		return JournalTimestamp;
	}

	public void setJournalTimestamp(Calendar journalTimestamp) {
		JournalTimestamp = journalTimestamp;
	}

	public String getJournalDescription() {
		return JournalDescription;
	}

	public void setJournalDescription(String journalDescription) {
		JournalDescription = journalDescription;
	}

	public String getJournalInformation() {
		return JournalInformation;
	}

	public void setJournalInformation(String journalInformation) {
		JournalInformation = journalInformation;
	}

	public Integer getJournaluserID() {
		return JournaluserID;
	}

	public void setJournaluserID(Integer journaluserID) {
		JournaluserID = journaluserID;
	}
	
} // Klasse zu
