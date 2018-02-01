package view;

import controller.Controller;
import model.Availability;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import java.util.List;

public class View {
    private Controller controller = new Controller();

    public static void main(String[] args) {
        View view = new View();
        view.test();
    }

    @Inject
    private Conversation conversation;

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
        controller.createAvailability();
        List<Availability> list = controller.fetchAvailabilities("12345678-9000");
        for(Availability item : list)
            System.out.println(item.toString());
    }
}
