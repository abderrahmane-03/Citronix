package net.yc.citronix.service;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.mapper.FieldMapper;
import net.yc.citronix.model.Farm;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FarmRepository;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private FieldMapper fieldMapper;


    // In FieldService
    public List<Tree> getTreesByFieldId(Long fieldId) {
        return treeRepository.findByFieldId(fieldId);
    }

    public FieldDTO save(FieldDTO fieldDTO) {
        // Fetch the associated farm
        Long farmId = fieldDTO.getFarmId();
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);

        if (optionalFarm.isEmpty()) {
            throw new IllegalArgumentException("Farm not found for ID: " + fieldDTO.getFarmId());
        }

        Farm farm = optionalFarm.get();

        // Validate the number of fields for the farm
        long fieldCount = fieldRepository.countByFarmId(farm.getId());
        if (fieldCount >= 10) {
            throw new IllegalArgumentException(
                    "A farm cannot have more than 10 fields. Current field count: " + fieldCount
            );
        }

        // Validate the 50% size rule
        if (fieldDTO.getSize() > farm.getSize() * 0.5) {
            throw new IllegalArgumentException(
                    "Field size cannot exceed 50% of the total farm size. " +
                            "Farm size: " + farm.getSize() + ", Max allowed: " + (farm.getSize() * 0.5)
            );
        }

        // Map DTO to entity and save
        Field field = fieldMapper.toEntity(fieldDTO);
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toDTO(savedField);
    }



    public List<FieldDTO> show() {
        return fieldRepository.findAll()
                .stream()
                .map(fieldMapper::toDTO)
                .collect(Collectors.toList());
    }


    public FieldDTO update(Long id, FieldDTO updatedFieldDTO) {
        Optional<Field> existingFieldOpt = fieldRepository.findById(id);

        if (existingFieldOpt.isPresent()) {
            Field existingField = existingFieldOpt.get();
            existingField.setSize(updatedFieldDTO.getSize());
            existingField.setTreeCount(updatedFieldDTO.getTreeCount());
            Field updatedField = fieldRepository.save(existingField);
            return fieldMapper.toDTO(updatedField);
        } else {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
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
