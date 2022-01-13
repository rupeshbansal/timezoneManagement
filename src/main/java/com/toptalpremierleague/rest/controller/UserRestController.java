package com.toptalpremierleague.rest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.collect.ImmutableSet;
import com.toptalpremierleague.rest.auth.AppUser;
import com.toptalpremierleague.rest.dao.UserDB;
import com.toptalpremierleague.rest.dao.UserTimezoneDb;
import com.toptalpremierleague.rest.representations.User;
import com.toptalpremierleague.rest.representations.UserTimezone;
import io.dropwizard.auth.Auth;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestController {

    private final Validator validator;

    public UserRestController(Validator validator) {
        this.validator = validator;
    }

//    @GET
//    public Response getEmployees() {
//        return Response.ok(EmployeeDB.getEmployees()).build();
//    }

    @PermitAll
    @GET
    public Response getUsers(@Auth AppUser appUser) {
        return Response.ok(UserDB.getUsers()).build();
    }

    @RolesAllowed({ "ADMIN" })
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Integer id, @Auth AppUser appUser) {
        User user = UserDB.getUser(id);
        if (user != null)
            return Response.ok(user).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response createUser(User user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = UserDB.getUser(user.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            UserDB.updateUser(user.getId(), user);
            return Response.created(new URI("/employees/" + user.getId()))
                    .build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUserById(@PathParam("id") Integer id, User user) {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = UserDB.getUser(user.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            user.setId(id);
            UserDB.updateUser(id, user);
            return Response.ok(user).build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response createUserTimezoneId(@PathParam("id") Integer id, String timezoneId) {
        User user = UserDB.getUser(id);
        Set<String> existingTimezoneIds = UserTimezoneDb.getAllTimezones(id);
        if (existingTimezoneIds.contains(timezoneId)) {
            String validationMessage = "Timezone already associated";
            return Response.status(Status.BAD_REQUEST).entity(validationMessage).build();
        } else if(!UserTimezone.getAllTimezoneIds().contains(timezoneId)) {
            String validationMessage = "Invalid timezone id";
            return Response.status(Status.BAD_REQUEST).entity(validationMessage).build();
        }
        if (user != null) {
            UserTimezoneDb.setAllTimezones(id, ImmutableSet.of(timezoneId));
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeUserTimezoneId(@PathParam("id") Integer id, String timezoneId) {
        User user = UserDB.getUser(id);
        if (user != null) {
            UserDB.removeUser(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
}