package net.yc.citronix.service;

import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.mapper.TreeMapper;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreeService {


        @Autowired
        private TreeRepository treeRepository;

        @Autowired
        private TreeMapper treeMapper;

        // Save Tree using DTO
        public TreeDTO save(TreeDTO treeDTO) {
            calculateAndSetTreeAge(treeDTO);

            // Validate the tree
            validateTree(treeDTO);

            // Map DTO to entity and save
            Tree tree = treeMapper.toEntity(treeDTO);
            Tree savedTree = treeRepository.save(tree);

            // Return the saved entity as a DTO
            return treeMapper.toDTO(savedTree);
        }
    private void calculateAndSetTreeAge(TreeDTO treeDTO) {
        LocalDate plantationDate = treeDTO.getPlantationDate();
        long age = ChronoUnit.YEARS.between(plantationDate, LocalDate.now());

        // Ensure age is always positive
        if (age < 0) {
            throw new IllegalArgumentException("Plantation date cannot be in the future.");
        }
        if (age<20){
            treeDTO.setProductive(true);
        }
        treeDTO.setAge((int) age); // Assuming age is an integer
    }
    private void validateTree(TreeDTO treeDTO) {
        LocalDate plantationDate = treeDTO.getPlantationDate();
        Month month = plantationDate.getMonth();

        // Validate if plantation date is between March and May
        if (month.getValue() < Month.MARCH.getValue() || month.getValue() > Month.MAY.getValue()) {
            throw new IllegalArgumentException("Tree can only be planted between March and May.");
        }

    }
        // Get all Trees and return as DTOs
        public List<TreeDTO> show() {
            return treeRepository.findAll()
                    .stream()
                    .map(treeMapper::toDTO) // Convert each Tree entity to DTO
                    .collect(Collectors.toList());
        }

        // Update Tree using DTO
        public TreeDTO update(Long id, TreeDTO updatedTreeDTO) {
            Optional<Tree> existingTreeOpt = treeRepository.findById(id);

            if (existingTreeOpt.isPresent()) {
                Tree existingTree = existingTreeOpt.get();
                existingTree.setPlantationDate(updatedTreeDTO.getPlantationDate());
                existingTree.setAge(updatedTreeDTO.getAge());
                existingTree.setFieldId(updatedTreeDTO.getFieldId());
                Tree updatedTree = treeRepository.save(existingTree);
                return treeMapper.toDTO(updatedTree);
            } else {
                throw new IllegalArgumentException("Tree with ID " + id + " not found.");
            }
        }

        // Delete Tree by ID
        public void delete(Long id) {
            Optional<Tree> tree = treeRepository.findById(id);

            if (tree.isPresent()) {
                treeRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Tree with ID " + id + " not found.");
            }
        }



}
