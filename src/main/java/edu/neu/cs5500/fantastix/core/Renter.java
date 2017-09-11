package edu.neu.cs5500.fantastix.core;

import edu.neu.cs5500.fantastix.core.User;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "renter", schema = "fantastix")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Renter.findOne",
                query = "SELECT u FROM edu.neu.cs5500.fantastix.core.User u JOIN Renter r ON u.email = r.email WHERE r.email = :email"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Renter.findAll",
                query = "SELECT u FROM edu.neu.cs5500.fantastix.core.User u JOIN Renter r ON u.email = r.email"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Renter.delete",
                query = "DELETE FROM Renter r WHERE r.email = :email"
        )
})

public class Renter extends User {
    public Renter(String email, String password, String name, String address, String city, String state, String zip) {
        super(email, password, name, address, city, state, zip);
    }
}
