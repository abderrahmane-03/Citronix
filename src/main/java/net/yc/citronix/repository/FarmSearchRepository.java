package net.yc.citronix.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.model.Farm;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<FarmDTO> searchFarms(String name, String location, Double minSize, Double maxSize, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FarmDTO> query = cb.createQuery(FarmDTO.class);
        Root<FarmDTO> farm = query.from(FarmDTO.class);

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
            predicates.add(cb.greaterThanOrEqualTo(farm.get("establishedDate"), startDate));
        }

        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(farm.get("establishedDate"), endDate));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

}
