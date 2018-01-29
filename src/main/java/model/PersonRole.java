package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "person_role")
public class PersonRole {

    @Id
    @Column(name = "person_id", nullable = false)
    private long personId;

    @Id
    @Column(name = "role_id", nullable = false)
    private long roleId;

    public PersonRole() {    }

    public PersonRole(long personId, long roleId) {
        this.personId = personId;
        this.roleId = roleId;
    }

    public long getPersonId() {
        return personId;
    }

    public long getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "PersonRole{" +
                "personId=" + personId +
                ", roleId=" + roleId +
                '}';
    }
}
