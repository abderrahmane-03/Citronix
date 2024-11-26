package net.yc.citronix.repository.searchImplementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.mapper.FarmMapper;
import net.yc.citronix.model.Farm;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final FarmMapper farmMapper;

    public FarmSearchRepository(FarmMapper farmMapper) {
        this.farmMapper = farmMapper;
    }
    public List<FarmDTO> searchFarms(String name, String location, Double minSize, Double maxSize, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> farm = query.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(farm.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (location != null && !location.isEmpty()) {
            predicates.add(cb.like(cb.lower(farm.get("location")), "%" + location.toLowerCase() + "%"));
        }

        if (minSize != null) {
            predicates.add(cb.greaterThanOrEqualTo(farm.get("size"), minSize));
        }

        if (maxSize != null) {
            predicates.add(cb.lessThanOrEqualTo(farm.get("size"), maxSize));
        }

        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(farm.get("creationDate"), startDate));
        }

        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(farm.get("creationDate"), endDate));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Farm> farms = entityManager.createQuery(query).getResultList();

        return farmMapper.toDTOs(farms);
    }
}
