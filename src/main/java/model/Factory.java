package model;

import integration.Integration;
import org.hibernate.SessionFactory;

/**
 * A class for being able to get the factory for the hibernate used.
 * This in order to be able to handle sessions and transactions, even if not in the same class as the factory.
 */
public class Factory {

    /**
     * Fetches the hibernate factory for the program.
     * @return the hibernate factory.
     */
    public static SessionFactory getFactory() {
        return Integration.getFactory();
    }
}
