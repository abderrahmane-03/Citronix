package net.yc.citronix.serviceInterface;

import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.mapper.FarmMapper;
import net.yc.citronix.model.Farm;
import net.yc.citronix.repository.FarmRepository;
import net.yc.citronix.repository.FarmSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface FarmServiceINF {


     List<FarmDTO> searchFarms(String name, String location, Double minSize, Double maxSize, LocalDate startDate, LocalDate endDate);

     FarmDTO save(FarmDTO farmDTO);

     List<FarmDTO> show();

     FarmDTO update(Long id, FarmDTO updatedFarmDTO);

     void delete(Long id);
}
