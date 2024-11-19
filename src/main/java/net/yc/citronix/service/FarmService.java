package net.yc.citronix.service;

import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.mapper.FarmMapper;
import net.yc.citronix.model.Farm;
import net.yc.citronix.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmMapper farmMapper;

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
    public FarmDTO update(String id, FarmDTO updatedFarmDTO) {
        Optional<Farm> existingFarmOpt = farmRepository.findById(id);

        if (existingFarmOpt.isPresent()) {
            Farm existingFarm = existingFarmOpt.get();
            existingFarm.setName(updatedFarmDTO.getName());
            existingFarm.setLocation(updatedFarmDTO.getLocation());
            existingFarm.setSize(updatedFarmDTO.getSize());
            existingFarm.setCreationDate(LocalDate.parse(updatedFarmDTO.getCreationDate()));
            Farm updatedFarm = farmRepository.save(existingFarm);
            return farmMapper.toDTO(updatedFarm);
        } else {
            throw new IllegalArgumentException("Farm with ID " + id + " not found.");
        }
    }

    // Delete Farm by ID
    public void delete(String id) {
        Optional<Farm> farm = farmRepository.findById(id);

        if (farm.isPresent()) {
            farmRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Farm with ID " + id + " not found.");
        }
    }
}
