package model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "person")
public class JobApplication {
    private List<PersonExperience> personExperiences;

    @Id
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
    public List<PersonExperience> getPersonExperiences() {
        return personExperiences;
    }

    public void setPersonExperiences(List<PersonExperience> personExperiences) {
        this.personExperiences = personExperiences;
    }

    public void addPersonExperiences(PersonExperience personExperience) {
        this.personExperiences.add(personExperience);
    }
}
