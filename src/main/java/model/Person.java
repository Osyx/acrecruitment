package model;
import javax.persistence.*;
import java.io.Serializable;



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


    public Person() {}

    public Person(String name, String surname, String ssn, String email) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
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