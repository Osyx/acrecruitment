package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "role")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id", nullable = false)
    private float personId;

    @Column(name = "name")
    private String name;

    public Role() {    }

    public Role(float personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public float getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                '}';
    }
}
