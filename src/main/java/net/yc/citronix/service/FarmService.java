package net.yc.citronix.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.mapper.FarmMapper;
import net.yc.citronix.model.Farm;
import net.yc.citronix.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmMapper farmMapper;



    @Autowired
    private EntityManager entityManager;

    public List<Farm> searchFarms(String name, String location, Double minSize, Double maxSize, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> farm = query.from(Farm.class);

        Predicate predicate = cb.conjunction();

        if (name != null && !name.isEmpty()) {
            predicate = cb.and(predicate, cb.like(cb.lower(farm.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (location != null && !location.isEmpty()) {
            predicate = cb.and(predicate, cb.like(cb.lower(farm.get("location")), "%" + location.toLowerCase() + "%"));
        }
        if (minSize != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(farm.get("size"), minSize));
        }
        if (maxSize != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(farm.get("size"), maxSize));
        }
        if (startDate != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(farm.get("creationDate"), startDate));
        }
        if (endDate != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(farm.get("creationDate"), endDate));
        }

        query.where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
    // Save Farm using DTO
    public FarmDTO save(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }

    // Get all Farms and return as DTOs
    public List<FarmDTO> show() {
        return farmRepository.findAll()
                .stream()
                .map(farmMapper::toDTO) // Convert each Farm entity to DTO
                .collect(Collectors.toList());
    }

    // Update Farm using DTO
    public FarmDTO update(UUID id, FarmDTO updatedFarmDTO) {
        Optional<Farm> existingFarmOpt = farmRepository.findById(id);

        if (existingFarmOpt.isPresent()) {
            Farm existingFarm = existingFarmOpt.get();
            existingFarm.setName(updatedFarmDTO.getName());
            existingFarm.setLocation(updatedFarmDTO.getLocation());
            existingFarm.setSize(updatedFarmDTO.getSize());
            existingFarm.setCreationDate(updatedFarmDTO.getCreationDate());
            Farm updatedFarm = farmRepository.save(existingFarm);
            return farmMapper.toDTO(updatedFarm);
        } else {
            throw new IllegalArgumentException("Farm with ID " + id + " not found.");
        }
    }

    // Delete Farm by ID
    public void delete(UUID id) {
        Optional<Farm> farm = farmRepository.findById(id);

        if (farm.isPresent()) {
            farmRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Farm with ID " + id + " not found.");
        }
    }
}
