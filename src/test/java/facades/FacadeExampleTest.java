package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
import utils.EMF_Creator;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public FacadeExampleTest() {
    }

    

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeAll
    public static void setUpClass() throws InterruptedException {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        facade=PersonFacade.getFacade(emf);
        //facade=GroupMemberFacade.getGMPFacade(emf);

        
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            Person dto = new Person("Matti","Hansen","60606060");

            em.persist(dto);
             
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    

    
    @Test
    public void testGetByID() {
        PersonDTO p =facade.getPerson(1);
        PersonDTO dto = new PersonDTO("Matti","Hansen","60606060");
        assertEquals(dto,p);
       

    }
    @Test
    public void testGetAll() {
        PersonsDTO p1=facade.getAllPersons();
        
        assertTrue(p1!=null);
       

    }
    
    
}
