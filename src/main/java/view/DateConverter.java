package view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("myDateTimeConverter")
public class DateConverter extends DateTimeConverter {

    public DateConverter() {
        setPattern("dd/mm/yyyy");
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String userInputDate) {
        if (userInputDate != null && userInputDate.length() != getPattern().length()) {
            throw new ConverterException("Invalid format");
        }

        return super.getAsObject(context, component, userInputDate);
    }

}