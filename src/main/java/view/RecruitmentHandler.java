package view;

import common.*;
import controller.Controller;
import integration.entity.Availability;
import integration.entity.Experience;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The RecruitmentHandler handles all communication with the client UI
 */
@ManagedBean(name = "recruitmentHandler")
@ViewScoped
public class RecruitmentHandler implements Serializable {

    private final Controller controller = new Controller();
    private PersonDTO personDTO;
    private AvailabilityDTO availabilityDTO;
    private ApplicationDTO applicationDTO;

    private List <JobApplicationDTO> jobApplications = new ArrayList<>();
    private List<ExperienceDTO> experienceDTOs = new ArrayList<>();
    private List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();

    private String[] experienceNames = new String[5];
    private Double[] years = new Double[5];

    private java.util.Date fromDate;
    private java.util.Date toDate;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String conPassword;

    private int searchSelection;
    private String searchName;
    private String searchLastName;
    private String searchExp;
    private java.util.Date searchFromDate;
    private java.util.Date searchToDate;

    private UIComponent registerButton;
    private UIComponent loginButton;
    private UIComponent registerAppButton;

    private static final Logger LOG = Logger.getLogger(RecruitmentHandler.class.getName());
    private final ResourceBundle rb = ResourceBundle.getBundle("messages");
    private LanguageBean languageBean = new LanguageBean();


    /**
     * Registers a user in the database
     */
    public void regUser(){
        try {
            if(conPassword.equals(password)){
                UserDTO userDTO = new UserDTO(username, password);
                controller.registerUser(userDTO);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", userDTO.getUsername());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("password", userDTO.getPassword());
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/confirmation.xhtml");
            } else {
                FacesMessage message = new FacesMessage(rb.getString("passwords_dont_match"));
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(registerButton.getClientId(context), message);
            }
        } catch (Exception registerPersonException) {
            LOG.log(Level.WARNING, Messages.REGISTER_USER_ERROR.name(), registerPersonException);
            FacesMessage message = new FacesMessage(
                    registerPersonException.getMessage() != null ?
                            registerPersonException.getMessage() : registerPersonException.getClass().getSimpleName().equals("SystemException") ?
                            ((SystemException) registerPersonException).getMessageKey() : registerPersonException.toString());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(registerButton.getClientId(context), message);
        }
    }

