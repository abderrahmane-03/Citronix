package net.yc.citronix.repository;

import net.yc.citronix.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByFieldId(Long aLong);
}
