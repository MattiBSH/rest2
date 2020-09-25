package rest;

import dtos.PersonDTO;
import entities.Address;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonRestTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1,p2;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        
        
        
    }
//    
    @AfterAll
    public static void closeTestServer(){
        
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
       
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
             p1 = new Person("Matti", "Hansen", "60606060");
            p2= new Person("Bob", "Hansen", "70606060");
            
            Address address=new Address("elle","2982","ballerup");
            p1.setAddress(address);
            Address address2=new Address("olle","2982","københavn");
            p2.setAddress(address);
            em.persist(p1);
            em.persist(p2);
            
            
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
   
   @Test
    public void testApiAll() throws Exception {
        List<PersonDTO>personsdtos;

        personsdtos=given()
        .contentType("application/json")
        .when()
        .get("/person/all")
        .then()
        .extract().body().jsonPath().getList("all",PersonDTO.class);
        
        PersonDTO p1DTO= new PersonDTO(p1);
        PersonDTO p2DTO= new PersonDTO(p2);
        System.out.println(personsdtos);
        assertThat(personsdtos, containsInAnyOrder(p1DTO, p2DTO));
        System.out.println(containsInAnyOrder(p1DTO, p2DTO));
    }
    
    @Test
    public void addPerson() throws Exception {
       given()
        .contentType("application/json")
        .body(new PersonDTO("Matti","Hansen","60606060"))
        .when()
        .post("person")
        .then()
        .body("fName", equalTo("Matti"))
        .body("lName", equalTo("Hansen"))
        .body("id",notNullValue());
  
    }
    
    
}
