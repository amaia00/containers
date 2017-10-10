package fr.univlyon1.tiw.tiw1.calendar.dao;

import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class JPACalendarDAOTest {

    @Test @Ignore // Pour ne pas faire échouer les tests si JPA n'est pas bien mis en place
    public void emSetupTest() {
        EntityManager em = Persistence.createEntityManagerFactory("pu-test").createEntityManager();
        em.close();
    }
}
