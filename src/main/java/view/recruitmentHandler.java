package view;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Controller;
import model.Availability;
import model.Experience;
import model.Person;
import model.User;
import model.Role;
import sun.rmi.runtime.Log;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "recruitmentHandler")
public class recruitmentHandler implements Serializable{

    private Controller controller = new Controller();
    private Person person;
    private User user;
    private Experience experience;
 //   private List<Availability> availability;
    private Role role;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String conPassword;
    private String regPersonError = "There was an error when trying to register";
    private String regJobAppError = "There was an error when trying to register the job application";
    private String regAvailabilityError = "There was an error when trying to register the availability";
    private String regExperienceError = "There was an error when trying to register the experience";
    private String regRoleError = "There was an error when trying to register the role";
    private String regPersonExpError = "There was an error when trying to register the PersonExperience";
    private String loginError = "There was an error when trying to log in";

    private static final Logger LOG = Logger.getLogger(recruitmentHandler.class.getName());


    public void regPerson(){
        LOG.log(Level.INFO, "Hej");

            person = new Person(firstName, lastName, ssn, email);
            user = new User(username, password);
            user.setPerson(person);
            controller.registerUser(person, user);

            LOG.log(Level.INFO, "FEEEEEL");
            System.out.println(regPersonError);


    }

    public void regAvailabilites(){
        try{
           // controller.createAvailability(long personID, Date fromDate, Date toDate);

        }catch(Exception registerAvailabilityException){
            System.out.println(regAvailabilityError);
        }

    }

    public void regExperiences(){
        try {
           // controller.registerExperiences();
        }catch(Exception registerExperienceException){
            System.out.println(regExperienceError);
        }
    }

    public void regRole(){
        try{

        }catch(Exception registerRoleException){
            System.out.println(regRoleError);
        }

    }

    public void regPersonExperiences(){
        try{

        }catch(Exception registerPersonExpEexception){
            System.out.println(regPersonExpError);
        }

    }

    public void regJobApplication(){
        try {


          //  controller.registerJobApplication(person, experienceList, yearsOfExprienceList, availabiliesList);
        }catch(Exception registerJobAppException){
            System.out.println(regJobAppError);
        }
    }

    public void login(){
        try {
            boolean loginSuccess = controller.login(username, password);
        } catch (Exception loginException) {
            System.out.println(loginError);
        }

    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSsn(String ssn){
        this.ssn = ssn;
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

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }


}
