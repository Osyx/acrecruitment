package integration;

import common.ApplicationDTO;
import common.Messages;
import common.SystemException;
import integration.entity.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//@Transactional(value = Transactional.TxType.MANDATORY)
@Singleton
public class Integration {

    private static final Logger LOG = Logger.getLogger(Integration.class.getName());

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
     * Register a person (recruit) with a username and password.
     * @param person The person to be added to the database.
     * @param user The user details for the person to be added.
     */
    public void registerUser(Person person, User user) throws SystemException {
        try {
            if (user != null && person != null) {
                user.setPerson(person);
                if (getUser(user.getUsername()) == null) {
                    if (personHasUser(person.getSsn()) == null) {
                        Person regPerson = getPerson(person.getSsn());
                        if (regPerson == null) {
                            createObject(person, user);
                        }
                        user.setPerson(regPerson);
                        createObject(user);
                        createObject(new PersonRole(
                                person,
                                getRole("recruit")
                        ));
                    } else
                        throw new SystemException(Messages.REGISTER_USER_ERROR.name(), Messages.REGISTER_USER_ERROR.getErrorMessage());
                } else
                    throw new SystemException(Messages.REGISTER_USER_ERROR.name(), Messages.REGISTER_USERNAME_ERROR.getErrorMessage());
            } else if(person != null) {
                createObject(person);
            } else if (person == null)
                throw new SystemException(Messages.REGISTER_USER_ERROR.name(), Messages.PERSON_MISSING.getErrorMessage());

        } catch (SystemException exception) {
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
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
        try {
            registerUser(person, null);
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
        } catch (SystemException exception) {
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
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
     * Accept or decline a job application.
     * @param applicationDTO A DTO encapsulating the job application to be changed and has the <code>accepted</code> value changed to the new value.
     */
    public void acceptOrDeclineJobApplication(ApplicationDTO applicationDTO) {
        Boolean accepted = null;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update application a set a.accepted = :status where a.applicationId = :id");
        if(applicationDTO.getAccepted() != null)
            accepted = applicationDTO.getAccepted().equals("Accepted");
        query.setParameter("status", accepted);
        query.setParameter("id", applicationDTO.getApplicationNr());
        query.executeUpdate();
        session.getTransaction().commit();
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

    private void createObject(Object... objectList) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            for (Object object : objectList) {
                if (object != null)
                    session.save(object);
            }
            session.flush();
            session.getTransaction().commit();
        } catch (Exception versionMismatch) {
            SystemException exception = new SystemException(Messages.SAVE_TO_DB_FAILED.name(), versionMismatch.toString());
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }

    private void removeObject(Object object) throws SystemException {
        try {
            if(object == null)  return;
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception versionMismatch) {
            SystemException exception = new SystemException(Messages.SAVE_TO_DB_FAILED.name(), versionMismatch.toString());
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }
}
