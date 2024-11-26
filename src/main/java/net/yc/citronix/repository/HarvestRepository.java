package net.yc.citronix.repository;
import net.yc.citronix.enums.Season;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    boolean existsByFieldAndSeason(Field field, Season season);

    boolean existsByFieldAndSeasonAndIdNot(Field field, Season season, Long id);
}
