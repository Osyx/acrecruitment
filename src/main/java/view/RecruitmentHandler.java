package view;

import controller.Controller;
import integration.entity.*;
import common.*;
import model.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;
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

    private List <JobApplicationDTO> jobApplications = new ArrayList<>();
    private List<ExperienceDTO> experienceDTOs = new ArrayList<>();
    private List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();
    private List<ApplicationDTO> applicationDTOs = new ArrayList<>();

    private Experience experience;
    private String[] experienceNames = new String[5];
    private Double[] years = new Double[5];

    private java.util.Date fromDate;
    private java.util.Date toDate;
    private java.sql.Date fromSQLDate;
    private java.sql.Date toSQLDate;
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


    /**
     * Registers a user in the database
     */
    public void regUser(){
        try {
            if(conPassword == password){
                userDTO = new UserDTO(username, password);
                controller.registerUser(userDTO);
                success = true;
            }
        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, Messages.REGISTER_USER_ERROR.name(), registerPersonException);
        }
    }

    /**
     * Registers a persons availabilities
     */
    public void regAvailability() {
        try {
            dateConverter();
            availabilityDTO = new AvailabilityDTO(fromSQLDate, toSQLDate);
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
            for (int i = 0; i < experienceNames.length; i++) {
                if(years[i] != null & experienceNames[i] != null & experienceNames[i].trim().length() > 2) {
                    ExperienceDTO TempExperienceDTO = new ExperienceDTO(experienceNames[i], years[i]);
                    experienceDTOs.add(TempExperienceDTO);
                }
            }

        } catch (Exception registerExperienceException) {
            LOG.log(Level.WARNING, regExperienceError, registerExperienceException);
        }
    }

    /**
     * Converts a java.util.Date to java.sql.Date
     */
    public void dateConverter(){
        fromSQLDate = new java.sql.Date(fromDate.getTime());
        toSQLDate = new java.sql.Date(toDate.getTime());
    }

    /**
     * Registers the date of a new job application
     */
    public void regApplication() {
        try {
            java.util.Date regDate = Calendar.getInstance().getTime();
            Date regSQLDate = new java.sql.Date(regDate.getTime());
            applicationDTO = new ApplicationDTO(regSQLDate);
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
            personDTO.setRole("applicant");
            regExperiences();
            regAvailability();
            regApplication();
            controller.registerRESTJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
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
           // LOG.log(Level.WARNING, Messages.ACCEPT_DECLINE_APP_ERROR.name(), acceptDeclieAppException);
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

    public java.util.Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.util.Date fromDate) {
        this.fromDate = fromDate;
    }

    public java.util.Date getToDate() {
        return toDate;
    }

    public void setToDate(java.util.Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromSQLDate() {
        return fromSQLDate;
    }

    public void setFromSQLDate(Date fromSQLDate) {
        this.fromSQLDate = fromSQLDate;
    }

    public Date getToSQLDate() {
        return toSQLDate;
    }

    public void setToSQLDate(Date toSQLDate) {
        this.toSQLDate = toSQLDate;
    }

    public String[] getExperienceNames() {
        return experienceNames;
    }

    public void setExperienceNames(String[] experienceNames) {
        this.experienceNames = experienceNames;
    }

    public Double[] getYears() {
        return years;
    }

    public void setYears(Double[] years) {
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
