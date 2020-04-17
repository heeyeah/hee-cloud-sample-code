package shb.cloud.license.api;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.service.ShbLicenseService;


@Slf4j
@Component
public class ShbLicenseHandler {

    private ShbLicenseService shbLicenseService;

    public HandlerFunction getLicenses = req -> {

        log.info(" HandlerFunction = [getLicenses] start");
        MultiValueMap queryParams = req.queryParams();

        Flux list;

        if(queryParams.isEmpty()) {
            log.info("전체조회 ");
            list = shbLicenseService.getLicenses();
        } else {
            log.info("조건조회 ");
            list = req.queryParam("userId")
                    .map(userId -> shbLicenseService.getLicensesByContractorId(userId))
                    .orElse(Flux.empty());
        }

        req.queryParam("userId").map(
                userId -> {

                    WebClient webClient = WebClient.create();

                    return Mono.empty();
                }
        ).orElse(Mono.empty());


        Mono res = ok().body(list, ShbLicense.class);
        return res;
    };


    public HandlerFunction getLicenseById = req -> {

        log.info(" HandlerFunction = [getLicenseById] start");

        Map pathVariables = req.pathVariables();

        String licenseId = (String) pathVariables.getOrDefault("licenseId", "sampleLicenseId");

        Mono res = ok().body(shbLicenseService.getLicenseById(licenseId), ShbLicense.class);
        return res;
    };

    public ShbLicenseHandler(ShbLicenseService shbLicenseService) {
        this.shbLicenseService = shbLicenseService;
    }
}
