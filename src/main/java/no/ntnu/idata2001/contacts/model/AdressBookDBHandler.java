package no.ntnu.idata2001.contacts.model;


import org.eclipse.persistence.config.TargetServer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AdressBookDBHandler implements AddressBook {

    EntityManagerFactory emf;
    protected EntityManager em;





    public AdressBookDBHandler() {
        try{
            emf = Persistence.createEntityManagerFactory("contacts-pu");
            em = emf.createEntityManager();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void addContact(ContactDetails contact) {
        try{
            em.getTransaction().begin();
            em.persist(contact);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeContact(String phoneNumber) {
        Query q = this.em.createQuery("SELECT i FROM ContactDetails i WHERE i.phone = :Phone");
        q.setParameter("Phone", phoneNumber);
        ContactDetails cd = (ContactDetails) q.getResultList().get(0);
        this.em.getTransaction().begin();
        this.em.persist(cd);
        this.em.remove(cd);
        this.em.getTransaction().commit();
    }

    @Override
    public Collection<ContactDetails> getAllContacts() {
        Query q = em.createQuery("SELECT '*' FROM ContactDetails");
        return q.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }



    @Override
    public Iterator<ContactDetails> iterator() {
        return null;
    }
}
