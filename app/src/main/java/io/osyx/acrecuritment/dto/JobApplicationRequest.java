package io.osyx.acrecuritment.dto;

import com.google.gson.GsonBuilder;

import java.util.List;

public class JobApplicationRequest {
    private PersonDTO person;
    private List<AvailabilityDTO> availabilities;
    private List<ExperienceDTO> experiences;
    private ApplicationDTO application;

    public JobApplicationRequest() {}

    public JobApplicationRequest(PersonDTO person, List<AvailabilityDTO> availabilities, List<ExperienceDTO> experiences, ApplicationDTO application) {
        this.person = person;
        this.availabilities = availabilities;
        this.experiences = experiences;
        this.application = application;
    }

    public String toJSON() {
        return new GsonBuilder().create().toJson(this, JobApplicationRequest.class);
    }
}

