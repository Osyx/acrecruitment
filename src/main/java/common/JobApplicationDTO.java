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
    private String email;
    private List<AvailabilityDTO> availabilities;
    private List<ExperienceDTO> experiences;
    private List<ApplicationDateDTO> applicationDates;

    public JobApplicationDTO() {}

    public JobApplicationDTO(String name, String surname, String email, List<AvailabilityDTO> availabilities, List<ExperienceDTO> experiences, List<ApplicationDateDTO> applicationDates) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.availabilities = availabilities;
        this.experiences = experiences;
        this.applicationDates = applicationDates;
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

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public List<ExperienceDTO> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceDTO> experiences) {
        this.experiences = experiences;
    }

    public List<ApplicationDateDTO> getApplicationDates() {
        return applicationDates;
    }

    public void setApplicationDates(List<ApplicationDateDTO> applicationDates) {
        this.applicationDates = applicationDates;
    }
}
