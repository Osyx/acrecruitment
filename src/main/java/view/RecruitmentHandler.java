package view;

import common.*;
import controller.Controller;
import integration.entity.Availability;
import integration.entity.Experience;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
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

    private String searchName;
    private String searchLastName;
    private String searchExp;
    private java.util.Date searchFromDate;
    private java.util.Date searchToDate;
    private java.sql.Date searchFromSQLDate;
    private java.sql.Date searchToSQLDate;

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
            if(success) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/confirmation.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/register.xhtml");
            }
        } catch (Exception registerPersonException) {
            errorMessage = registerPersonException.getMessage();
            LOG.log(Level.WARNING, Messages.REGISTER_USER_ERROR.name(), registerPersonException);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/error.xhtml");
            } catch (IOException e) {
                LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), e);
            }
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
        fromSQLDate = fromDate != null ? new java.sql.Date(fromDate.getTime()) : null;
        toSQLDate = toDate != null ? new java.sql.Date(toDate.getTime()) : null;
    }

    /**
     * Converts a java.util.Date to java.sql.Date
     */
    public void dateConverterSearch(){
        searchFromDate = searchFromDate != null ? new java.sql.Date(searchFromDate.getTime()): null;
        searchToDate = searchToDate != null ? new java.sql.Date(searchToDate.getTime()): null;
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
            if (roleDTO != null && roleDTO.getRole().equals("recruit")) {
                success = true;
                personDTO = new PersonDTO(firstName, lastName, ssn, email);
                personDTO.setRole("applicant");
                regExperiences();
                regAvailability();
                regApplication();
                controller.registerRESTJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
            } else {
                throw new SystemException(Messages.LOGIN_ERROR.name(), Messages.USER_NOT_LOGGED_IN.getErrorMessageWithArg("with recruit status."));
            }
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
            if(Objects.equals(statusApplication, "accepted")) {
                applicationDTO.setAccepted("accepted");
            }else if(Objects.equals(statusApplication, "declined")){
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
    public void login(String from) {
        try {
            roleDTO = controller.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", roleDTO.getRole());
            if(from.isEmpty() || from.equals("%20failed") || from.equals(" failed"))
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/index.xhtml");
            else if (from.contains("failed")) {
                from = from.replace("%20failed", "");
                from = from.replace(" failed", "");
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment" + from);
            } else
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment" + from);
        } catch (Exception loginException) {
            String[] fromArr = {};
            if(from.contains("%20"))
                fromArr =from.split("%20");
            else if(from.contains(" "))
                fromArr = from.split(" ");
            if(fromArr.length >= 5)
                LOG.log(Level.WARNING, Messages.LOGIN_ERROR.name(), loginException);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/login.xhtml?from=" + from + "%20failed");
            } catch (IOException redirectException) {
                LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), redirectException);
            }
        }
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("role");
        try {
            roleDTO = null;
            FacesContext.getCurrentInstance().getExternalContext().redirect("/acrecruitment/index.xhtml");
        } catch (Exception e) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), e);
        }
    }

    /**
     * Fetches all job applications
     */
    public void fetchJobApplications() {
        LOG.severe("hej");
        try {
            LOG.severe("hej");
            jobApplications = controller.fetchJobApplications("en");
            LOG.severe(jobApplications.get(0).getPerson().getName());
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    public void hej(){
        LOG.severe("hej");
    }

    /**
     * Fetches job applications by name of person
     */
    public void fetchJobApplicationsByName(){
        try {
            PersonDTO person = new PersonDTO();
            person.setName(searchName);
            person.setSurname(searchLastName);
            LOG.warning(person.toString());
            jobApplications = controller.fetchJobApplicationsByName(person, "en");
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by experience
     */
    public void fetchJobApplicationsByExperience(){
        LOG.warning("hejhej");
        try {
            ExperienceDTO exp = new ExperienceDTO();
            exp.setName(searchExp);
            jobApplications = controller.fetchJobApplicationsByExperience(exp, "sv");
            System.out.println("");
        } catch (Exception fetchException) {
            LOG.log(Level.WARNING, Messages.SYSTEM_ERROR.name(), fetchException);
        }
    }

    /**
     * Fetches job applications by availability
     */
    public void fetchJobApplicationsByAvailability(){
        try {
            dateConverterSearch();
            AvailabilityDTO availability = new AvailabilityDTO();
            availability.setFromDate(searchFromSQLDate);
            availability.setToDate(searchToSQLDate);
            jobApplications = controller.fetchJobApplicationsByAvailability(availability, "en");
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

    public Date getSearchFromSQLDate() {
        return searchFromSQLDate;
    }

    public void setSearchFromSQLDate(Date searchFromSQLDate) {
        this.searchFromSQLDate = searchFromSQLDate;
    }

    public Date getSearchToSQLDate() {
        return searchToSQLDate;
    }

    public void setSearchToSQLDate(Date searchToSQLDate) {
        this.searchToSQLDate = searchToSQLDate;
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

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
