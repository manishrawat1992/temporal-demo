package org.tdemo.config;

import io.dropwizard.core.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoConfiguration extends Configuration {
    String temporalEndpoint;
}
