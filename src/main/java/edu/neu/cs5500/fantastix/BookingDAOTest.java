package edu.neu.cs5500.fantastix;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;

import edu.neu.cs5500.fantastix.data.BookingDAO;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;
import org.junit.Test;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import java.awt.print.Book;

import static org.junit.Assert.*;


public class BookingDAOTest {
    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(User.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Test
    public void testCreate() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        Booking testBooking = testBookingDAO.create(booking);
        assertNotNull(testBooking);
        assertEquals(1, booking.getBookingID());
        assertEquals(2, booking.getPropertyID());
        assertEquals("abc@email.com", booking.getRenterEmail());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());
        assertEquals(Status.PENDING, booking.getStatus());
    }

    @Test
    public void testFindOne() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        Booking testBooking = testBookingDAO.create(booking);
        assertEquals(booking, testBookingDAO.findOne(1));
    }



    @Test
    public void testFindByProperty() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        Booking testBooking = testBookingDAO.create(booking);
        List<Booking> bookings = Arrays.asList(testBooking);
        List<Booking> bList = testBookingDAO.findByProperty(2);
        assertEquals(bookings, bList);
    }

    @Test
    public void testFindByRenter() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        Booking testBooking = testBookingDAO.create(booking);
        List<Booking> bookings = Arrays.asList(testBooking);
        List<Booking> bList = testBookingDAO.findByRenter("abc@email.com");
        assertEquals(bookings, bList);
    }

    @Test
    public void testFindByStatus() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        Booking testBooking = testBookingDAO.create(booking);
        List<Booking> bookings = Arrays.asList(testBooking);
        List<Booking> bList = testBookingDAO.findByStatus(Status.PENDING);
        assertEquals(bookings, bList);
    }


    @Test
    public void testUpdateStatus() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        BookingDAO testBookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        Booking testBooking = testBookingDAO.create(booking);
        Booking booking2 = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.ACCEPTED);
        assertEquals(booking2, testBookingDAO.updateStatus(Status.ACCEPTED, 1));

    }
}