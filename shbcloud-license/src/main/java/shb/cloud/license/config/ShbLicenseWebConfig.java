package shb.cloud.license.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import shb.cloud.license.api.ShbLicenseHandler;
import shb.cloud.license.service.ShbLicenseService;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableWebFlux
public class ShbLicenseWebConfig implements WebFluxConfigurer {

    @Autowired
    ShbLicenseService shbLicenseService;

    @Bean
    public RouterFunction<ServerResponse> routes() {

        ShbLicenseHandler handler = new ShbLicenseHandler(shbLicenseService);
        return RouterFunctions.route(GET("/licenses"), handler.getLicenses)
                .andRoute(GET("/licenses/{licenseId}"), handler.getLicenseById);
    }
}
