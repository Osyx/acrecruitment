package integration.entity;

import common.UserDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The entity for the users.
 */
@Entity(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long user_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public User() {    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
