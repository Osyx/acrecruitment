package model;

import common.ApplicationDateDTO;
import common.AvailabilityDTO;
import common.ExperienceDTO;
import common.JobApplicationDTO;
import integration.Integration;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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

            JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                    person.getName(),
                    person.getSurname(),
                    person.getEmail(),
                    availabilities,
                    experiences,
                    applicationDates
            );
            jobApplications.add(jobApplicationDTO);
        }
        return jobApplications;
    }
}
