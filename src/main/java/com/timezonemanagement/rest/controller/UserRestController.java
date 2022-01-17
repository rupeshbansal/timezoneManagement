package com.timezonemanagement.rest.controller;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.representations.api.UserApi;
import com.timezonemanagement.rest.service.UserService;

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
        if (userService.getUserByEmailId(user.getEmail()).isPresent()) {
            return Response.status(409).entity("User already exists").build();
        }
        System.out.println(user.getEmail() + " " + user.getPassword());
        userService.createUser(user.toUser());
        return Response.ok().build();
    }
}