package view;

import controller.Controller;
import integration.entity.*;
import common.*;
import model.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "recruitmentHandler")
public class RecruitmentHandler implements Serializable {

    private final Controller controller = new Controller();
    private PersonDTO personDTO;
    private PersonPublicDTO personPublicDTO;
    private UserDTO userDTO;
    private RoleDTO roleDTO;
    private AvailabilityDTO availabilityDTO;
    private ExperienceDTO experienceDTO;
    private ApplicationDTO applicationDTO;
    private JobApplicationDTO jobApplicationDTO;

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
    private String statusApplication;

    private final String regJobAppDTOError = "There was an error when trying to register the job application DTO";
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
     * Registers a user in the database
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
     * Registers a persons availabilities
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
     * Registers a persons experiences
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
     * Registers the date of a new job application
     */
    public void regApplication() {
        try {
            java.util.Date regDate = Calendar.getInstance().getTime();
            Date regSQLDate = new java.sql.Date(regDate.getTime());
            applicationDTO = new ApplicationDTO(applicationID, regSQLDate);  //Ska jag skicka med ett ID här? Borde inte det vara ett autoincrement?
        } catch (Exception registerApplicationException) {
            LOG.log(Level.WARNING, regApplicationError, registerApplicationException);
        }
    }

    /**
     * Registers a job application DTO
     */
    public void regJobAppDTO() { // skapade denna om det behövs
        try {
            personPublicDTO = new PersonPublicDTO(firstName, lastName, email);
            jobApplicationDTO = new JobApplicationDTO(personPublicDTO, availabilityDTOs, experienceDTOs, applicationDTO);
        } catch(Exception registerJobAppDTOException) {
            LOG.log(Level.WARNING, regJobAppDTOError, registerJobAppDTOException);
        }


    }

    /**
     * Registers a job application
     */
    public void regJobApplication() {
        try {
            personDTO = new PersonDTO(firstName, lastName, ssn, email);
            regExperiences();
            regAvailability();
            regApplication();
            controller.registerJobApplication(personDTO, userDTO, experienceDTOs, availabilityDTOs, applicationDTO);
        } catch(Exception registerJobAppException) {
            LOG.log(Level.WARNING, Messages.REGISTER_JOB_APP_ERROR.name(), registerJobAppException);
        }


    }

    /**
     * Registers a job application (REST) without a user login
     */
    public void regRestJobApplication() { // Var det tillåtet att regga sig utan att logga in?
        try {
            personDTO = new PersonDTO(firstName, lastName, ssn, email);
            regExperiences();
            regAvailability();
            regApplication();
            controller.registerRESTJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
        } catch(Exception registerRestJobAppException) {
            LOG.log(Level.WARNING, Messages.REGISTER_JOB_APP_ERROR.name(), registerRestJobAppException);
        }


    }

    /**
     * Accepts or declines a jobb application
     */
    public void acceptOrDeclineApplication(){  // bör eventuellt delas upp i två metoder?
        try {
            if(statusApplication == "accepted") {
                applicationDTO.setAccepted("accepted");
            }else if(statusApplication == "declined"){
                applicationDTO.setAccepted("declined");
            }
            controller.acceptOrDeclineJobApplication(applicationDTO);
        }catch (Exception acceptDeclieAppException){
            LOG.log(Level.WARNING, Messages.ACCEPT_DECLINE_APP_ERROR.name(), acceptDeclieAppException);
        }
    }

    /**
     * Will let a user log in
     */
    public void login() {
        try {
            roleDTO = controller.login(username, password);
        } catch (Exception loginException) {
            LOG.log(Level.WARNING, Messages.LOGIN_ERROR.name(), loginException);
        }
    }

    /**
     * Fetches job applications
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
