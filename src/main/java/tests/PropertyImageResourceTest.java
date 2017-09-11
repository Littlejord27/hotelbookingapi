package tests;

import edu.neu.cs5500.fantastix.ServiceConfiguration;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.PropertyImageDAO;
import edu.neu.cs5500.fantastix.data.PropertyDAO;
import edu.neu.cs5500.fantastix.resources.PropertyResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.junit.Test;
import java.util.List;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;


public class PropertyImageResourceTest {
    private PropertyResource PR;

    public PropertyImageResourceTest(PropertyResource PR){
        PR = PR;
    }

    @Test
    public void testUploadImage() throws Exception {
        //int imageID, int propertyID, String title, String description, String url
        PropertyImage propertyImage = new PropertyImage(10, 20, "image10", "living room", "www.testURL.com");
        Response r = PR.uploadImage(propertyImage.getPropertyID(), propertyImage);
        PropertyImage p = r.readEntity(PropertyImage.class);
        assertEquals(propertyImage, p);
        assertEquals(r.getStatus(), 201);
    }

    @Test
    public void testFindImagesByProperty() throws Exception {
        PropertyImage propertyImage1 = new PropertyImage(11, 20, "image10", "living room", "www.testURL1.com");
        PropertyImage propertyImage2 = new PropertyImage(12, 20, "image12", "kitchen", "www.testURL2.com");

        Response c1 = PR.uploadImage(propertyImage1.getPropertyID(), propertyImage1);
        PropertyImage p1 = c1.readEntity(PropertyImage.class);
        Response c2 = PR.uploadImage(propertyImage2.getPropertyID(), propertyImage2);
        PropertyImage p2 = c2.readEntity(PropertyImage.class);

        Response r = PR.findImagesByProperty(20);
        List<PropertyImage> pList = r.readEntity(List.class);
        assertEquals(p1, pList.get(0));
        assertEquals(p2, pList.get(1));
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testFindImageByProperty() throws Exception {
        PropertyImage propertyImage = new PropertyImage(10, 20, "image10", "living room", "www.testURL.com");

        Response c = PR.uploadImage(propertyImage.getPropertyID(), propertyImage);
        PropertyImage p1 = c.readEntity(PropertyImage.class);
        Response r = PR.findImageByProperty(p1.getPropertyID(), p1.getImageID());
        PropertyImage p = r.readEntity(PropertyImage.class);
        assertEquals(propertyImage, p);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testUpdateDescription() throws Exception {
        PropertyImage propertyImage1 = new PropertyImage(10, 20, "image10", "living room", "www.testURL.com");
        PropertyImage propertyImage2 = new PropertyImage(10, 20, "image10", "dining room", "www.testURL.com");
        Response c = PR.uploadImage(propertyImage1.getPropertyID(), propertyImage1);
        PropertyImage p1 = c.readEntity(PropertyImage.class);
        Response r = PR.updateDescription(p1.getPropertyID(), p1.getImageID(), propertyImage2);
        PropertyImage p = r.readEntity(PropertyImage.class);
        assertEquals(propertyImage2, p);
        assertEquals(r.getStatus(), 200);
    }

    @Test
    public void testDeleteImageByProperty() throws Exception {
        PropertyImage propertyImage = new PropertyImage(10, 20, "image10", "living room", "www.testURL.com");
        Response c = PR.uploadImage(propertyImage.getPropertyID(), propertyImage);
        PropertyImage p1 = c.readEntity(PropertyImage.class);
        Response r = PR.deleteImageByProperty(p1.getPropertyID(), p1.getImageID());
        PropertyImage p = r.readEntity(PropertyImage.class);
        assertNull(p);
        assertEquals(r.getStatus(), 404);
    }
}