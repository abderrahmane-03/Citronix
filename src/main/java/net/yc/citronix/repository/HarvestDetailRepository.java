package net.yc.citronix.repository;
import net.yc.citronix.model.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
}
