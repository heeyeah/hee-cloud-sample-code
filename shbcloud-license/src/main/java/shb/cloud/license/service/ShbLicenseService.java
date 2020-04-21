package shb.cloud.license.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import shb.cloud.license.entity.ShbLicense;
import shb.cloud.license.entity.ShbLicenseRepository;
import shb.cloud.license.entity.ShbTotalEntity;

@Slf4j
@Component
public class ShbLicenseService {

  static TcpClient tcpClient =
      TcpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
          .doOnDisconnected(
              connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
              });

  static WebClient webClient =
      WebClient.builder()
          .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
          .baseUrl("http://localhost:9000")
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .build();

  @Autowired private ShbLicenseRepository shbLicenseRepository;

  /**
   * Another Container Response 값 converted to Map!
   *
   * @param obj
   * @return
   */
  public static Map<String, String> convertResToMap(Object obj) {
    return new ObjectMapper().convertValue(obj, Map.class);
  }

  /**
   * R2DBC 사용 (단순조회)
   *
   * @return
   */
  public Flux<ShbLicense> getLicenses() {
    return shbLicenseRepository.findAll();
  }

  /**
   * R2DBC 사용 + Another Container Call (WebClient 사용)
   *
   * @param contractorId
   * @return
   */
  public Flux<ShbTotalEntity> getLicensesByContractorId(String contractorId) {

    WebClient.RequestHeadersSpec headersSpec =
        webClient.get().uri(uriBuilder -> uriBuilder.pathSegment("employee", contractorId).build());

    Mono employeeInfo = headersSpec.retrieve().bodyToMono(Map.class);
    /* 이건 sync call일 때, async로 만들기 위한 것이라 한다. jdbc sync call과 같은 경우를 말함.
        Mono.fromCallable(() -> headersSpec.retrieve().bodyToMono(Map.class))
            .subscribeOn(Schedulers.elastic());
    */

    log.info(" #1 ");

    Flux<ShbTotalEntity> list =
        shbLicenseRepository
            .findAllByContractorId(contractorId)
            .map(lf -> new ShbTotalEntity(lf))
            .flatMap(
                tlf ->
                    employeeInfo.map(
                        emply -> {
                          Map<String, String> map = convertResToMap(emply);
                          tlf.setContractorName(map.get("employeeName"));
                          tlf.setContractorContactNumber(map.get("employeeContactNumber"));
                          return tlf;
                        }));

    return list;
  }

  /**
   * 망한 코드. 이렇게 비동기 개발에 적응하기가 쉽지 않습니다...:p
   *
   * @param contractorId
   * @return
   */
  public Flux<ShbTotalEntity> __1_foolishCode(String contractorId) {

    WebClient.RequestHeadersSpec headersSpec =
        webClient.get().uri(uriBuilder -> uriBuilder.pathSegment("employee", contractorId).build());

    Mono employeeInfo = headersSpec.retrieve().bodyToMono(Map.class);

    log.info(" #1 ");

    Flux<ShbTotalEntity> list =
        shbLicenseRepository
            .findAllByContractorId(contractorId)
            .map(
                lf -> {
                  log.info(" #2 : {}", lf.toString());
                  ShbTotalEntity totalLf = new ShbTotalEntity(lf);
                  return totalLf;
                });

    log.info(" #3 ");
    employeeInfo.subscribe(
        employee -> {
          log.info(" #4 ");
          Map<String, String> employeeMap = convertResToMap(employee);
          String name = employeeMap.get("employeeName");
          String num = employeeMap.get("contactNumber");
          log.info("name : {} , num : {}", name, num);
          list.subscribe(
              e -> {
                log.info(" #5 ");
                e.setContractorName(name);
                e.setContractorContactNumber(num);
              });
        });
    log.info(" #6 ");
    return list;
  }

  public Mono<ShbLicense> getLicenseById(String licenseId) {
    return shbLicenseRepository.findById(licenseId);
  }
}
