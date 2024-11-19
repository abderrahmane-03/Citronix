package net.yc.citronix.service;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.mapper.FieldMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.repository.FieldRepository;
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
    private FieldMapper fieldMapper;

    // Save Field using DTO
    public FieldDTO save(FieldDTO fieldDTO) {
        Field field = fieldMapper.toEntity(fieldDTO);
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toDTO(savedField);
    }

    // Get all Fields and return as DTOs
    public List<FieldDTO> show() {
        return fieldRepository.findAll()
                .stream()
                .map(fieldMapper::toDTO) // Convert each Field entity to DTO
                .collect(Collectors.toList());
    }

    // Update Field using DTO
    public FieldDTO update(String id, FieldDTO updatedFieldDTO) {
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

    // Delete Field by ID
    public void delete(String id) {
        Optional<Field> field = fieldRepository.findById(id);

        if (field.isPresent()) {
            fieldRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
    }
}