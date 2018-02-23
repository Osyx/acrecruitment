package io.osyx.acrecuritment.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonPublicDTO {
    public String name;
    public String surname;
    public String email;

    public PersonPublicDTO() {}

    public PersonPublicDTO(JSONObject json_person) throws JSONException {
        this.name = json_person.getString("name");
        this.surname = json_person.getString("surname");
        this.email = json_person.getString("email");
    }
}
