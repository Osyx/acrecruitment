package viewmodel.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class used to be able to display the <code>SystemException</code>s for REST clients, due to
 * the fact that it can be converted to a JSON/XML object.
 */
@XmlRootElement
public class ErrorMessage {
    private String errorMessage;
    private String errorMessageKey;

    public ErrorMessage() {    }

    public ErrorMessage(String errorMessage, String errorMessageKey) {
        this.errorMessage = errorMessage;
        this.errorMessageKey = errorMessageKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }
}
