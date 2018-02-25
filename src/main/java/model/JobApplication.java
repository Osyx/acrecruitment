package model;

import common.*;
import integration.Integration;
import integration.entity.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JobApplication {

    private final Integration integration = new Integration();

    /**
     * Fetches all job applications.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     */
    public List<JobApplicationDTO> getJobApplications() {
        List<JobApplicationDTO> jobApplications = new ArrayList<>();
        List<Person> personList = integration.getPersonsByRole("applicant");
        for(Person person : personList) {
            List<ExperienceDTO> experiences = new ArrayList<>();
            for(PersonExperience personExperience : person.getPersonExperiences()) {
                experiences.add(new ExperienceDTO(
                        personExperience.getExperience().getName(),
                        personExperience.getYearsOfExperience()
                ));
                }

            List<AvailabilityDTO> availabilities = new ArrayList<>();
            for(Availability availability : person.getAvailabilities()) {
                availabilities.add(new AvailabilityDTO(
                        availability.getFromDate(),
                        availability.getToDate()
                ));
            }

            Application application = person.getApplication();
            String accepted = "Under consideration";
            if(application.isAccepted() != null)
                accepted = application.isAccepted() ? "Accepted" : "Rejected";

            ApplicationDTO applicationDTO = new ApplicationDTO(
                    application.getAppDate(),
                    accepted
            );

            PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);

            JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                    personPublicDTO,
                    availabilities,
                    experiences,
                    applicationDTO
            );
            jobApplications.add(jobApplicationDTO);
        }
        return jobApplications;
    }

    /**
     * Registers a new job application.
     * @param personDTO The DTO of the person to be the applicant.
     * @param experienceDTOs The list of experiences that the applicant has.
     * @param availabilityDTOs The list of dates that the applicant is available.
     * @param applicationDTO The dates of when the applicant registered the applications.
     * @throws SystemException in case that there is an error when registering the application to the database.
     */
    public void registerJobApplication(PersonDTO personDTO, UserDTO userDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        integration.registerJobApplication(personDTO, userDTO, experienceDTOs, availabilityDTOs, applicationDTO);
    }

    /**
     * Accept or decline a job application.
     * @param applicationDTO A DTO encapsulating the job application to be changed and has the <code>accepted</code> value changed to the new value.
     */
    public void acceptOrDeclineJobApplication(ApplicationDTO applicationDTO) {
        integration.acceptOrDeclineJobApplication(applicationDTO);
    }
}
