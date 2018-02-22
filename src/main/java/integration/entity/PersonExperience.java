package integration.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "person_experience")
public class PersonExperience implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "person_experience_id", nullable = false)
    private long personExperienceId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience;

    @Column(name = "years_of_experience", nullable = false)
    private double yearsOfExperience;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public PersonExperience() {    }

    public PersonExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public PersonExperience(Person person, Experience experience, double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
        this.person = person;
        this.experience = experience;
    }

    public long getPersonExperienceId() {
        return personExperienceId;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public Person getPerson() {
        return person;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonExperience{" +
                "personExperienceId=" + personExperienceId +
                ", personId=" + person.getPersonId() +
                ", experienceId=" + experience.getExperienceId() +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
