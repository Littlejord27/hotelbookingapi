package edu.neu.cs5500.fantastix.resources;


import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.UserDAO;
import io.dropwizard.db.DataSourceFactory;
import org.junit.Test;

import static org.junit.Assert.*;
import io.dropwizard.hibernate.HibernateBundle;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Karthik on 3/28/2016.
 */
public class UserResourceTest {
    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(User.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private void assertEquality(User u1, User u2) {
        assertEquals(u1.getEmail(), u2.getEmail());
        assertEquals(u1.getPassword(), u2.getPassword());
        assertEquals(u1.getName(), u2.getName());
        assertEquals(u1.getAddress(), u2.getAddress());
        assertEquals(u1.getCity(), u2.getCity());
        assertEquals(u1.getState(), u2.getState());
        assertEquals(u1.getZip(), u2.getZip());
    }

    private void assertEqualityNoEmail(User u1, User u2) {
        assertEquals(u1.getPassword(), u2.getPassword());
        assertEquals(u1.getName(), u2.getName());
        assertEquals(u1.getAddress(), u2.getAddress());
        assertEquals(u1.getCity(), u2.getCity());
        assertEquals(u1.getState(), u2.getState());
        assertEquals(u1.getZip(), u2.getZip());
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        UserDAO testUserDAO = new UserDAO(hibernateBundle.getSessionFactory());
        UserResource testUserResource = new UserResource(testUserDAO);
        Response r = testUserResource.create(user);
        User u = r.readEntity(User.class);
        assertEquality(u, user);
        assertEquals(r.getStatus(), 201);
    }

    @Test
    public void testFindOne() throws Exception {
        User user = new User("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        UserDAO testUserDAO = new UserDAO(hibernateBundle.getSessionFactory());
        UserResource testUserResource = new UserResource(testUserDAO);
        Response r = testUserResource.findOne(user.getEmail());
        User u = r.readEntity(User.class);
        assertEquality(u, user);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindAll() throws Exception {
        User user = new User("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        User user1 = new User("test1@test.com", "testPassword1", "testName1", "testAddress1", "testCity1", "testState1", "testZip1");
        UserDAO testUserDAO = new UserDAO(hibernateBundle.getSessionFactory());
        UserResource testUserResource = new UserResource(testUserDAO);
        Response r = testUserResource.create(user);
        User u = r.readEntity(User.class);
        Response r1 = testUserResource.create(user1);
        User u1 = r1.readEntity(User.class);
        Response r2 = testUserResource.findAll();
        List<User> uList = r2.readEntity(List.class);
        assertEquality(u, uList.get(0));
        assertEquality(u1, uList.get(1));
        assertEquals(r2.getStatus(), 200);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        User user1 = new User("test1@test.com", "testPassword1", "testName1", "testAddress1", "testCity1", "testState1", "testZip1");
        UserDAO testUserDAO = new UserDAO(hibernateBundle.getSessionFactory());
        UserResource testUserResource = new UserResource(testUserDAO);
        Response r = testUserResource.create(user);
        Response r1 = testUserResource.create(user1);
        User u1 = r1.readEntity(User.class);
        Response r2 = testUserResource.update("test1@test.com", user);
        assertEqualityNoEmail(u1, user);
        assertEquals(r2.getStatus(), 200);
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        UserDAO testUserDAO = new UserDAO(hibernateBundle.getSessionFactory());
        UserResource testUserResource = new UserResource(testUserDAO);
        Response r = testUserResource.create(user);
        Response r2 = testUserResource.delete(user.getEmail());
        Response r1 = testUserResource.findOne(user.getEmail());
        User u1 = r1.readEntity(User.class);
        assertNull(u1);
        assertEquals(r2.getStatus(), 204);
    }
}