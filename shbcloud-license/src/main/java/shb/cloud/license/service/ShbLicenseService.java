package shb.cloud.license.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import shb.cloud.license.entity.ShbLicense;

@Component
public class ShbLicenseService {

    public Flux<ShbLicense> getLicenses() {
        //TODO
        return Flux.just(new ShbLicense());
    }
}
