package common;

/**
 * A class containing the error messages used in this application.
 * This classed is used to standardize the error management in the app.
 */
public enum Messages {
    MISSING_REQUIRED_FIELD("Missing required field(s). Please check that you have included them all."),
    RECORD_ALREADY_EXISTS("Record already exists."),
    REGISTER_JOB_APP_ERROR("There was an error when trying to register the job application"),
    LOGIN_ERROR("Wrong username or password"),
    SYSTEM_ERROR("There was an error during execute"),
    REGISTER_USERNAME_ERROR("Username already exists"),
    REGISTER_NO_USER_ERROR("No user or faulty user details was sent to be registered."),
    REGISTER_USER_ERROR("You already have an user registered"),
    PERSON_MISSING("There is either no such person found or a person isn't given"),
    SAVE_TO_DB_FAILED("Something went wrong during the save to the DB"),
    USER_NOT_LOGGED_IN("Cannot access this option when not logged in with a registered user"),
    WRONG_INPUT("There was an error in one of the input fields: "),
    ACCEPT_DECLINE_APP_ERROR("There was an error when trying to accept/decline application"),
    REGISTER_AVAILABILITY_ERROR("There was an error when trying to register the availability"),
    REGISTER_EXPERIENCE_ERROR("There was an error when trying to register the experience"),
    REGISTER_APPLICATION_ERROR("There was an error when trying to register the application"),
    REGISTER_JOB_APP_DTO_ERROR("There was an error when trying to register the job application DTO");

    private String errorMessage;

    Messages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorMessageWithArg(String arg) {
        return errorMessage + arg;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
