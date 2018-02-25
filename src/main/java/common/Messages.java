package common;

public enum Messages {
    MISSING_REQUIRED_FIELD("Missing required field(s). Please check that you have included them all."),
    RECORD_ALREADY_EXISTS("Record already exists."),
    REGISTER_PERSON_ERROR("There was an error when trying to register"),
    REGISTER_JOB_APP_ERROR("There was an error when trying to register the job application"),
    REGISTER_AVAILABILITY_ERROR("There was an error when trying to register the availability"),
    REGISTER_EXPERIENCE_ERROR("There was an error when trying to register the experience"),
    REGISTER_ROLE_ERROR("There was an error when trying to register the role"),
    REGISTER_PERSON_EXPERIENCE_ERROR("There was an error when trying to register the PersonExperience"),
    LOGIN_ERROR("There was an error when trying to log in"),
    SYSTEM_ERROR("There was an error during execute"),
    REGISTER_USERNAME_ERROR("Username already exists"),
    REGISTER_NO_USER_ERROR("No user or faulty user details was sent to be registered."),
    REGISTER_USER_ERROR("You already have an user registered"),
    PERSON_MISSING("There is either no such person found or a person isn't given"),
    SAVE_TO_DB_FAILED("Something went wrong during the save to the DB"),
    USER_NOT_LOGGED_IN("Cannot access this option when not logged in with a registered user");

    private String errorMessage;

    Messages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
