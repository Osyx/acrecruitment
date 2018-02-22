package view;

import controller.Controller;
import integration.entity.*;
import model.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "recruitmentHandler")
public class recruitmentHandler implements Serializable {

    private final Controller controller = new Controller();
    private Person person;
    private User user;
    private Role role;
    private Experience experience;
    private List<Availability> availabilities;
    private List<Experience> experiences;
    private List<Double> yearsOfExperiences;
    private List <JobApplication> jobApplication;
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
    private final String regPersonError = "There was an error when trying to register";
    private final String regJobAppError = "There was an error when trying to register the job application";
    private final String regAvailabilityError = "There was an error when trying to register the availability";
    private final String regExperienceError = "There was an error when trying to register the experience";
    private final String regPersonExpError = "There was an error when trying to register the PersonExperience";
    private final String loginError = "There was an error when trying to log in";

    private boolean success = false;

    private static final Logger LOG = Logger.getLogger(recruitmentHandler.class.getName());


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
    public void regPerson(){
        try {
            person = new Person(firstName, lastName, ssn, email);
            user = new User(username, password);
            user.setPerson(person);
            success = controller.registerUser(person, user); // Ska väl sparas i en variabel?
            success = true;
        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, regPersonError, registerPersonException);
        }
    }

    /**
     * registers a persons availabilities for a job application
     */
    public void regAvailabilities() {
        try {
           controller.createAvailability(person, fromDate, toDate);
        } catch (Exception registerAvailabilityException) {
            LOG.log(Level.WARNING, regAvailabilityError, registerAvailabilityException);
        }

    }

    /**
     * registers a persons experiences
     */
    public Experience regExperiences(String experienceName) { // Hur blir det här om vi får ett exception?
        try {
            experience = controller.createExperience(experienceName);
            return experience;
        } catch (Exception registerExperienceException) {
            LOG.log(Level.WARNING, regExperienceError, registerExperienceException);
        }
        return experience;
    }

    /**
     *
     */
    public void regPersonExperiences() {
        try {

            for (int i = 0; i < experienceNames.size(); i++){
                String temp = years.get(i);
                experience = regExperiences(experienceNames.get(i));
                controller.createPersonExperience(person, experience, Double.parseDouble(temp));
            }
        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, regPersonExpError, registerPersonException);
        }

    }

    /**
     * registers a persons job application
     */
    public void regJobApplication() {
        try {
            regPerson();
            regPersonExperiences();
            regAvailabilities();
            //controller.registerJobApplication(person, experiences, yearsOfExperiences, availabilities);
        } catch(Exception registerJobAppException) {
            LOG.log(Level.WARNING, regJobAppError, registerJobAppException);
        }
    }

    /**
     * will let a user log in
     */
    public void login() {
        try {
            boolean loginSuccess = controller.login(username, password);
        } catch (Exception loginException) {
            LOG.log(Level.WARNING, loginError, loginException);
        }
    }

    /**
     * fetches a persons availabilities according to the ssn
     */
    public List<Availability> getAvailabilities(){
        availabilities = controller.fetchAvailabilities(ssn);
        return availabilities;
    }

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
     * fetches job applications by time period or date of registration
     */
    public List<JobApplication> fetchJobApplications() {
        //jobApplication = controller.fetchJobApplications(searchTimePeriod, fromDate, toDate);
        return jobApplication;
    }

    /**
     * fetches job applications by experience

     public List<JobApplication> fetchJobApplications() {
     return controller.fetchJobApplications();
     }
     */

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
