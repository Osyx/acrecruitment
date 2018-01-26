package model;

import javax.persistence.*;
import java.sql.Date;

@NamedQueries({
        @NamedQuery(
                name = "deleteFile",
                query = "DELETE FROM availability avail " +
                        "WHERE avail.availability_id = :availabilityId"
        )
})

@Entity(name = "availability")
public class Availability {
    @Id
    @Column(name = "availability_id", nullable = false)
    private long availability_id;

    @Column(name = "person_id", nullable = false)
    private long person_id;

    @JoinColumn(name = "from_date", nullable = false)
    private Date from_date;

    @Column(name = "to_date", nullable = false)
    private Date to_date;

    @Version
    @Column(name = "version")
    private int versionNum;

    public Availability() {}

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

    public int getVersionNum() {
        return versionNum;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        return "Availability: [" +
                "Availability id: " + availability_id +
                ", person id: " + person_id +
                ", from date: " + from_date +
                ", to_date: " + to_date +
                "]";
    }
}
