package tests;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.FeedbackDAO;
import edu.neu.cs5500.fantastix.resources.FeedbackResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.sql.Date;

import static org.junit.Assert.*;


public class FeedbackResourceTest {
    private FeedbackResource FR;

    public FeedbackResourceTest(FeedbackResource FR){
        FR = FR;
    }

    @Test
    public void testPostFeedback() throws Exception {
        Date currentDate = new Date(1459206495);
        Feedback feedback = new Feedback(1, 2, "abc@email.com", 5, "great", currentDate);
        Response r = FR.postFeedback(feedback);
        Feedback f = r.readEntity(Feedback.class);
        assertEquals(feedback, f);
        assertEquals(r.getStatus(), 201);
    }

    @Test
    public void testUpdateRating() throws Exception {
        Date currentDate = new Date(1459206495);
        Feedback feedback = new Feedback(1, 2, "abc@email.com", 5, "great", currentDate);
        Feedback feedback2 = new Feedback(1, 2, "abc@email.com", 4, "great", currentDate);

        Response c = FR.postFeedback(feedback);
        Feedback f1 = c.readEntity(Feedback.class);
        Response r = FR.updateRating(f1.getFeedbackID(), 4);
        Feedback f = r.readEntity(Feedback.class);
        assertEquals(feedback2, f);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindOne() throws Exception {
        Date currentDate = new Date(1459206495);
        Feedback feedback = new Feedback(1, 2, "abc@email.com", 5, "great", currentDate);

        Response c = FR.postFeedback(feedback);
        Feedback f1 = c.readEntity(Feedback.class);
        Response r = FR.findOne(f1.getFeedbackID());
        Feedback f = r.readEntity(Feedback.class);
        assertEquals(feedback, f);
        assertEquals(r.getStatus(), 200);
    }
}