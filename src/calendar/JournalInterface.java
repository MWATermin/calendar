package calendar;

import java.util.ArrayList;

public interface JournalInterface {
	
	public void addJournalEntry( String description, String information, Integer userID);
	
	public ArrayList<Journal> getJournalList();

}
