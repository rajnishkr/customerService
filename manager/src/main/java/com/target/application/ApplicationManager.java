package com.target.application;

import applicationManager.module.DI;
import applicationManager.config.ServiceConfiguration;
import applicationManager.module.ServiceModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationManager extends Application<ServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new ApplicationManager().run(args);
        log.info("Application Started...");
    }

    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<ServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServiceConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
        GuiceBundle.Builder<ServiceConfiguration> guiceBundleBuilder = GuiceBundle.newBuilder();
        GuiceBundle<ServiceConfiguration> guiceBundle = guiceBundleBuilder
                .setConfigClass(ServiceConfiguration.class)
                .enableAutoConfig("com.target")
                .addModule(new ServiceModule())
                .build();


        bootstrap.addBundle(guiceBundle);
        DI.di().init(guiceBundle.getInjector());


    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) {

        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        environment.getObjectMapper().setSubtypeResolver(new StdSubtypeResolver());
    }
}

