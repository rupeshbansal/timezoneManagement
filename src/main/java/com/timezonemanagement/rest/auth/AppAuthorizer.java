package com.timezonemanagement.rest.auth;

import com.timezonemanagement.rest.representations.User;
import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User>
{
    @Override
    public boolean authorize(User user, String _role) {
        return user.getIsAdmin();
    }
}