    /**
     * Will let a user log in
     */
    public void login(String from) {
        try {
            RoleDTO roleDTO = controller.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", roleDTO.getRole());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("password", password);
            if(from.isEmpty())
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/index.xhtml");
            else
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment" + from);
        } catch (Exception loginException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), loginException);
            FacesMessage message = new FacesMessage(
                    loginException.getMessage() != null ? 
                            loginException.getMessage() : loginException.getClass().getSimpleName().equals("SystemException") ? 
                                    ((SystemException) loginException).getMessageKey() : loginException.toString());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(loginButton.getClientId(context), message);
        }
    }

    /**
     * Is used to logout the current user.
     */
    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("username");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("password");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("role");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/index.xhtml");
        } catch (Exception e) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), e);
        }
    }

    /**
     * Registers a job application
     */
    public void regJobApplication() {
        try {
            if (isApplicant()) {
                personDTO = new PersonDTO(firstName, lastName, ssn, email);
                personDTO.setRole("applicant");
                regExperiences();
                regAvailability();
                regApplication();
                checkIfValidInput();
                UserDTO userDTO = new UserDTO(
                        (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"),
                        (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("password")
                );
                controller.registerJobApplication(personDTO, userDTO, experienceDTOs, availabilityDTOs, applicationDTO);
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/registeredjobapp.xhtml");
                } catch (Exception e) {
                    LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), e);
                }
            } else {
                throw new SystemException(Messages.LOGIN_ERROR.name(), Messages.USER_NOT_LOGGED_IN.getErrorMessageWithArg(rb.getString("wrong_input_applicant_status")));
            }
        } catch(Exception registerJobAppException) {
            LOG.log(Level.WARNING, Messages.REGISTER_JOB_APP_ERROR.name(), registerJobAppException);
            FacesMessage message = new FacesMessage(
                    registerJobAppException.getMessage() != null ?
                            registerJobAppException.getMessage() : registerJobAppException.getClass().getSimpleName().equals("SystemException") ?
                            ((SystemException) registerJobAppException).getMessageKey() : registerJobAppException.toString());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(registerAppButton.getClientId(context), message);
        }
    }

    /**
     * Fetches all job applications
     */
    public void fetchJobApplications() {
        try {
            jobApplications = controller.fetchJobApplications(languageBean.getLocaleCode());
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }


    /**
     * Fetches job applications by name of person
     */
    public void fetchJobApplicationsByName(){
        try {
            PersonDTO person = new PersonDTO();
            person.setName(Util.capitalize(searchName));
            person.setSurname(Util.capitalize(searchLastName));
            LOG.warning(person.toString());
            jobApplications = controller.fetchJobApplicationsByName(person, languageBean.getLocaleCode());
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by experience
     */
    public void fetchJobApplicationsByExperience(){
        try {
            ExperienceDTO exp = new ExperienceDTO();
            exp.setName(Util.capitalize(searchExp));
            jobApplications = controller.fetchJobApplicationsByExperience(exp, languageBean.getLocaleCode());
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by availability
     */
    public void fetchJobApplicationsByAvailability(){
        try {
            java.sql.Date searchFrom = dateConverter(searchFromDate);
            java.sql.Date searchTo = dateConverter(searchToDate);
            AvailabilityDTO availability = new AvailabilityDTO();
            availability.setFromDate(searchFrom);
            if(searchToDate != null)
                availability.setToDate(searchTo);
            jobApplications = controller.fetchJobApplicationsByAvailability(availability, languageBean.getLocaleCode());
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Accepts a job application
     * @param id is an id for the application that should be accepted
     */
    public void acceptApplication(long id) {
        try {
            ApplicationDTO application = new ApplicationDTO();
            application.setAccepted("accepted");
            application.setApplicationId(id);
            controller.acceptOrDeclineJobApplication(application);
            refreshList();
        }catch (Exception acceptDeclineAppException){
            LOG.log(Level.WARNING, Messages.ACCEPT_DECLINE_APP_ERROR.name(), acceptDeclineAppException);
        }
    }

    /**
     * Declines a job application
     * @param id is an id for the application that should be declined
     */
    public void declineApplication(long id) {
        try {
            ApplicationDTO application = new ApplicationDTO();
            application.setApplicationId(id);
            application.setAccepted("declined");
            controller.acceptOrDeclineJobApplication(application);
            refreshList();
        }catch (Exception acceptDeclineAppException){
            LOG.log(Level.WARNING, Messages.ACCEPT_DECLINE_APP_ERROR.name(), acceptDeclineAppException);
        }
    }




    public String getRegUsername() {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchLastName() {
        return searchLastName;
    }

    public void setSearchLastName(String searchLastName) {
        this.searchLastName = searchLastName;
    }

    public String getSearchExp() {
        return searchExp;
    }

    public void setSearchExp(String searchExp) {
        this.searchExp = searchExp;
    }

    public java.util.Date getSearchFromDate() {
        return searchFromDate;
    }

    public void setSearchFromDate(java.util.Date searchFromDate) {
        this.searchFromDate = searchFromDate;
    }

    public java.util.Date getSearchToDate() {
        return searchToDate;
    }

    public void setSearchToDate(java.util.Date searchToDate) {
        this.searchToDate = searchToDate;
    }

    public int getSearchSelection() {
        return searchSelection;
    }

    public void setSearchSelection(int searchSelection) {
        this.searchSelection = searchSelection;
    }

    public boolean isApplicant() {
        String role = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role");
        return role != null && role.equals("applicant");
    }

    public boolean isRecruit() {
        String role = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role");
        return role != null && role.equals("recruit");
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

    public ApplicationDTO getApplicationDTO() {
        return applicationDTO;
    }

    public void setApplicationDTO(ApplicationDTO applicationDTO) {
        this.applicationDTO = applicationDTO;
    }

    public List<JobApplicationDTO> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<JobApplicationDTO> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public List<ExperienceDTO> getExperienceDTOs() {
        return experienceDTOs;
    }

    public void setExperienceDTOs(List<ExperienceDTO> experienceDTOs) {
        this.experienceDTOs = experienceDTOs;
    }

    public List<AvailabilityDTO> getAvailabilityDTOs() {
        return availabilityDTOs;
    }

    public void setAvailabilityDTOs(List<AvailabilityDTO> availabilityDTOs) {
        this.availabilityDTOs = availabilityDTOs;
    }

    public UIComponent getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(UIComponent registerButton) {
        this.registerButton = registerButton;
    }

    public UIComponent getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(UIComponent loginButton) {
        this.loginButton = loginButton;
    }

    public UIComponent getRegisterAppButton() {
        return registerAppButton;
    }

    public void setRegisterAppButton(UIComponent registerAppButton) {
        this.registerAppButton = registerAppButton;
    }

    // private functions

    /**
     * Registers a persons availabilities
     */
    private void regAvailability() {
        try {
            java.sql.Date from = dateConverter(fromDate);
            java.sql.Date to = dateConverter(toDate);
            availabilityDTO = new AvailabilityDTO(from, to);
            availabilityDTOs.add(availabilityDTO);
        } catch (Exception registerAvailabilityException) {
            LOG.log(Level.WARNING, Messages.REGISTER_AVAILABILITY_ERROR.name(), registerAvailabilityException);
        }

    }

    /**
     * Registers a persons experiences
     */
    private void regExperiences() {
        try {
            for (int i = 0; i < experienceNames.length; i++) {
                if(years[i] != null & experienceNames[i] != null & experienceNames[i].trim().length() > 2) {
                    ExperienceDTO TempExperienceDTO = new ExperienceDTO(Util.capitalize(experienceNames[i]), years[i]);
                    experienceDTOs.add(TempExperienceDTO);
                }
            }

        } catch (Exception registerExperienceException) {
            LOG.log(Level.WARNING, Messages.REGISTER_EXPERIENCE_ERROR.name(), registerExperienceException);
        }
    }

    /**
     * Converts a java.util.Date to java.sql.Date
     * @param utilDate is the date that is supposed to be converted
     */
    private java.sql.Date dateConverter(java.util.Date utilDate){
        java.sql.Date sqlDate;
        sqlDate = utilDate != null ? new java.sql.Date(utilDate.getTime()): null;
        return sqlDate;
    }

    /**
     * Registers the date of a new job application
     */
    private void regApplication() {
        try {
            java.util.Date regDate = Calendar.getInstance().getTime();
            Date regSQLDate = new java.sql.Date(regDate.getTime());
            applicationDTO = new ApplicationDTO(regSQLDate);
        } catch (Exception registerApplicationException) {
            LOG.log(Level.WARNING, Messages.REGISTER_APPLICATION_ERROR.name(), registerApplicationException);
        }
    }

    /**
     * Checks if the input is valid
     */
    private void checkIfValidInput() throws SystemException {
        Util.checkPerson(personDTO);
        for(AvailabilityDTO availabilityDTO : availabilityDTOs) {
            Util.checkDate(availabilityDTO.getFromDate());
            if(availabilityDTO.getToDate() != null)
                Util.checkDate(availabilityDTO.getToDate());
        }
    }

    /**
     * Will refresh the page if the status of the application has been changed
     */
    private void refreshList() {
        switch (searchSelection){
            case 1:
                fetchJobApplications();
                break;
            case 2:
                fetchJobApplicationsByName();
                break;
            case 3:
                fetchJobApplicationsByExperience();
                break;
            case 4:
                fetchJobApplicationsByAvailability();
                break;
        }
    }
}
