package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "person_experience")
public class PersonExperience {

    @Id
    @Column(name = "person_experience_id", nullable = false)
    private long personExperienceId;

    @Column(name = "person_id", nullable = false)
    private long personId;

    @Column(name = "experience_id", nullable = false)
    private long experienceId;

    @Column(name = "years_of_experience")
    private double yearsOfExperience;

    public PersonExperience() {    }

    public long getPersonExperienceId() {
        return personExperienceId;
    }

    public long getPersonId() {
        return personId;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String toString() {
        return "PersonExperience{" +
                "personExperienceId=" + personExperienceId +
                ", personId=" + personId +
                ", experienceId=" + experienceId +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
