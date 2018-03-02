package integration;

import common.*;
import integration.entity.*;
import integration.entity.User;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static common.Util.capitalize;
import static common.Util.checkDate;
import static common.Util.checkPerson;

@Transactional(value = Transactional.TxType.MANDATORY)
@Singleton
public class Integration {

    private static final Logger LOG = Logger.getLogger(Integration.class.getName());

    private static final SessionFactory factory = new Configuration()
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
     * @return the role the user has.
     * @throws SystemException if the wrong login details are given.
     */
    public RoleDTO login(String username, String password) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            Query query = session.createQuery("select u from user u where u.username = :username and u.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            List fakeList = query.getResultList();
            if (!fakeList.isEmpty()) {
                Person person = ((User) fakeList.get(0)).getPerson();
                Role role = person == null ? getRole("applicant") : person.getRole();
                return new RoleDTO(role);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new SystemException(Messages.LOGIN_ERROR.name(), Messages.LOGIN_ERROR.getErrorMessage());
        }
        throw new SystemException(Messages.LOGIN_ERROR.name(), Messages.LOGIN_ERROR.getErrorMessage());
    }

    /**
     * Register a user with a username and password.
     * @param user The user details for the user to be added.
     * @throws SystemException if there was an error during user registration.
     */
    public void registerUser(UserDTO user) throws SystemException {
         try {
            registerUser(new User(
                    user.getUsername(),
                    user.getPassword()
            ));
        } catch (SystemException se) {
            LOG.log(Level.SEVERE, se.toString(), se);
            throw se;
        } catch (Exception e) {
             LOG.log(Level.SEVERE, e.toString(), e);
             throw new SystemException(Messages.SAVE_TO_DB_FAILED.name(), e.getMessage());
         }
    }

