package view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
/**
 * Converts a date into the right format
 */
@FacesConverter("myDateTimeConverter")
public class DateConverter extends DateTimeConverter {

    public DateConverter() {
        setPattern("yyyy-mm-dd");
    }

    /**
     * Will convert the date the user entered to the right format
     * @param context
     * @param component
     * @param userInputDate
     * @return the date as an object
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String userInputDate) {
        if (userInputDate != null && userInputDate.length() != getPattern().length()) {
            throw new ConverterException("Invalid format");
        }

        return super.getAsObject(context, component, userInputDate);
    }

}