package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import exceptions.PersonNotFoundException;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PersonFacade FACADE =  PersonFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        


    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String isUp() {
        
        
        
        return "yes";
        
    }
    
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String id(@PathParam("id")int id) throws PersonNotFoundException {
        
        PersonDTO dto =FACADE.getPerson(id);
        
        return GSON.toJson(dto);
        
    }
    @Path("all")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getPersons() throws PersonNotFoundException {
        
       PersonsDTO dto =FACADE.getAllPersons();
       
        return GSON.toJson(dto);
        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPerson(String person) {
        
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);        
        PersonDTO p= FACADE.addPerson(personDTO.getfName(),personDTO.getlName(),personDTO.getPhone(), personDTO.getStreet(),personDTO.getZip() , personDTO.getCity());        
        return Response.ok(p).build();
        
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteById(@PathParam("id")int id) throws PersonNotFoundException{
        PersonDTO dto=FACADE.deletePerson(id);
        return GSON.toJson(dto);
    }
    
    
    @PUT
    @Path("update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateById(@PathParam("id")int id, String person) throws PersonNotFoundException{
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        pDTO.setId(id);
        PersonDTO p=FACADE.editPerson(pDTO);
        return GSON.toJson(p);
        
    }
}
