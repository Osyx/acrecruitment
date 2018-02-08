package model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "application_date")
public class ApplicationDate implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "application_date_id", nullable = false)
    private long applicationDateId;

    @Column(name = "app_date", nullable = false)
    private Date appDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public ApplicationDate() {  }

    public ApplicationDate(Date appDate) {
        this.appDate = appDate;
    }

    public long getApplicationDateId() {
        return applicationDateId;
    }

    public Date getAppDate() {
        return appDate;
    }

    public Person getPerson() {
        return person;
    }
}
