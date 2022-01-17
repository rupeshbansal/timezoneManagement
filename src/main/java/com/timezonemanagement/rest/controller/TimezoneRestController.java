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
    @Path("/createUserTimezoneAsAdmin")
    public Response createUserTimezoneAsAdmin(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getId() != null) {
                return Response.status(400).entity("ID is required").build();
            }

            if (!userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(404).entity("User Not Found").build();
            }
            return Response.ok(timezoneService.createTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), userTimezoneApi.getUserEmailId().get())).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint to update an associated timezone for a specified user. Only admin users can access this
     */
    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/updateUserTimezoneAsAdmin")
    public Response updateUserTimezoneAsAdmin(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getId() == null) {
                return Response.status(400).entity("ID is required").build();
            }

            if (!userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(404).entity("User Not Found").build();
            }
            timezoneService.updateTimezone(userTimezoneApi.getId(), userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), userTimezoneApi.getUserEmailId().get());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    /**
     * An endpoint to delete an associated timezone for a specified user. Only admin users can access this
     */
    // TODO: deleteTimezoneAsAdmin
    // TODO: Functional/unit tests
    // TODO: Admin to crud on users
    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/deleteUserTimezoneAsAdmin")
    public Response deleteUserTimezoneAsAdmin(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getId() == null) {
                return Response.status(400).entity("ID is required").build();
            }

            if (!userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(404).entity("User Not Found").build();
            }
            timezoneService.deleteTimezone(userTimezoneApi.getId(), userTimezoneApi.getTimezoneId(), userTimezoneApi.getUserEmailId().get());
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
        return Response.ok(timezoneService.getAllUserTimezones(user.getEmail())).build();
    }

    /**
     * An endpoint to associated the user with a new timezoneId
     */
    @PermitAll
    @POST
    @Path("/createTimezone")
    public Response createTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getId() != null) {
                return Response.status(400).entity("The id is auto-generated. Please do not send it").build();
            }

            if (userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(401).entity("Not authorized").build();
            }
            return Response.ok(timezoneService.createTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), user.getEmail())).build();
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
            if (userTimezoneApi.getId() == null) {
                return Response.status(400).entity("ID is required").build();
            }

            if (userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(401).entity("Not authorized").build();
            }
            timezoneService.updateTimezone(userTimezoneApi.getId(), userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), user.getEmail());
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
    @Path("/deleteTimezone")
    public Response deleteTimezone(@Valid @NotNull UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getId() == null) {
                return Response.status(400).entity("ID is required").build();
            }

            timezoneService.deleteTimezone(userTimezoneApi.getId(), userTimezoneApi.getTimezoneId(), user.getEmail());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }
}