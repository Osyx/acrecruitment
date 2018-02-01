package controller;

import integration.Integration;
import model.Availability;
import model.Experience;
import model.Person;
import model.User;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private Integration integration = new Integration();

    public long createPerson(String personSsn) {
        Person person = new Person("Pelle", "Svanslös", personSsn, "hej@telia.se");
        integration.createObject(person);
        return person.getPersonId();
    }

    public void createAvailability(long personId, Date fromDate, Date toDate) {
        Availability availability = new Availability(personId, fromDate, toDate);
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

    public void registerJobApplication(List<Experience>, List<>) {

    }

}
