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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<Availability> fetchAvailabilities(String personSsn) {
        Session session = factory.getCurrentSession();
        //session.beginTransaction();
        Query query = session.createQuery("select a from availability a, person p where a.person_id = p.id and p.ssn = :ssn", Availability.class);
        query.setParameter("ssn", personSsn);
        List availabilityList = query.getResultList();
        //session.getTransaction().commit();
        return (List<Availability>) availabilityList;
    }

    public Serializable createObject(Object object) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Serializable ser = session.save(object);
        session.getTransaction().commit();
        return ser;
    }

    public void removeAll() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from person").executeUpdate();
        session.createQuery("delete from availability").executeUpdate();
        session.getTransaction().commit();
    }

    public static Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }


}
