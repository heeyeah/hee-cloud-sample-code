package shb.cloud.license.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
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

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

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
          // .defaultUriVariables(Collections.singletonMap("url","http://localhost:9000"))
          .build();

  @Autowired private ShbLicenseRepository shbLicenseRepository;

  public Flux<ShbLicense> getLicenses() {
    return shbLicenseRepository.findAll();
  }

  public Flux<ShbTotalEntity> getLicensesByContractorId(String contractorId) {

    WebClient.RequestHeadersSpec headersSpec =
        webClient.get().uri(uriBuilder -> uriBuilder.pathSegment("employee", contractorId).build());

    Mono employeeInfo = headersSpec.retrieve().bodyToMono(Map.class);

      Flux<ShbTotalEntity> list = shbLicenseRepository
              .findAllByContractorId(contractorId)
              .map(
                      lf -> {
                          ShbTotalEntity totalLf = new ShbTotalEntity(lf);
                          return totalLf;
                      });

      employeeInfo.subscribe(
              employee -> {
                  ObjectMapper mapper = new ObjectMapper();
                  Map<String, String> employeeMap = mapper.convertValue(employee, Map.class);
                  String name = employeeMap.get("employeeName");
                  String num = employeeMap.get("contactNumber");

                  list.map(e -> {
                      e.setContractorName(name);
                      e.setContractorContactNumber(num);
                      return e;
                  });
              });

      return list;

  }

  public Mono<ShbLicense> getLicenseById(String licenseId) {
    return shbLicenseRepository.findById(licenseId);
  }
}
