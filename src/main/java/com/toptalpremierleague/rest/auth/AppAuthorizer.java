package com.toptalpremierleague.rest.auth;

import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<AppUser>
{
    @Override
    public boolean authorize(AppUser appUser, String role) {
        return appUser.getRoles() != null && appUser.getRoles().contains(role);
    }
}