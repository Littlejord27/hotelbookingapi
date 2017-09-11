package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Booking;

import edu.neu.cs5500.fantastix.core.Status;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.List;

public class BookingDAO extends AbstractDAO<Booking> {

    public BookingDAO(SessionFactory factory) {
        super(factory);
    }

    public Booking create(Booking booking) {
        return persist(booking);
    }

    public Booking findOne(int bookingID) {
        return get(bookingID);
    }

    public List<Booking> findByProperty(int propertyID) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Booking.findByProperty").setParameter("propertyID", propertyID));
    }

    public List<Booking> findByRenter(String renterEmail) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Booking.findByRenter").setParameter("renterEmail", renterEmail));
    }

    public List<Booking> findByStatus(Status status) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Booking.findByStatus").setParameter("status", status));
    }

    public Booking updateStatus(Status status, int bookingID) {
        Booking booking = get(bookingID);
        if (booking != null) {
            booking.setStatus(status);
            return persist(booking);
        }
        return null;
    }
}