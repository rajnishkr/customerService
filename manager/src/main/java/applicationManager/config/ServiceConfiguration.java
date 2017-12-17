package applicationManager.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Configuration;

public class ServiceConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
