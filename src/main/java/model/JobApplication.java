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
     * @return All job aaplications.
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

            List<ApplicationDTO> applicationDates = new ArrayList<>();
            for(Application application : integration.getApplicationDates(person.getSsn())) {
                applicationDates.add(new ApplicationDTO(
                        application.getAppDate()
                ));
            }

            PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);

            JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                    personPublicDTO,
                    availabilities,
                    experiences,
                    applicationDates
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
     * @param applicationDTOs The dates of when the applicant registered the applications.
     * @throws SystemException in case that there is an error when registering the application to the database.
     */
    public void registerJobApplication(PersonDTO personDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, List<ApplicationDTO> applicationDTOs) throws SystemException {
        Person person = new Person(
                personDTO.getName(),
                personDTO.getSurname(),
                personDTO.getSsn(),
                personDTO.getEmail()
        );
        List<Experience> experiences = new ArrayList<>();
        List<Double> yearsOfExperiences = new ArrayList<>();
        for(ExperienceDTO experienceDTO : experienceDTOs) {
            experiences.add(new Experience(
                    experienceDTO.getName()
            ));
            yearsOfExperiences.add(experienceDTO.getYearsOfExperience());
        }
        List<Availability> availabilities = new ArrayList<>();
        for(AvailabilityDTO availabilityDTO : availabilityDTOs) {
            availabilities.add(new Availability(
                    Date.valueOf(availabilityDTO.getFromDate()),
                    Date.valueOf(availabilityDTO.getToDate())
            ));
        }
        List<Application> applications = new ArrayList<>();
        for(ApplicationDTO applicationDTO : applicationDTOs) {
            applications.add(new Application(
                    Date.valueOf(applicationDTO.getDate())
            ));
        }
        integration.registerJobApplication(person, experiences, yearsOfExperiences, availabilities, applications);
    }
}
