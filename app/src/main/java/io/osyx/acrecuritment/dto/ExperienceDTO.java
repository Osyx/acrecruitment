package io.osyx.acrecuritment.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class ExperienceDTO {

    public String name;
    public double yearsOfExperience;

    public ExperienceDTO(JSONObject json_experiences) throws JSONException {
        this.name = json_experiences.getString("name");
        this.yearsOfExperience = json_experiences.getDouble("yearsOfExperience");
    }

    public ExperienceDTO(String name, double yearsOfExperience) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }
}
