package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.User;
import edu.neu.cs5500.fantastix.data.UserDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/user", description = "")
public class UserResource {

    private static UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @POST
    @ApiOperation(value = "User", notes = "Return the newly created user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created")
    })
    public Response create(User user) {
        int statusCode = 201;
        User u = userDAO.create(user);
        return Response.status(statusCode).entity(u).build();
    }

    @GET
    @Path("{email}")
    @ApiOperation(value = "User", notes = "Return user with the given email if exists, null otherwise", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "No user with such email found")
    })
    public Response findOne(@PathParam("email") String email) {
        User u = userDAO.findOne(email);
        int statusCode = (u != null) ? 200 : 404;
        return Response.status(statusCode).entity(u).build();
    }

    @GET
    @ApiOperation(value="User[]", notes="Return all users if any", response=User[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users found"),
            @ApiResponse(code = 404, message = "No users registered")
    })
    public Response findAll() {
        User[] u = (User[]) userDAO.findAll().toArray();
        int statusCode = (u.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(u).build();
    }

    @PUT
    @Path("{email}")
    @ApiOperation(value = "User", notes = "Return updated user if user with given email exists, null otherwise", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 404, message = "No user with such email found")
    })
    public Response update(@PathParam("email") String email, User user) {
        User u = userDAO.update(user, email);
        int statusCode = (u != null) ? 200 : 404;
        return Response.status(statusCode).entity(u).build();
    }

    @DELETE
    @Path("{email}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User deleted"),
            @ApiResponse(code = 404, message = "No user with such email found")
    })
    public Response delete(@PathParam("email") String email) {
        userDAO.delete(email);
        int statusCode = 204;
        return Response.status(statusCode).build();
    }
}
