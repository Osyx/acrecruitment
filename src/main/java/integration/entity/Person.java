package integration.entity;
import common.PersonDTO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity(name = "person")
public class Person implements Serializable {
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

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PersonExperience> personExperiences;

    @OneToMany(mappedBy="person", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Availability> availabilities;

    @OneToOne(mappedBy="person", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Application application;


    public Person() {}

    public Person(String name, String surname, String ssn, String email) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
    }

    public Person(PersonDTO personDTO) {
        this.name = personDTO.getName();
        this.surname = personDTO.getSurname();
        this.ssn = personDTO.getSsn();
        this.email = personDTO.getEmail();
    }

    public long getPersonId() {
        return personId;
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

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PersonExperience> getPersonExperiences() {
        return personExperiences;
    }

    public void setPersonExperiences(List<PersonExperience> personExperiences) {
        this.personExperiences = personExperiences;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", ssn='" + ssn + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}