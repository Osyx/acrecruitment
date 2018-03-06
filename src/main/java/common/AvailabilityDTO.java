package common;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

/**
 * A DTO containing the dates between which the applicant is available.
 */
@XmlRootElement
public class AvailabilityDTO {
    private String fromDate;
    private String toDate;

    public AvailabilityDTO() {}

    public AvailabilityDTO(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public AvailabilityDTO(Date fromDate, Date toDate) {
        this.fromDate = fromDate.toString();
        this.toDate = toDate.toString();
    }

    public AvailabilityDTO(Date fromDate) {
        this.fromDate = fromDate.toString();
        this.toDate = null;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate.toString();
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate.toString();
    }
}
