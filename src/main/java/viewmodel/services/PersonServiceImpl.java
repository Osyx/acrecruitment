package viewmodel.services;

import common.PersonDTO;

public class PersonServiceImpl {
    PersonUtils personUtils = new PersonUtils();

    PersonDTO createPerson(PersonDTO personDTO) {
        PersonDTO returnPerson = new PersonDTO();

        personUtils.validateRequiredFields(personDTO);

        return returnPerson;
    }
}
