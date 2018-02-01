package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "user_id", nullable = false)
    private long user_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "person_id", nullable = false)
    private long personId;

    public User() {    }

    public User(String username, String password, long personId) {
        this.username = username;
        this.password = password;
        this.personId = personId;
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

    public long getPersonId() {
        return personId;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personId=" + personId +
                '}';
    }
}
