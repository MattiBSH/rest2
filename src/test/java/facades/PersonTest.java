package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import entities.Person;
import utils.EMF_Creator;
import entities.RenameMe;
import exceptions.PersonNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    Person p1;
    Person p2;
    Person p3;
    
    public PersonTest() {
    }

    

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeAll
    public static void setUpClass() throws InterruptedException {

        //facade=GroupMemberFacade.getGMPFacade(emf);

        
        
    }
    
    @BeforeEach
     public void setUp() throws InterruptedException {
         
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        facade=PersonFacade.getFacade(emf);
         try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            
             p1= new Person("Matti","Hansen","60606060");
             p2 = new Person("Bob","Pelle","60606090");
             p3 = new Person("Bent","Benny","60606080");      
            Address address1=new Address("elle","2982","ballerup");
            Address address2=new Address("ertt","2982","ballerup");
            Address address3=new Address("okok","2982","ballerup");
            p1.setAddress(address1);
            p2.setAddress(address3);
            p3.setAddress(address2);
            em.persist(p1);
            
            em.persist(p2);
            em.persist(p3);
             
            em.getTransaction().commit();
        } finally {
            em.close();
        }
     }
//    
//    
//
//    
    @Test
    public void testGetByID() throws PersonNotFoundException {
        PersonDTO p =facade.getPerson(p1.getId());
        PersonDTO dto = new PersonDTO("Matti","Hansen","60606060");
        assertEquals(dto,p);
       

    }
    
    @Test
    public void testGetAll() throws PersonNotFoundException {
        PersonsDTO result = facade.getAllPersons();
        List<PersonDTO>list=result.getAll();
        assertThat(list, everyItem(hasProperty("fName")));
        assertThat(list, hasItems( // or contains or containsInAnyOrder 
                Matchers.<PersonDTO>hasProperty("fName", is("Matti")),
                Matchers.<PersonDTO>hasProperty("lName", is("Hansen")),
                Matchers.<PersonDTO>hasProperty("phone", is("60606060"))
        )
        );
    }

    @Test
    public void edited() throws PersonNotFoundException {
        
        PersonDTO person= new PersonDTO(p1);
       
        person.setPhone("999999999");
        
        PersonDTO pdto=facade.editPerson(person);
        
        assertEquals(person.getPhone(),pdto.getPhone());
    }
    
    @Test
    public void deleted() throws PersonNotFoundException {
        PersonDTO result = facade.deletePerson(p1.getId());
        PersonsDTO persons=facade.getAllPersons();
        List<PersonDTO>list=persons.getAll();
        assertEquals(2,list.size());
        
    }
    @Test
    public void add() throws PersonNotFoundException {
        
        PersonDTO result = facade.addPerson(p1.getfName(), p1.getlName(), p1.getPhone(), p1.getAddress().getStreet(), p1.getAddress().getZip(), p1.getAddress().getCity());
        PersonsDTO persons=facade.getAllPersons();
        List<PersonDTO>list=persons.getAll();
        assertEquals(4,list.size());
        
    }
    
    
    @Test
public void whenExceptionThrown_thenAssertionSucceeds() {
    Exception exception = assertThrows(PersonNotFoundException.class, () -> {
        PersonDTO p =facade.getPerson(200);
        
    });
    String expectedMessage = "No person with provided id found";
    String actualMessage = exception.getMessage();
 
    assertTrue(actualMessage.contains(expectedMessage));
}
    
    
    
}
