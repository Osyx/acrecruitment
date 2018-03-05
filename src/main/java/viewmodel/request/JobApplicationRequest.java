package viewmodel.request;

import common.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * A JSON/XML compatible class which encapsulates all the info which is required
 * from a request to create a new job application.
 */
@XmlRootElement
public class JobApplicationRequest {
    private PersonDTO person;
    private List<AvailabilityDTO> availabilities;
    private List<ExperienceDTO> experiences;
    private ApplicationDTO application;

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

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
        this.application = application;
    }
}
