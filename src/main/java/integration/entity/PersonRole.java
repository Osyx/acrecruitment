package integration.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "person_role")
public class PersonRole implements Serializable {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public PersonRole() {}

    public PersonRole(Person person, Role role) {
        this.person = person;
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PersonRole{" +
                "personId=" + person.getPersonId() +
                ", roleId=" + role.getName() +
                '}';
    }
}
