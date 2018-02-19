package integration;

import controller.Controller;
import integration.entity.Availability;
import integration.entity.Person;
import integration.entity.User;
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
        //Person person = controller.createPerson("Hej", "hej", "ssn", "email");
        //User user = controller.createUser("user", "pass", person);

        Person person = new Person("Hej", "hej", personSsn, "email");
        User user = new User("user", "pass");
        user.setPerson(person);
        boolean created = controller.registerUser(person, user);
        Person personFetch = controller.getPerson(personSsn);

        System.out.println(personFetch.toString());
        Assert.assertTrue(personFetch != null && created);

        removeObject();
    }

    @Test
    public void createAvailability() {
        controller.createPerson("Pelle", "Svanslös", personSsn, "hej@telia.se");
        Person person = controller.getPerson(personSsn);
        controller.createAvailability(person, getDate("20110312"), getDate("20160530"));
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