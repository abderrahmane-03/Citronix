package net.yc.citronix.repository;
import net.yc.citronix.model.HarvestDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HarvestDetailRepository extends MongoRepository<HarvestDetail,String> {
}
