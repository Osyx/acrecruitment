package viewmodel.entrypoints;

import common.PersonDTO;
import org.springframework.beans.BeanUtils;
import viewmodel.request.CreatePersonRequestModel;
import viewmodel.response.PersonRest;
import viewmodel.services.PersonService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/persons")
public class PersonsEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public PersonRest createPerson(CreatePersonRequestModel requestObject) {
        PersonRest personRest = new PersonRest();

        PersonDTO personDTO = new PersonDTO();
        BeanUtils.copyProperties(requestObject, personDTO);

        PersonService personService = new UserServiceImpl();
        PersonDTO createdPerson = personService.createPerson(personDTO);

        BeanUtils.copyProperties(createdPerson, personRest);

        return personRest;
    }
}
