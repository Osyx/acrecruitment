package common;

import viewmodel.MissingRequiredFieldException;
import viewmodel.response.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {

    @Override
    public Response toResponse(MissingRequiredFieldException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),
                ErrorMessages.MISSING_REQUIRED_FIELD.name(), "randomUrlForErrorHere.");
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
