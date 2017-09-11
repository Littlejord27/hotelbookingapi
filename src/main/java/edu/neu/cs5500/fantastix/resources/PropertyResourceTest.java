package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.PropertyDAO;
import edu.neu.cs5500.fantastix.data.PropertyImageDAO;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.sql.Time;
import java.util.List;

import static org.junit.Assert.*;


public class PropertyResourceTest {
    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(User.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Test
    public void testCreate() throws Exception {
        //int propertyID, String name, String managerEmail, double price, String address, String city, String state, String zip, int accommodation, double bathrooms, int beds, Time checkInTime, Time checkOutTime, String roomType
        Time checkin = new Time(1462044601);
        Time checkout = new Time(1462102201);
        Property property = new Property(20, "Tree House Hotel", "manager@email.com", 120, "2 Tree Ln", "Seattle", "WA", "98102", 2, 1, 1, checkin, checkout, "small");
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource testPropertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);
        Response r = testPropertyResource.create(property);
        Property p = r.readEntity(Property.class);
        assertEquals(property, p);
        assertEquals(r.getStatus(), 201);

    }

    @Test
    public void testUpdate() throws Exception {
        Time checkin = new Time(1462044601);
        Time checkout = new Time(1462102201);
        Property property = new Property(20, "Tree House Hotel", "manager@email.com", 120, "2 Tree Ln", "Seattle", "WA", "98102", 2, 1, 1, checkin, checkout, "small");
        Property property1 = new Property(21, "Tree House Hotel1", "manager1@email.com", 121, "21 Tree Ln", "Philadelphia", "PA", "54301", 3, 2, 2, checkin, checkout, "small");
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource testPropertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);
        Response r = testPropertyResource.update(20, property1);

        Property property2 = r.readEntity(Property.class);
        assertEquals(property2.getName(), property1.getName());
        assertEquals(property2.getManagerEmail(), property1.getManagerEmail());
        assertEquals(property2.getAccommodation(), property1.getAccommodation());
        assertEquals(property2.getAddress(), property1.getAddress());
        assertEquals(property2.getBeds(), property1.getBeds());
        assertEquals(property2.getCity(), property1.getCity());
        assertEquals(property2.getZip(), property1.getZip());
        assertEquals(property2.getState(), property1.getState());
        assertEquals(property2.getRoomType(), property1.getRoomType());
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindOne() throws Exception {
        Time checkin = new Time(1462044601);
        Time checkout = new Time(1462102201);
        Property property = new Property(20, "Tree House Hotel", "manager@email.com", 120, "2 Tree Ln", "Seattle", "WA", "98102", 2, 1, 1, checkin, checkout, "small");
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource testPropertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);

        Response c = testPropertyResource.create(property);
        Property p1 = c.readEntity(Property.class);
        Response r = testPropertyResource.findOne(p1.getPropertyID());
        Property p = r.readEntity(Property.class);
        assertEquals(property, p);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindAll() throws Exception {
        Time checkin = new Time(1462044601);
        Time checkout = new Time(1462102201);
        Property property = new Property(20, "Tree House Hotel", "manager@email.com", 120, "2 Tree Ln", "Seattle", "WA", "98102", 2, 1, 1, checkin, checkout, "small");
        Property property1 = new Property(21, "Tree House Hotel1", "manager1@email.com", 121, "21 Tree Ln", "Philadelphia", "PA", "54301", 3, 2, 2, checkin, checkout, "small");
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource testPropertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);
        Response r = testPropertyResource.create(property);
        Property property2 = r.readEntity(Property.class);
        Response r1 = testPropertyResource.create(property1);
        Property property3 = r1.readEntity(Property.class);
        Response r2 = testPropertyResource.findAll();
        List<User> pList = r2.readEntity(List.class);
        assertEquals(property, pList.get(0));
        assertEquals(property1, pList.get(1));
        assertEquals(r2.getStatus(), 200);
    }

    @Test
    public void testFindByName() throws Exception {
        Time checkin = new Time(1462044601);
        Time checkout = new Time(1462102201);
        Property property = new Property(20, "Tree House Hotel", "manager@email.com", 120, "2 Tree Ln", "Seattle", "WA", "98102", 2, 1, 1, checkin, checkout, "small");
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource testPropertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);

        Response c = testPropertyResource.create(property);
        Property p1 = c.readEntity(Property.class);
        Response r = testPropertyResource.findByName(p1.getName());
        Property p = r.readEntity(Property.class);
        assertEquals(property, p);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testDelete() throws Exception {
        Time checkin = new Time(1462044601);
        Time checkout = new Time(1462102201);
        Property property = new Property(20, "Tree House Hotel", "manager@email.com", 120, "2 Tree Ln", "Seattle", "WA", "98102", 2, 1, 1, checkin, checkout, "small");
        PropertyDAO testPropertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        PropertyImageDAO testPropertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        PropertyResource testPropertyResource = new PropertyResource(testPropertyDAO, testPropertyImageDAO);

        Response c = testPropertyResource.create(property);
        Property p1 = c.readEntity(Property.class);
        Response r = testPropertyResource.delete(p1.getPropertyID());
        Property p = r.readEntity(Property.class);
        assertNull(p);
        assertEquals(r.getStatus(), 404);
    }
}