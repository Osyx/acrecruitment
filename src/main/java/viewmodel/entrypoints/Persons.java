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
    private final Controller controller = new Controller();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createPerson(PersonDTO requestObject) {
        controller.createPerson(requestObject);
    }
}
