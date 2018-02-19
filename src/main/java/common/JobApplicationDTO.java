package common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class JobApplicationDTO {
    private PersonPublicDTO person;
    private List<AvailabilityDTO> availabilities;
    private List<ExperienceDTO> experiences;
    private List<ApplicationDateDTO> applicationDates;

    public JobApplicationDTO() {}

    public JobApplicationDTO(PersonPublicDTO person, List<AvailabilityDTO> availabilities, List<ExperienceDTO> experiences, List<ApplicationDateDTO> applicationDates) {
        this.person = person;
        this.availabilities = availabilities;
        this.experiences = experiences;
        this.applicationDates = applicationDates;
    }

    public PersonPublicDTO getPerson() {
        return person;
    }

    public void setPerson(PersonPublicDTO person) {
        this.person = person;
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