package com.timezonemanagement.rest.controller;

import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.representations.api.UserTimezoneApi;
import com.timezonemanagement.rest.service.TimezoneService;
import com.timezonemanagement.rest.service.UserService;
import io.dropwizard.auth.Auth;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/timezone")
@Produces(MediaType.APPLICATION_JSON)
public class TimezoneRestController {
    private final TimezoneService timezoneService;
    private final UserService userService;

    public TimezoneRestController(TimezoneService timezoneService, UserService userService) {
        this.timezoneService = timezoneService;
        this.userService = userService;
    }

//    @RolesAllowed({"ADMIN"})
//    @GET
//    @Path("/populateTimezones")
//    public Response populateTimezones() {
//        timezoneService.populateTimezones();
//        return Response.ok().build();
//    }

    /**
     * An endpoint to get all the timezones registered in the service. This is an exhaustive list of all the timezones that
     * the user can be associated with
     * Todo(Rupesh Bansal): Paginate this API
     */
    @GET
    @Path("/getAllTimezones")
    public Response getAllTimezones() {
        return Response.ok(timezoneService.getAllTimezones()).build();
    }

    /**
     * An endpoint to get all the timezones that each user is associated with. Only Admin users can access this
     * Todo(Rupesh Bansal): Paginate this API
     */
    @RolesAllowed({"ADMIN"})
    @GET
    @Path("/getAllUserTimezones")
    public Response getAllUserTimezones(@Auth User user) {
        Set<String> userEmails = userService.getAllUserEmails();
        return Response.ok(timezoneService.getAllTimezonesForAllUsers(userEmails)).build();
    }

    /**
     * An endpoint to associate some specific user with a specified timezone. Only admin users can access this
     */
    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/createAdminUserTimezone")
    public Response createAdminUserTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (!userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(404).entity("User Not Found").build();
            }
            timezoneService.createTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), userTimezoneApi.getUserEmailId().get());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint to update an associated timezone for a specified user. Only admin users can access this
     */
    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/updateAdminUserTimezone")
    public Response updateAdminUserTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (!userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(404).entity("User Not Found").build();
            }
            timezoneService.updateTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), userTimezoneApi.getUserEmailId().get());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint to delete an associated timezone for a specified user. Only admin users can access this
     */
    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/deleteAdminUserTimezone")
    public Response deleteAdminUserTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (!userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(404).entity("User Not Found").build();
            }
            timezoneService.deleteTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getUserEmailId().get());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint get all the timezones that the user is associated with
     */
    @PermitAll
    @GET
    public Response getAllTimezones(@Auth User user) {
        return Response.ok(timezoneService.getAllTimezones(user.getEmail())).build();
    }

    /**
     * An endpoint to associated the user with a new timezoneId
     */
    @PermitAll
    @POST
    @Path("/createTimezone")
    public Response createTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(401).entity("Not authorized").build();
            }
            timezoneService.createTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), user.getEmail());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint to update an already associated timezone for the user
     */
    @PermitAll
    @POST
    @Path("/updateTimezone")
    public Response updateTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(401).entity("Not authorized").build();
            }
            timezoneService.updateTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), user.getEmail());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint to delete an already associated timezone for the user
     */
    @PermitAll
    @DELETE
    @Path("/deleteTimezone/{timezoneId}")
    public Response deleteTimezone(@PathParam("timezoneId") int timezoneId, @Auth User user) {
        try {
            timezoneService.deleteTimezone(timezoneId, user.getEmail());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }
}