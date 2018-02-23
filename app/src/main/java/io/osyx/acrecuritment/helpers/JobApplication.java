package io.osyx.acrecuritment.helpers;

import java.util.ArrayList;
import java.util.List;

import io.osyx.acrecuritment.dto.ApplicationDTO;
import io.osyx.acrecuritment.dto.AvailabilityDTO;
import io.osyx.acrecuritment.dto.ExperienceDTO;
import io.osyx.acrecuritment.dto.PersonPublicDTO;

public class JobApplication {

    public PersonPublicDTO person;
    public List<AvailabilityDTO> availabilities;
    public List<ExperienceDTO> experiences;
    public ApplicationDTO application;

    public JobApplication() {
        this.availabilities = new ArrayList<>();
        this.experiences = new ArrayList<>();
    }
}

