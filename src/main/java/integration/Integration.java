package integration;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
@Singleton
public class Integration {

    private final SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(ApplicationDate.class)
            .addAnnotatedClass(Availability.class)
            .addAnnotatedClass(Experience.class)
            .addAnnotatedClass(JobApplication.class)
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(PersonExperience.class)
            .addAnnotatedClass(PersonRole.class)
            .addAnnotatedClass(Role.class)
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    /**
     * Takes one entity object and saves it to the database.
     * @param object The object to be saved in the database.
     */
    public void createObject(Object object) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }

    /**
     * Takes several entities and saves them in the order that the arguments are given in.
     * @param objectList A list created by the params given, to be saved to the database.
     */
    public void createObject(Object... objectList) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for(Object object : objectList)
            session.save(object);
        session.getTransaction().commit();
    }

    /**
     * Takes an entity and removes it from the database.
     * @param object The object to be deleted.
     */
    public void removeObject(Object object) {
        if(object == null)  return;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }

    /**
     * Fetches the person in the database with the SSN given.
     * @param personSsn The SSN of the person to be fetched.
     * @return the person with the given SSN.
     */
    public Person getPerson(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p where p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List fakeList = query.getResultList();
        Person person = fakeList.isEmpty() ? null : (Person) fakeList.get(0);
        session.getTransaction().commit();
        return person;
    }

    /**
     * Checks if the login details are correct.
     * @param username The username of the user to be logged in.
     * @param password  The password of the user to be logged in.
     * @return a boolean indicating whether it was the correct details or not.
     * <code>True</code> for correct, <code>false</code> for incorrect.
     */
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

    /**
     * Register a person with a username and password.
     * @param person The person to be added to the database.
     * @param user The user details for the person to be added.
     * @return a boolean indicating whether the user creation was successful (<code>true</code>)
     * or not (<code>false</code>).
     */
    public boolean userRegister(Person person, User user) {
        if(getPerson(person.getSsn()) == null) {
            createObject(person, user);
            return true;
        }
        return false;
    }

    /**
     * Register a new job application.
     * @param person The applicant that us applying for a job.
     * @param experiences The previous experiences that the applicant has.
     * @param yearsOfExperiences The amount of years the applicant has in each <code>experience</code>.
     * @param availabilities The time slots where the applicant can work.
     */
    public void registerJobApplication(Person person, List<Experience> experiences, List<Double> yearsOfExperiences ,List<Availability> availabilities) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for (Availability availability : availabilities) {
            availability.getPerson().setPersonId(person.getPersonId());
            session.save(availability);
        }
        int yoei = 0;
        for(Experience experience : experiences) {
            PersonExperience personExperience = new PersonExperience(person, experience, yearsOfExperiences.get(yoei++));
            session.save(personExperience);
            session.save(experience);
        }
        session.getTransaction().commit();
    }

    /**
     * Fetch the availabilities of a applicant by their SSN.
     * @param personSsn The SSN of the applicant.
     * @return A list of the availabilities for the applicant.
     */
    public List<Availability> fetchAvailabilities(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select a from availability a, person p where a.person.personId = p.id and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List availabilityList = query.getResultList();
        session.getTransaction().commit();
        return (List<Availability>) availabilityList;
    }

    /**
     * Fetch the job applications, with date filters.
     * Available filters:
     * * <code>time period</code>
     * * <code>date of registration</code>
     * @param searchParameter The filter to choose.
     * @param fromDate The date from which you want applications.
     * @param toDate The date to which you want the applications.
     * @return A list with the available job applications.
     */
    public List<JobApplication> fetchJobApplications(String searchParameter, Date fromDate, Date toDate) {
        boolean createdQuery = false;
        Session session = factory.getCurrentSession();
        String lcParameter = searchParameter.toLowerCase();
        Query query = null;
        if("time period".equals(lcParameter)) {
            query = session.createQuery(
                    "select p.ssn, p.name, p.surname, p.email, a.fromDate, a.toDate, e.name, pe.yearsOfExperience, ad.appDate" +
                    " from person p, availability a, person_experience pe, experience e, application_date ad" +
                    " where p.id = a.person.personId" +
                    " and p.id = pe.person.personId" +
                    " and p.id = ad.person.personId" +
                    " and pe.experience.experienceId = e.id" +
                    " and a.fromDate >= :fromDate" +
                    " and a.toDate <= :toDate" +
                    " order by a.fromDate asc");
            createdQuery = true;
        } else if("date of registration".equals(lcParameter)) {
            query = session.createQuery(
                    "select p.ssn, p.name, p.surname, p.email, a.fromDate, a.toDate, e.name, pe.yearsOfExperience, ad.appDate" +
                            " from person p, availability a, person_experience pe, experience e, application_date ad" +
                            " where p.id = a.person.personId" +
                            " and p.id = pe.person.personId" +
                            " and p.id = ad.person.personId" +
                            " and pe.experience.experienceId = e.id" +
                            " and ad.appDate >= :fromDate" +
                            " and ad.appDate <= :toDate" +
                            " order by ad.appDate asc");
            createdQuery = true;
        }
        if(createdQuery) {
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setMaxResults(1000);
            session.beginTransaction();
            List list = query.getResultList();
            session.getTransaction().commit();
            return (List<JobApplication>) list;
        }
        return null;
    }

    /**
     * Fetch the job applications, with experience or applicant name filters.
     * Available filters:
     * * <code>experience</code> name.
     * * <code>name</code> of the applicant.
     * @param searchParameter The filter to choose.
     * @param searchString The name or experience to search for.
     * @return A list with the available job applications.
     */
    public List<JobApplication> fetchJobApplications(String searchParameter, String... searchString) {
        boolean createdQuery = false;
        Session session = factory.getCurrentSession();
        String lcParameter = searchParameter.toLowerCase();
        Query query = null;
        if("experience".equals(lcParameter)) {
            query = session.createQuery(
                    "select p.ssn, p.name, p.surname, p.email, a.fromDate, a.toDate, e.name, pe.yearsOfExperience, ad.appDate" +
                            " from person p, availability a, person_experience pe, experience e, application_date ad" +
                            " where p.id = a.person.personId" +
                            " and p.id = pe.person.personId" +
                            " and p.id = ad.person.personId" +
                            " and pe.experience.experienceId = e.id" +
                            " and e.name = :experience " +
                            " order by pe.yearsOfExperience desc");
            query.setParameter("experience", searchString[0]);
            query.setMaxResults(1000);
            createdQuery = true;
        } else if("name".equals(lcParameter)) {
            query = session.createQuery(
                    "select p.ssn, p.name, p.surname, p.email, a.fromDate, a.toDate, e.name, pe.yearsOfExperience, ad.appDate" +
                            " from person p, availability a, person_experience pe, experience e, application_date ad" +
                            " where p.id = a.person.personId" +
                            " and p.id = pe.person.personId" +
                            " and p.id = ad.person.personId" +
                            " and pe.experience.experienceId = e.id" +
                            " and p.name = :name" +
                            " and p.surname = :surname" +
                            " order by p.personId asc");
            query.setParameter("name", searchString[0]);
            query.setParameter("surname", searchString[1]);
            query.setMaxResults(1000);
            createdQuery = true;
        }
        if(createdQuery) {
            session.beginTransaction();
            List list = query.getResultList();
            session.getTransaction().commit();
            return (List<JobApplication>) list;
        }
        return null;
    }
}
