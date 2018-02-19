package integration;

import common.ErrorMessages;
import common.SystemException;
import integration.entity.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import java.util.List;

//@Transactional(value = Transactional.TxType.MANDATORY)
@Singleton
public class Integration {

    private final SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Application.class)
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
     */
    public void userRegister(Person person, User user) throws SystemException {
        if(user == null && person != null) {
            createObject(person);
        } else {
            user.setPerson(person);
            if (getUser(user.getUsername()) == null) {
                if (person == null)
                    throw new SystemException(ErrorMessages.REGISTER_USER_ERROR.name(), ErrorMessages.PERSON_MISSING.getErrorMessage());
                else {
                    if (personHasUser(person.getSsn()) == null) {
                        Person regPerson = getPerson(person.getSsn());
                        if (regPerson == null) {
                            createObject(person, user);
                        }
                        user.setPerson(regPerson);
                        createObject(user);
                    } else
                        throw new SystemException(ErrorMessages.REGISTER_USER_ERROR.name(), ErrorMessages.REGISTER_USER_ERROR.getErrorMessage());
                }
            } else
                throw new SystemException(ErrorMessages.REGISTER_USER_ERROR.name(), ErrorMessages.REGISTER_USERNAME_ERROR.getErrorMessage());
        }
    }

    /**
     * Register a new job application.
     * @param person The applicant that us applying for a job.
     * @param experiences The previous experiences that the applicant has.
     * @param yearsOfExperiences The amount of years the applicant has in each <code>experience</code>.
     * @param availabilities The time slots where the applicant can work.
     */
    public void registerJobApplication(Person person, List<Experience> experiences, List<Double> yearsOfExperiences ,List<Availability> availabilities, List<Application> applications) throws SystemException {
        userRegister(person, null);
        Person savedPerson = getPerson(person.getSsn());
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for (Availability availability : availabilities) {
            availability.setPerson(savedPerson);
            session.save(availability);
        }
        int yoei = 0;
        for(Experience experience : experiences) {
            Experience existingExperience = getExperience(experience.getName());
            if(existingExperience != null)
                experience = existingExperience;
            PersonExperience personExperience = new PersonExperience(savedPerson, experience, yearsOfExperiences.get(yoei++));
            session.save(personExperience);
        }
        for (Application application : applications) {
            application.setPerson(savedPerson);
            session.save(application);
        }
        session.getTransaction().commit();
        createObject(new PersonRole(
                person,
                getRole("applicant")
        ));
    }

    /**
     * Get all persons of a certain role, thus eg all applicants.
     * @param role The role which persons part of are returned.
     * @return All persons which has the specified role.
     */
    public List<Person> getPersonsByRole(String role) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p, role r, person_role pr where p.id = pr.person.id and r.id = pr.role.id and r.name = :role");
        query.setParameter("role", role);
        List personList = query.getResultList();
        session.getTransaction().commit();
        return (List<Person>) personList;
    }

    /**
     * Get the dates for which a person has applied for.
     * @param personSsn The SSN of the person whose application dates are returned.
     * @return The application dates registered by the given person.
     */
    public List<Application> getApplicationDates(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select ad from person p, application_date ad where ad.person.personId = p.personId and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List dateList = query.getResultList();
        session.getTransaction().commit();
        return (List<Application>) dateList;
    }

    // Private functions

    private Role getRole(String type) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select r from role r where r.name = :type");
        query.setParameter("type", type);
        List fakeList = query.getResultList();
        session.getTransaction().commit();
        return fakeList.isEmpty() ? null : (Role) fakeList.get(0);
    }

    private Experience getExperience(String experienceName) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("select e from experience e where e.name = :name");
        query.setParameter("name", experienceName);
        List fakeList = query.getResultList();
        return fakeList.isEmpty() ? null : (Experience) fakeList.get(0);
    }

    private Person getPerson(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p where p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List fakeList = query.getResultList();
        Person person = fakeList.isEmpty() ? null : (Person) fakeList.get(0);
        session.getTransaction().commit();
        return person;
    }

    private User getUser(String username) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select u from user u where u.username = :username");
        query.setParameter("username", username);
        List fakeList = query.getResultList();
        User user = fakeList.isEmpty() ? null : (User) fakeList.get(0);
        session.getTransaction().commit();
        return user;
    }

    private User personHasUser(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select u from user u, person p where u.person.personId = p.personId and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List fakeList = query.getResultList();
        User user = fakeList.isEmpty() ? null : (User) fakeList.get(0);
        session.getTransaction().commit();
        return user;
    }

    private void createObject(Object... objectList) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for(Object object : objectList) {
            if(object != null)
                session.save(object);
        }
        session.getTransaction().commit();
    }

    private void removeObject(Object object) {
        if(object == null)  return;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }
}