    /**
     * Register a new job application if user is logged in.
     * @param personDTO The applicant that is applying for a job.
     * @param userDTO The user that the person has.
     * @param experienceDTOs The previous experiences that the applicant has.
     * @param availabilityDTOs The time slots where the applicant can work.
     * @param applicationDTO The details concerning this application, e.g. registration date.
     * @throws SystemException in case of an error during registration.
     */
    public void registerJobApplication(PersonDTO personDTO, UserDTO userDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            User user = getUser(userDTO.getUsername());
            if(user == null || !user.getPassword().equals(userDTO.getPassword()))
                throw new SystemException(Messages.USER_NOT_LOGGED_IN.name(), Messages.USER_NOT_LOGGED_IN.getErrorMessage());
            if(personHasUser(personDTO.getSsn()) != null)
                throw new SystemException(Messages.REGISTER_USER_ERROR.name(), Messages.REGISTER_USER_ERROR.getErrorMessage());
            session.evict(user);
            Person person = getAvailablePerson(personDTO, session);
            List<PersonExperience> personExperiences = new ArrayList<>();
            List<Availability> availabilities = new ArrayList<>();
            dtoIntoEntity(person, experienceDTOs, personExperiences, availabilityDTOs, applicationDTO, availabilities);
            person.setName(personDTO.getName());
            person.setSurname(personDTO.getSurname());
            user.setPerson(person);
            session.merge(user);

        } catch (SystemException se) {
            LOG.log(Level.SEVERE, se.toString(), se);
            throw se;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SAVE_TO_DB_FAILED.name(), e.getMessage());
        }
    }

    /**
     * Register a new job application, made for the REST endpoints where a user isn't necessary.
     * @param personDTO The applicant that is applying for a job.
     * @param experienceDTOs The previous experiences that the applicant has.
     * @param availabilityDTOs The time slots where the applicant can work.
     * @param applicationDTO The details concerning this application, e.g. registration date.
     * @throws SystemException in case of an error during registration.
     */
    public void registerRESTJobApplication(PersonDTO personDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            Person person = getAvailablePerson(personDTO, session);
            List<PersonExperience> personExperiences = new ArrayList<>();
            List<Availability> availabilities = new ArrayList<>();
            dtoIntoEntity(person, experienceDTOs, personExperiences, availabilityDTOs, applicationDTO, availabilities);
            person.setName(personDTO.getName());
            person.setSurname(personDTO.getSurname());
            session.merge(person);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SAVE_TO_DB_FAILED.name(), e.getMessage());
        }
    }

    /**
     * Get all persons of a certain role, thus eg all applicants.
     * @param role The role which persons part of are returned.
     * @return All persons which has the specified role.
     * @throws SystemException in case of an error during fetch of all persons.
     */
    @SuppressWarnings("unchecked")
    public List<Person> getPersonsByRole(String role) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            Query query = session.createQuery("select p from person p where p.role.name = :role");
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            SystemException exception = new SystemException(Messages.SAVE_TO_DB_FAILED.name(), e.getMessage());
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }

    /**
     * Accept or decline a job application.
     * @param applicationDTO A DTO encapsulating the job application to be changed and has the <code>accepted</code> value changed to the new value.
     * @throws SystemException in case of an error during update of the application status.
     */
    public void acceptOrDeclineJobApplication(ApplicationDTO applicationDTO) throws SystemException {
        try {
            Boolean accepted = null;
            Session session = factory.getCurrentSession();
            Query query = session.createQuery("update application a set a.accepted = :status where a.applicationId = :id");
            if (applicationDTO.getAccepted() != null)
                accepted = applicationDTO.getAccepted().toLowerCase().equals("accepted");
            query.setParameter("status", accepted);
            query.setParameter("id", applicationDTO.getApplicationId());
            query.executeUpdate();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SAVE_TO_DB_FAILED.name(), e.getMessage());
        }
    }

    /**
     * Fetches the hibernate factory for the program.
     * @return the hibernate factory.
     */
    public static SessionFactory getFactory() {
        return factory;
    }

    // Private functions

    private void dtoIntoEntity(Person person, List<ExperienceDTO> experienceDTOs, List<PersonExperience> personExperiences,
                                      List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO, List<Availability> availabilities) throws SystemException {
        try {
            Boolean skip = false;
            for (ExperienceDTO experienceDTO : experienceDTOs) {
                Experience experience;
                String experienceName = capitalize(experienceDTO.getName());
                Experience existingExperience = getExperience(experienceName);
                if (existingExperience != null) {
                    for (PersonExperience personExperience : person.getPersonExperiences()) {
                        if (personExperience.getExperience().getName_sv().equals(experienceName)) {
                            skip = true;
                            break;
                        }
                    }
                    if (skip) {
                        skip = false;
                        continue;
                    }
                    experience = existingExperience;
                } else
                    experience = new Experience(experienceName, "English for " + experienceName);
                personExperiences.add(
                        new PersonExperience(
                                person,
                                experience,
                                experienceDTO.getYearsOfExperience()
                        )
                );
            }
            person.setPersonExperiences(personExperiences);

            for (AvailabilityDTO availabilityDTO : availabilityDTOs) {
                Availability newAvailability;
                if(availabilityDTO.getToDate() == null) {
                    newAvailability = new Availability(
                            person,
                            Date.valueOf(checkDate(availabilityDTO.getFromDate())),
                            null
                    );
                } else {
                    newAvailability = new Availability(
                            person,
                            Date.valueOf(checkDate(availabilityDTO.getFromDate())),
                            Date.valueOf(checkDate(availabilityDTO.getToDate()))
                    );
                }
                if (person.getAvailabilities() != null) {
                    for (Availability availability : person.getAvailabilities()) {
                        if(availability.getToDate() == null) {
                            if (availability.getFromDate().equals(newAvailability.getFromDate()) && newAvailability.getToDate() == null) {
                                skip = true;
                                break;
                            }
                        } else {
                            if (availability.getFromDate().equals(newAvailability.getFromDate()) && availability.getToDate().equals(newAvailability.getToDate())) {
                                skip = true;
                                break;
                            }
                        }
                    }
                    if (skip) {
                        skip = false;
                        continue;
                    }
                }
                availabilities.add(newAvailability);
            }
            person.setAvailabilities(availabilities);

            if (person.getApplication() != null) {
                Application application = person.getApplication();
                application.setAppDate(Date.valueOf(checkDate(applicationDTO.getDate())));
                person.setApplication(application);
            } else {
                person.setApplication(new Application(
                        person,
                        Date.valueOf(checkDate(applicationDTO.getDate()))
                ));
            }
        } catch (Exception e) {
            SystemException exception = new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }

    private Person getAvailablePerson(PersonDTO personDTO, Session session) throws SystemException {
        try {
            personDTO = checkPerson(personDTO);
            Person person;
            Person oldPerson = getPerson(personDTO);
            if (oldPerson != null) {
                session.evict(oldPerson);
                person = oldPerson;
            } else {
                person = new Person(personDTO);
                Role role = getRole(personDTO.getRole());
                person.setRole(role);
            }

            return person;
        } catch (Exception e) {
            SystemException exception = new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }

    private void registerUser(User user) throws SystemException {
            if(user == null) throw new SystemException(
                        Messages.REGISTER_USER_ERROR.name(),
                        Messages.REGISTER_NO_USER_ERROR.getErrorMessage()
                );
            if(getUser(user.getUsername()) != null) throw new SystemException(
                        Messages.REGISTER_USER_ERROR.name(),
                        Messages.REGISTER_USERNAME_ERROR.getErrorMessage()
                );
            createObject(user);
    }

    private Role getRole(String type) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("select r from role r where r.name = :type");
        query.setParameter("type", type);
        List fakeList = query.getResultList();
        return fakeList.isEmpty() ? null : (Role) fakeList.get(0);
    }

    private Experience getExperience(String experienceName) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("select e from experience e where e.name_sv = :name");
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
        Query query = session.createQuery("select u from user u, person p where u.person.personId = p.personId and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List fakeList = query.getResultList();
        return fakeList.isEmpty() ? null : (User) fakeList.get(0);
    }

    private void createObject(Object... objectList) throws SystemException {
        try {
            Session session = factory.getCurrentSession();
            for (Object object : objectList) {
                if (object != null)
                    session.save(object);
            }
            session.flush();
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
            session.delete(object);
            session.flush();
        } catch (Exception versionMismatch) {
            SystemException exception = new SystemException(Messages.SAVE_TO_DB_FAILED.name(), versionMismatch.toString());
            LOG.log(Level.SEVERE, exception.toString(), exception);
            throw exception;
        }
    }
}
