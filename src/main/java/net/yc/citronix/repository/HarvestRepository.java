package net.yc.citronix.repository;
import net.yc.citronix.enums.Season;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    boolean existsByFieldAndSeason(Optional<Field> field, Season season);
}
