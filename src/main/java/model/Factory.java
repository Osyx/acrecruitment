package model;

import integration.Integration;
import org.hibernate.SessionFactory;

public class Factory {

    /**
     * Fetches the hibernate factory for the program.
     * @return the hibernate factory.
     */
    public static SessionFactory getFactory() {
        return Integration.getFactory();
    }
}
