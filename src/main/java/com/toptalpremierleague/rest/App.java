package com.toptalpremierleague.rest;

import com.toptalpremierleague.rest.auth.AppAuthorizer;
import com.toptalpremierleague.rest.auth.AppBasicAuthenticator;
import com.toptalpremierleague.rest.auth.AppUser;
import com.toptalpremierleague.rest.controller.UserRestController;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }

    @Override
    public void run(Configuration c, Environment e) throws Exception {
        e.jersey().register(new UserRestController(e.getValidator()));

//        final Client client = new JerseyClientBuilder(e).build("DemoRESTClient");
//        e.jersey().register(new RESTClientController(client));

//         Application health check
//        e.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));

//         Run multiple health checks
//        e.jersey().register(new HealthCheckController(e.healthChecks()));

        //****** Dropwizard security - custom classes ***********/
        e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<AppUser>()
                .setAuthenticator(new AppBasicAuthenticator())
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        e.jersey().register(RolesAllowedDynamicFeature.class);
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(AppUser.class));
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}