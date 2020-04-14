package shb.cloud.license.service;

import io.r2dbc.spi.Connection;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shb.cloud.license.config.ShbPostgreConfig;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.entity.ShbLicenseRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ShbLicenseService {

    @Autowired
    private ShbLicenseRepository shbLicenseRepository;

    public Flux<ShbLicense> getLicenses() {

        Flux<ShbLicense> list = shbLicenseRepository.findAll();


        return list;
    }

    public Flux<ShbLicense> getLicensesByUserId(String userId) {

        log.info("getLicensesByUserId : {}", userId);
        Flux<ShbLicense> list = shbLicenseRepository.findAllByContractorId(userId);

        return list;
    }


    public Mono<ShbLicense> getLicenseById(String licenseId) {
        return shbLicenseRepository.findById(licenseId);
    }

}
