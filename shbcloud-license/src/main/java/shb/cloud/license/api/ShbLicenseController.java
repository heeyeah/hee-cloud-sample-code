package shb.cloud.license.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shb.cloud.license.entity.ShbEmployee;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.service.ShbLicenseService;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@RestController
public class ShbLicenseController {
    @Autowired
    private ShbLicenseService shbLicenseService;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    @GetMapping("/licenses/{licenseId}")
    public Mono<ShbLicense> getLicenseById(@PathVariable String licenseId) {

        log.info(" restcontroller = [getLicenseById] start");
        log.info("licenseId={}", licenseId);
        return shbLicenseService.getLicenseById(licenseId);
    }

    @GetMapping("/webflux/{userId}")
    public Mono<ShbEmployee> performWebClientInWebflux(@PathVariable String userId) {

        log.info("=> /webflux/{}",  userId);



        WebClient.RequestHeadersSpec headersSpec =
                ShbLicenseService.webClient.get().uri(uriBuilder -> uriBuilder.pathSegment("employee", userId).build());

        return headersSpec.retrieve().bodyToMono(ShbEmployee.class);
    }

}
