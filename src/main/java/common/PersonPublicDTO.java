package common;

import integration.entity.Person;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonPublicDTO {
    private String name;
    private String surname;
    private String email;

    public PersonPublicDTO() {    }

    public PersonPublicDTO(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public PersonPublicDTO(Person person) {
        this.name = person.getName();
        this.surname = person.getSurname();
        this.email = person.getEmail();
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
