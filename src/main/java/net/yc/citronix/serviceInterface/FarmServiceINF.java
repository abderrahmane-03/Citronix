package net.yc.citronix.serviceInterface;

import net.yc.citronix.DTO.FarmDTO;

import java.time.LocalDate;
import java.util.List;


public interface FarmServiceINF {


     List<FarmDTO> searchFarms(String name, String location, Double minSize, Double maxSize, LocalDate startDate, LocalDate endDate);

     FarmDTO save(FarmDTO farmDTO);

     List<FarmDTO> show();

     FarmDTO update(Long id, FarmDTO updatedFarmDTO);

     void delete(Long id);
}
