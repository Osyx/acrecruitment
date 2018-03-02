package model;

import integration.Integration;
import org.hibernate.SessionFactory;

public class Factory {

    public static SessionFactory getFactory() {
        return Integration.getFactory();
    }
}
