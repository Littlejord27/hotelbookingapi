package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Booking;
import edu.neu.cs5500.fantastix.core.Status;
import edu.neu.cs5500.fantastix.data.BookingDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/booking", description = "")
public class BookingResource {

    private BookingDAO bookingDAO;

    public BookingResource(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @POST
    @ApiOperation(value = "Booking", notes = "Return created booking", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Booking created")
    })
    public Response createBooking(Booking booking) {
        int statusCode = 201;
        Booking b = bookingDAO.create(booking);
        return Response.status(statusCode).entity(b).build();
    }

    @PUT
    @Path("{bookingID}")
    @ApiOperation(value = "Booking", notes = "Return updated booking if booking with given ID exists, null otherwise", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking updated"),
            @ApiResponse(code = 404, message = "No booking with such ID found")
    })
    public Response updateStatus(@PathParam("bookingID") int bookingID, Booking booking) {
        Booking b = bookingDAO.updateStatus(booking.getStatus(), bookingID);
        int statusCode = (b != null) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }

    @GET
    @Path("{bookingID}")
    @ApiOperation(value = "Booking", notes = "Return booking with given ID if exists, null otherwise", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking found"),
            @ApiResponse(code = 404, message = "No booking with such ID found")
    })
    public Response findOne(@PathParam("bookingID") int bookingID) {
        Booking b = bookingDAO.findOne(bookingID);
        int statusCode = (b != null) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }
}