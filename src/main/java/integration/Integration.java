package integration;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
@Singleton
public class Integration {

    private SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Availability.class)
            .addAnnotatedClass(Experience.class)
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(PersonExperience.class)
            .addAnnotatedClass(PersonRole.class)
            .addAnnotatedClass(Role.class)
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    public boolean login(String username, String password) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select u from user u where u.username = :username and u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.getSingleResult();
        session.getTransaction().commit();
        return !(user == null);
    }

    public Serializable createObject(Object object) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Serializable ser = session.save(object);
        session.getTransaction().commit();
        return ser;
    }

    public List<Serializable> createObject(Object... objectList) {
        List<Serializable> ser = new ArrayList<Serializable>();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for(Object object : objectList)
            ser.add(session.save(object));
        session.getTransaction().commit();
        return ser;
    }

    public Person getPerson(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p where p.ssn = :ssn", Person.class);
        query.setParameter("ssn", personSsn);
        Person person = (Person) query.getSingleResult();
        session.getTransaction().commit();
        return person;
    }

    public void removeObject(Object object) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }

    public List<Availability> fetchAvailabilities(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select a from availability a, person p where a.person_id = p.id and p.ssn = :ssn", Availability.class);
        query.setParameter("ssn", personSsn);
        List availabilityList = query.getResultList();
        session.getTransaction().commit();
        return (List<Availability>) availabilityList;
    }

    public boolean userRegister(Person person, User user) {
        if(getPerson(person.getSsn()) != null) {
            createObject(person, user);
            return true;
        }
        return false;
    }


}
