package model;

import javax.persistence.*;
import java.util.List;

public class JobApplication {
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

    @OneToMany
    @JoinTable (
        name="person_experience",
        joinColumns={ @JoinColumn(name="person_id", referencedColumnName="person_id") },
        inverseJoinColumns={ @JoinColumn(name="experience_id", referencedColumnName="experience_id", unique=true) }
    )
    private List<Experience> experiences;


    private List<Double> yearsOfExperience;
}
