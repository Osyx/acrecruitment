package view;

import controller.Controller;
import model.Availability;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import java.io.Serializable;
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
        //startConversation();
        controller.removeAll();
        long personId = controller.createPerson();
        controller.createAvailability(personId);
        List<Availability> list = controller.fetchAvailabilities("12345678-9000");
        for(Availability item : list)
            System.out.println(item.toString());
    }
}
