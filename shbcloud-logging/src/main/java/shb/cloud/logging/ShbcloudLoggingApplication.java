package shb.cloud.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
public class ShbcloudLoggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShbcloudLoggingApplication.class, args);
	}

}
