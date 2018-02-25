package integration;

import common.*;
import integration.entity.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import java.sql.Date;
import java.util.ArrayList;
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
    public RoleDTO login(String username, String password) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select u from user u where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List fakeList = query.getResultList();
        if(!fakeList.isEmpty()) {
            Person person = ((User) fakeList.get(0)).getPerson();
            Role role = person == null ? getRole("applicant") : person.getRole();
            return new RoleDTO(role);
        }
        session.getTransaction().commit();
        return null;
    }

    /**
     * Register a user with a username and password.
     * @param user The user details for the user to be added.
     */
    public void registerUser(UserDTO user) throws SystemException {
        registerUser(new User(
                user.getUsername(),
                user.getPassword()
        ));
    }
    
    /**
     * Register a new job application if user is logged in.
     * @param personDTO The applicant that is applying for a job.
     * @param userDTO The user that the person has.
     * @param experienceDTOs The previous experiences that the applicant has.
     * @param availabilityDTOs The time slots where the applicant can work.
     */
    public void registerJobApplication(PersonDTO personDTO, UserDTO userDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            User user = getUser(userDTO.getUsername());
            if(user == null || !user.getPassword().equals(userDTO.getPassword()))
                throw new SystemException(Messages.USER_NOT_LOGGED_IN.name(), Messages.USER_NOT_LOGGED_IN.getErrorMessage());
            session.evict(user);
            Person person;
            Person oldPerson = getPerson(personDTO);
            if(oldPerson != null) {
                session.evict(oldPerson);
                person = oldPerson;
            } else
                person = new Person(personDTO);
            List<PersonExperience> personExperiences = new ArrayList<>();
            for(ExperienceDTO experienceDTO : experienceDTOs) {
                Experience experience;
                Experience existingExperience = getExperience(experienceDTO.getName());
                if(existingExperience != null)
                    experience = existingExperience;
                else
                    experience = new Experience(experienceDTO.getName());
                personExperiences.add(
                        new PersonExperience(
                                person,
                                experience,
                                experienceDTO.getYearsOfExperience()
                        )
                );
            }
            List<Availability> availabilities = new ArrayList<>();
            for(AvailabilityDTO availabilityDTO : availabilityDTOs) {
                availabilities.add(new Availability(
                        person,
                        Date.valueOf(availabilityDTO.getFromDate()),
                        Date.valueOf(availabilityDTO.getToDate())
                ));
            }
            Application application = new Application(
                    person,
                    Date.valueOf(applicationDTO.getDate())
            );
            person.setPersonExperiences(personExperiences);
            person.setAvailabilities(availabilities);
            person.setApplication(application);
            user.setPerson(person);
            session.merge(user);
            session.getTransaction().commit();

        } catch (SystemException exception) {
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        } catch (Exception exception) {
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw new SystemException(Messages.SAVE_TO_DB_FAILED.name(), Messages.SAVE_TO_DB_FAILED.getErrorMessage());
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
        Query query = session.createQuery("select p from person p where p.role.name = :role");
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
        query.setParameter("id", "FIXA DET HÄR ------------------------------------------------------------------------------------------------------------------------------------------------------");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    // Private functions

    private void registerUser(User user) throws SystemException {
        try {
            if(user == null) throw new SystemException(
                        Messages.REGISTER_USER_ERROR.name(),
                        Messages.REGISTER_NO_USER_ERROR.getErrorMessage()
                );
            if(getUser(user.getUsername()) != null) throw new SystemException(
                        Messages.REGISTER_USER_ERROR.name(),
                        Messages.REGISTER_USERNAME_ERROR.getErrorMessage()
                );
            createObject(user);
        } catch (SystemException exception) {
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }

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

    private Person getPerson(PersonDTO personDTO) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("select p from person p where p.ssn = :ssn");
        query.setParameter("ssn", personDTO.getSsn());
        List fakeList = query.getResultList();
        return fakeList.isEmpty() ? null : (Person) fakeList.get(0);
    }

    private User getUser(String username) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("select u from user u where u.username = :username");
        query.setParameter("username", username);
        List fakeList = query.getResultList();
        return fakeList.isEmpty() ? null : (User) fakeList.get(0);
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
