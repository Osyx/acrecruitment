package integration.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private float roleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public Role() {    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                ", name='" + name + '\'' +
                '}';
    }
}
