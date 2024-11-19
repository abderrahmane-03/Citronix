package net.yc.citronix.repository;

import net.yc.citronix.model.Field;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldRepository extends MongoRepository<Field,String> {
}
