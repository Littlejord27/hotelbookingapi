package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Booking;
import edu.neu.cs5500.fantastix.core.Feedback;
import edu.neu.cs5500.fantastix.core.Renter;
import edu.neu.cs5500.fantastix.data.BookingDAO;
import edu.neu.cs5500.fantastix.data.FeedbackDAO;
import edu.neu.cs5500.fantastix.data.RenterDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/renter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/renter", description = "")
public class RenterResource {

    private static RenterDAO renterDAO;
    private static BookingDAO bookingDAO;
    private static FeedbackDAO feedbackDAO;

    public RenterResource(RenterDAO renterDAO, BookingDAO bookingDAO, FeedbackDAO feedbackDAO) {
        this.renterDAO = renterDAO;
        this.bookingDAO = bookingDAO;
        this.feedbackDAO = feedbackDAO;
    }

    @POST
    @ApiOperation(value = "Renter", notes = "Return the newly created renter", response = Renter.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Renter created")
    })
    public Response create(Renter renter) {
        int statusCode = 201;
        Renter r = renterDAO.create(renter);
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @Path("{renterEmail}")
    @ApiOperation(value = "Renter", notes = "Return a renter with the given email if exists, null otherwise", response = Renter.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Renter found"),
            @ApiResponse(code = 404, message = "No renter with such email found")
    })
    public Response findOne(@PathParam("renterEmail") String renterEmail) {
        Renter r = renterDAO.findOne(renterEmail);
        int statusCode = (r != null) ? 200 : 404;
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @ApiOperation(value = "Renter[]", notes = "Return all renters if any", response = Renter[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Renters found"),
            @ApiResponse(code = 404, message = "No renters registered")
    })
    public Response findAll() {
        Renter[] r = (Renter[]) renterDAO.findAll().toArray();
        int statusCode = (r.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @Path("{renterEmail}/booking")
    @ApiOperation(value = "Booking[]", notes = "Return all bookings by this renter if any", response = Booking[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Bookings found by the renter"),
            @ApiResponse(code = 404, message = "No bookings made by the renter")
    })
    public Response findBookings(@PathParam("renterEmail") String renterEmail) {
        Booking[] b = (Booking[]) bookingDAO.findByRenter(renterEmail).toArray();
        int statusCode = (b.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }

    @GET
    @Path("{renterEmail}/feedback")
    @ApiOperation(value = "Feedback[]", notes = "Return all feedback by this renter if any", response = Feedback[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback found by the renter"),
            @ApiResponse(code = 404, message = "No feedback made by the renter")
    })
    public Response findFeedback(@PathParam("renterEmail") String renterEmail) {
        Feedback[] f = (Feedback[]) feedbackDAO.findByRenter(renterEmail).toArray();
        int statusCode = (f.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }

    @DELETE
    @Path("{renterEmail}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Renter deleted"),
            @ApiResponse(code = 404, message = "No renter with such email found")
    })
    public Response delete(@PathParam("renterEmail") String renterEmail) {
        renterDAO.delete(renterEmail);
        int statusCode = 204;
        return Response.status(statusCode).build();
    }
}
