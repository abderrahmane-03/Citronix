package net.yc.citronix.service;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.enums.Season;
import net.yc.citronix.mapper.FarmMapper;
import net.yc.citronix.model.Farm;
import net.yc.citronix.repository.FarmRepository;
import net.yc.citronix.repository.searchImplementation.FarmSearchRepository;
import net.yc.citronix.serviceInterface.FarmServiceINF;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmService implements FarmServiceINF {


    private final FarmRepository farmRepository;


    private final FarmSearchRepository FarmSearchRepository;


    private final FarmMapper farmMapper;

    public List<FarmDTO> searchFarms(String name, String location, Double minSize, Double maxSize, LocalDate startDate, LocalDate endDate) {
        return FarmSearchRepository.searchFarms(name, location, minSize, maxSize, startDate, endDate);
    }

    public FarmDTO save(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }

    public List<FarmDTO> show() {
        return farmRepository.findAll()
                .stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FarmDTO update(Long id, FarmDTO updatedFarmDTO) {
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

    public void delete(Long id) {
        Optional<Farm> farm = farmRepository.findById(id);

        if (farm.isPresent()) {
            farmRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Farm with ID " + id + " not found.");
        }
    }
}
