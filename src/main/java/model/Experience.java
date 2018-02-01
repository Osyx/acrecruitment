package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name="experience")
public class Experience implements Serializable {
    @Id
    @Column(name = "experience_id", nullable = false)
    private long experienceId;

    @Column(name = "name", nullable = false)
    private String name;

    public Experience() {   }

    public Experience(long experienceId, String name) {
        this.experienceId = experienceId;
        this.name = name;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "experienceId=" + experienceId +
                ", name='" + name + '\'' +
                '}';
    }
}
