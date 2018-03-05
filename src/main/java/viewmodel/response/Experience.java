package viewmodel.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class used to be able to send the experience names to the REST client.
 * Can be converted to a JSON/XML object.
 */
@XmlRootElement
public class Experience {
    private String name;

    public Experience() {}

    public Experience(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
