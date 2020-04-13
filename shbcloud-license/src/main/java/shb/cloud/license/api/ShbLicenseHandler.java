package shb.cloud.license.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.service.ShbLicenseService;

import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Slf4j
@Component
public class ShbLicenseHandler {

    @Autowired
    private ShbLicenseService shbLicenseService;

    HandlerFunction getLicenses = req -> {


        MultiValueMap queryParams = req.queryParams();

        log.info("### getLicenses");
        log.info("=> key set ::");
        queryParams.keySet().stream().forEach(s -> {
            log.info("{}={}", s, queryParams.get(s));
        });

        Flux list = Flux.just(new ShbLicense());//shbLicenseService.getLicenses();
        Mono res = ok().body(list, ShbLicense.class);
        return res;
    };

    HandlerFunction getLicenseById = req -> {
        log.info("### getLicenseById");
        Map pathVariables = req.pathVariables();

        pathVariables.keySet().stream().forEach(s-> {
            log.info("{}={}", s, pathVariables.get(s));
        });

        Flux list = Flux.just(new ShbLicense());
        Mono res = ok().body(list, ShbLicense.class);
        return res;
    };


//    HandlerFunction getLicensesByUserId = req -> {
//        Flux list = shbLicenseService.getLicenses();
//        Mono res = ok().body(list, ShbLicense.class);
//        return res;
//    };
}
