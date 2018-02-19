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
    @Path("/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplications(@PathParam("username") String username, @PathParam("password") String password) throws SystemException {
        if(controller.login(username, password))
            return controller.fetchJobApplications();
        else
            throw new SystemException(ErrorMessages.LOGIN_ERROR.getErrorMessage(), ErrorMessages.LOGIN_ERROR.name());
    }

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
