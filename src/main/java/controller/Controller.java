package controller;

import integration.Integration;
import model.Availability;
import model.Person;

import javax.transaction.Transactional;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private Integration integration = new Integration();

    public long createPerson() {
        Person person = new Person("Pelle", "Svanslös", "12345678-9000", "hej@telia.se");
        integration.createObject(person);
        return person.getPersonId();
    }

    public long createAvailability(long personId) {
        Availability availability = new Availability(personId, Integration.getDate("20110312"), Integration.getDate("20160530"));
        integration.createObject(availability);
        return availability.getAvailability_id();
    }

    public void removeAll() {
        integration.removeAll();
    }

    public List<Availability> fetchAvailabilities(String personSsn) {
        return integration.fetchAvailabilities(personSsn);
    }

}
