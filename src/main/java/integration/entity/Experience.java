package integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name="experience")
public class Experience implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "experience_id", nullable = false)
    private long experienceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

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

    @Override
    public String toString() {
        return "Experience{" +
                "experienceId=" + experienceId +
                ", name='" + name + '\'' +
                '}';
    }
}
