package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Feedback;
import edu.neu.cs5500.fantastix.data.FeedbackDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/feedback", description = "")
public class FeedbackResource {

    private FeedbackDAO feedbackDAO;

    public FeedbackResource(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }

    @POST
    @ApiOperation(value = "Feedback", notes = "Return posted feedback", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Feedback posted")
    })
    public Response postFeedback(Feedback feedback) {
        int statusCode = 201;
        Feedback f = feedbackDAO.create(feedback);
        return Response.status(statusCode).entity(f).build();
    }

    @PUT
    @ApiOperation(value = "Feedback", notes = "Return updated feedback if booking with given ID exists, null otherwise", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback updated"),
            @ApiResponse(code = 404, message = "No booking with such ID found")
    })
    @Path("{feedbackID}")
    public Response updateRating(@PathParam("feedbackID") int feedbackID, int rating) {
        Feedback f = feedbackDAO.findOne(feedbackID);
        int statusCode = (f != null) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }


    @GET
    @ApiOperation(value = "Feedback", notes = "Return feedback with given ID if exists, null otherwise", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback found"),
            @ApiResponse(code = 404, message = "No feedback with such ID found")
    })
    @Path("{feedbackID}")
    public Response findOne(@PathParam("feedbackID") int feedbackID) {
        Feedback f = feedbackDAO.findOne(feedbackID);
        int statusCode = (f != null) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }
}