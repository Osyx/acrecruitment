package io.osyx.acrecuritment.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationDTO {

    public String date;
    public String accepted;

    public ApplicationDTO(JSONObject json_application) throws JSONException {
        this.date = json_application.getString("date");
        this.accepted = json_application.getString("accepted");
    }

    public ApplicationDTO(String date) {
        this.date = date;
    }
}
