package common;

import model.Person;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonPublicDTO {
    private long personId;
    private String name;
    private String surname;
    private String email;

    public PersonPublicDTO() {    }

    public PersonPublicDTO(long personId, String name, String surname, String email) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public PersonPublicDTO(Person person) {
        this.personId = person.getPersonId();
        this.name = person.getName();
        this.surname = person.getSurname();
        this.email = person.getEmail();
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
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

    public void setEmail(String email) {
        this.email = email;
    }
}
