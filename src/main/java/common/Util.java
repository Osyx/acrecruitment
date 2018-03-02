package common;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.Year;
import java.util.Calendar;

public class Util {

    /**
     * Takes the string argument and returns a string which is capitalized.
     * E.g. 'pelle' -> 'Pelle' or PELLE -> 'Pelle'.
     * @param s The string to be capitalized.
     * @return The argument string capitalized.
     */
    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Checks if there are only letter symbols in the string.
     * @param s The string to be checked if it only contains letters.
     * @return <code>true</code> if the string only contains letters,
     *         <code>false</code> if it does not.
     */
    public static boolean checkLetterOnlyString(String s) {
        return s.chars().allMatch(Character::isLetter);
    }

    /**
     * Checks if the email is an valid email.
     * @param email The string to check if it contains a valid email.
     * @return <code>true</code> if the string is a valid email,
     *         <code>false</code> if it is not.
     */
    public static boolean checkValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Checks id the string is a valid SSN.
     * @param ssn The string to check if it is a valid.
     * @return A <code>String</code> containing the SSN in the right format, if the string was an valid SSN.
     *         <code>null</code> if it wasn't a valid SSN.
     */
    public static String checkSsn(String ssn) {
        if(ssn.matches("^[1-9][0-9]{7}[-][0-9]{4}$"))
            return ssn;
        else if(ssn.matches("^[1-9][0-9]{11}$")) {
            return ssn.substring(0,8) + "-" + ssn.substring(8);
        } else if(ssn.matches("^[0-9]{10}$")) {
            if(Integer.parseInt(ssn.substring(0,2)) > Integer.parseInt(String.valueOf(Year.now().getValue()).substring(2)))
                return "19" + ssn.substring(0,6)  + "-" + ssn.substring(6);
            else
                return "20" + ssn.substring(0, 6)  + "-" + ssn.substring(6);
        }
        return null;
    }

    /**
     * Checks if the string contains a valid date.
     * @param date The string to check for a valid date.
     * @return The date <code>String</code> if it was correct, throws an error otherwise.
     * @throws SystemException if the <code>String</code> didn't contain a valid date.
     */
    public static String checkDate(String date) throws SystemException {
        if(DateValidator.getInstance().isValid(date, "yyyy-MM-dd"))
            throw new SystemException(
                    Messages.WRONG_INPUT.name(),
                    Messages.WRONG_INPUT.getErrorMessageWithArg("Invalid date: " + date)
            );
        return date;
    }

    /**
     * Check a person (<code>PersonDTO</code>) if its fields are valid.
     * @param personDTO The DTO to check if the fields are valid within.
     * @return The <code>PersonDTO</code> with all fields valid and in the right format.
     * @throws SystemException if the DTO contained non-valid fields.
     */
    public static common.PersonDTO checkPerson(common.PersonDTO personDTO) throws SystemException {
        if(personDTO == null)
            throw new SystemException(
                    Messages.PERSON_MISSING.name(),
                    Messages.PERSON_MISSING.getErrorMessage()
            );

        if(!checkLetterOnlyString(personDTO.getName()+personDTO.getSurname()))
            throw new SystemException(
                    Messages.WRONG_INPUT.name(),
                    Messages.WRONG_INPUT.getErrorMessageWithArg("Invalid characters in first or/and last name: \'" + personDTO.getName() + " " + personDTO.getSurname() + "\'.")
            );

        if(!checkValidEmail(personDTO.getEmail()) || personDTO.getEmail() == null)
            throw new SystemException(
                    Messages.WRONG_INPUT.name(),
                    Messages.WRONG_INPUT.getErrorMessageWithArg("Invalid email: \'" + personDTO.getEmail() + "\'.")
            );

        String orgSsn = personDTO.getSsn();
        personDTO.setSsn(checkSsn(personDTO.getSsn()));
        if(personDTO.getSsn() == null)
            throw new SystemException(
                    Messages.WRONG_INPUT.name(),
                    Messages.WRONG_INPUT.getErrorMessageWithArg("Invalid ssn: \'" + orgSsn + "\'.")
            );

        if(personDTO.getRole() != null) {
            personDTO.setRole(personDTO.getRole().toLowerCase());
            if(!personDTO.getRole().equals("applicant"))
                throw new SystemException(
                        Messages.WRONG_INPUT.name(),
                        Messages.WRONG_INPUT.getErrorMessageWithArg("Invalid role: \'" + personDTO.getRole() + "\'.")
                );
        } else
            throw new SystemException(
                    Messages.WRONG_INPUT.name(),
                    Messages.WRONG_INPUT.getErrorMessageWithArg("Invalid role: \'" + personDTO.getRole() + "\'.")
            );

        personDTO.setName(capitalize(personDTO.getName()));
        personDTO.setSurname(capitalize(personDTO.getSurname()));
        return personDTO;
    }
}
