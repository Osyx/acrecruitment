package model;
import javax.persistence.*;
import java.io.Serializable;


@NamedQueries({
        @NamedQuery(
                name = "deleteUser",
                query = "DELETE FROM users user " +
                        "WHERE user.username LIKE :username " +
                        "AND user.password LIKE :password"
        )
        ,
        @NamedQuery(
                name = "checkUser",
                query = "SELECT user FROM users user " +
                        "WHERE user.username LIKE :username",
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "loginUser",
                query = "SELECT user FROM users user " +
                        "WHERE user.username LIKE :username " +
                        "AND user.password LIKE :password",
                lockMode = LockModeType.OPTIMISTIC
        )
})


@Entity(name = "person")
public class personDTO implements Serializable {
    @Id
    @Column(name = "person_id", nullable = false)
    private long personId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "ssn", nullable = false)
    private String ssn;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role_id", nullable = false)
    private long roleId;

    @Column(name = "username", nullable = false)
    private String username;

    @Version
    @Column(name = "version")
    private int versionNum;

    public User() {}

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}