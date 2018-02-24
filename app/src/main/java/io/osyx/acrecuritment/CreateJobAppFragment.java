package io.osyx.acrecuritment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.osyx.acrecuritment.dto.ApplicationDTO;
import io.osyx.acrecuritment.dto.AvailabilityDTO;
import io.osyx.acrecuritment.dto.ExperienceDTO;
import io.osyx.acrecuritment.dto.JobApplicationRequest;
import io.osyx.acrecuritment.dto.PersonDTO;

public class CreateJobAppFragment extends Fragment {

    public CreateJobAppFragment() {}
    private String HOST = "http://192.168.10.218:8080";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_application, container, false);

        Button b = v.findViewById(R.id.submitButton);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitJobApplication(v);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //HOST = getArguments().getString("host");
    }

    public static CreateJobAppFragment newInstance(String host) {
        CreateJobAppFragment createJobAppFragment = new CreateJobAppFragment();
        Bundle args = new Bundle();
        args.putString("host", host);
        createJobAppFragment.setArguments(args);
        return  createJobAppFragment;
    }

    public void submitJobApplication(View view) {
        EditText firstName = getView().findViewById(R.id.firstname);
        EditText surname = getView().findViewById(R.id.surname);
        EditText ssn = getView().findViewById(R.id.ssn);
        EditText email = getView().findViewById(R.id.email);
        PersonDTO personDTO = new PersonDTO(
                firstName.getText().toString(),
                surname.getText().toString(),
                ssn.getText().toString(),
                email.getText().toString()
        );
        List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();
        EditText availableFrom = getView().findViewById(R.id.availableFrom);
        EditText availableTo = getView().findViewById(R.id.availableTo);
        availabilityDTOs.add(
                new AvailabilityDTO(
                         availableFrom.getText().toString(),
                        availableTo.getText().toString()
                )
        );
        List<ExperienceDTO> experienceDTOs = new ArrayList<>();
        EditText experience = getView().findViewById(R.id.experiences);
        String[] experienceWithYears = experience.getText().toString().split(",");
        for(int i = 0; i < experienceWithYears.length; i++) {
            experienceDTOs.add(
                    new ExperienceDTO(
                            experienceWithYears[i],
                            Double.parseDouble(experienceWithYears[++i])
                    )
            );
        }
        ApplicationDTO applicationDTO = new ApplicationDTO(
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())
        );
        JobApplicationRequest jobApplicationRequest = new JobApplicationRequest(
                personDTO,
                availabilityDTOs,
                experienceDTOs,
                applicationDTO
        );
        JSONObject jsonObject = null;
        try {
            String json = jobApplicationRequest.toJSON();
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Toast.makeText(getView().getContext(), "Couldn't package the application :(", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        sendPost(jsonObject);
    }

    private void sendPost(JSONObject data) {
        RequestQueue queue = Volley.newRequestQueue(getView().getContext());
        String url = HOST + "/acrecruitment/api/jobapplications/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getView().getContext(), response.get("message").toString(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getView().getContext(), "Faulty response...", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getView().getContext(), "Couldn't send the application :(", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
        };
        queue.add(jsonObjectRequest);
    }
}