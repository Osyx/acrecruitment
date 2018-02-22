package controller;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

//@RunWith(Arquillian.class)
public class ControllerTest {
    //@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Controller.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void login() {
    }

    @Test
    public void registerUser() {
    }

    @Test
    public void registerJobApplication() {
    }

    @Test
    public void fetchJobApplications() {
    }

    @Test
    public void acceptOrDeclineJobApplication() {
    }
}
