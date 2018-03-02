package controller;

import common.*;
import model.*;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class Controller {
    private final JobApplication jobApplication = new JobApplication();
    private final User user = new User();
    private final SessionFactory factory = Factory.getFactory();

    /**
     * Checks if the login details are correct.
     * @param username The username of the user to be logged in.
     * @param password  The password of the user to be logged in.
     * @return the role the user has.
     * @throws SystemException if the wrong login details are given.
     */
    public RoleDTO login(String username, String password) throws SystemException {
        RoleDTO login;
        factory.getCurrentSession().beginTransaction();
        try {
             login = user.login(username, password);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return login;
    }

    /**
     * Register a user with a username and password.
     * @param userDTO The user details for the user to be added.
     */
    public void registerUser(UserDTO userDTO) throws SystemException {
        factory.getCurrentSession().beginTransaction();
        try {
            user.registerUser(userDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
    }

    /**
     * Fetches the available experiences from the database.
     * @return A list of experienceDTOs containing all experiences available.
     */
    public List<ExperienceDTO> getExperiences() {
        List<ExperienceDTO> experienceDTOs;
        factory.getCurrentSession().beginTransaction();
        try {
            experienceDTOs = jobApplication.getExperiences();
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return experienceDTOs;
    }

    /**
     * Registers a new job application.
     * @param personDTO The DTO of the person to be the applicant.
     * @param userDTO The DTO of the user registering.
     * @param experienceDTOs The list of experiences that the applicant has.
     * @param availabilityDTOs The list of dates that the applicant is available.
     * @param applicationDTO The dates of when the applicant registered the applications.
     * @throws SystemException in case that there is an error when registering the application to the database.
     */
    public void registerJobApplication(PersonDTO personDTO, UserDTO userDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplication.registerJobApplication(personDTO, userDTO, experienceDTOs, availabilityDTOs, applicationDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
    }

    /**
     * Register a new job application, made for the REST endpoints where a user isn't necessary.
     * @param personDTO The applicant that is applying for a job.
     * @param experienceDTOs The previous experiences that the applicant has.
     * @param availabilityDTOs The time slots where the applicant can work.
     * @param applicationDTO The details concerning this application, e.g. registration date.
     * @throws SystemException in case of an error during registration.
     */
    public void registerRESTJobApplication(PersonDTO personDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplication.registerRESTJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
    }

    /**
     * Fetches all job applications.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> fetchJobApplications() throws SystemException {
        List<JobApplicationDTO> jobApplications;
        factory.getCurrentSession().beginTransaction();
        try {
             jobApplications = jobApplication.getJobApplications();
        }
        finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return jobApplications;
    }

    /**
     * Fetches all job applications submitted by persons with a certain name.
     * @param personDTO A DTO containing the name that we want the applicants to have.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> fetchJobApplicationsByName(PersonDTO personDTO) throws SystemException {
        List<JobApplicationDTO> jobApplications;
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplications = jobApplication.getJobApplicationsByName(personDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return jobApplications;
    }

    /**
     * Fetches all job applications that has a certain experience.
     * @param experienceDTO An DTO containing the experience that we want the applicants to have.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> fetchJobApplicationsByExperience(ExperienceDTO experienceDTO) throws SystemException {
        List<JobApplicationDTO> jobApplications;
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplications = jobApplication.getJobApplicationsByExperience(experienceDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return jobApplications;
    }

    /**
     * Fetches all job applications by the date the application was registered.
     * @param applicationDTO an applicationDTO containing the date that we want the applicants to have.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> fetchJobApplicationsByAppDate(ApplicationDTO applicationDTO) throws SystemException {
        List<JobApplicationDTO> jobApplications;
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplications = jobApplication.getJobApplicationsByAppDate(applicationDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return jobApplications;
    }

    /**
     * Fetches all job applications by availability.
     * @param availabilityDTO the availability that we want the applicants to have.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> fetchJobApplicationsByAvailability(AvailabilityDTO availabilityDTO) throws SystemException {
        List<JobApplicationDTO> jobApplications;
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplications = jobApplication.getJobApplicationsByAvailability(availabilityDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
        return jobApplications;
    }

    /**
     * Accept or decline a job application.
     * @param applicationDTO A DTO encapsulating the job application to be changed and has the <code>accepted</code> value changed to the new value.
     * @throws SystemException in case of an error during update of the application status.
     */
    public void acceptOrDeclineJobApplication(ApplicationDTO applicationDTO) throws SystemException {
        factory.getCurrentSession().beginTransaction();
        try {
            jobApplication.acceptOrDeclineJobApplication(applicationDTO);
        } finally {
            factory.getCurrentSession().getTransaction().commit();
        }
    }

}
