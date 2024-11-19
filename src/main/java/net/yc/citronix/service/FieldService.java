package net.yc.citronix.service;

import net.yc.citronix.model.Field;
import net.yc.citronix.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    public Field save(Field field){
        return fieldRepository.save(field);
    }
    public List<Field> show(){
        return fieldRepository.findAll();
    }
    public Field update(String id, Field updatedField) {
        Optional<Field> existingFieldOpt = fieldRepository.findById(id);

        if (existingFieldOpt.isPresent()) {
            Field existingField = existingFieldOpt.get();
            existingField.setSize(updatedField.getSize());
            existingField.setTreeCount(updatedField.getTreeCount());
            return fieldRepository.save(existingField);
        } else {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
    }
    public void delete(String id) {
        Optional<Field> field = fieldRepository.findById(id);

        if (field.isPresent()) {
            fieldRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Field with ID " + id + " not found.");
        }
    }


}
