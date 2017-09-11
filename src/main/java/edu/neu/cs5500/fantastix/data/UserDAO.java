package edu.neu.cs5500.fantastix.data;
import io.dropwizard.hibernate.AbstractDAO;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;
import edu.neu.cs5500.fantastix.core.User;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    @UnitOfWork
    public User create(User user) {
        return persist(user);
    }

    @UnitOfWork
    public User findOne(String email) {
        return get(email);
    }

    @UnitOfWork
    public List<User> findAll() {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Property.findAll"));
    }

    @UnitOfWork
    public User update(User user, String email) {
        User u = get(email);
        if (u != null) {
            u.setPassword(user.getPassword());
            u.setName(user.getName());
            u.setAddress(user.getAddress());
            u.setCity(user.getCity());
            u.setState(user.getState());
            u.setZip(user.getZip());
            return persist(u);
        }
        return null;
    }

    @UnitOfWork
    public void delete(String email) {
        namedQuery("edu.neu.cs5500.fantastix.core.User.delete").setParameter("email", email);
    }
}
