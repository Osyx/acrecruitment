package view;

import controller.Controller;
import integration.entity.*;
import common.*;
import viewmodel.response.Message;

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
    private UserDTO userDTO;
    private Role role;
    private Experience experience;
    private List<Availability> availabilities;
    private List<Experience> experiences;
    private List<Double> yearsOfExperiences;
    private List <JobApplicationDTO> jobApplications;
    private List <String> experienceNames;
    private List <String> years;
    private Date fromDate;
    private Date toDate;
    private double yearsOfExperience;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String conPassword;
    private String searchTimePeriod = "time period";
    private String searchRegDate = "date of registration";
    private String searchExperience = "experience";
    private String searchName = "name";

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
    public void regJobApplication() {
        try {
            //controller.registerJobApplication(person, experiences, yearsOfExperiences, availabilities);
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
