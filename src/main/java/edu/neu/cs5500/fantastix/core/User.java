package edu.neu.cs5500.fantastix.core;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "fantastix")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.User.findAll",
                query = "SELECT u FROM User u"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.User.delete",
                query = "DELETE FROM User u WHERE u.email = :email"
        )
})

public class User
{
    @Id
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    public User(String email, String password, String name, String address, String city, String state, String zip) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}