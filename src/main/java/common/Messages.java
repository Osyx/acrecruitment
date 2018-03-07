package common;

import view.LanguageBean;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A class containing the error messages used in this application.
 * This classed is used to standardize the error management in the app.
 */
public enum Messages {
    MISSING_REQUIRED_FIELD,
    RECORD_ALREADY_EXISTS,
    REGISTER_JOB_APP_ERROR,
    LOGIN_ERROR,
    SYSTEM_ERROR,
    REGISTER_USERNAME_ERROR,
    REGISTER_NO_USER_ERROR,
    REGISTER_USER_ERROR,
    PERSON_MISSING,
    SAVE_TO_DB_FAILED,
    USER_NOT_LOGGED_IN,
    WRONG_INPUT,
    ACCEPT_DECLINE_APP_ERROR,
    REGISTER_AVAILABILITY_ERROR,
    REGISTER_EXPERIENCE_ERROR,
    REGISTER_APPLICATION_ERROR,
    REGISTER_JOB_APP_DTO_ERROR;

    private String errorMessage;

    Messages() {
        ResourceBundle res = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        this.errorMessage = res.getString(name().toLowerCase());
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
