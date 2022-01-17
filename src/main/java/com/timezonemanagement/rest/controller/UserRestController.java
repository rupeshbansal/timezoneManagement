package com.timezonemanagement.rest.controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.representations.api.UserApi;
import com.timezonemanagement.rest.service.UserService;
import io.dropwizard.auth.Auth;

import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @GET
    public Response getUser(@QueryParam("email") String email) {
        Optional<User> maybeUser = userService.getUserByEmailId(email);
        if (maybeUser.isPresent()) {
            return Response.ok(maybeUser.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/createUser")
    public Response createUser(@Valid @NotNull UserApi user) {
        userService.createUser(user.toUser());
        return Response.ok().build();
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/deleteUser")
    public Response deleteUser(@QueryParam("userEmailId") String userEmailId, @Auth User user) {
        try {
            userService.deleteUser(userEmailId);
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/createUserAsAdmin")
    public Response createUserAsAdmin(UserApi userApi, @Auth User user) {
        try {
            userService.createUserAsAdmin(userApi.toUser());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
        }
    }
}