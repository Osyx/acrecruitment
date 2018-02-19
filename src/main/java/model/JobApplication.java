package model;

import common.*;
import integration.Integration;
import integration.entity.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JobApplication {

    private Integration integration = new Integration();

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

            List<ApplicationDateDTO> applicationDates = new ArrayList<>();
            for(ApplicationDate applicationDate : integration.getApplicationDates(person.getSsn())) {
                applicationDates.add(new ApplicationDateDTO(
                        applicationDate.getAppDate()
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

    public void registerJobApplication(PersonDTO personDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, List<ApplicationDateDTO> applicationDateDTOs) {
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
        List<ApplicationDate> applicationDates = new ArrayList<>();
        for(ApplicationDateDTO applicationDateDTO : applicationDateDTOs) {
            applicationDates.add(new ApplicationDate(
                    Date.valueOf(applicationDateDTO.getDate())
            ));
        }
        integration.registerJobApplication(person, experiences, yearsOfExperiences, availabilities, applicationDates);
        integration.createObject(new PersonRole(
                person,
                integration.getRole("applicant")
        ));
    }
}
