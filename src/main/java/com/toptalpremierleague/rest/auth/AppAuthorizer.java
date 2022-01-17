package com.toptalpremierleague.rest.auth;

import com.toptalpremierleague.rest.representations.User;
import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User>
{
    @Override
    public boolean authorize(User user, String role) {
        System.out.println("Expected role is " + role);
        System.out.println("User is " + user.getIsAdmin());
        System.out.println("User Email is " + user.getEmail());
        System.out.println("User is " + user);
        return true;
    }
}