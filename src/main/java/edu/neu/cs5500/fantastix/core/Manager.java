package edu.neu.cs5500.fantastix.core;

import edu.neu.cs5500.fantastix.core.User;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "manager", schema = "fantastix")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Manager.findOne",
                query = "SELECT u FROM edu.neu.cs5500.fantastix.core.User u JOIN Manager m ON u.email = m.email WHERE m.email = :email"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Manager.findAll",
                query = "SELECT u FROM edu.neu.cs5500.fantastix.core.User u JOIN Manager m ON u.email = m.email"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Manager.delete",
                query = "DELETE FROM Manager m WHERE m.email = :email"
        )
})

public class Manager extends User {
    public Manager(String email, String password, String name, String address, String city, String state, String zip) {
        super(email, password, name, address, city, state, zip);
    }
}