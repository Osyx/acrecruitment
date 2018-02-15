package common;

import model.Person;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonDTO {
    private long id;
    private String name;
    private String surname;
    private String ssn;
    private String email;

    public PersonDTO() {    }

    public PersonDTO(String name, String surname, String ssn, String email) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
    }

    public PersonDTO(Person person) {
        this.id = person.getPersonId();
        this.name = person.getName();
        this.surname = person.getSurname();
        this.ssn = person.getSsn();
        this.email = person.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
