package viewmodel.entrypoints;

import common.PersonDTO;
import controller.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/persons")
public class PersonsEntryPoint {
    private Controller controller = new Controller();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPerson(PersonDTO requestObject) {
        controller.createPerson()

    }
}
