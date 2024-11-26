package net.yc.citronix.repository;
import net.yc.citronix.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
   }
