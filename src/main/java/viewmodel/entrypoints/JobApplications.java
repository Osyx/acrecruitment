package viewmodel.entrypoints;

import common.*;
import controller.Controller;
import viewmodel.request.JobApplicationRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jobapplications")
public class JobApplications {
    Controller controller = new Controller();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplications() {
        return controller.fetchJobApplications();
    }
/*
    @GET
    @Path("/TimePeriod/{fromDate}/{toDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplicationsByTP(@PathParam("fromDate") Date fromDate, @PathParam("toDate") Date toDate) {
        return controller.fetchJobApplications("time period", fromDate, toDate);
    }

    @GET
    @Path("/DateOfRegistration/{fromDate}/{toDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplicationsByDOR(@PathParam("fromDate") Date fromDate, @PathParam("toDate") Date toDate) {
        return controller.fetchJobApplications("date of registration", fromDate, toDate);
    }

    @GET
    @Path("/Experience/{experience}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplicationsByExperience(@PathParam("experience") String experience) {
        return controller.fetchJobApplications("experience", experience);
    }

    @GET
    @Path("/Name/{firstname}/{surname}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplicationsByName(@PathParam("firstname") String firstname, @PathParam("surname") String surname) {
        return controller.fetchJobApplications("name", firstname, surname);
    }
    */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createJobApplication(JobApplicationRequest jobApplicationRequest) {
        controller.registerJobApplication(
                jobApplicationRequest.getPerson(),
                jobApplicationRequest.getExperiences(),
                jobApplicationRequest.getAvailabilities(),
                jobApplicationRequest.getApplicationDates()
        );
    }
}
