package net.yc.citronix.service;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.mapper.FieldMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldMapper fieldMapper;


    public FieldDTO save(FieldDTO fieldDTO) {
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


    public FieldDTO update(UUID id, FieldDTO updatedFieldDTO) {
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


    public void delete(UUID id) {
        Optional<Field> field = fieldRepository.findById(id);

        if (field.isPresent()) {
            fieldRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
    }
}
