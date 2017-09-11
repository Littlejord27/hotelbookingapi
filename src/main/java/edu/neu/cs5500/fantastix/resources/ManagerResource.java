package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Manager;
import edu.neu.cs5500.fantastix.core.Property;
import edu.neu.cs5500.fantastix.data.ManagerDAO;
import edu.neu.cs5500.fantastix.data.PropertyDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/manager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/manager", description = "")
public class ManagerResource {

    private static ManagerDAO managerDAO;
    private static PropertyDAO propertyDAO;

    public ManagerResource(ManagerDAO managerDAO, PropertyDAO propertyDAO) {
        this.managerDAO = managerDAO;
        this.propertyDAO = propertyDAO;
    }

    @POST
    @ApiOperation(value = "Manager", notes = "Return the newly created manager", response = Manager.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Manager created")
    })
    public Response create(Manager manager) {
        int statusCode = 201;
        Manager r = managerDAO.create(manager);
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @Path("{managerEmail}")
    @ApiOperation(value = "Manager", notes = "Return a manager with the given email if exists, null otherwise", response = Manager.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Manager found"),
            @ApiResponse(code = 404, message = "No manager with such email found")
    })
    public Response findOne(@PathParam("managerEmail") String managerEmail) {
        Manager m = managerDAO.findOne(managerEmail);
        int statusCode = (m != null) ? 200 : 404;
        return Response.status(statusCode).entity(m).build();
    }

    @GET
    @ApiOperation(value = "Manager[]", notes = "Return all managers if any", response = Manager[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Managers found"),
            @ApiResponse(code = 404, message = "No managers registered")
    })
    public Response findAll() {
        Manager[] m = (Manager[]) managerDAO.findAll().toArray();
        int statusCode = (m.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(m).build();
    }

    @GET
    @Path("{managerEmail}/property")
    @ApiOperation(value = "Property[]", notes = "Return all properties by this manager if any", response = Property[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Properties found by the manager"),
            @ApiResponse(code = 404, message = "No properties managed by the manager")
    })
    public Response findProperties(@PathParam("managerEmail") String managerEmail) {
        Property[] p = (Property[]) propertyDAO.findByManager(managerEmail).toArray();
        int statusCode = (p.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @DELETE
    @Path("{managerEmail}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Manager deleted"),
            @ApiResponse(code = 404, message = "No manager with such email found")
    })
    public Response delete(@PathParam("managerEmail") String managerEmail) {
        managerDAO.delete(managerEmail);
        int statusCode = 204;
        return Response.status(statusCode).build();
    }
}
