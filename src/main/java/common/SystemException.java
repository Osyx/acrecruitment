package common;

public class SystemException extends Exception {
    private String message;
    private String messageKey;

    public SystemException(String message) {
        this.message = message;
        this.messageKey = ErrorMessages.SYSTEM_ERROR.name();
    }

    public SystemException(String message, String messageKey) {
        this.message = message;
        this.messageKey = messageKey;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
