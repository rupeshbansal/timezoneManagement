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

import java.util.List;
import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestController {

    private final Validator validator;
    private final UserService userService;

    public UserRestController(Validator validator, UserService userService) {
        this.validator = validator;
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

//    @PUT
//    @Path("/{id}")
//    public Response updateUserById(@PathParam("id") Integer id, User user) {
//        // validation
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        User e = UserDB.getUser(user.getId());
//        if (violations.size() > 0) {
//            ArrayList<String> validationMessages = new ArrayList<String>();
//            for (ConstraintViolation<User> violation : violations) {
//                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
//            }
//            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
//        }
//        if (e != null) {
//            user.setId(id);
//            UserDB.updateUser(id, user);
//            return Response.ok(user).build();
//        } else
//            return Response.status(Status.NOT_FOUND).build();
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response createUserTimezoneId(@PathParam("id") Integer id, String timezoneId) {
//        User user = UserDB.getUser(id);
//        Set<String> existingTimezoneIds = UserTimezoneDb.getAllTimezones(id);
//        if (existingTimezoneIds.contains(timezoneId)) {
//            String validationMessage = "Timezone already associated";
//            return Response.status(Status.BAD_REQUEST).entity(validationMessage).build();
//        } else if (!UserTimezone.getAllTimezoneIds().contains(timezoneId)) {
//            String validationMessage = "Invalid timezone id";
//            return Response.status(Status.BAD_REQUEST).entity(validationMessage).build();
//        }
//        if (user != null) {
//            UserTimezoneDb.setAllTimezones(id, ImmutableSet.of(timezoneId));
//            return Response.ok().build();
//        } else
//            return Response.status(Status.NOT_FOUND).build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response removeUserTimezoneId(@PathParam("id") Integer id, String timezoneId) {
//        User user = UserDB.getUser(id);
//        if (user != null) {
//            UserDB.removeUser(id);
//            return Response.ok().build();
//        } else
//            return Response.status(Status.NOT_FOUND).build();
//    }
}