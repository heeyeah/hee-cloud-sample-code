package shb.cloud.employee.entity;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ShbEmployeeRepository extends ReactiveCrudRepository<ShbEmployee, String> {

  @Query("select * from sw_license where contractor_id = $1")
  Flux<ShbEmployee> findAllByContractorId(String contractorId);
}
