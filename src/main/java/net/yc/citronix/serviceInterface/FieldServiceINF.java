package net.yc.citronix.serviceInterface;

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


public interface FieldServiceINF {

     List<Tree> getTreesByFieldId(Long fieldId);

     FieldDTO save(FieldDTO fieldDTO);

     int calculateMaxTreeCount(double fieldSize);

     List<FieldDTO> show();

     FieldDTO update(Long id, FieldDTO updatedFieldDTO);

     void delete(Long id);
}
