package integration;

import model.Availability;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(Arquillian.class)
public class IntegrationTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Integration.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    Integration integration;

    @Test
    public void can_fetch_objects_from_db() {
        Assert.fail("Not implemented");
    }

    @Test
    public void can_update_db() {
        Assert.fail("Not implemented");
    }

    @Test
    public void can_remove_from_db() {
        Assert.fail("Not implemented");
    }

    @Test
    public void can_put_in_db() {
        Availability availability = new Availability(0, getDate("20110312"), getDate("20160530"));
        Serializable serAvail = integration.createObject(availability);
        Assert.assertTrue(serAvail.equals(availability));
    }

    Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }
}