package shb.cloud.license.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Slf4j
@Configuration
@EnableWebFlux
public class ShbLicenseWebConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routes() {

        ShbLicenseHandler handler = new ShbLicenseHandler();
        return RouterFunctions.route(GET("/licenses"), handler.getLicenses)
                .andRoute(GET("/licenses/{licenseId}"), handler.getLicenseById);
    }
}
