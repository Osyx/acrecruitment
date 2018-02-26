package model;

import common.RoleDTO;
import common.SystemException;
import common.UserDTO;
import integration.Integration;

public class User {

    private final Integration integration = new Integration();

    /**
     * Checks if the login details are correct.
     * @param username The username of the user to be logged in.
     * @param password  The password of the user to be logged in.
     * @return a boolean indicating whether it was the correct details or not.
     * <code>True</code> for correct, <code>false</code> for incorrect.
     */
    public RoleDTO login(String username, String password) {
        return integration.login(username, password);
    }

    /**
     * Register a user with a username and password.
     * @param user The user details for the user to be added.
     */
    public void registerUser(UserDTO user) throws SystemException {
        integration.registerUser(user);
    }
}
