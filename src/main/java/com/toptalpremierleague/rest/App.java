package com.toptalpremierleague.rest;

import com.toptalpremierleague.rest.auth.AppAuthorizer;
import com.toptalpremierleague.rest.auth.AppBasicAuthenticator;
import com.toptalpremierleague.rest.controller.TimezoneRestController;
import com.toptalpremierleague.rest.controller.UserRestController;
import com.toptalpremierleague.rest.dao.TimezoneDao;
import com.toptalpremierleague.rest.dao.UserDao;
import com.toptalpremierleague.rest.dao.UserTimezoneDao;
import com.toptalpremierleague.rest.representations.User;
import com.toptalpremierleague.rest.service.TimezoneService;
import com.toptalpremierleague.rest.service.UserService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;

public class App extends Application<HelloWorldConfiguration> {
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

        TimezoneService timezoneService = new TimezoneService(timezoneDao, userTimezoneDao);
        UserService userService = new UserService(userDao);

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
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

}