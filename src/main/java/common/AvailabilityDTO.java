package common;

import java.sql.Date;

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
