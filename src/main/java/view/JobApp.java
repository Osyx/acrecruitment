package view;

import common.*;

import java.util.List;


public class JobApp {

    private String firstName;
    private String lastName;
    private String email;
    private String fromDate;
    private String toDate;
    private String regDate;
    private String accepted;
    private String exp1;
    private String exp2;
    private String exp3;
    private String exp4;
    private String years1;
    private String years2;
    private String years3;
    private String years4;

    public JobApp(){}

    public JobApp(PersonPublicDTO person, ApplicationDTO app, AvailabilityDTO availability, ExperienceDTO exp1, ExperienceDTO exp2){
        this.firstName = person.getName();
        this.lastName = person.getSurname();
        this.email = person.getEmail();
        this.regDate = app.getDate();
        this.accepted = app.getAccepted();
        this.fromDate = availability.getFromDate();
        this.toDate = availability.getToDate();
        this.exp1 = exp1.getName();
        this.exp2 = exp2.getName();
        this.years1 = String.valueOf(exp1.getYearsOfExperience());
        this.years2 = String.valueOf(exp2.getYearsOfExperience());
    }

    public JobApp(PersonPublicDTO person, ApplicationDTO app, AvailabilityDTO availability, ExperienceDTO exp1, ExperienceDTO exp2, ExperienceDTO exp3){
        this.firstName = person.getName();
        this.lastName = person.getSurname();
        this.email = person.getEmail();
        this.regDate = app.getDate();
        this.accepted = app.getAccepted();
        this.fromDate = availability.getFromDate();
        this.toDate = availability.getToDate();
        this.exp1 = exp1.getName();
        this.exp2 = exp2.getName();
        this.exp3 = exp3.getName();
        this.years1 = String.valueOf(exp1.getYearsOfExperience());
        this.years2 = String.valueOf(exp2.getYearsOfExperience());
        this.years3 = String.valueOf(exp3.getYearsOfExperience());
    }

    public JobApp(PersonPublicDTO person, ApplicationDTO app, AvailabilityDTO availability, ExperienceDTO exp1, ExperienceDTO exp2, ExperienceDTO exp3, ExperienceDTO exp4){
        this.firstName = person.getName();
        this.lastName = person.getSurname();
        this.email = person.getEmail();
        this.regDate = app.getDate();
        this.accepted = app.getAccepted();
        this.fromDate = availability.getFromDate();
        this.toDate = availability.getToDate();
        this.exp1 = exp1.getName();
        this.exp2 = exp2.getName();
        this.exp3 = exp3.getName();
        this.exp4 = exp4.getName();
        this.years1 = String.valueOf(exp1.getYearsOfExperience());
        this.years2 = String.valueOf(exp2.getYearsOfExperience());
        this.years3 = String.valueOf(exp3.getYearsOfExperience());
        this.years4 = String.valueOf(exp4.getYearsOfExperience());
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public String getExp2() {
        return exp2;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2;
    }

    public String getExp3() {
        return exp3;
    }

    public void setExp3(String exp3) {
        this.exp3 = exp3;
    }

    public String getExp4() {
        return exp4;
    }

    public void setExp4(String exp4) {
        this.exp4 = exp4;
    }

    public String getYears1() {
        return years1;
    }

    public void setYears1(String years1) {
        this.years1 = years1;
    }

    public String getYears2() {
        return years2;
    }

    public void setYears2(String years2) {
        this.years2 = years2;
    }

    public String getYears3() {
        return years3;
    }

    public void setYears3(String years3) {
        this.years3 = years3;
    }

    public String getYears4() {
        return years4;
    }

    public void setYears4(String years4) {
        this.years4 = years4;
    }
}
