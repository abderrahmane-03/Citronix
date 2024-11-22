package net.yc.citronix.serviceInterface;

import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.enums.Season;
import net.yc.citronix.mapper.HarvestMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.HarvestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface HarvestServiceINF {

     HarvestDTO generateHarvest(HarvestDTO harvestDTO);

     double calculateTreeProductivity(int age);

     Season determineSeason(int month);

     HarvestDTO save(HarvestDTO harvestDTO);

     List<HarvestDTO> show();

     HarvestDTO update(Long id, HarvestDTO updatedHarvestDTO);

     void delete(Long id);
}
