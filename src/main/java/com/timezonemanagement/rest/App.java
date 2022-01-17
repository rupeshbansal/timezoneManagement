package com.timezonemanagement.rest;

import com.timezonemanagement.rest.validator.TimezoneValidator;
import com.timezonemanagement.rest.auth.AppAuthorizer;
import com.timezonemanagement.rest.auth.AppBasicAuthenticator;
import com.timezonemanagement.rest.controller.TimezoneRestController;
import com.timezonemanagement.rest.controller.UserRestController;
import com.timezonemanagement.rest.dao.TimezoneDao;
import com.timezonemanagement.rest.dao.UserDao;
import com.timezonemanagement.rest.dao.UserTimezoneDao;
import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.service.TimezoneService;
import com.timezonemanagement.rest.service.UserService;
import com.timezonemanagement.rest.validator.UserValidator;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.eclipse.jetty.util.security.Credential;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<HelloWorldConfiguration> {
    Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    }

    @Override
    public void run(HelloWorldConfiguration c, Environment e) throws Exception {

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(e, c.getDataSourceFactory(), "postgresql");
        final UserDao userDao = jdbi.onDemand(UserDao.class);
        final TimezoneDao timezoneDao = jdbi.onDemand(TimezoneDao.class);
        final UserTimezoneDao userTimezoneDao = jdbi.onDemand(UserTimezoneDao.class);
        final UserValidator userValidator = new UserValidator(userDao);
        final TimezoneValidator timezoneValidator = new TimezoneValidator(timezoneDao, userTimezoneDao);
        TimezoneService timezoneService = new TimezoneService(timezoneDao, userTimezoneDao, userValidator, timezoneValidator);
        UserService userService = new UserService(userDao, userTimezoneDao, userValidator);

        e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new AppBasicAuthenticator(userDao))
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        e.jersey().register(RolesAllowedDynamicFeature.class);
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        e.jersey().register(userDao);
        e.jersey().register(new UserRestController(userService));
        e.jersey().register(new TimezoneRestController(timezoneService, userService));
        bootstrapTableIfNotAlreadyPresent(c, userDao, timezoneDao, userTimezoneDao, timezoneService);
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    private void bootstrapTableIfNotAlreadyPresent(HelloWorldConfiguration configuration, UserDao userDao, TimezoneDao timezoneDao, UserTimezoneDao userTimezoneDao, TimezoneService timezoneService) {
        try {
            userDao.createUserTable();
            timezoneDao.createTable();
            userTimezoneDao.createUserTimezonesTable();

            userDao.insertUserAsAdmin(configuration.getAdminUsername(), "Admin", "Admin", Credential.MD5.digest(configuration.getPassword()), Boolean.TRUE);
            timezoneService.populateTimezones();
        } catch (Exception e) {
            logger.warn("Failed to initialize the application. Its probably already initialized", e);
        }
    }

}