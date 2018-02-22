package common;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement
public class ApplicationDTO {
    private long applicationNr;
    private String date;
    private String accepted;

    public ApplicationDTO() {}

    public ApplicationDTO(long applicationNr, String date, String accepted) {
        this.applicationNr = applicationNr;
        this.date = date;
        this.accepted = accepted;
    }

    public ApplicationDTO(long applicationNr, Date date, String accepted) {
        this.applicationNr = applicationNr;
        this.date = date.toString();
        this.accepted = accepted;
    }

    public long getApplicationNr() {
        return applicationNr;
    }

    public void setApplicationNr(long applicationNr) {
        this.applicationNr = applicationNr;
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
