package common;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

/**
 * A DTO for info about the job applications.
 * Contains info about when it was registered and what status the application has.
 */
@XmlRootElement
public class ApplicationDTO {
    private long applicationId;
    private String date;
    private String accepted;

    public ApplicationDTO() {}

    public ApplicationDTO(long id, String date) {
        this.applicationId = id;
        this.date = date;
        this.accepted = "";
    }

    public ApplicationDTO(long id, Date date) {
        this.applicationId = id;
        this.date = date.toString();
        this.accepted = "";
    }

    public ApplicationDTO(Date date) {
        this.date = date.toString();
        this.accepted = "";
    }

    public ApplicationDTO(long id, String date, String accepted) {
        this.applicationId = id;
        this.date = date;
        this.accepted = accepted;
    }

    public ApplicationDTO(long id, Date date, String accepted) {
        this.applicationId = id;
        this.date = date.toString();
        this.accepted = accepted;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
