package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Manager;
import edu.neu.cs5500.fantastix.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class ManagerDAO extends AbstractDAO<Manager> {

    public ManagerDAO(SessionFactory factory) {
        super(factory);
    }

    public Manager create(Manager manager) {
        return persist(manager);
    }

    public Manager findOne(String email) {
        return (Manager) namedQuery("edu.neu.cs5500.fantastix.core.Manager.findAll").setParameter("email", email).uniqueResult();
    }

    public List<Manager> findAll() {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Manager.findAll"));
    }

    public void delete(String email) {
        namedQuery("edu.neu.cs5500.fantastix.core.Manager.delete").setParameter("email", email);
    }
}
