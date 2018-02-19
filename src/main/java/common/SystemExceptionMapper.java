package common;

import viewmodel.response.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//@Provider
public class SystemExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                ErrorMessages.SYSTEM_ERROR.name()
        );
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
