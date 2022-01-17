package com.toptalpremierleague.rest.controller;

import javax.annotation.security.PermitAll;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.toptalpremierleague.rest.dao.UserDao;
import com.toptalpremierleague.rest.representations.User;
import com.toptalpremierleague.rest.service.UserService;
import io.dropwizard.auth.Auth;
import org.eclipse.jetty.util.security.Credential;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
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
    public Response createUser(User user) {
        if (userService.getUserByEmailId(user.getEmail()).isPresent()) {
            return Response.status(409, "User already exists").build();
        }
        userService.createUser(user);
        return Response.ok().build();
    }
}