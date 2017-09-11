package edu.neu.cs5500.fantastix.data;

import io.dropwizard.hibernate.AbstractDAO;
import edu.neu.cs5500.fantastix.core.Property;
import org.hibernate.SessionFactory;

import java.util.List;

public class PropertyDAO extends AbstractDAO<Property> {

    public PropertyDAO(SessionFactory factory) {
        super(factory);
    }

    public Property create(Property property) {
        return persist(property);
    }

    public Property findOne(int propertyID) {
        return get(propertyID);
    }

    public List<Property> findAll() {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Property.findAll"));
    }

    public Property update(Property property, int propertyID) {
        Property p = get(propertyID);
        if (p != null) {
            p.setName(property.getName());
            p.setManagerEmail(property.getManagerEmail());
            p.setPrice(property.getPrice());
            p.setAddress(property.getAddress());
            p.setCity(property.getCity());
            p.setState(property.getState());
            p.setZip(property.getZip());
            p.setAccommodation(property.getAccommodation());
            p.setBathrooms(property.getBathrooms());
            p.setBeds(property.getBeds());
            p.setCheckInTime(property.getCheckInTime());
            p.setCheckOutTime(property.getCheckOutTime());
            p.setRoomType(property.getRoomType());
            return persist(p);
        }
        return null;
    }

    public void delete(int propertyID) {
        namedQuery("edu.neu.cs5500.fantastix.core.Property.delete").setParameter("propertyID", propertyID);
    }

    public List<Property> findByManager(String managerEmail) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Property.findByManager").setParameter("managerEmail", managerEmail));
    }

    public List<Property> findByName(String name) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Property.findByName").setParameter("name", name));
    }


}