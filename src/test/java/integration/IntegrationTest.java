package integration;

import controller.Controller;
import model.Availability;
import model.Person;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

//@RunWith(Arquillian.class)
public class IntegrationTest {
    /*
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Integration.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    Integration integration;
*/

    Controller controller = new Controller();
    String personSsn = "12345678-9000";

    @Test
    public void removeObject() {
        Person person = controller.getPerson(personSsn);
        controller.removeObject(person);
        List<Availability> list = controller.fetchAvailabilities(personSsn);

        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void createPerson() {
        controller.createPerson("Pelle", "Svanslös", personSsn, "hej@telia.se");
        Person person = controller.getPerson(personSsn);

        Assert.assertTrue(person != null);

        removeObject();
    }

    @Test
    public void createAvailability() {
        controller.createPerson("Pelle", "Svanslös", personSsn, "hej@telia.se");
        Person person = controller.getPerson(personSsn);
        controller.createAvailability(person.getPersonId(), getDate("20110312"), getDate("20160530"));
        List<Availability> list = controller.fetchAvailabilities(personSsn);

        Assert.assertTrue(list.size() > 0);

        removeObject();
    }

    @Test
    public void login() {
        Assert.fail("Not yet implemented.");
    }

    @Test
    public void registerUser() {
        Assert.fail("Not yet implemented.");
    }

    @Test
    public void registerJobApplication() {
        Assert.fail("Not yet implemented.");
    }

    static Date getDate(String date) {
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