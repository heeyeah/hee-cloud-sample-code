package shb.cloud.license.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ShbLicenseRepository extends ReactiveCrudRepository<ShbLicense, String> {

    @Query("select * from sw_license where contractor_id = $1")
    Flux<ShbLicense> findAllByContractorId(String contractorId);
}
