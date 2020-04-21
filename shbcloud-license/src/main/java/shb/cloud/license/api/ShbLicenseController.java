package shb.cloud.license.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.service.ShbLicenseService;

import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@RestController
public class ShbLicenseController {
  @Autowired private ShbLicenseService shbLicenseService;

  @GetMapping("/licenses/{licenseId}")
  public Mono<ShbLicense> getLicenseById(@PathVariable String licenseId) {

    log.info(" restcontroller = [getLicenseById] start");
    log.info("licenseId={}", licenseId);
    return shbLicenseService.getLicenseById(licenseId);
  }
}
