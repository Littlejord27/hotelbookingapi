package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Renter;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class RenterDAO extends AbstractDAO<Renter> {

    public RenterDAO(SessionFactory factory) {
        super(factory);
    }

    public Renter create(Renter renter) {
        return persist(renter);
    }

    public Renter findOne(String email) {
        return (Renter) namedQuery("edu.neu.cs5500.fantastix.core.Renter.findAll").setParameter("email", email).uniqueResult();
    }

    public List<Renter> findAll() {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Renter.findAll"));
    }

    public void delete(String email) {
        namedQuery("edu.neu.cs5500.fantastix.core.Renter.delete").setParameter("email", email);
    }
}
