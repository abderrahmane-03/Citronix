package net.yc.citronix.serviceInterface;

import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.mapper.TreeMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface TreeServiceINF {

         TreeDTO save(TreeDTO treeDTO);

         int calculateMaxTreeCount(double fieldSize);

         void calculateAndSetTreeAge(TreeDTO treeDTO);

         void validateTree(TreeDTO treeDTO);

         List<TreeDTO> show();

         TreeDTO update(Long id, TreeDTO updatedTreeDTO);

         void delete(Long id);

}
