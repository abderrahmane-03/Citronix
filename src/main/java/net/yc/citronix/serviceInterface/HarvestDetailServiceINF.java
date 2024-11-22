package net.yc.citronix.serviceInterface;

import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.mapper.HarvestDetailMapper;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.repository.HarvestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface HarvestDetailServiceINF {

     HarvestDetailDTO save(HarvestDetailDTO harvestDetailDTO);

     List<HarvestDetailDTO> show();

     HarvestDetailDTO update(Long id, HarvestDetailDTO updatedHarvestDetailDTO);

     void delete(Long id);
}
