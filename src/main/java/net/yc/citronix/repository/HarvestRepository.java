package net.yc.citronix.repository;
import net.yc.citronix.model.Harvest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HarvestRepository extends MongoRepository<Harvest,String> {
}
