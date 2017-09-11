package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.BookingDAO;
import edu.neu.cs5500.fantastix.data.FeedbackDAO;
import edu.neu.cs5500.fantastix.data.RenterDAO;
import edu.neu.cs5500.fantastix.data.UserDAO;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Karthik on 3/29/2016.
 */
public class RenterResourceTest {
    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(User.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Test
    public void testCreate() throws Exception {
        Renter renter = new Renter("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        RenterDAO testRenterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        FeedbackDAO testFeedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());
        RenterResource testRenterResource = new RenterResource(testRenterDAO, testBookingDAO, testFeedbackDAO);
        Response r = testRenterResource.create(renter);
        Renter renter1 = r.readEntity(Renter.class);
        assertEquals(renter, renter1);
        assertEquals(r.getStatus(), 201);
    }

    @Test
    public void testFindOne() throws Exception {
        Renter renter = new Renter("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        RenterDAO testRenterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        FeedbackDAO testFeedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());
        RenterResource testRenterResource = new RenterResource(testRenterDAO, testBookingDAO, testFeedbackDAO);
        testRenterResource.create(renter);

        Response r = testRenterResource.findOne(renter.getEmail());
        Renter renter1 = r.readEntity(Renter.class);
        assertEquals(renter, renter1);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindAll() throws Exception {
        Renter renter = new Renter("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        Renter renter1 = new Renter("test1@test.com", "testPassword1", "testName1", "testAddress1", "testCity1", "testState1", "testZip1");
        RenterDAO testRenterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        FeedbackDAO testFeedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());
        RenterResource testRenterResource = new RenterResource(testRenterDAO, testBookingDAO, testFeedbackDAO);
        Response r = testRenterResource.create(renter);
        Renter renter2 = r.readEntity(Renter.class);
        Response r1 = testRenterResource.create(renter1);
        Renter renter3 = r1.readEntity(Renter.class);
        Response r2 = testRenterResource.findAll();
        List<User> rList = r2.readEntity(List.class);
        assertEquals(renter2, rList.get(0));
        assertEquals(renter3, rList.get(1));
        assertEquals(r2.getStatus(), 200);
    }

    @Test
    public void testFindBookings() throws Exception {
        Renter renter = new Renter("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        Booking booking = new Booking(1, 1, "test@test.com", new Date(12345), new Date(23456), Status.ACCEPTED);
        Booking booking1 = new Booking(2, 2, "test@test.com", new Date(12345), new Date(23456), Status.ACCEPTED);
        RenterDAO testRenterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        FeedbackDAO testFeedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());

        RenterResource testRenterResource = new RenterResource(testRenterDAO, testBookingDAO, testFeedbackDAO);
        BookingResource testBookingResource = new BookingResource(testBookingDAO);

        testBookingResource.createBooking(booking);
        testBookingResource.createBooking(booking1);

        Response r = testRenterResource.findBookings("test@test.com");
        Booking[] bookings = r.readEntity(Booking[].class);

        assertEquals(booking, bookings[0]);
        assertEquals(booking1, bookings[1]);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindFeedback() throws Exception {
        Renter renter = new Renter("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        Feedback feedback= new Feedback(1, 1, "test@test.com", 4, "they're awesome", new Date(12345));
        Feedback feedback1= new Feedback(2, 2, "test@test.com", 4, "they're also awesome", new Date(23456));
        RenterDAO testRenterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        FeedbackDAO testFeedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());

        RenterResource testRenterResource = new RenterResource(testRenterDAO, testBookingDAO, testFeedbackDAO);
        FeedbackResource testFeedbackResource = new FeedbackResource(testFeedbackDAO);

        testFeedbackResource.postFeedback(feedback);
        testFeedbackResource.postFeedback(feedback1);

        Response r = testRenterResource.findFeedback("test@test.com");
        Feedback[] feedbacks = r.readEntity(Feedback[].class);

        assertEquals(feedback, feedbacks[0]);
        assertEquals(feedback1, feedbacks[1]);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testDelete() throws Exception {
        Renter renter = new Renter("test@test.com", "testPassword", "testName", "testAddress", "testCity", "testState", "testZip");
        RenterDAO testRenterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        FeedbackDAO testFeedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());
        RenterResource testRenterResource = new RenterResource(testRenterDAO, testBookingDAO, testFeedbackDAO);
        Response r = testRenterResource.create(renter);
        Response r2 = testRenterResource.delete(renter.getEmail());
        Response r1 = testRenterResource.findOne(renter.getEmail());
        Renter renter1 = r1.readEntity(Renter.class);
        assertNull(renter1);
        assertEquals(r2.getStatus(), 204);
    }
}