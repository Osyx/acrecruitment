package common;

import model.ApplicationDate;
import model.Availability;
import model.PersonExperience;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class JobApplicationDTO {
    private String name;
    private String surname;
    private String ssn;
    private String email;
    private List<ApplicationDate> applicationDates;
    private List<Availability> availabilities;
    private List<PersonExperience> personExperiences;

    public JobApplicationDTO() {}

    public JobApplicationDTO(String name, String surname, String ssn, String email, List<ApplicationDate> applicationDates, List<Availability> availabilities, List<PersonExperience> personExperiences) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
        this.applicationDates = applicationDates;
        this.availabilities = availabilities;
        this.personExperiences = personExperiences;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ApplicationDate> getApplicationDates() {
        return applicationDates;
    }

    public void setApplicationDates(List<ApplicationDate> applicationDates) {
        this.applicationDates = applicationDates;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<PersonExperience> getPersonExperiences() {
        return personExperiences;
    }

    public void setPersonExperiences(List<PersonExperience> personExperiences) {
        this.personExperiences = personExperiences;
    }
}
