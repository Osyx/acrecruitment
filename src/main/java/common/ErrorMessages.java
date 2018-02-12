package common;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field(s). Please check that you have included them all."),
    RECORD_ALREADY_EXISTS("Record already exists."),
    REGISTER_PERSON_ERROR("There was an error when trying to register"),
    REGISTER_JOB_APP_ERROR("There was an error when trying to register the job application"),
    REGISTER_AVAILABILITY_ERROR("There was an error when trying to register the availability"),
    REGISTER_EXPERIENCE_ERROR("There was an error when trying to register the experience"),
    REGISTER_ROLE_ERROR("There was an error when trying to register the role"),
    REGISTER_PERSON_EXPERIENCE_ERROR("There was an error when trying to register the PersonExperience"),
    LOGIN_ERROR("There was an error when trying to log in");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
