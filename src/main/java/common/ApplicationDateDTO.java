package common;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement
public class ApplicationDateDTO {
    private String date;

    public ApplicationDateDTO() {}

    public ApplicationDateDTO(String date) {
        this.date = date;
    }

    public ApplicationDateDTO(Date date) {
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
}
