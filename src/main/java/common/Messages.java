package common;

import view.LanguageBean;

import java.util.ResourceBundle;

/**
 * A class containing the error messages used in this application.
 * This classed is used to standardize the error management in the app.
 */
public enum Messages {
    MISSING_REQUIRED_FIELD(LanguageBean.getRb().getString("missing_required_field")),
    RECORD_ALREADY_EXISTS(LanguageBean.getRb().getString("record_already_exists")),
    REGISTER_JOB_APP_ERROR(LanguageBean.getRb().getString("register_job_app_error")),
    LOGIN_ERROR(LanguageBean.getRb().getString("login_error")),
    SYSTEM_ERROR(LanguageBean.getRb().getString("system_error")),
    REGISTER_USERNAME_ERROR(LanguageBean.getRb().getString("register_username_error")),
    REGISTER_NO_USER_ERROR(LanguageBean.getRb().getString("register_no_user_error")),
    REGISTER_USER_ERROR(LanguageBean.getRb().getString("register_user_error")),
    PERSON_MISSING(LanguageBean.getRb().getString("person_missing")),
    SAVE_TO_DB_FAILED(LanguageBean.getRb().getString("save_to_db_failed")),
    USER_NOT_LOGGED_IN(LanguageBean.getRb().getString("user_not_logged_in")),
    WRONG_INPUT(LanguageBean.getRb().getString("wrong_input")),
    ACCEPT_DECLINE_APP_ERROR(LanguageBean.getRb().getString("accept_decline_app_error")),
    REGISTER_AVAILABILITY_ERROR(LanguageBean.getRb().getString("register_availability_error")),
    REGISTER_EXPERIENCE_ERROR(LanguageBean.getRb().getString("register_experience_error")),
    REGISTER_APPLICATION_ERROR(LanguageBean.getRb().getString("register_application_error")),
    REGISTER_JOB_APP_DTO_ERROR(LanguageBean.getRb().getString("register_job_app_dto_error"));

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
