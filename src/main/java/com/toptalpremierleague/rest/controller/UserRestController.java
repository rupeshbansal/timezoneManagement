package com.toptalpremierleague.rest.controller;

import javax.annotation.security.PermitAll;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.toptalpremierleague.rest.dao.UserDao;
import com.toptalpremierleague.rest.representations.User;
import io.dropwizard.auth.Auth;
import org.eclipse.jetty.util.security.Credential;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestController {

    private final Validator validator;
    private final UserDao userDAO;

    public UserRestController(Validator validator, UserDao userDAO) {
        this.validator = validator;
        this.userDAO = userDAO;
    }

//    @GET
//    public Response getEmployees() {
//        return Response.ok(EmployeeDB.getEmployees()).build();
//    }

//    @PermitAll
//    @GET
//    public Response getUsers(@Auth AppUser appUser) {
//        return Response.ok(UserDB.getUsers()).build();
//    }

    @PermitAll
    @GET
    @Path("/{id}")
    public Response getUsersById(@PathParam("id") Integer id, @Auth User user) {
//        User user = UserDB.getUser(id);
//        userDAO.createSomethingTable();
//        userDAO.insert(id, "some random string");
//        if (user != null)
        return Response.ok().build();
//        else
//            return Response.status(Status.NOT_FOUND).build();
    }

//    @RolesAllowed({"ADMIN"})
    @PermitAll
    @GET
    public Response getUser(@QueryParam("email") String email) {
//        User user = UserDB.getUser(id);
//        userDAO.createSomethingTable();
//        userDAO.insert(id, "some random string");
//        if (user != null)
        return Response.ok(userDAO.findUserByEmail(email)).build();
//        else
//            return Response.status(Status.NOT_FOUND).build();
    }


    @POST
    @Path("/createUser")
    public Response createUser(User user) {
        userDAO.insert(user.getEmail(), user.getFirstName(), user.getLastName(), Credential.MD5.digest(user.getSalt()));
//        userDAO.createSomethingTable();
//        if (user != null)
        return Response.ok(userDAO.findUserByEmail(user.getEmail())).build();
//        else
//            return Response.status(Status.NOT_FOUND).build();
    }

//    @POST
//    public Response createAppUser(User user) throws URISyntaxException {
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
//            UserDB.updateUser(user.getId(), user);
//            return Response.created(new URI("/employees/" + user.getId()))
//                    .build();
//        } else
//            return Response.status(Status.NOT_FOUND).build();
//    }

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