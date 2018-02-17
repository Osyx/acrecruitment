package model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "person")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private long personId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "ssn", nullable = false)
    private String ssn;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "person")
    private List<ApplicationDate> applicationDates;

    @OneToMany(mappedBy = "person")
    private List<Availability> availabilities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<PersonExperience> personExperiences;

    public void addPersonExperiences(PersonExperience personExperience) {
        this.personExperiences.add(personExperience);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public List<ApplicationDate> getApplicationDates() {
        return applicationDates;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public List<PersonExperience> getPersonExperiences() {
        return personExperiences;
    }
}
