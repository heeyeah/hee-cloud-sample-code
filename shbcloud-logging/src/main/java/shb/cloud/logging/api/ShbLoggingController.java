package shb.cloud.logging.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shb.cloud.logging.entity.LogFormat;
import shb.cloud.logging.entity.ShbLoggingRepository;

@Slf4j
@RestController
public class ShbLoggingController {

  @Autowired private ShbLoggingRepository shbLoggingRepository;

  @PostMapping("/log")
  public Mono<LogFormat> logOnMongoDB(
      @RequestHeader HttpHeaders headers, @RequestBody LogFormat logFormat) {

    headers.keySet().stream().forEach(e -> log.info("header==> {}={}", e, headers.getFirst(e)));

    String userAgent =
        headers.getFirst("User-Agent");

    logFormat.setLogClient(userAgent);
    logFormat.setLogDateTime(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    log.info("log Format : {}", logFormat);

    return shbLoggingRepository
        .save(logFormat)
        .doOnError(
            error -> {
              log.error("error! {}", error.getMessage());
            })
        .doOnSuccess(
            result -> {
              log.info(" success ! ");
            }
        );

    //      return ResponseEntity.ok().body("End");
  }

  @GetMapping("/logs")
  public Flux<LogFormat> getLogs() {
    return shbLoggingRepository.findAll();
  }

  @GetMapping("/log/{logId}")
  public Mono<LogFormat> getLog(@PathVariable String logId) {
    return shbLoggingRepository.findById(logId);
  }
}
