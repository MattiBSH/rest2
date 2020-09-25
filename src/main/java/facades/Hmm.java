/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Address;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;


public class Hmm {
       private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

       private static final PersonFacade FACADE =  PersonFacade.getFacade(EMF);

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        //facade=GroupMemberFacade.getGMPFacade(emf);

        
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            em.createQuery("DELETE from Address").executeUpdate();
            Person p = new Person("Matti","Hansen","60606060");
            Address address=new Address("elle","2982","ballerup");
            p.setAddress(address);
            em.persist(p);
             
            em.getTransaction().commit();
        } finally {
            em.close();
        }
}
}
