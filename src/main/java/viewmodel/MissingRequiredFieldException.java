package viewmodel;

public class MissingRequiredFieldException extends RuntimeException {

    public MissingRequiredFieldException(String errorMessage) {
        super(errorMessage);
    }
}
