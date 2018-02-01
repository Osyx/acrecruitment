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
        Query query = session.createQuery("from availability a, person p where a.person_id = p.id and p.ssn = :ssn", Availability.class);
        query.setParameter("ssn", personSsn);
        List availabilityList = query.getResultList();
        return (List<Availability>) availabilityList;
    }

    public Serializable createAvailability(Availability availability) {
        Session session = factory.getCurrentSession();
        return session.save(availability);
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
