package view;

import controller.Controller;
import model.Availability;
import model.Person;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class View implements Serializable{
    private Controller controller = new Controller();
    @Inject
    private Conversation conversation;

    public static void main(String[] args) {
        View view = new View();
        view.test();
    }

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
    }

    private void test() {
        String personSsn = "12345678-9000";
        List<Availability> list = controller.fetchAvailabilities(personSsn);
        for(Availability item : list)
            controller.removeObject(item);
        Person person = controller.getPerson(personSsn);
        controller.removeObject(person);
        controller.createPerson(personSsn);
        person = controller.getPerson(personSsn);
        controller.createAvailability(person.getPersonId(), getDate("20110312"), getDate("20160530"));
        list = controller.fetchAvailabilities(personSsn);
        for(Availability item : list)
            System.out.println(item.toString());
    }

    public Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }
}
