package net.yc.citronix.service;

import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.mapper.TreeMapper;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TreeService {


        @Autowired
        private TreeRepository treeRepository;

        @Autowired
        private TreeMapper treeMapper;

        // Save Tree using DTO
        public TreeDTO save(TreeDTO treeDTO) {
            Tree tree = treeMapper.toEntity(treeDTO);
            Tree savedTree = treeRepository.save(tree);
            return treeMapper.toDTO(savedTree);
        }

        // Get all Trees and return as DTOs
        public List<TreeDTO> show() {
            return treeRepository.findAll()
                    .stream()
                    .map(treeMapper::toDTO) // Convert each Tree entity to DTO
                    .collect(Collectors.toList());
        }

        // Update Tree using DTO
        public TreeDTO update(UUID id, TreeDTO updatedTreeDTO) {
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
        public void delete(UUID id) {
            Optional<Tree> tree = treeRepository.findById(id);

            if (tree.isPresent()) {
                treeRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Tree with ID " + id + " not found.");
            }
        }

}
