package com.toptalpremierleague.rest.controller;

import com.toptalpremierleague.rest.representations.PreUserTimezone;
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
    public Response createAdminUserTimezone(PreUserTimezone preUserTimezone, @Auth User user) {
        timezoneService.createTimezone(preUserTimezone.getTimezone(), preUserTimezone.getEmailId());
        return Response.ok().build();
    }


    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/updateAdminUserTimezone")
    public Response updateAdminUserTimezone(PreUserTimezone preUserTimezone, @Auth User user) {
        timezoneService.updateTimezone(preUserTimezone.getTimezone(), preUserTimezone.getEmailId());
        return Response.ok().build();
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/deleteAdminUserTimezone")
    public Response deleteAdminUserTimezone(PreUserTimezone preUserTimezone, @Auth User user) {
        timezoneService.deleteTimezone(preUserTimezone.getTimezone().getId(), preUserTimezone.getEmailId());
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
    public Response createTimezone(Timezone timezone, @Auth User user) {
        timezoneService.createTimezone(timezone, user.getEmail());
        return Response.ok().build();
    }

    @PermitAll
    @POST
    @Path("/updateTimezone")
    public Response updateTimezone(Timezone timezone, @Auth User user) {
        timezoneService.updateTimezone(timezone, user.getEmail());
        return Response.ok().build();
    }

    @PermitAll
    @DELETE
    @Path("/deleteTimezone/{timezoneId}")
    public Response deleteTimezone(@PathParam("timezoneId") int timezoneId, @Auth User user) {
        timezoneService.deleteTimezone(timezoneId, user.getEmail());
        return Response.ok().build();
    }
}