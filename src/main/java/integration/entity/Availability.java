package integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "availability")
public class Availability implements Serializable {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    private long availabilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public Availability() {    }

    public Availability(Person person, Date fromDate, Date toDate) {
        this.person = person;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public long getAvailabilityId() {
        return availabilityId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        return "Availability{" +
                "availability_id=" + availabilityId +
                ", person_id=" + person.getPersonId() +
                ", from_date=" + fromDate +
                ", to_date=" + toDate +
                '}';
    }
}
