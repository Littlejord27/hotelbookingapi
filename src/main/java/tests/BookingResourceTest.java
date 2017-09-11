package tests;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.BookingDAO;
import edu.neu.cs5500.fantastix.resources.BookingResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.junit.Test;
import javax.ws.rs.core.Response;
import java.awt.print.Book;
import java.sql.Date;

import static org.junit.Assert.*;


public class BookingResourceTest {
    private BookingResource BR;

    public BookingResourceTest(BookingResource BR){
        BR = BR;
    }

    @Test
    public void testCreateBooking() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        Response r = BR.createBooking(booking);
        Booking b = r.readEntity(Booking.class);
        assertEquals(booking, b);
        assertEquals(r.getStatus(), 201);
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);
        Booking booking2 = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.ACCEPTED);

        Response c = BR.createBooking(booking);
        Booking b1 = c.readEntity(Booking.class);
        Response r = BR.updateStatus(b1.getBookingID(), booking2);
        Booking b = r.readEntity(Booking.class);
        assertEquals(booking2, b);
        assertEquals(r.getStatus(), 200);

    }

    @Test
    public void testFindOne() throws Exception {
        Date startDate = new Date(1459206495);
        Date endDate = new Date(1459379286);
        Booking booking = new Booking(1, 2, "abc@email.com", startDate, endDate, Status.PENDING);

        Response c = BR.createBooking(booking);
        Booking b1 = c.readEntity(Booking.class);
        Response r = BR.findOne(b1.getBookingID());
        Booking b = r.readEntity(Booking.class);
        assertEquals(booking, b);
        assertEquals(r.getStatus(), 200);

    }
}