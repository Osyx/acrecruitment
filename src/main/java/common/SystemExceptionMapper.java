package common;

import viewmodel.response.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SystemExceptionMapper implements ExceptionMapper<SystemException> {

    @Override
    public Response toResponse(SystemException e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                e.getMessageKey()
        );
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
