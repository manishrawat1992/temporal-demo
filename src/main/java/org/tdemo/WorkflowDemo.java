package org.tdemo;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.tdemo.config.DemoConfiguration;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class WorkflowDemo extends Application<DemoConfiguration> {
    public static void main(String[] args) throws Exception {
        new WorkflowDemo().run(args);
    }

    @Override
    public void run(DemoConfiguration configuration, Environment environment) throws Exception {
    }

    @Override
    public void initialize(Bootstrap<DemoConfiguration> bootstrap) {

        bootstrap.addBundle(GuiceBundle.builder()
                .modules(new DemoModule())
                .enableAutoConfig()
                .build());

    }
}