package io.osyx.acrecuritment.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class AvailabilityDTO {

    public String fromDate;
    public String toDate;

    public AvailabilityDTO(JSONObject json_availabilities) throws JSONException {
        this.fromDate = json_availabilities.getString("fromDate");
        this.toDate = json_availabilities.getString("toDate");
    }
}
