package net.yc.citronix.service;

import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.mapper.HarvestDetailMapper;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.repository.HarvestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HarvestDetailService {

    @Autowired
    private HarvestDetailRepository harvestDetailRepository;

    @Autowired
    private HarvestDetailMapper harvestDetailMapper;

    // Save HarvestDetail using DTO
    public HarvestDetailDTO save(HarvestDetailDTO harvestDetailDTO) {
        HarvestDetail harvestDetail = harvestDetailMapper.toEntity(harvestDetailDTO);
        HarvestDetail savedHarvestDetail = harvestDetailRepository.save(harvestDetail);
        return harvestDetailMapper.toDTO(savedHarvestDetail);
    }

    // Get all HarvestDetails and return as DTOs
    public List<HarvestDetailDTO> show() {
        return harvestDetailRepository.findAll()
                .stream()
                .map(harvestDetailMapper::toDTO) // Convert each HarvestDetail entity to DTO
                .collect(Collectors.toList());
    }

    // Update HarvestDetail using DTO
    public HarvestDetailDTO update(Long id, HarvestDetailDTO updatedHarvestDetailDTO) {
        Optional<HarvestDetail> existingHarvestDetailOpt = harvestDetailRepository.findById(id);

        if (existingHarvestDetailOpt.isPresent()) {
            HarvestDetail existingHarvestDetail = existingHarvestDetailOpt.get();
            existingHarvestDetail.setTreeId(updatedHarvestDetailDTO.getTreeId());
            existingHarvestDetail.setQuantity(updatedHarvestDetailDTO.getQuantity());
            HarvestDetail updatedHarvestDetail = harvestDetailRepository.save(existingHarvestDetail);
            return harvestDetailMapper.toDTO(updatedHarvestDetail);
        } else {
            throw new IllegalArgumentException("HarvestDetail with ID " + id + " not found.");
        }
    }

    // Delete HarvestDetail by ID
    public void delete(Long id) {
        Optional<HarvestDetail> harvestDetail = harvestDetailRepository.findById(id);

        if (harvestDetail.isPresent()) {
            harvestDetailRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("HarvestDetail with ID " + id + " not found.");
        }
    }
}
