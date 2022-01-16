package com.toptalpremierleague.rest.auth;

import com.toptalpremierleague.rest.representations.User;
import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User>
{
    @Override
    public boolean authorize(User user, String role) {
        System.out.println("Expected role is " + role);
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}