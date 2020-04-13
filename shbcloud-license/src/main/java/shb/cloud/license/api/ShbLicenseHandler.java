package shb.cloud.license.api;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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

    MultiValueMap queryParams = req.queryParams();
    log.info("### getLicenses");
    log.info("=> key set ::");
    queryParams.keySet().stream().forEach(s -> {
      log.info("{}={}", s, queryParams.get(s));
    });

    Optional<String> optionalUserId = req.queryParam("userId");
    String userId = optionalUserId.orElse("sampleUserId");
    Flux list = (queryParams.isEmpty()) ? shbLicenseService.getLicenses()
        : shbLicenseService.getLicensesByUserId(userId);

    Mono res = ok().body(list, ShbLicense.class);
    return res;
  };

  public HandlerFunction getLicenseById = req -> {

    log.info("### getLicenseById");
    Map pathVariables = req.pathVariables();
    pathVariables.keySet().stream().forEach(s -> {
      log.info("{}={}", s, pathVariables.get(s));
    });

    String licenseId = (String) pathVariables.getOrDefault("licenseId", "sampleLicenseId");

    Mono res = ok().body(shbLicenseService.getLicenseById(licenseId), ShbLicense.class);
    return res;
  };


  public ShbLicenseHandler(ShbLicenseService shbLicenseService) {
    this.shbLicenseService = shbLicenseService;
  }

}
