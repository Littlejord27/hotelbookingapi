package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Property;
import edu.neu.cs5500.fantastix.core.PropertyImage;
import edu.neu.cs5500.fantastix.data.PropertyDAO;
import edu.neu.cs5500.fantastix.data.PropertyImageDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/property")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/property", description = "")
public class PropertyResource {

    private PropertyDAO propertyDAO;
    private PropertyImageDAO propertyImageDAO;

    public PropertyResource(PropertyDAO propertyDAO, PropertyImageDAO propertyImageDAO) {
        this.propertyDAO = propertyDAO;
        this.propertyImageDAO = propertyImageDAO;
    }

    @POST
    @ApiOperation(value = "Property", notes = "Return the newly created property", response = Property.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Property created")
    })
    public Response create(Property property) {
        int statusCode = 201;
        Property p = propertyDAO.create(property);
        return Response.status(statusCode).entity(p).build();
    }

    @PUT
    @Path("{propertyID}")
    @ApiOperation(value = "Property", notes = "Return updated property if property with given ID exists, null otherwise", response = Property.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property updated"),
            @ApiResponse(code = 404, message = "No property with such ID found")
    })
    public Response update(@PathParam("propertyID") int propertyID, Property property) {
        Property p = propertyDAO.update(property, propertyID);
        int statusCode = (p != null) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @GET
    @Path("{propertyID}")
    @ApiOperation(value = "Property", notes = "Return property with given ID if exists, null otherwise", response = Property.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property found"),
            @ApiResponse(code = 404, message = "No property with such ID found")
    })
    public Response findOne(@PathParam("propertyID") int propertyID) {
        Property p = propertyDAO.findOne(propertyID);
        int statusCode = (p != null) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @GET
    @ApiOperation(value = "Property[]", notes = "Return all properties if any", response = Property[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Properties found"),
            @ApiResponse(code = 404, message = "No properties listed")
    })
    public Response findAll() {
        Property[] p = (Property[]) propertyDAO.findAll().toArray();
        int statusCode = (p.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @GET
    @Path("?")
    @ApiOperation(value = "Property[]", notes = "Return all properties with similar name if any", response = Property[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Properties found with similar name"),
            @ApiResponse(code = 404, message = "No property with similar name found")
    })
    public Response findByName(@QueryParam("name") String name) {
        Property[] p = (Property[]) propertyDAO.findByName(name).toArray();
        int statusCode = (p.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @DELETE
    @Path("{propertyID}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Property deleted"),
            @ApiResponse(code = 404, message = "No property with such ID found")
    })
    public Response delete(@PathParam("propertyID") int propertyID) {
        propertyDAO.delete(propertyID);
        int statusCode = 204;
        return Response.status(statusCode).build();
    }

    @POST
    @Path("{propertyID}/image")
    @ApiOperation(value = "PropertyImage", notes = "Return the newly uploaded image", response = PropertyImage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Image uploaded")
    })
    public Response uploadImage(@PathParam("propertyID") int propertyID, PropertyImage image) {
        int statusCode = 201;
        image.setPropertyID(propertyID);
        return Response.status(statusCode).entity(image).build();
    }

    @GET
    @Path("{propertyID}/image")
    @ApiOperation(value = "PropertyImage[]", notes = "Return all images of the property if any", response = PropertyImage[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Images found for this property"),
            @ApiResponse(code = 404, message = "No images uploaded for this property")
    })
    public Response findImagesByProperty(@PathParam("propertyID") int propertyID) {
        PropertyImage[] pi = (PropertyImage[]) propertyImageDAO.findByProperty(propertyID).toArray();
        int statusCode = (pi.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(pi).build();
    }

    @GET
    @Path("{propertyID}/image/{imageID}")
    @ApiOperation(value = "PropertyImage", notes = "Return images of the property if exists, null otherwise", response = PropertyImage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Image found for this property"),
            @ApiResponse(code = 404, message = "No image uploaded for this property with such ID found")
    })
    public Response findImageByProperty(@PathParam("propertyID") int propertyID, @PathParam("imageID") int imageID) {
        PropertyImage pi = propertyImageDAO.findOne(imageID);
        int statusCode = (pi != null && pi.getPropertyID() == propertyID) ? 200 : 404;
        return Response.status(statusCode).entity(pi).build();
    }

    @PUT
    @Path("{propertyID}/image/{imageID}")
    @ApiOperation(value = "PropertyImage", notes = "Return updated image if image of the property with given ID exists, null otherwise", response = PropertyImage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Image description updated"),
            @ApiResponse(code = 404, message = "No image for this property with such ID found")
    })
    public Response updateDescription(@PathParam("propertyID") int propertyID, @PathParam("imageID") int imageID, PropertyImage propertyImage) {
        PropertyImage pi = propertyImageDAO.findOne(imageID);
        if (pi != null && pi.getPropertyID() == propertyID) {
            pi = propertyImageDAO.updateDescription(propertyImage.getDescription(), imageID);
            return Response.status(200).entity(pi).build();
        }
        return Response.status(404).entity(null).build();
    }

    @DELETE
    @Path("{propertyID}/image/{imageID}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Image deleted for this property"),
            @ApiResponse(code = 404, message = "No image for this property with such ID found")
    })
    public Response deleteImageByProperty(@PathParam("propertyID") int propertyID, @PathParam("imageID") int imageID) {
        PropertyImage pi = propertyImageDAO.findOne(imageID);
        int statusCode = 404;
        if (pi != null && pi.getPropertyID() == propertyID) {
            propertyImageDAO.delete(propertyID);
            statusCode = 204;
        }
        return Response.status(statusCode).build();
    }
}