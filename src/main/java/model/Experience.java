package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name="experience")
public class Experience implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "experience_id", nullable = false)
    private long experienceId;

    @Column(name = "name", nullable = false)
    private String name;

    public Experience() {   }

    public Experience(String name) {
        this.name = name;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "experience")
    private List<PersonExperience> personExperiences;

    public void addPersonExperiences(PersonExperience personExperience) {
        this.personExperiences.add(personExperience);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "experienceId=" + experienceId +
                ", name='" + name + '\'' +
                '}';
    }
}
