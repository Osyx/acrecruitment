package viewmodel.request;

import common.ApplicationDateDTO;
import common.AvailabilityDTO;
import common.ExperienceDTO;
import common.PersonDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class JobApplicationRequest {
    private PersonDTO person;
    private List<AvailabilityDTO> availabilities;
    private List<ExperienceDTO> experiences;
    private List<ApplicationDateDTO> applicationDates;

    public JobApplicationRequest() {}

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
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
