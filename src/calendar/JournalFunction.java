package calendar;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class JournalFunction implements JournalLocalInterface {
	
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	
	public JournalFunction() {
		//
	}
	
	public void addJournalEntry( String description, String information, Integer userID) {
		Journal j = new Journal( description, information, userID);
		em.persist( j);
		return;
	}
	
	public ArrayList<Journal> getJournalList() {
		ArrayList<Journal> journalList;
		
		journalList = (ArrayList<Journal>) em.createQuery("FROM Journal ORDER BY id DESC").getResultList();
		
		return journalList;
	}

}
