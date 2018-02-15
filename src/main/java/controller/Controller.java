package controller;

import integration.Integration;
import model.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private final Integration integration = new Integration();

    public Person createPerson(String firstName, String lastName, String personSsn, String emailAdress) {
        Person person = new Person(firstName, lastName, personSsn, emailAdress);
        integration.createObject(person);
        return person;
    }

    public User createUser(String userName, String password, Person person) {
        User user = new User(userName, password);
        user.setPerson(person);
        integration.createObject(user);
        return user;
    }

    public void createAvailability(Person person, Date fromDate, Date toDate) {
        Availability availability = new Availability(fromDate, toDate);
        availability.setPerson(person);
        integration.createObject(availability);
    }

    public Experience createExperience(String experienceName) {
        Experience experience = new Experience(experienceName);
        integration.createObject(experience);
        return experience;
    }

    public void createPersonExperience(Person person, Experience experience, double yearsOfExperience){
        PersonExperience personExperience = new PersonExperience(person, experience, yearsOfExperience);
        integration.createObject(personExperience);
    }

    public void createRole(Person person, String roleName){
        Role role = new Role(person, roleName);  //Ska det verkligen vara en float här?
        integration.createObject(role);
    }

    public void createPersonRole(Person person, Role role){
        PersonRole personRole = new PersonRole(person, role);
        integration.createObject(personRole);
    }

    public void removeObject(Object object) {
        integration.removeObject(object);
    }

    public Person getPerson(String personSsn) {
        return integration.getPerson(personSsn);
    }

    public List<Availability> fetchAvailabilities(String personSsn) {
        return integration.fetchAvailabilities(personSsn);
    }

    public List<Experience> fetchExperiences() {
        return integration.fetchExperiences();
    }

    public List<Double> fetchYearsOfExperiences() {
        return integration.fetchYearsOfExperiences();
    }

    public boolean login(String username, String password) {
        return integration.login(username, password);
    }

    public boolean registerUser(Person person, User user) {
        return integration.userRegister(person, user);
    }

    public void registerJobApplication(Person person, List<Experience> experiences, List<Double> yearsOfExperiences ,List<Availability> availabilities) {
        integration.registerJobApplication(person, experiences, yearsOfExperiences, availabilities);
    }

    public List<JobApplication> fetchJobApplications(String searchParameter, Date fromDate, Date toDate) {
        return integration.fetchJobApplications(searchParameter, fromDate, toDate);
    }

    public List<JobApplication> fetchJobApplications(String searchParameter, String... searchString) {
        return integration.fetchJobApplications(searchParameter, searchString);
    }

}
