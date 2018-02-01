package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "person_experience")
public class PersonExperience implements Serializable {

    @Id
    @Column(name = "person_experience_id", nullable = false)
    private long personExperienceId;

    @Column(name = "person_id", nullable = false)
    private long personId;

    @Column(name = "experience_id", nullable = false)
    private long experienceId;

    @Column(name = "years_of_experience", nullable = false)
    private double yearsOfExperience;

    public PersonExperience() {    }

    public PersonExperience(long personId, long experienceId, double yearsOfExperience) {
        this.personId = personId;
        this.experienceId = experienceId;
        this.yearsOfExperience = yearsOfExperience;
    }

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

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public void setExperienceId(long experienceId) {
        this.experienceId = experienceId;
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
