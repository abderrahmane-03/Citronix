package net.yc.citronix.service;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.mapper.FieldMapper;
import net.yc.citronix.model.Farm;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FarmRepository;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.TreeRepository;
import net.yc.citronix.serviceInterface.FieldServiceINF;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldService implements FieldServiceINF {

    private final FieldRepository fieldRepository;

    private final FarmRepository farmRepository;

    private final TreeRepository treeRepository;

    private final FieldMapper fieldMapper;


    public List<Tree> getTreesByFieldId(Long fieldId) {
        return treeRepository.findByFieldId(fieldId);
    }

    public FieldDTO save(FieldDTO fieldDTO) {

        Long farmId = fieldDTO.getFarm().getId();
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);

        if (optionalFarm.isEmpty()) {
            throw new IllegalArgumentException("Farm not found for ID: " + fieldDTO.getFarm().getId());
        }

        Farm farm = optionalFarm.get();


        long fieldCount = fieldRepository.countByFarmId(farm.getId());
        if (fieldCount >= 10) {
            throw new IllegalArgumentException(
                    "A farm cannot have more than 10 fields. Current field count: " + fieldCount
            );
        }


        if (fieldDTO.getSize() > farm.getSize() * 0.5) {
            throw new IllegalArgumentException(
                    "Field size cannot exceed 50% of the total farm size. " +
                            "Farm size: " + farm.getSize() + ", Max allowed: " + (farm.getSize() * 0.5)
            );
        }


        int maxTreeCount = calculateMaxTreeCount(fieldDTO.getSize());
        fieldDTO.setTreeCount(maxTreeCount);

        // Map DTO to entity and save
        Field field = fieldMapper.toEntity(fieldDTO);
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toDTO(savedField);
    }

    public int calculateMaxTreeCount(double fieldSize) {
        return (int) Math.floor(fieldSize * 100);
    }


    public List<FieldDTO> show() {
        return fieldRepository.findAll()
                .stream()
                .map(fieldMapper::toDTO)
                .collect(Collectors.toList());
    }


    public FieldDTO update(Long id, FieldDTO updatedFieldDTO) {
        // Retrieve the existing field
        Optional<Field> existingFieldOpt = fieldRepository.findById(id);
        if (existingFieldOpt.isEmpty()) {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
        Field existingField = existingFieldOpt.get();


        Long farmId = updatedFieldDTO.getFarm().getId();
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        if (optionalFarm.isEmpty()) {
            throw new IllegalArgumentException("Farm not found for ID: " + updatedFieldDTO.getFarm().getId());
        }
        Farm farm = optionalFarm.get();


        long fieldCount = fieldRepository.countByFarmId(farm.getId());
        if (fieldCount > 10) {
            throw new IllegalArgumentException(
                    "A farm cannot have more than 10 fields. Current field count: " + fieldCount
            );
        }

        if (updatedFieldDTO.getSize() > farm.getSize() * 0.5) {
            throw new IllegalArgumentException(
                    "Field size cannot exceed 50% of the total farm size. " +
                            "Farm size: " + farm.getSize() + ", Max allowed: " + (farm.getSize() * 0.5)
            );
        }

        int maxTreeCount = calculateMaxTreeCount(updatedFieldDTO.getSize());
        updatedFieldDTO.setTreeCount(maxTreeCount);

        existingField.setSize(updatedFieldDTO.getSize());
        existingField.setTreeCount(updatedFieldDTO.getTreeCount());
        existingField.setFarm(farm);

        Field updatedField = fieldRepository.save(existingField);
        return fieldMapper.toDTO(updatedField);
    }


    public void delete(Long id) {
        Optional<Field> field = fieldRepository.findById(id);

        if (field.isPresent()) {
            fieldRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
    }
}
