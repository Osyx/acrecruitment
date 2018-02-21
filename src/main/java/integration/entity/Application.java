package integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "application_date")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "application_date_id", nullable = false)
    private long applicationDateId;

    @Column(name = "app_date", nullable = false)
    private Date appDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    //private boolean accepted;

    public Application() {  }

    public Application(Date appDate) {
        this.appDate = appDate;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
/*
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
    */
}
