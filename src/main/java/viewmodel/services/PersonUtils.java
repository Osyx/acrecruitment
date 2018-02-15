package viewmodel.services;

import common.ErrorMessages;
import viewmodel.MissingRequiredFieldException;

public class PersonUtils {
    public void validateRequiredFields(PersonDTO personDTO) throws MissingRequiredFieldException {
        if(personDTO.getName() == null ||
                personDTO.getName().isEmpty() ||
                personDTO.getSurname() == null ||
                personDTO.getSurname().isEmpty() ||
                personDTO.getEmail() == null ||
                personDTO.getEmail().isEmpty())
            throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    }
}
