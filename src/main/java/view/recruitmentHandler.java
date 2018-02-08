package view;

import controller.Controller;
import model.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "recruitmentHandler")
public class recruitmentHandler implements Serializable {

    private final Controller controller = new Controller();
    private Person person;
    private User user;
    private Role role;
    private List<Availability> availabilities;
    private List<Experience> experiences;

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
    private final String regRoleError = "There was an error when trying to register the role";
    private final String regPersonExpError = "There was an error when trying to register the PersonExperience";
    private final String loginError = "There was an error when trying to log in";

    private boolean success = false;

    private static final Logger LOG = Logger.getLogger(recruitmentHandler.class.getName());


    // This method will create a person and a user,
    // connect the person to the user and register the user in the database
    public void regPerson(){
        try {
            person = new Person(firstName, lastName, ssn, email);
            user = new User(username, password);
            user.setPerson(person);
            controller.registerUser(person, user);
            success = true;
        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, regPersonError, registerPersonException);
        }
    }

    /**
     *
     */
    public void regAvailabilities() {
        try {
           // controller.createAvailability(long personID, Date fromDate, Date toDate);

        } catch (Exception registerAvailabilityException) {
            LOG.log(Level.WARNING, regAvailabilityError, registerAvailabilityException);
        }

    }

    /**
     *
     */
    public void regExperiences() {
        try {
           // controller.registerExperiences();
        } catch (Exception registerExperienceException) {
            LOG.log(Level.WARNING, regExperienceError, registerExperienceException);
        }
    }

    /**
     *
     */
    public void regRole(){
        try {

        } catch (Exception registerRoleException) {
            LOG.log(Level.WARNING, regRoleError, registerRoleException);
        }

    }

    /**
     *
     */
    public void regPersonExperiences() {
        try {

        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, regPersonExpError, registerPersonException);
        }

    }

    /**
     *
     */
    public void regJobApplication() {
        try {
          //  controller.registerJobApplication(person, experienceList, yearsOfExperienceList, availabilitiesList);
        } catch(Exception registerJobAppException) {
            LOG.log(Level.WARNING, regJobAppError, registerJobAppException);
        }
    }

    /**
     *
     */
    public void login() {
        try {
            boolean loginSuccess = controller.login(username, password);
        } catch (Exception loginException) {
            LOG.log(Level.WARNING, loginError, loginException);
        }

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
