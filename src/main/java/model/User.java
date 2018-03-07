package model;

import common.RoleDTO;
import common.SystemException;
import common.UserDTO;
import integration.Integration;

import javax.transaction.Transactional;

/**
 * The class which takes the calls from the controller regarding the users and then
 * forwards these to the integration layer.
 */
@Transactional(value = Transactional.TxType.MANDATORY)
public class User {

    private final Integration integration = new Integration();

    /**
     * Checks if the login details are correct.
     * @param username The username of the user to be logged in.
     * @param password  The password of the user to be logged in.
     * @return the role the user has.
     * @throws SystemException if the wrong login details are given.
     */
    public RoleDTO login(String username, String password) throws SystemException {
        return integration.login(username, password);
    }

    /**
     * Register a user with a username and password.
     * @param userDTO The user details for the user to be added.
     * @throws SystemException if an error occurs during registration.
     */
    public void registerUser(UserDTO userDTO) throws SystemException {
        integration.registerUser(userDTO);
    }
}
