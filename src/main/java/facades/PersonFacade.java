/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
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
    public PersonDTO addPerson(String fName, String lName, String phone) {

        Person person = new Person(fName,lName,phone);
        EntityManager em = emf.createEntityManager();
        
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            PersonDTO personDTO = new PersonDTO(fName,lName,phone);
            em.close();
            return personDTO;
        
            
        
    
    }

    

    @Override
    public PersonDTO deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        
            em.getTransaction().begin();
            Person p1 = em.find(Person.class, id);
            
            em.remove(p1);
            em.getTransaction().commit();
            em.close();
            return new PersonDTO(p1);
    }
    
   
    

    @Override
    public PersonDTO getPerson(int id) {
     EntityManager em = emf.createEntityManager();
        try{
            Person p = em.find(Person.class,id);
            
            PersonDTO dto= new PersonDTO(p.getfName(),p.getlName(),p.getPhone());
            return dto;
        }finally {
            em.close();
        }}


    @Override
    public PersonsDTO getAllPersons() {
        
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Person> query = 
                       em.createQuery("Select p from Person p",Person.class);
            PersonsDTO dto= new PersonsDTO(query.getResultList());
            return dto;
        }finally {
            em.close();
        }}

    

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        
        EntityManager em = emf.createEntityManager();
        Person person= em.find(Person.class, p.getId());

        try{                        
            em.getTransaction().begin();
            person.setfName(p.getfName());
            person.setlName(p.getlName());
            person.setPhone(p.getPhone());
            person.setLastEdited();
            em.getTransaction().commit();
            
            
        }finally {
            em.close();
        }
        return new PersonDTO(person);

    }
    
}
