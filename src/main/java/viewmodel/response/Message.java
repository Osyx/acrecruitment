package viewmodel.response;

import javax.xml.bind.annotation.XmlRootElement;

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
