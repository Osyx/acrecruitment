package view;

import controller.Controller;
import integration.entity.*;
import common.*;
import model.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "recruitmentHandler")
public class RecruitmentHandler implements Serializable {

    private final Controller controller = new Controller();
    private PersonDTO personDTO;
    private PersonPublicDTO personPublicDTO;
    private UserDTO userDTO;
    private AvailabilityDTO availabilityDTO;
    private ExperienceDTO experienceDTO;
    private List <JobApplicationDTO> jobApplications;
    private List<ExperienceDTO> experienceDTOs;
    private List<AvailabilityDTO> availabilityDTOs;
    private List<ApplicationDTO> applicationDTOs;

    private Experience experience;
    private List <String> experienceNames;
    private List <String> years;
    private List<Double> yearsOfExperiences;
    private double yearsOfExperience;

    private Date fromDate;
    private Date toDate;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String conPassword;

    private final String regPersonError = "There was an error when trying to register";
    private final String regJobAppError = "There was an error when trying to register the job application";
    private final String regAvailabilityError = "There was an error when trying to register the availability";
    private final String regExperienceError = "There was an error when trying to register the experience";
    private final String regApplicationError = "There was an error when trying to register the application";
    private final String loginError = "There was an error when trying to log in";

    private boolean success = false;

    private static final Logger LOG = Logger.getLogger(RecruitmentHandler.class.getName());


    {
        experienceNames = new ArrayList<>();
        years = new ArrayList<>();
        for(int i = 1; i<=10; i++) {
            experienceNames.add("");
            years.add("");
        }
    }

    /**
     * Creates a person and a user, connects the person
     * to the user and registers the user in the database
     */
    public void regUser(){
        try {
            userDTO = new UserDTO(username, password);
            controller.registerUser(userDTO);
            success = true;
        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, Messages.REGISTER_USER_ERROR.name(), registerPersonException);
        }
    }

    /**
     * Registers a persons job application
     */
    public void regAvailability() {
        try {
            availabilityDTO = new AvailabilityDTO(fromDate, toDate);
            availabilityDTOs.add(availabilityDTO);
        } catch (Exception registerAvailabilityException) {
            LOG.log(Level.WARNING, regAvailabilityError, registerAvailabilityException);
        }

    }

    /**
     * registers a persons experiences
     */
    public void regExperiences() {
        try {
            for (int i = 0; i < years.size(); i++) {
                Double temp = Double.parseDouble(years.get(i));
                ExperienceDTO TempExperienceDTO = new ExperienceDTO(experienceNames.get(i), temp);
                experienceDTOs.add(TempExperienceDTO);
            }

        } catch (Exception registerExperienceException) {
            LOG.log(Level.WARNING, regExperienceError, registerExperienceException);
        }
    }


    /**
     *
     */
    public void regApplication() {
        try {


        } catch (Exception registerApplicationException) {
            LOG.log(Level.WARNING, regApplicationError, registerApplicationException);
        }

    }

    /**
     * registers a persons job application
     */
    public void regJobApplication() {
        try {
            regPerson();
            regExperiences();
            regAvailability();
            regApplication();
            controller.registerJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTOs);
        } catch(Exception registerJobAppException) {
            LOG.log(Level.WARNING, Messages.REGISTER_JOB_APP_ERROR.name(), registerJobAppException);
        }


    }

    /**
     * Will let a user log in
     */
    public void login() {
        try {
            //boolean loginSuccess = controller.login(username, password);
        } catch (Exception loginException) {
            LOG.log(Level.WARNING, Messages.LOGIN_ERROR.name(), loginException);
        }
    }

    /**
     *
     * @return
     */
    /*public List<Availability> getAvailabilities(){
        //availabilities = controller.fetchAvailabilities(ssn);
        //return availabilities;
    }*/

    /**
     * fetches a persons experiences according to
     */
    /*public List<Experience> getExperiences(){
        experiences = controller.fetchExperiences();
        return experiences;
    }*/

    /**
     * fetches a persons years of experience for all experiences according to the
     */

  /*  public List<Double> getYearsOfExperiences(){
        yearsOfExperiences = controller.fetchYearsOfExperiences();
        return yearsOfExperiences;
    }
  */
    /**
     * fetches job applications
     */
    public List<JobApplicationDTO> fetchJobApplications() {
        try {
            jobApplications = controller.fetchJobApplications();
            return jobApplications;
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
        return null;
    }



    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<String> getExperienceNames() {
        return experienceNames;
    }

    public void setExperienceNames(List<String> experienceNames) {
        this.experienceNames = experienceNames;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getSsn() {
        return ssn;
    }

    public String getConPassword() {
        return conPassword;
    }

    public boolean getSuccess() {
        boolean tempSuccess = success;
        success = false;
        return tempSuccess;
    }


}
