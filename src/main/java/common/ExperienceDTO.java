package common;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A DTO containing an experience and the years the applicant has in it.
 */
@XmlRootElement
public class ExperienceDTO {
    private String name;
    private double yearsOfExperience;

    public ExperienceDTO() {}

    public ExperienceDTO(String name, double yearsOfExperience) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
