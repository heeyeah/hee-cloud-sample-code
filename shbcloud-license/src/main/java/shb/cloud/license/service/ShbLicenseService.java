package shb.cloud.license.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.entity.ShbLicenseRepository;

@Component
public class ShbLicenseService {

  @Autowired(required = true)
  private ShbLicenseRepository shbLicenseRepository;

  public Flux<ShbLicense> getLicenses() {

    Flux<ShbLicense> list = shbLicenseRepository.findAll();

    return list;
  }

  public Flux<ShbLicense> getLicensesByUserId(String userId) {
    //todo

    Flux<ShbLicense> list = Flux.empty();
    return list;
  }

  public Mono<ShbLicense> getLicenseById(String licenseId) {
      return shbLicenseRepository.findById(licenseId);
  }

}
