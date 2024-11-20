package net.yc.citronix.repository;
import net.yc.citronix.model.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
}
