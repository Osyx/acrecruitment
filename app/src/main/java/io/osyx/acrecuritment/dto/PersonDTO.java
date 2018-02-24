package io.osyx.acrecuritment.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonDTO {
    public String name;
    public String surname;
    public String ssn;
    public String email;

    public PersonDTO() {}

    public PersonDTO(String name, String surname, String ssn, String email) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
    }
}
