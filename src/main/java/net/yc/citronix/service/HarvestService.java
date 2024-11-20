package net.yc.citronix.service;

import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.mapper.HarvestMapper;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.repository.HarvestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HarvestService {

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private HarvestMapper harvestMapper;

    // Save Harvest using DTO
    public HarvestDTO save(HarvestDTO harvestDTO) {
        Harvest harvest = harvestMapper.toEntity(harvestDTO);
        Harvest savedHarvest = harvestRepository.save(harvest);
        return harvestMapper.toDTO(savedHarvest);
    }

    // Get all Harvests and return as DTOs
    public List<HarvestDTO> show() {
        return harvestRepository.findAll()
                .stream()
                .map(harvestMapper::toDTO) // Convert each Harvest entity to DTO
                .collect(Collectors.toList());
    }

    // Update Harvest using DTO
    public HarvestDTO update(UUID id, HarvestDTO updatedHarvestDTO) {
        Optional<Harvest> existingHarvestOpt = harvestRepository.findById(id);

        if (existingHarvestOpt.isPresent()) {
            Harvest existingHarvest = existingHarvestOpt.get();
            existingHarvest.setSeason(updatedHarvestDTO.getSeason());
            existingHarvest.setHarvestDate(updatedHarvestDTO.getHarvestDate());
            existingHarvest.setTotalQuantity(updatedHarvestDTO.getTotalQuantity());
            existingHarvest.setHarvestDetails(updatedHarvestDTO.getHarvestDetails());
            Harvest updatedHarvest = harvestRepository.save(existingHarvest);
            return harvestMapper.toDTO(updatedHarvest);
        } else {
            throw new IllegalArgumentException("Harvest with ID " + id + " not found.");
        }
    }

    // Delete Harvest by ID
    public void delete(UUID id) {
        Optional<Harvest> harvest = harvestRepository.findById(id);

        if (harvest.isPresent()) {
            harvestRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Harvest with ID " + id + " not found.");
        }
    }
}
