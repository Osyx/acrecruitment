package controller;

import integration.Integration;
import model.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private Integration integration = new Integration();

    public void createPerson(String firstName, String lastName, String personSsn, String emailAdress) {
        Person person = new Person(firstName, lastName, personSsn, emailAdress);
        integration.createObject(person);
    }

    public void createAvailability(Person person, Date fromDate, Date toDate) {
        Availability availability = new Availability(fromDate, toDate);
        availability.setPerson(person);
        integration.createObject(availability);
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
