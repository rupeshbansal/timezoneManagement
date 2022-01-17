package com.toptalpremierleague.rest.controller;

import com.toptalpremierleague.rest.representations.api.UserTimezoneApi;
import com.toptalpremierleague.rest.representations.Timezone;
import com.toptalpremierleague.rest.representations.User;
import com.toptalpremierleague.rest.service.TimezoneService;
import com.toptalpremierleague.rest.service.UserService;
import io.dropwizard.auth.Auth;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

    @GET
    @Path("/getAllTimezones")
    public Response getAllTimezones() {
        return Response.ok(timezoneService.getAllTimezones()).build();
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Path("/getAllUserTimezones")
    public Response getAllUserTimezones(@Auth User user) {
        Set<String> userEmails = userService.getAllUserEmails();
        return Response.ok(timezoneService.getAllTimezonesForAllUsers(userEmails)).build();
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/createAdminUserTimezone")
    public Response createAdminUserTimezone(UserTimezoneApi userTimezoneApi, @Auth User user) {
        if (!userTimezoneApi.getUserEmailId().isPresent()) {
            return Response.status(409, "User Not Found").build();
        }
        timezoneService.createTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), userTimezoneApi.getUserEmailId().get());
        return Response.ok().build();
    }


    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/updateAdminUserTimezone")
    public Response updateAdminUserTimezone(UserTimezoneApi userTimezoneApi, @Auth User user) {
        if (!userTimezoneApi.getUserEmailId().isPresent()) {
            return Response.status(409, "User not found").build();
        }
        timezoneService.updateTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), userTimezoneApi.getUserEmailId().get());
        return Response.ok().build();
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/deleteAdminUserTimezone")
    public Response deleteAdminUserTimezone(UserTimezoneApi userTimezoneApi, @Auth User user) {
        if (!userTimezoneApi.getUserEmailId().isPresent()) {
            return Response.status(409, "User not found").build();
        }
        timezoneService.deleteTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getUserEmailId().get());
        return Response.ok().build();
    }

    @PermitAll
    @GET
    public Response getAllTimezones(@Auth User user) {
        return Response.ok(timezoneService.getAllTimezones(user.getEmail())).build();
    }

    @PermitAll
    @POST
    @Path("/createTimezone")
    public Response createTimezone(UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(401, "User not authorized").build();
            }
            timezoneService.createTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), user.getEmail());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    @PermitAll
    @POST
    @Path("/updateTimezone")
    public Response updateTimezone(UserTimezoneApi userTimezoneApi, @Auth User user) {
        try {
            if (userTimezoneApi.getUserEmailId().isPresent()) {
                return Response.status(401, "User not authorized").build();
            }
            timezoneService.updateTimezone(userTimezoneApi.getTimezoneId(), userTimezoneApi.getName(), user.getEmail());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

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