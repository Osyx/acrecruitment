package controller;

import integration.Integration;
import model.*;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private Integration integration = new Integration();

    public Person createPerson(String firstName, String lastName, String personSsn, String emailAdress) {
        Person person = new Person(firstName, lastName, personSsn, emailAdress);
        integration.createObject(person);
        return person;
    }

    public User createUser(String userName, String password, long personId) {
        User user = new User(userName, password, personId);
        integration.createObject(user);
        return user;
    }

    public void createAvailability(long personId, Date fromDate, Date toDate) {
        Availability availability = new Availability(personId, fromDate, toDate);
        integration.createObject(availability);
    }

    public void createExperience(String experienceName) {
        Experience experience = new Experience(experienceName);
        integration.createObject(experience);
    }

    public void createPersonExperience(long personID, long experienceID, double yearsOfExperience){
        PersonExperience personExperience = new PersonExperience(personID, experienceID, yearsOfExperience);
        integration.createObject(personExperience);
    }

    public void createRole(long personID, String roleName){
        Role role = new Role(personID, roleName);
        integration.createObject(role);
    }

    public void createPersonRole(long personID, long roleID){
        PersonRole personRole = new PersonRole(personID, roleID);
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

    /*
    public List<Experience> fetchExperiences() {
      //  return integration.fetchExperiences();
    }
    */

    public boolean login(String username, String password) {
        return integration.login(username, password);
    }

    public boolean registerUser(Person person, User user) {
        return integration.userRegister(person, user);
    }

    public void registerJobApplication(Person person, List<Experience> experiences, List<Double> yearsOfExperiences ,List<Availability> availabilities) {
        integration.registerJobApplication(person, experiences, yearsOfExperiences, availabilities);
    }

}
