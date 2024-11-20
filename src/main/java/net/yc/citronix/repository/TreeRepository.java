package net.yc.citronix.repository;

import net.yc.citronix.model.Tree;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TreeRepository extends MongoRepository<Tree,String> {
}
