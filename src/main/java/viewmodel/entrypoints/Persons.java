package viewmodel.entrypoints;

import common.PersonDTO;
import controller.Controller;
import org.springframework.beans.BeanUtils;
import common.PersonPublicDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/persons")
public class Persons {
    private Controller controller = new Controller();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonPublicDTO> getPersons() {
        return controller.getPersons();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonPublicDTO getPerson(@PathParam("id") long personId) {
        PersonDTO person = controller.getPersonByID(personId);
        PersonPublicDTO response = new PersonPublicDTO();
        BeanUtils.copyProperties(person, response);
        response.setPersonId(person.getId());
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createPerson(PersonDTO requestObject) {
        controller.createPerson(requestObject);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updatePerson(PersonDTO personDTO) {
        controller.updatePerson(personDTO);
    }
}
