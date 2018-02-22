package viewmodel.entrypoints;

import common.JobApplicationDTO;
import common.SystemException;
import controller.Controller;
import viewmodel.request.JobApplicationRequest;
import viewmodel.response.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jobapplications")
public class JobApplications {
    private final Controller controller = new Controller();

    /**
     * REST method for fetching the JobApplications.
     * @return A <code>List</code> containing <code>JobApplicationDTO</code>s,
     * is converted to a JSON object if asked for by REST.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplications() {
        return controller.fetchJobApplications();
    }

    /**
     * Creates a job application from the given info.
     * @param jobApplicationRequest Converted from JSON into an <code>JobApplicationRequest</code> object
     *                              which contains the person, experiences, availability dates and application
     *                              which is registered as an job application.
     * @throws SystemException in case something goes wrong during registration
     *                         which is sent as a response back to the client.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message createJobApplication(JobApplicationRequest jobApplicationRequest) throws SystemException {
        controller.registerJobApplication(
                jobApplicationRequest.getPerson(),
                jobApplicationRequest.getExperiences(),
                jobApplicationRequest.getAvailabilities(),
                jobApplicationRequest.getApplications()
        );
        return new Message("SUCCESS", "Registered the job application.");
    }
}
