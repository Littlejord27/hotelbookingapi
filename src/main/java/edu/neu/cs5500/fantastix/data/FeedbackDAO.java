package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Feedback;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class FeedbackDAO extends AbstractDAO<Feedback> {
    public FeedbackDAO(SessionFactory factory)
    {
        super(factory);
    }

    public Feedback create(Feedback feedback)
    {
        return persist(feedback);
    }

    public Feedback findOne(int feedbackID) {
        return get(feedbackID);
    }

    public List<Feedback> findByProperty(int propertyID) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Feedback.findByProperty").setParameter("propertyID", propertyID));
    }

    public List<Feedback> findByRenter(String renterEmail) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Feedback.findByRenter").setParameter("renterEmail", renterEmail));
    }

    public double findAverageRatingByProperty(int propertyID) {
        return (double) namedQuery("edu.neu.cs5500.fantastix.core.Feedback.findAverageRatingByProperty").setParameter("propertyID", propertyID).uniqueResult();
    }

    public Feedback updateRating(int rating, int feedbackID) {
        Feedback feedback = get(feedbackID);
        if (feedback != null) {
            feedback.setRating(rating);
            return persist(feedback);
        }
        return null;
    }

    public void delete(int feedbackID) {
        namedQuery("edu.neu.cs5500.fantastix.core.Feedback.delete").setParameter("feedbackID", feedbackID);
    }
}