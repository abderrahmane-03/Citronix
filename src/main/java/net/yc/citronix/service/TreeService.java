package net.yc.citronix.service;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.exceptions.FieldNotFoundException;
import net.yc.citronix.exceptions.TreeNotFoundException;
import net.yc.citronix.mapper.TreeMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.TreeRepository;
import net.yc.citronix.serviceInterface.TreeServiceINF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreeService implements TreeServiceINF {


        private final FieldRepository fieldRepository;

        private final TreeRepository treeRepository;

        private final TreeMapper treeMapper;

        public TreeDTO save(TreeDTO treeDTO) {
                Long fieldId = treeDTO.getField().getId();

                if (fieldId == null) {
                    throw new FieldNotFoundException("Field ID cannot be null.");
                }

                Optional<Field> optionalField = fieldRepository.findById(fieldId);
                if (optionalField.isEmpty()) {
                    throw new IllegalArgumentException("Field not found for ID: " + fieldId);
                }

                Field field = optionalField.get();
                treeDTO.setField(field);

                int maxTreeCount = calculateMaxTreeCount(field.getSize());

                long currentTreeCount = treeRepository.countByFieldId(fieldId);
                if (currentTreeCount >= maxTreeCount) {
                    throw new IllegalArgumentException(
                            "Cannot add more trees to this field. Maximum allowed: " + maxTreeCount +
                                    ", Current: " + currentTreeCount
                    );
                }

                calculateAndSetTreeAge(treeDTO);


                validateTree(treeDTO);

                Tree tree = treeMapper.toEntity(treeDTO);
                Tree savedTree = treeRepository.save(tree);

                return treeMapper.toDTO(savedTree);
            }

    public int calculateMaxTreeCount(double fieldSize) {
        return (int) Math.floor(fieldSize * 100);
    }

    public void calculateAndSetTreeAge(TreeDTO treeDTO) {
        LocalDate plantationDate = treeDTO.getPlantationDate();
        long age = ChronoUnit.YEARS.between(plantationDate, LocalDate.now());

        if (age < 0) {
            throw new IllegalArgumentException("Plantation date cannot be in the future.");
        }
        if (age<20){
            treeDTO.setProductive(true);
        }
        treeDTO.setAge((int) age);
    }
    public void validateTree(TreeDTO treeDTO) {
        LocalDate plantationDate = treeDTO.getPlantationDate();
        Month month = plantationDate.getMonth();

        if (month.getValue() < Month.MARCH.getValue() || month.getValue() > Month.MAY.getValue()) {
            throw new IllegalArgumentException("Tree can only be planted between March and May.");
        }

    }
        public List<TreeDTO> show() {
            return treeRepository.findAll()
                    .stream()
                    .map(treeMapper::toDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public TreeDTO update(Long id, TreeDTO updatedTreeDTO) {
            Tree existingTree = treeRepository.findById(id)
                    .orElseThrow(() -> new TreeNotFoundException("Tree with ID " + id + " not found."));

            Long fieldId = updatedTreeDTO.getField().getId();
            Field field = fieldRepository.findById(fieldId)
                    .orElseThrow(() -> new FieldNotFoundException("Field with ID " + fieldId + " not found."));

            int maxTreeCount = calculateMaxTreeCount(field.getSize());

            long currentTreeCount = treeRepository.countByFieldId(fieldId);

            if (currentTreeCount > maxTreeCount) {
                throw new IllegalArgumentException(
                        "Cannot exceed the maximum allowed trees for this field. Maximum allowed: " + maxTreeCount +
                                ", Current: " + currentTreeCount
                );
            }

            validateTree(updatedTreeDTO);

            calculateAndSetTreeAge(updatedTreeDTO);

            existingTree.setPlantationDate(updatedTreeDTO.getPlantationDate());
            existingTree.setField(field);

            Tree updatedTree = treeRepository.save(existingTree);
            return treeMapper.toDTO(updatedTree);
        }


        public void delete(Long id) {
            Optional<Tree> tree = treeRepository.findById(id);

            if (tree.isPresent()) {
                treeRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Tree with ID " + id + " not found.");
            }
        }



}
