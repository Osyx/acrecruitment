package view;

import common.*;
import controller.Controller;
import integration.entity.Experience;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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

    private List <JobApplicationDTO> jobApplications = new ArrayList<>();
    private List<ExperienceDTO> experienceDTOs = new ArrayList<>();
    private List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();
    private List<JobApp> jobApplicationsNew = new ArrayList<>();

    private Experience experience;
    private String[] experienceNames = new String[5];
    private Double[] years = new Double[5];

    private java.util.Date fromDate;
    private java.util.Date toDate;
    private java.sql.Date fromSQLDate;
    private java.sql.Date toSQLDate;
    private int searchSelection;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String conPassword;
    private String statusApplication;
    private String errorMessage;

    private boolean success = false;

    private static final Logger LOG = Logger.getLogger(RecruitmentHandler.class.getName());


    /**
     * Registers a user in the database
     */
    public void regUser(){
        try {
            if(conPassword.equals(password)){
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
            LOG.log(Level.WARNING, Messages.REGISTER_AVAILABILITY_ERROR.name(), registerAvailabilityException);
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
            LOG.log(Level.WARNING, Messages.REGISTER_EXPERIENCE_ERROR.name(), registerExperienceException);
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
            LOG.log(Level.WARNING, Messages.REGISTER_APPLICATION_ERROR.name(), registerApplicationException);
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
            LOG.log(Level.WARNING, Messages.REGISTER_JOB_APP_DTO_ERROR.name(), registerJobAppDTOException);
        }


    }

    /**
     * Registers a job application
     */
    public void regJobApplication() {
        try {
            success = true;
            personDTO = new PersonDTO(firstName, lastName, ssn, email);
            personDTO.setRole("applicant");
            regExperiences();
            regAvailability();
            regApplication();
            controller.registerRESTJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
        } catch(Exception registerJobAppException) {
            FacesMessage message = new FacesMessage(registerJobAppException.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(mybutton.getClientId(context), message);
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
     * Fetches all job applications
     */
    public void fetchJobApplications() {
        try {
            jobApplications = controller.fetchJobApplications("en");
            convertList();
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by name of person
     */
    public void fetchJobApplicationsByName(){
        try {
            jobApplications = controller.fetchJobApplicationsByName(personDTO, "en");
            convertList();
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by experience
     */
    public void fetchJobApplicationsByExperience(){
        try {
            jobApplications = controller.fetchJobApplicationsByExperience(experienceDTO, "en");
            convertList();
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by availability
     */
    public void fetchJobApplicationsByAvailability(){
        try {
            jobApplications = controller.fetchJobApplicationsByAvailability(availabilityDTO, "en");
            convertList();
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Converts a list of JobApplicationDTOs to a list of JobApps
     */
    public void convertList(){
        try {
            JobApp jobApp;
            JobApplicationDTO temp;
            AvailabilityDTO availability;
            int numOfAvailabilities;
            int numOfExp;
            for (int i = 0; i < jobApplications.size(); i++) {
                temp = jobApplications.get(i);
                numOfAvailabilities = temp.getAvailabilities().size();
                availability = temp.getAvailabilities().get(numOfAvailabilities - 1);
                numOfExp = jobApplicationDTO.getExperiences().size();
                if (numOfExp == 2) {
                    jobApp = new JobApp(temp.getPerson(), temp.getApplication(), availability, temp.getExperiences().get(0), temp.getExperiences().get(1));
                } else if (numOfExp == 3) {
                    jobApp = new JobApp(temp.getPerson(), temp.getApplication(), availability, temp.getExperiences().get(0), temp.getExperiences().get(1), temp.getExperiences().get(2));
                } else {
                    jobApp = new JobApp(temp.getPerson(), temp.getApplication(), availability, temp.getExperiences().get(0), temp.getExperiences().get(1), temp.getExperiences().get(2), temp.getExperiences().get(3));
                }
                jobApplicationsNew.add(jobApp);
            }
        }catch(Exception conversionException){
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), conversionException);
        }
    }

    public List<JobApp> getJobApplicationsNew() {
        return jobApplicationsNew;
    }

    public void setJobApplicationsNew(List<JobApp> jobApplicationsNew) {
        this.jobApplicationsNew = jobApplicationsNew;
    }

    public int getSearchSelection() {
        return searchSelection;
    }

    public void setSearchSelection(int searchSelection) {
        this.searchSelection = searchSelection;
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

    public boolean getSuccess() {
        boolean tempSuccess = success;
        success = false;
        return tempSuccess;
    }


}
