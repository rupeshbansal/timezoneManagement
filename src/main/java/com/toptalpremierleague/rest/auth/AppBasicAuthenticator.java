package com.toptalpremierleague.rest.auth;

import com.google.common.collect.Iterables;
import com.toptalpremierleague.rest.dao.UserDao;
import com.toptalpremierleague.rest.representations.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;
import java.util.Set;

import org.eclipse.jetty.util.security.Credential;

public class AppBasicAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDao userDAO;

    public AppBasicAuthenticator(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Set<User> users = userDAO.findUserByEmail(credentials.getUsername());
        if(users.size() == 0) {
            return Optional.empty();
        }

        User user = Iterables.getOnlyElement(users);
        String myHash = Credential.MD5.digest(credentials.getPassword());
        if (user != null && user.getSalt().equals(myHash))
        {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}