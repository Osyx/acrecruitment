package viewmodel.entrypoints;

import common.JobApplicationDTO;
import common.SystemException;
import controller.Controller;
import viewmodel.request.JobApplicationRequest;
import viewmodel.response.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The class which acts as an endpoint for the REST API for the job applications.
 */
@Path("/jobApplications")
public class JobApplications {
    private final Controller controller = new Controller();

    /**
     * REST method for fetching the JobApplications.
     * @return A <code>List</code> containing <code>JobApplicationDTO</code>s,
     * is converted to a JSON object if asked for by REST.
     * @throws SystemException if an error occurs during the fetch.
     */
    @GET
    @Path("/{lang}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobApplicationDTO> getJobApplications(@PathParam("lang") String lang) throws SystemException {
        return controller.fetchJobApplications(lang);
    }

    /**
     * Creates a job application from the given info.
     * @param jobApplicationRequest Converted from JSON into an <code>JobApplicationRequest</code> object
     *                              which contains the person, experiences, availability dates and application
     *                              which is registered as an job application.
     * @return A success message in case of a successful registration.
     * @throws SystemException in case something goes wrong during registration
     *                         which is sent as a response back to the client.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message createJobApplication(JobApplicationRequest jobApplicationRequest) throws SystemException {
        controller.registerRESTJobApplication(
                jobApplicationRequest.getPerson(),
                jobApplicationRequest.getExperiences(),
                jobApplicationRequest.getAvailabilities(),
                jobApplicationRequest.getApplication()
        );
        return new Message("SUCCESS", "Registered the job application.");
    }
}
