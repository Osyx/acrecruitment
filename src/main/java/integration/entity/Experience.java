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

    @Column(name = "name_sv", nullable = false)
    private String name_sv;

    @Column(name = "name_en", nullable = false)
    private String name_en;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public Experience() {   }

    public Experience(String name_sv, String name_en) {
        this.name_sv = name_sv;
        this.name_en = name_en;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public String getName_sv() {
        return name_sv;
    }

    public String getName_en() {
        return name_en;
    }

    @OneToMany(mappedBy = "experience")
    private List<PersonExperience> personExperiences;

}
