package net.yc.citronix.service;

import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.enums.Season;
import net.yc.citronix.mapper.HarvestMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.HarvestRepository;
import net.yc.citronix.serviceInterface.HarvestServiceINF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HarvestService implements HarvestServiceINF {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private HarvestMapper harvestMapper;
    @Autowired
    private FieldRepository fieldRepository;

    public HarvestDTO generateHarvest(HarvestDTO harvestDTO) {

        Long fieldId = harvestDTO.getFieldId();
        // Fetch trees in the field using the fieldId (Assume `FieldService` provides tree data)
        List<Tree> trees = fieldService.getTreesByFieldId(fieldId);

        // Calculate HarvestDetails
        List<HarvestDetail> harvestDetails = trees.stream()
                .map(tree -> HarvestDetail.builder()
                        .treeId(tree.getId())
                        .quantity(calculateTreeProductivity(tree.getAge()))
                        .build())
                .collect(Collectors.toList());


        // Set HarvestDetails and totalQuantity in the HarvestDTO
        double totalQuantity = harvestDetails.stream().mapToDouble(HarvestDetail::getQuantity).sum();
        harvestDTO.setHarvestDetails(harvestDetails);
        harvestDTO.setTotalQuantity(totalQuantity);
        // Save the harvest
        return save(harvestDTO); // Save method already implemented
    }

    // Helper method to calculate productivity based on age
    public double calculateTreeProductivity(int age) {
        if (age < 3) {
            return 2.5; // Young tree productivity
        } else if (age <= 10) {
            return 12; // Mature tree productivity
        } else if(age <20) {
            return 20; // Old tree productivity
        }
        else {
            return 0; // Old tree productivity
        }
    }
    public Season determineSeason(int month) {
        if (month == 12 || month == 1 || month == 2) {
            return Season.WINTER;
        } else if (month >= 3 && month <= 5) {
            return Season.SPRING;
        } else if (month >= 6 && month <= 8) {
            return Season.SUMMER;
        } else {
            return Season.AUTUMN;
        }
    }
    // Save Harvest using DTO
    public HarvestDTO save(HarvestDTO harvestDTO) {

        Long fieldId = harvestDTO.getFieldId();
        Optional<Field> byId = fieldRepository.findById(fieldId);
        boolean exists = harvestRepository.existsByFieldAndSeason(byId, harvestDTO.getSeason());

        if (harvestDTO.getHarvestDate() != null) {
            harvestDTO.setSeason(determineSeason(harvestDTO.getHarvestDate().getMonthValue()));
        }
        if (exists) {
            throw new IllegalArgumentException("A harvest already exists for this field and season.");
        }
        Harvest harvest = harvestMapper.toEntity(harvestDTO);

        // Set harvest in each harvest detail
        harvest.getHarvestDetails().forEach(detail -> detail.setHarvest(harvest));

        // Save the harvest and its details
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
    public HarvestDTO update(Long id, HarvestDTO updatedHarvestDTO) {
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
    public void delete(Long id) {
        Optional<Harvest> harvest = harvestRepository.findById(id);

        if (harvest.isPresent()) {
            harvestRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Harvest with ID " + id + " not found.");
        }
    }
}
