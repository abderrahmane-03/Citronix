package net.yc.citronix.repository;

import net.yc.citronix.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale,String> {
}
