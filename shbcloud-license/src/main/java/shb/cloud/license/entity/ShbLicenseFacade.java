package shb.cloud.license.entity;

import org.springframework.context.annotation.Description;
import reactor.core.publisher.Flux;

@Deprecated
@Description("only example for using facade pattern")
public interface ShbLicenseFacade {
    Flux<ShbLicense> getLicensesByUserId(String contractorId);
}
