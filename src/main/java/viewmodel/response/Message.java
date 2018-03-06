package viewmodel.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class used to be able to display messages to the REST clients, due to
 * the fact that it can be converted to a JSON/XML object.
 */
@XmlRootElement
public class Message {
    private String messageKey;
    private String message;

    public Message() {}

    public Message(String messageKey, String message) {
        this.messageKey = messageKey;
        this.message = message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
