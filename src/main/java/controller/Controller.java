package controller;

import common.PersonDTO;
import common.PersonPublicDTO;
import integration.Integration;
import model.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private final Integration integration = new Integration();

    /**
     * Creates and saves a person in the database with the given info.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param personSsn The social security number.
     * @param emailAdress The email adress.
     * @return The person object that was created and saved.
     */
    public Person createPerson(String firstName, String lastName, String personSsn, String emailAdress) {
        Person person = new Person(firstName, lastName, personSsn, emailAdress);
        integration.createObject(person);
        return person;
    }

    /**
     * Creates and saves a person in the database with the given info in a PersonDTO.
     * @param personDTO A personDTO object encapsulating the info for the new person.
     * @return The person object that was created and saved.
     */
    public Person createPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO.getName(), personDTO.getSurname(), personDTO.getSsn(), personDTO.getEmail());
        integration.createObject(person);
        return person;
    }

    /**
     * Creates a user with the given info.
     * @param userName The username for the user,
     * @param password The password for the user.
     * @param person The person who owns this user,
     * @return The created and saved User object.
     */
    public User createUser(String userName, String password, Person person) {
        User user = new User(userName, password);
        user.setPerson(person);
        integration.createObject(user);
        return user;
    }

    /**
     *
     * @param person
     * @param fromDate
     * @param toDate
     */
    public void createAvailability(Person person, Date fromDate, Date toDate) {
        Availability availability = new Availability(fromDate, toDate);
        availability.setPerson(person);
        integration.createObject(availability);
    }

    /**
     *
     * @param experienceName
     * @return
     */
    public Experience createExperience(String experienceName) {
        Experience experience = new Experience(experienceName);
        integration.createObject(experience);
        return experience;
    }

    /**
     *
     * @param person
     * @param experience
     * @param yearsOfExperience
     */
    public void createPersonExperience(Person person, Experience experience, double yearsOfExperience){
        PersonExperience personExperience = new PersonExperience(person, experience, yearsOfExperience);
        integration.createObject(personExperience);
    }
/*
    public void createRole(Person person, String roleName){
        Role role = new Role(person, roleName);  //Ska det verkligen vara en float här?
        integration.createObject(role);
    }
*/

    /**
     *
     * @param person
     * @param role
     */
    public void createPersonRole(Person person, Role role){
        PersonRole personRole = new PersonRole(person, role);
        integration.createObject(personRole);
    }

    /**
     *
     * @param object
     */
    public void removeObject(Object object) {
        integration.removeObject(object);
    }

    /**
     *
     * @param personPublicDTO
     */
    public void updatePerson(PersonDTO personPublicDTO) {
        integration.updatePerson(personPublicDTO);
    }

    /**
     *
     * @param personSsn
     * @return
     */
    public Person getPerson(String personSsn) {
        return integration.getPerson(personSsn);
    }

    /**
     *
     * @param id
     * @return
     */
    public PersonDTO getPersonByID(long id) {
        return integration.getPersonById(id);
    }

    /**
     *
     * @return
     */
    public List<PersonPublicDTO> getPersons() {
        return integration.getPersons();
    }

    /**
     *
     * @param personSsn
     * @return
     */
    public List<Availability> fetchAvailabilities(String personSsn) {
        return integration.fetchAvailabilities(personSsn);
    }

    /*
    public List<Experience> fetchExperiences() {
        return integration.fetchExperiences();
    }

    public List<Double> fetchYearsOfExperiences() {
        return integration.fetchYearsOfExperiences();
    }
*/

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) {
        return integration.login(username, password);
    }

    /**
     *
     * @param person
     * @param user
     * @return
     */
    public boolean registerUser(Person person, User user) {
        return integration.userRegister(person, user);
    }

    /**
     *
     * @param person
     * @param experiences
     * @param yearsOfExperiences
     * @param availabilities
     */
    public void registerJobApplication(Person person, List<Experience> experiences, List<Double> yearsOfExperiences ,List<Availability> availabilities) {
        integration.registerJobApplication(person, experiences, yearsOfExperiences, availabilities);
    }

    /**
     *
     * @param searchParameter
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<JobApplication> fetchJobApplications(String searchParameter, Date fromDate, Date toDate) {
        return integration.fetchJobApplications(searchParameter, fromDate, toDate);
    }

    /**
     *
     * @param searchParameter
     * @param searchString
     * @return
     */
    public List<JobApplication> fetchJobApplications(String searchParameter, String... searchString) {
        return integration.fetchJobApplications(searchParameter, searchString);
    }
}
