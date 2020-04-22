package shb.cloud.logging.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import shb.cloud.logging.entity.LogFormat;

public interface ShbLoggingRepository extends ReactiveMongoRepository<LogFormat, String> {
}
