package controller;

import integration.Integration;
import model.Availability;

import javax.transaction.Transactional;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private Integration integration = new Integration();

    public void createAvailability() {
        Availability availability = new Availability(0, Integration.getDate("20110312"), Integration.getDate("20160530"));
        integration.createAvailability(availability);
    }

    public List<Availability> fetchAvailabilities(String personSsn) {
        return integration.fetchAvailabilities(personSsn);
    }

}
