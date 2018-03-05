package io.osyx.acrecuritment;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.osyx.acrecuritment.dto.ApplicationDTO;
import io.osyx.acrecuritment.dto.AvailabilityDTO;
import io.osyx.acrecuritment.dto.ExperienceDTO;
import io.osyx.acrecuritment.dto.PersonPublicDTO;
import io.osyx.acrecuritment.helpers.JobApplication;
import io.osyx.acrecuritment.helpers.JobApplicationAdapter;


public class ListJobAppsFragment extends Fragment{
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String HOST = "http://130.229.189.200:8080";

    public ListJobAppsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //HOST = getArguments().getString("host");
        return inflater.inflate(R.layout.list_job_apps, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new AsyncFetch().execute();
    }

    public static ListJobAppsFragment newInstance(String host) {
        ListJobAppsFragment ListJobAppsFragment = new ListJobAppsFragment();
        Bundle args = new Bundle();
        args.putString("host", host);
        ListJobAppsFragment.setArguments(args);
        return  ListJobAppsFragment;
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getView().getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(HOST + "/acrecruitment/api/jobApplications/en");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null)
                        result.append(line);

                    return (result.toString());

                } else
                    return ("Failed");

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            List<JobApplication> data = new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                for(int i = 0; i < jArray.length(); i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    JobApplication jobApplication = new JobApplication();
                    JSONObject json_person = json_data.getJSONObject("person");
                    JSONObject json_application = json_data.getJSONObject("application");
                    JSONArray json_availabilities = json_data.getJSONArray("availabilities");
                    JSONArray json_experiences = json_data.getJSONArray("experiences");
                    jobApplication.person = new PersonPublicDTO(json_person);
                    jobApplication.application = new ApplicationDTO(json_application);
                    for(int j = 0; j < json_availabilities.length(); j++) {
                        AvailabilityDTO availabilityDTO = new AvailabilityDTO(json_availabilities.getJSONObject(j));
                        jobApplication.availabilities.add(availabilityDTO);
                    }
                    for(int j = 0; j < json_experiences.length(); j++) {
                        ExperienceDTO experienceDTO = new ExperienceDTO(json_experiences.getJSONObject(j));
                        jobApplication.experiences.add(experienceDTO);
                    }
                    data.add(jobApplication);
                }

                RecyclerView mRVJobApps = getView().findViewById(R.id.jobAppList);
                JobApplicationAdapter mAdapter = new JobApplicationAdapter(getView().getContext(), data);
                mRVJobApps.setAdapter(mAdapter);
                mRVJobApps.setLayoutManager(new LinearLayoutManager(getView().getContext()));

            } catch (JSONException e) {
                Log.e("LIST_JOB", e.getMessage() , e);
                Toast.makeText(getView().getContext(), "Error when fetching the job applications. Try again later.", Toast.LENGTH_LONG).show();
            }

        }

    }
}
