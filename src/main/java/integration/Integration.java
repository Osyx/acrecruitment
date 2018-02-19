package integration;

import common.JobApplicationDTO;
import common.PersonDTO;
import common.PersonPublicDTO;
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
     * Fetches the person in the database with the SSN given.
     * @param personId The ID of the person to be fetched.
     * @return the person with the given ID.
     */
    public PersonDTO getPersonById(long personId) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p where p.personId = :id");
        query.setParameter("id", personId);
        List fakeList = query.getResultList();
        Person person = fakeList.isEmpty() ? null : (Person) fakeList.get(0);
        session.getTransaction().commit();
        return new PersonDTO(person);
    }

    /**
     * Fetches a list of all persons in the db.
     * @return A list containing information about the persons, but not their ssn.
     */
    public List<PersonPublicDTO> getPersons() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p");
        List persons = query.getResultList();
        session.getTransaction().commit();
        List<PersonPublicDTO> response = new ArrayList<PersonPublicDTO>();
        if(persons != null) {
            for(Object person : persons)
                response.add(new PersonPublicDTO((Person) person));
        }
        return response;
    }

    /**
     * Updates information about a person.
     * @param personDTO The DTO object encapsulating the JSON info given, which will be the new info.
     */
    public void updatePerson(PersonDTO personDTO) {
        Person person = getPerson(personDTO.getSsn());
        if(!personDTO.getEmail().trim().isEmpty())
            person.setEmail(personDTO.getEmail());
        if(!personDTO.getName().trim().isEmpty())
            person.setName(personDTO.getName());
        if(!personDTO.getSurname().trim().isEmpty())
            person.setSurname(personDTO.getSurname());
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.update(person);
        session.getTransaction().commit();
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
        Query query = session.createQuery("select a from availability a, person p " + "where a.person.personId = p.id and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List availabilityList = query.getResultList();
        session.getTransaction().commit();
        return (List<Availability>) availabilityList;
    }

    public List<Person> getPersonsByRole(String role) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select p from person p, role r where r.personId = p.personId and r.name = :role");
        query.setParameter("role", role);
        List personList = query.getResultList();
        session.getTransaction().commit();
        return (List<Person>) personList;
    }

    public List<ApplicationDate> getApplicationDates(String personSsn) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select ad from person p, application_date ad where ad.person.personId = p.personId and p.ssn = :ssn");
        query.setParameter("ssn", personSsn);
        List dateList = query.getResultList();
        session.getTransaction().commit();
        return (List<ApplicationDate>) dateList;
    }
}
