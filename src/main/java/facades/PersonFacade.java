/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import entities.Person;
import exceptions.PersonNotFoundException;
import facades.IPersonFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author matti
 */
public class PersonFacade implements IPersonFacade{
private static PersonFacade instance;
    private static EntityManagerFactory emf;
    private static EntityManager em;
 
    public static PersonFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }
    @Override
    public PersonDTO addPerson(String fName, String lName, String phone, String street, String zip, String city) {

        Person person = new Person(fName,lName,phone);
        Address a = new Address(street,zip,city);
        person.setAddress(a);
        EntityManager em = emf.createEntityManager();
        
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            PersonDTO personDTO = new PersonDTO(person);
            em.close();
            return personDTO;
        
            
        
    
    }

    

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException{
        EntityManager em = emf.createEntityManager();
        Person p1 = em.find(Person.class, id);
        if(p1!=null){
            em.getTransaction().begin();
            
            em.remove(p1);
            em.remove(p1.getAddress());
            em.getTransaction().commit();
        }else{
            throw new PersonNotFoundException("{\"message\": \"Could not delete, provided id does not exist\"}");
        }
            
            em.close();
            return new PersonDTO(p1);
    }
    
   
    

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException{
     EntityManager em = emf.createEntityManager();
        try{
            Person p = em.find(Person.class,id);
            if(p!=null){
                PersonDTO dto= new PersonDTO(p.getfName(),p.getlName(),p.getPhone());
            return dto;
            }else{
                throw new PersonNotFoundException("{\"message\": \"No person with provided id found\"}");
            }
            
        }finally {
            em.close();
        }
}


    @Override
    public PersonsDTO getAllPersons() throws PersonNotFoundException{
        
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Person> query = 
                       em.createQuery("Select p from Person p",Person.class);
            if(query==null){
                throw new PersonNotFoundException("{\"message\": \"No one in here mate\"}");
            }else{
               PersonsDTO dto= new PersonsDTO(query.getResultList());
            return dto; 
            }
            
        }finally {
            em.close();
        }}

    

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        
        EntityManager em = emf.createEntityManager();
        Person person= em.find(Person.class, p.getId());

        try{                        
            if(person!=null){
               em.getTransaction().begin();
            person.setfName(p.getfName());
            person.setlName(p.getlName());
            person.setPhone(p.getPhone());
            person.setLastEdited();
            person.getAddress().setStreet(p.getStreet());
            person.getAddress().setZip(p.getZip());
            person.getAddress().setCity(p.getCity());
            em.getTransaction().commit(); 
            }else{
                throw new PersonNotFoundException("{\"message\": \"No person with provided id found so we could not edit him\"}");
            }
            
            
            
        }finally {
            em.close();
        }
        return new PersonDTO(person);

    }
    
}
