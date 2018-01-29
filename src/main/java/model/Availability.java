package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "availability")
public class Availability {
    @Id
    @Column(name = "availability_id", nullable = false)
    private long availability_id;

    @Column(name = "person_id", nullable = false)
    private long person_id;

    @Column(name = "from_date", nullable = false)
    private Date from_date;

    @Column(name = "to_date", nullable = false)
    private Date to_date;

    public Availability() {    }

    public Availability(long availability_id, long person_id, Date from_date, Date to_date) {
        this.availability_id = availability_id;
        this.person_id = person_id;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public long getAvailability_id() {
        return availability_id;
    }

    public long getPerson_id() {
        return person_id;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        return "Availability{" +
                "availability_id=" + availability_id +
                ", person_id=" + person_id +
                ", from_date=" + from_date +
                ", to_date=" + to_date +
                '}';
    }
}
