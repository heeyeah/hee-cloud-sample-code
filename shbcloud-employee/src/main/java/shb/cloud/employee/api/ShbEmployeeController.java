package shb.cloud.employee.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import shb.cloud.employee.entity.ShbEmployee;
import shb.cloud.employee.entity.ShbEmployeeRepository;

@Slf4j
@RestController
public class ShbEmployeeController {

  @Autowired private ShbEmployeeRepository shbEmployeeRepository;

  @GetMapping("/employee/{employeeId}")
  public Mono<ShbEmployee> getEmployeeById(@PathVariable String employeeId) {

    log.info("getEmployeeById : {}", employeeId);

    Mono<ShbEmployee> employeeInfo = this.shbEmployeeRepository.findById(employeeId);

    return employeeInfo;
  }
}
