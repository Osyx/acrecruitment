package viewmodel.entrypoints;

import common.JobApplicationDTO;
import controller.Controller;
import model.JobApplication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
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
}
