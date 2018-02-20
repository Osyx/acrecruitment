package common;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement
public class ApplicationDTO {
    private String date;
    private String accepted;

    public ApplicationDTO() {}

    public ApplicationDTO(String date) {
        this.date = date;
    }

    public ApplicationDTO(Date date) {
        this.date = date.toString();
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
