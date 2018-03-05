package viewmodel.response;

import javax.xml.bind.annotation.XmlRootElement;

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
