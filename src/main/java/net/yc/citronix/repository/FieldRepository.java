package net.yc.citronix.repository;

import net.yc.citronix.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Long> {
    long countByFarmId(Long farmId);
    Optional<Field> getFieldById(Long fieldId);
}
