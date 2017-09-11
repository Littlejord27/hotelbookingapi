package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.*;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.sql.Time;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Karthik on 3/28/2016.
 */
public class ManagerResourceTest {
    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(User.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Test
    public void testCreate() throws Exception {
        Manager manager = new Manager("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        ManagerDAO testManagerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        ManagerResource testManagerResource = new ManagerResource(testManagerDAO, testPropertyDAO);
        Response r = testManagerResource.create(manager);
        Manager manager1 = r.readEntity(Manager.class);
        assertEquals(manager, manager1);
        assertEquals(r.getStatus(), 201);
    }

    @Test
    public void testFindOne() throws Exception {
        Manager manager = new Manager("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        ManagerDAO testManagerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        ManagerResource testManagerResource = new ManagerResource(testManagerDAO, testPropertyDAO);
        testManagerResource.create(manager);

        Response r = testManagerResource.findOne(manager.getEmail());
        Manager manager1 = r.readEntity(Manager.class);
        assertEquals(manager1, manager1);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindAll() throws Exception {
        Manager manager = new Manager("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        Manager manager1 = new Manager("test1@test.com", "testPassword1", "testName1", "testAddress1", "testCity1", "testState1", "testZip1");
        ManagerDAO testManagerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        ManagerResource testManagerResource = new ManagerResource(testManagerDAO, testPropertyDAO);
        Response r = testManagerResource.create(manager);
        Manager manager2 = r.readEntity(Manager.class);
        Response r1 = testManagerResource.create(manager1);
        Manager manager3 = r1.readEntity(Manager.class);
        Response r2 = testManagerResource.findAll();
        List<User> rList = r2.readEntity(List.class);
        assertEquals(manager2, rList.get(0));
        assertEquals(manager3, rList.get(1));
        assertEquals(r2.getStatus(), 200);
    }

    @Test
    public void testFindProperties() throws Exception {
        Manager manager = new Manager("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        Property property = new Property(1, "Cal Park", "test@test.com", 75, "Capitol Hill",
                "Seattle", "WA", "98122", 2, 1, 1, new Time(1542), new Time(4535), "Micro Studio");
        Property property1 = new Property(2, "Base Park", "test@test.com", 75, "Capitol Hill",
                "Seattle", "WA", "98122", 2, 1, 1, new Time(5234), new Time(7654), "Micro Studio");
        ManagerDAO testManagerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        ManagerResource testManagerResource = new ManagerResource(testManagerDAO, testPropertyDAO);

        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource propertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);
        propertyResource.create(property);
        propertyResource.create(property1);

        Response r = testManagerResource.findProperties("test@test.com");
        Property[] pList = r.readEntity(Property[].class);

        assertEquals(property, pList[0]);
        assertEquals(property1, pList[1]);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testDelete() throws Exception {
        Manager manager = new Manager("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        ManagerDAO testManagerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        ManagerResource testManagerResource = new ManagerResource(testManagerDAO, testPropertyDAO);
        Response r = testManagerResource.create(manager);
        Response r2 = testManagerResource.delete(manager.getEmail());
        Response r1 = testManagerResource.findOne(manager.getEmail());
        Renter renter1 = r1.readEntity(Renter.class);
        assertNull(renter1);
        assertEquals(r2.getStatus(), 204);
    }
}