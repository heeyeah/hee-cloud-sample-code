package shb.cloud.license.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.Optional;

/**
 * not support Handler+Router Function (추측이지만, 맞는것같음)
 */
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(true)
            .select()
//        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .apis(RequestHandlerSelectors.any())
//            .apis(RequestHandlerSelectors.basePackage("shb.cloud.license.api"))
            .paths(PathSelectors.any())
        .build()
        .genericModelSubstitutes(Optional.class, Flux.class, Mono.class);
  }
}
