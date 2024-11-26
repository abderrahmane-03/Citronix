package net.yc.citronix.service;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.enums.Season;
import net.yc.citronix.mapper.HarvestDetailMapper;
import net.yc.citronix.mapper.HarvestMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.HarvestRepository;
import net.yc.citronix.serviceInterface.FieldServiceINF;
import net.yc.citronix.serviceInterface.HarvestServiceINF;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestService implements HarvestServiceINF {

    private final FieldServiceINF fieldService;

    private final HarvestRepository harvestRepository;

    private final HarvestMapper harvestMapper;

    private final HarvestDetailMapper harvestDetailMapper;


    private final FieldRepository fieldRepository;

    public HarvestDTO generateHarvest(HarvestDTO harvestDTO) {

        Long fieldId = harvestDTO.getField().getId();
        List<Tree> trees = fieldService.getTreesByFieldId(fieldId);

        List<HarvestDetailDTO> harvestDetails = trees.stream()
                .map(tree -> HarvestDetailDTO.builder()
                        .treeId(tree.getId())
                        .quantity(calculateTreeProductivity(tree.getAge()))
                        .build())
                .collect(Collectors.toList());


        double totalQuantity = harvestDetails.stream().mapToDouble(HarvestDetailDTO::getQuantity).sum();
        harvestDTO.setHarvestDetails(harvestDetails);
        harvestDTO.setTotalQuantity(totalQuantity);
        return save(harvestDTO);
    }

    public double calculateTreeProductivity(int age) {
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        } else if(age <20) {
            return 20;
        }
        else {
            return 0;
        }
    }
    public Season determineSeason(int month) {
        switch (month) {
            case 11: case 12: case 1: case 2:
                return Season.WINTER;
            case 3: case 4: case 5:
                return Season.SPRING;
            case 6: case 7: case 8:
                return Season.SUMMER;
            case 9: case 10:
                return Season.AUTUMN;
            default:
                throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    public HarvestDTO save(HarvestDTO harvestDTO) {
        Long fieldId = harvestDTO.getField().getId();
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field with ID " + fieldId + " not found."));

        boolean exists = harvestRepository.existsByFieldAndSeason(field, harvestDTO.getSeason());
        if (exists) {
            throw new IllegalArgumentException("A harvest already exists for this field and season.");
        }

        if (harvestDTO.getHarvestDate() != null) {
            harvestDTO.setSeason(determineSeason(harvestDTO.getHarvestDate().getMonthValue()));
        }

        Harvest harvest = harvestMapper.toEntity(harvestDTO);

        harvest.setField(field);

        if (harvest.getHarvestDetails() != null) {
            harvest.getHarvestDetails().forEach(detail -> detail.setHarvest(harvest));
        }

        Harvest savedHarvest = harvestRepository.save(harvest);
        return harvestMapper.toDTO(savedHarvest);
    }




    public List<HarvestDTO> show() {
        return harvestRepository.findAll()
                .stream()
                .map(harvestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HarvestDTO update(Long id, HarvestDTO updatedHarvestDTO) {
        Harvest existingHarvest = harvestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Harvest with ID " + id + " not found."));

        Long fieldId = updatedHarvestDTO.getField().getId();
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field with ID " + fieldId + " not found."));

        boolean exists = harvestRepository.existsByFieldAndSeasonAndIdNot(field, updatedHarvestDTO.getSeason(), id);
        if (exists) {
            throw new IllegalArgumentException("A harvest already exists for this field and season.");
        }

        if (updatedHarvestDTO.getHarvestDate() != null) {
            updatedHarvestDTO.setSeason(determineSeason(updatedHarvestDTO.getHarvestDate().getMonthValue()));
        }

        existingHarvest.setSeason(updatedHarvestDTO.getSeason());
        existingHarvest.setHarvestDate(updatedHarvestDTO.getHarvestDate());
        existingHarvest.setTotalQuantity(updatedHarvestDTO.getTotalQuantity());

        List<HarvestDetail> harvestDetails = harvestDetailMapper.toEntities(updatedHarvestDTO.getHarvestDetails());
        harvestDetails.forEach(detail -> detail.setHarvest(existingHarvest));
        existingHarvest.setHarvestDetails(harvestDetails);

        existingHarvest.setField(field);

        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toDTO(updatedHarvest);
    }

    public void delete(Long id) {
        Optional<Harvest> harvest = harvestRepository.findById(id);

        if (harvest.isPresent()) {
            harvestRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Harvest with ID " + id + " not found.");
        }
    }
}
