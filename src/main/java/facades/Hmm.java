/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Address;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;


public class Hmm {
       private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

       private static final PersonFacade FACADE =  PersonFacade.getFacade(EMF);

    public static void main(String[] args) {
        Address a = new Address("elle","1212","ballerup");
        
    }
}
