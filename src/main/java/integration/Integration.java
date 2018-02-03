package integration;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.Date;
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
        Query query = session.createQuery("select u from user u where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        boolean correctLogin = !query.getResultList().isEmpty();
        session.getTransaction().commit();
        return correctLogin;
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
        Query query = session.createQuery("select p from person p where p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        session.beginTransaction();
        List fakeList = query.getResultList();
        Person person = fakeList.isEmpty() ? null : (Person) fakeList.get(0);
        session.getTransaction().commit();
        return person;
    }

    public void removeObject(Object object) {
        if(object == null)
            return;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }

    public List<Availability> fetchAvailabilities(String personSsn) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("select a from availability a, person p where a.person_id = p.id and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        session.beginTransaction();
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

    public void registerJobApplication(Person person, List<Experience> experiences, List<Double> yearsOfExperiences ,List<Availability> availabilities) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for (Availability availability : availabilities) {
            availability.setPersonId(person.getPersonId());
            session.save(availability);
        }
        int yoei = 0;
        for(Experience experience : experiences) {
            PersonExperience personExperience = new PersonExperience(person.getPersonId(), experience.getExperienceId(), yearsOfExperiences.get(yoei++));
            session.save(personExperience);
            session.save(experience);
        }
        session.getTransaction().commit();
    }

    public List<Object> fetchJobApplications(String searchParameter, Date fromDate, Date toDate) {
        Session session = factory.getCurrentSession();
        String lcParameter = searchParameter.toLowerCase();
        Query query;
        if("time period".equals(lcParameter)) {
            query = session.createQuery(
                    "select p from person p, availability a, person_experience pe, experience e" +
                    " where p.id = a.person_id" +
                    " and p.id = pe.personId" +
                    " and pe.experienceId = e.id" +
                    " and a.from_date >= :fromDate" +
                    " and a.to_date <= :toDate" +
                    " order by a.from_date asc");
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setMaxResults(1000);
        } else if("date of registration".equals(lcParameter)) {
            query = session.createQuery(
                    "select p from person p, availability a, person_experience pe, experience e" +
                            " where p.id = a.person_id" +
                            " and p.id = pe.personId" +
                            " and pe.experienceId = e.id" +
                            " and a.from_date >= :fromDate" +
                            " and a.to_date <= :toDate" +
                            " order by a.from_date asc");
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setMaxResults(1000);
        } else if("competence".equals(lcParameter)) {

        } else if("name".equals(lcParameter)) {

        }

    }


}
