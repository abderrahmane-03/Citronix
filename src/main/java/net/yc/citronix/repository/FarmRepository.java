package net.yc.citronix.repository;

import net.yc.citronix.model.Farm;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FarmRepository extends MongoRepository<Farm, String> {
}
