package controller;

import common.*;
import integration.Integration;
import model.*;

import java.util.List;

//@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private final Integration integration = new Integration();
    private final JobApplication jobApplication = new JobApplication();

    /**
     * Checks if the login details are correct.
     * @param username The username of the user to be logged in.
     * @param password  The password of the user to be logged in.
     * @return a boolean indicating whether it was the correct details or not.
     * <code>True</code> for correct, <code>false</code> for incorrect.
     */
    public boolean login(String username, String password) {
        return integration.login(username, password);
    }

    /**
     * Register a person with a username and password.
     * @param person The person info to be added to the database.
     * @param user The user details for the person to be added.
     */
    public void registerUser(PersonDTO person, UserDTO user) throws SystemException {
        integration.registerUser(person, user);
    }

    /**
     * Registers a new job application.
     * @param personDTO The DTO of the person to be the applicant.
     * @param experienceDTOs The list of experiences that the applicant has.
     * @param availabilityDTOs The list of dates that the applicant is available.
     * @param applicationDTO The dates of when the applicant registered the applications.
     * @throws SystemException in case that there is an error when registering the application to the database.
     */
    public void registerJobApplication(PersonDTO personDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        jobApplication.registerJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
    }

    /**
     * Fetches all job applications.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     */
    public List<JobApplicationDTO> fetchJobApplications() {
        return jobApplication.getJobApplications();
    }

    /**
     * Accept or decline a job application.
     * @param applicationDTO A DTO encapsulating the job application to be changed and has the <code>accepted</code> value changed to the new value.
     */
    public void acceptOrDeclineJobApplication(ApplicationDTO applicationDTO) {
        jobApplication.acceptOrDeclineJobApplication(applicationDTO);
    }

}
