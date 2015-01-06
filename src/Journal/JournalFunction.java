package Journal;

import java.util.ArrayList;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.SecurityDomain;

import calendar.Roles;

@Stateless(name = "JournalFunction", mappedName = "JournalFunction")
@SecurityDomain("CalSecurity")
@DeclareRoles( { Roles.ADMIN, Roles.STUDENT, Roles.GUEST})
public class JournalFunction implements JournalLocalInterface {
	
	@PersistenceContext(unitName = "calenderPersistenceUnit")
	private EntityManager em;
	
	public JournalFunction() {
		//
	}
	
	@PermitAll
	@Override
	public void addJournalEntry( String description, String information, Integer userID) {
		Journal j = new Journal( description, information, userID);
		em.persist( j);
		return;
	}
	
	@PermitAll
	@Override
	public ArrayList<Journal> getJournalList() {
		ArrayList<Journal> journalList;
		
		journalList = (ArrayList<Journal>) em.createQuery("FROM Journal ORDER BY id DESC").getResultList();
		
		return journalList;
	}

}
